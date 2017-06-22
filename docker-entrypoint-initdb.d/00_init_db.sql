CREATE DATABASE testDB;
USE testDB;
CREATE TABLE `account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'main id',
  `type` tinyint(3) unsigned NOT NULL COMMENT 'type of the account',
  `created_at` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'the account created at',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='account table';
