-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ssafyhome
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ssafyhome
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafyhome` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ssafyhome` ;

-- -----------------------------------------------------
-- Table `ssafyhome`.`dongcodes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`dongcodes` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`dongcodes` (
  `dong_code` VARCHAR(10) NOT NULL comment '법정동코드',
  `sido_name` VARCHAR(30) NULL DEFAULT NULL comment '시도이름',
  `gugun_name` VARCHAR(30) NULL DEFAULT NULL comment '구군이름',
  `dong_name` VARCHAR(30) NULL DEFAULT NULL comment '동이름',
  PRIMARY KEY (`dong_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
comment '법정동코드테이블';


-- -----------------------------------------------------
-- Table `ssafyhome`.`houseinfos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`houseinfos` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`houseinfos` (
  `apt_seq` VARCHAR(20) NOT NULL comment '아파트코드',
  `sgg_cd` VARCHAR(5) NULL DEFAULT NULL comment '시군구코드',
  `umd_cd` VARCHAR(5) NULL DEFAULT NULL comment '읍면동코드',
  `umd_nm` VARCHAR(20) NULL DEFAULT NULL comment '읍면동이름',
  `jibun` VARCHAR(10) NULL DEFAULT NULL comment '지번',
  `road_nm_sgg_cd` VARCHAR(5) NULL DEFAULT NULL comment '도로명시군구코드',
  `road_nm` VARCHAR(20) NULL DEFAULT NULL comment '도로명',
  `road_nm_bonbun` VARCHAR(10) NULL DEFAULT NULL comment '도로명기초번호',
  `road_nm_bubun` VARCHAR(10) NULL DEFAULT NULL comment '도로명추가번호',
  `apt_nm` VARCHAR(40) NULL DEFAULT NULL comment '아파트이름',
  `build_year` INT NULL DEFAULT NULL comment '준공년도',
  `latitude` VARCHAR(45) NULL DEFAULT NULL comment '위도',
  `longitude` VARCHAR(45) NULL DEFAULT NULL comment '경도',
  PRIMARY KEY (`apt_seq`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
comment '주택정보테이블';


-- -----------------------------------------------------
-- Table `ssafyhome`.`housedeals`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`housedeals` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`housedeals` (
  `no` INT NOT NULL AUTO_INCREMENT comment '거래번호',
  `apt_seq` VARCHAR(20) NULL DEFAULT NULL comment '아파트코드',
  `apt_dong` VARCHAR(40) NULL DEFAULT NULL comment '아파트동',
  `floor` VARCHAR(3) NULL DEFAULT NULL comment '아파트층',
  `deal_year` INT NULL DEFAULT NULL comment '거래년도',
  `deal_month` INT NULL DEFAULT NULL comment '거래월',
  `deal_day` INT NULL DEFAULT NULL comment '거래일',
  `exclu_use_ar` DECIMAL(7,2) NULL DEFAULT NULL  comment '아파트면적',
  `deal_amount` VARCHAR(10) NULL DEFAULT NULL  comment '거래가격',
  PRIMARY KEY (`no`),
  INDEX `apt_seq_to_house_info_idx` (`apt_seq` ASC) VISIBLE,
  CONSTRAINT `apt_seq_to_house_info`
    FOREIGN KEY (`apt_seq`)
    REFERENCES `ssafyhome`.`houseinfos` (`apt_seq`))
ENGINE = InnoDB
AUTO_INCREMENT = 7084512
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
comment '주택거래정보테이블';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
