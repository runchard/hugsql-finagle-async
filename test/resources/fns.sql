--:name select-character-star :? :1
SELECT * FROM characters WHERE id = :id;

-- :name connection-check
-- :command :execute
-- :doc insert character
SELECT 1;

-- :name insert-character
-- :command :execute
-- :doc insert character
INSERT INTO `characters` (`name`, `weight`) VALUES(:name, :weight);

-- :name create-characters-table
-- :command :execute
-- :result :raw
-- :doc Create characters table
--  auto_increment and current_timestamp are
--  H2 Database specific (adjust to your DB)
CREATE TABLE characters (
  id         integer auto_increment PRIMARY KEY,
  name       varchar(40),
  weight     integer,
  created_at timestamp not null default current_timestamp,
  UNIQUE KEY uniq_weight (weight)
);

-- :name select-character-by-weight
-- :command :query
-- :result :one
-- :doc query characters by id
SELECT `id`, `name`, `weight`, `created_at`
  FROM :i:table-name
  WHERE `weight` = :weight
  ORDER BY created_at;

-- :name raw-query-character-less-than
-- :command :query
-- :result :raw
-- :doc query characters before id
SELECT `id`, `name`, `weight`, `created_at`
  FROM characters
  WHERE `weight` < :weight
  ORDER BY created_at DESC;


-- :name drop-characters-table :!
-- :doc Drop characters table if exists
drop table if exists characters;
