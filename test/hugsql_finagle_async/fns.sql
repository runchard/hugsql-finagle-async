--:name value-param :? :*
select * from characters where id = :id

-- :name insert-character
-- :command :execute
-- :doc insert character
INSERT INTO `characters` (`n`, `specialty`) VALUES(:n, :specialty);

-- -- :name create-characters-table
-- -- :command :execute
-- -- :result :raw
-- -- :doc Create characters table
-- --  auto_increment and current_timestamp are
-- --  H2 Database specific (adjust to your DB)
-- create table characters (
--   id         integer auto_increment primary key,
--   name       varchar(40),
--   specialty  varchar(40),
--   created_at timestamp not null default current_timestamp
-- );

-- -- :name query-character-by-id
-- -- :command :query
-- -- :result :one
-- -- :doc query characters by id
-- SELECT `id`, `name`, `specialty`, `created_at`
--   FROM :i:table-name
--   WHERE id = :id
--   ORDER BY created_at;

-- -- :name query-character
-- -- :command :query
-- -- :result :raw
-- -- :doc query characters before id
-- SELECT `id`, `name`, `specialty`, `created_at`
--   FROM :i:table-name
--   WHERE id <= :to-id
--   ORDER BY created_at DESC;


-- -- :name drop-characters-table :!
-- -- :doc Drop characters table if exists
-- drop table if exists characters;
