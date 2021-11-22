SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `tagprocessing` ;
CREATE SCHEMA IF NOT EXISTS `tagprocessing` DEFAULT CHARACTER SET utf8 ;
USE `tagprocessing` ;

-- -----------------------------------------------------
-- Table `tagprocessing`.`hibernate_sequence`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tagprocessing`.`hibernate_sequence` ;

CREATE  TABLE IF NOT EXISTS `tagprocessing`.`hibernate_sequence` (
  `next_val` BIGINT(20) NULL DEFAULT NULL )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tagprocessing`.`tb_rfid_data`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tagprocessing`.`tb_rfid_data` ;

CREATE  TABLE IF NOT EXISTS `tagprocessing`.`tb_rfid_data` (
  `id` BIGINT(20) NOT NULL ,
  `access_password` VARCHAR(255) NULL DEFAULT NULL ,
  `barcode` VARCHAR(255) NULL DEFAULT NULL ,
  `count` VARCHAR(255) NULL DEFAULT NULL ,
  `date` VARCHAR(255) NULL DEFAULT NULL ,
  `epc` VARCHAR(255) NULL DEFAULT NULL ,
  `kill_password` VARCHAR(255) NULL DEFAULT NULL ,
  `orderid` VARCHAR(255) NULL DEFAULT NULL ,
  `rssi` VARCHAR(255) NULL DEFAULT NULL ,
  `status` VARCHAR(255) NULL DEFAULT NULL ,
  `tid_data` VARCHAR(255) NULL DEFAULT NULL ,
  `time` VARCHAR(255) NULL DEFAULT NULL ,
  `user_memory` VARCHAR(255) NULL DEFAULT NULL ,
  `memory_rw_error` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tagprocessing`.`tb_rfid_error_log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tagprocessing`.`tb_rfid_error_log` ;

CREATE  TABLE IF NOT EXISTS `tagprocessing`.`tb_rfid_error_log` (
  `id` BIGINT(20) NOT NULL ,
  `bar_code` VARCHAR(255) NULL DEFAULT NULL ,
  `date` VARCHAR(255) NULL DEFAULT NULL ,
  `error_code` VARCHAR(255) NULL DEFAULT NULL ,
  `msg` VARCHAR(255) NULL DEFAULT NULL ,
  `order_id` VARCHAR(255) NULL DEFAULT NULL ,
  `time` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tagprocessing`.`tb_tag_data_feed`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tagprocessing`.`tb_tag_data_feed` ;

CREATE  TABLE IF NOT EXISTS `tagprocessing`.`tb_tag_data_feed` (
  `id` BIGINT(20) NOT NULL ,
  `ios_no` VARCHAR(255) NULL DEFAULT NULL ,
  `access_password` VARCHAR(255) NULL DEFAULT NULL ,
  `barcode_details` VARCHAR(255) NULL DEFAULT NULL ,
  `column_val` VARCHAR(255) NULL DEFAULT NULL ,
  `epc_code` VARCHAR(255) NULL DEFAULT NULL ,
  `file_name` VARCHAR(255) NULL DEFAULT NULL ,
  `file_path` VARCHAR(255) NULL DEFAULT NULL ,
  `kill_password` VARCHAR(255) NULL DEFAULT NULL ,
  `tag_kill_bit` VARCHAR(255) NULL DEFAULT NULL ,
  `unique_code_name` VARCHAR(255) NULL DEFAULT NULL ,
  `user_memory` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
