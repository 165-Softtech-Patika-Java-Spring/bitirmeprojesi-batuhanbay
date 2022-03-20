CREATE DATABASE `bitirmedb` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_turkish_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

use bitirmedb;

CREATE TABLE IF NOT EXISTS `usr_user`
(
   `id` bigint NOT NULL AUTO_INCREMENT,
    create_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by bigint NOT NULL DEFAULT '1',
    update_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL DEFAULT '1',
    name varchar(255) COLLATE utf8_turkish_ci DEFAULT NULL,
    password varchar(255) NOT NULL,
    surname varchar(255) COLLATE utf8_turkish_ci DEFAULT NULL,
    username varchar(255) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `usr_user_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

CREATE TABLE IF NOT EXISTS `prt_product_type`
(
   `id` bigint NOT NULL AUTO_INCREMENT,
    create_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by bigint NOT NULL DEFAULT '1',
    update_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL DEFAULT '1',
    kdv numeric(19,2) NOT NULL,
    name varchar(30) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;


CREATE TABLE IF NOT EXISTS `prd_product`
(
	`id` bigint NOT NULL AUTO_INCREMENT,
    create_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by bigint NOT NULL DEFAULT '1',
    update_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL DEFAULT '1',
    initial_price numeric(19,2) NOT NULL,
	kdv_amount numeric(19,2) NOT NULL,
    last_price numeric(19,2) NOT NULL,
	name varchar(255) NOT NULL,
    id_product_type bigint NOT NULL,
	PRIMARY KEY (`id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

CREATE TABLE IF NOT EXISTS `log_usr_user`
(
   `id` bigint NOT NULL ,
    create_date timestamp NOT NULL,
    created_by bigint NOT NULL,
    update_date timestamp NOT NULL,
    updated_by bigint NOT NULL,
    name varchar(255) DEFAULT NULL,
    password varchar(255) NOT NULL,
    surname varchar(255) DEFAULT NULL,
    username varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

CREATE TABLE IF NOT EXISTS `log_prt_product_type`
(
    `id` bigint NOT NULL ,
	create_date timestamp NOT NULL,
    created_by bigint NOT NULL,
    update_date timestamp NOT NULL,
    updated_by bigint NOT NULL,
    kdv numeric(19,2) NOT NULL,
    name varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;


CREATE TABLE IF NOT EXISTS `log_prd_product`
(
	`id` bigint NOT NULL ,
	create_date timestamp NOT NULL,
    created_by bigint NOT NULL,
    update_date timestamp NOT NULL,
    updated_by bigint NOT NULL,
    initial_price numeric(19,2) NOT NULL,
    kdv_amount numeric(19,2) NOT NULL,
    last_price numeric(19,2) NOT NULL,
	name varchar(255) NOT NULL,
    id_product_type bigint NOT NULL
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;
