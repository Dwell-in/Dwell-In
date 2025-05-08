drop table if exists `ssafyhome`.`noticeboard`;
drop table if exists `ssafyhome`.`board`;
drop table if exists `ssafyhome`.`chat_message`;
drop table if exists `ssafyhome`.`chat_room`;
drop table if exists `ssafyhome`.`pattern`;
drop table if exists `ssafyhome`.`member`;
drop table if exists `ssafyhome`.`star`;

-- 매물별 조회수 컬럼 추가 
-- alter table `ssafyhome`.`houseinfos` add column view_count int default 0;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`member` (
  `email` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `password` VARCHAR(100) NULL,
  `phone` VARCHAR(45) NULL,
  `role` VARCHAR(45) NULL,
  `kakao_id` VARCHAR(45) NULL, 
  `profile` MEDIUMBLOB,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`star`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyhome`.`star` (
 `star_seq` INT NOT NULL AUTO_INCREMENT,   
  `email` VARCHAR(45) NULL,
  `apt_seq` VARCHAR(45) NULL,
 
  PRIMARY KEY (`star_seq`),              
  UNIQUE INDEX `starno_UNIQUE` (`star_seq` ASC)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ssafyhome`.`board`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ssafyhome`.`board` (
  `boardid` INT NOT NULL auto_increment,
  `title` VARCHAR(45) NULL,
  `content` VARCHAR(200) NULL,
  `email` VARCHAR(45) NULL,
  `view_count` INT NULL,
  `reg_date` DATETIME NULL,
  PRIMARY KEY (`boardid`),
  INDEX `fk_board_member1_idx` (`email` ASC) VISIBLE,
  CONSTRAINT `fk_board_member1`
    FOREIGN KEY (`email`)
    REFERENCES `ssafyhome`.`member` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ssafyhome`.`noticeboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyhome`.`noticeboard` (
  `boardid` INT NOT NULL auto_increment,
  `title` VARCHAR(45) NULL,
  `content` VARCHAR(200) NULL,
  `email` VARCHAR(45) NULL,
  `view_count` INT NULL,
  `reg_date` DATETIME NULL,
  PRIMARY KEY (`boardid`),
  INDEX `fk_noticeboard_member1_idx` (`email` ASC) VISIBLE,
  CONSTRAINT `fk_noticeboard_member1`
    FOREIGN KEY (`email`)
    REFERENCES `ssafyhome`.`member` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- 채팅방 테이블
CREATE TABLE IF NOT EXISTS `ssafyhome`.`chat_room` (
    `room_id` VARCHAR(100) PRIMARY KEY,
    `user1_id` VARCHAR(45) NOT NULL,
    `user2_id` VARCHAR(45) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_users (user1_id, user2_id),
    FOREIGN KEY (user1_id) REFERENCES member(email),
    FOREIGN KEY (user2_id) REFERENCES member(email))
ENGINE = InnoDB;

-- 채팅 메시지 테이블
CREATE TABLE IF NOT EXISTS `ssafyhome`.`chat_message` (
    `message_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `room_id` VARCHAR(100) NOT NULL,
    `sender_id` VARCHAR(45) NOT NULL,
    `content` TEXT NOT NULL,
    `sent_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES chat_room(room_id),
    FOREIGN KEY (sender_id) REFERENCES member(email))
ENGINE = InnoDB;