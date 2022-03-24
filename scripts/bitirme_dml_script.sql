use bitirmedb;

INSERT INTO `prt_product_type` (`kdv`, `name`) VALUES ('1', 'FOOD');
INSERT INTO `prt_product_type` (`kdv`, `name`) VALUES ('8', 'STATIONARY');
INSERT INTO `prt_product_type` (`kdv`, `name`) VALUES ('8', 'CLOTHING');
INSERT INTO `prt_product_type` (`kdv`, `name`) VALUES ('18', 'TECHNOLOGY');
INSERT INTO `prt_product_type` (`kdv`, `name`) VALUES ('18', 'CLEANING');
INSERT INTO `prt_product_type` (`kdv`, `name`) VALUES ('18', 'OTHERS');

INSERT INTO `set_settings` (`set_settings_key`, `set_settings_value`) VALUES ('savedDirectoryPath', 'D:\\\\jasper_reports\\\\');

DELIMITER $$
CREATE DEFINER=`root`@`localhost` TRIGGER `after_update_usr_user_set_log_usr_user` AFTER UPDATE ON `usr_user` FOR EACH ROW BEGIN
	INSERT INTO `log_usr_user` (id, create_date, created_by, update_date, updated_by, name, password, surname, username)
    VALUES  (OLD.id, OLD.create_date, OLD.created_by, OLD.update_date, OLD.updated_by, OLD.name, OLD.password, OLD.surname, OLD.username);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` TRIGGER `after_delete_usr_user_set_log_usr_user` AFTER DELETE ON `usr_user` FOR EACH ROW BEGIN
	INSERT INTO `log_usr_user` (id, create_date, created_by, update_date, updated_by, name, password, surname, username)
    VALUES  (OLD.id, OLD.create_date, OLD.created_by, OLD.update_date, OLD.updated_by, OLD.name, OLD.password, OLD.surname, OLD.username);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` TRIGGER `after_update_prt_product_type_set_log_prt_product_type` AFTER UPDATE ON `prt_product_type` FOR EACH ROW BEGIN
	INSERT INTO `log_prt_product_type` (id, create_date, created_by, update_date, updated_by, kdv, name)
    VALUES  (OLD.id, OLD.create_date, OLD.created_by, OLD.update_date, OLD.updated_by, OLD.kdv, OLD.name);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` TRIGGER `after_update_prd_product_set_log_prd_product` AFTER UPDATE ON `prd_product` FOR EACH ROW BEGIN
	INSERT INTO `log_prd_product` (id, create_date, created_by, update_date, updated_by, initial_price, kdv_amount, last_price, name, id_product_type)
    VALUES  (OLD.id, OLD.create_date, OLD.created_by, OLD.update_date, OLD.updated_by, OLD.initial_price, OLD.kdv_amount, OLD.last_price, OLD.name, OLD.id_product_type);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` TRIGGER `after_delete_prd_product_set_log_prd_product` AFTER DELETE ON `prd_product` FOR EACH ROW BEGIN
	INSERT INTO `log_prd_product` (id, create_date, created_by, update_date, updated_by, initial_price, kdv_amount, last_price, name, id_product_type)
    VALUES  (OLD.id, OLD.create_date, OLD.created_by, OLD.update_date, OLD.updated_by, OLD.initial_price, OLD.kdv_amount, OLD.last_price, OLD.name, OLD.id_product_type);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` TRIGGER `after_update_set_setings_set_log_set_settings` AFTER UPDATE ON `set_settings` FOR EACH ROW BEGIN
	INSERT INTO `log_set_settings` (id, create_date, created_by, update_date, updated_by, set_settings_key, set_settings_value)
    VALUES  (OLD.id, OLD.create_date, OLD.created_by, OLD.update_date, OLD.updated_by, OLD.set_settings_key, OLD.set_settings_value);
END$$
DELIMITER ;
