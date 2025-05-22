drop table if exists `ssafyhome`.`noticeboard`;

DROP TABLE IF EXISTS `ssafyhome`.`property_safety_mapping`;
DROP TABLE IF EXISTS `ssafyhome`.`safety_feature`;
DROP TABLE IF EXISTS `ssafyhome`.`property_option_mapping`;
DROP TABLE IF EXISTS `ssafyhome`.`property_option`;
DROP TABLE IF EXISTS `ssafyhome`.`property_price`;
DROP TABLE IF EXISTS `ssafyhome`.`property`;

drop table if exists `ssafyhome`.`comment`;
drop table if exists `ssafyhome`.`board`;
drop table if exists `ssafyhome`.`post_category`;
drop table if exists `ssafyhome`.`chat_message`;
drop table if exists `ssafyhome`.`chat_room`;
drop table if exists `ssafyhome`.`pattern`;
drop table if exists `ssafyhome`.`star`;
drop table if exists `ssafyhome`.`email_verification_token`;
drop table if exists `ssafyhome`.`member`;

-- 매물별 조회수 컬럼 추가 
-- alter table `ssafyhome`.`houseinfos` add column view_count int default 0;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`member` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `password` VARCHAR(100) NULL,
  `phone` VARCHAR(45) NULL,
  `role` VARCHAR(45) NULL,
  `kakao_id` VARCHAR(45) NULL, 
  `state` VARCHAR(45) DEFAULT 'NORMAL',
  `profile` MEDIUMBLOB,
  `refresh_token` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`email`)
) ENGINE = InnoDB;
-- ALTER TABLE `ssafyhome`.`member`
-- ADD COLUMN `refresh_token` VARCHAR(255) DEFAULT NULL;

-- -----------------------------------------------------
-- Table `mydb`.`star`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyhome`.`star` (
  `user_id` VARCHAR(45) NOT NULL,
  `apt_seq` VARCHAR(45) NOT NULL,
 
  PRIMARY KEY (`user_id`,`apt_seq`)              
) ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `ssafyhome`.`post_category`(
	`category_id` INT NOT NULL,
    `category_name` VARCHAR(45) NULL,
    PRIMARY KEY (`category_id`)
    )
ENGINE = InnoDB;
insert into post_category
values
	(0, 'notice'),
    (1, '커뮤니티'),
    (2, 'FAQ'),
    (3, '1:1 문의');

-- -----------------------------------------------------
-- Table `ssafyhome`.`board`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `ssafyhome`.`board` (
  `board_id` INT NOT NULL auto_increment,
  `category_id` int NOT NULL, 
  `title` VARCHAR(45) NOT NULL,
  `content` VARCHAR(2000) NOT NULL,
  `user_id` INT NOT NULL,
  `view_count` INT DEFAULT 0,
  `like` INT DEFAULT 0,
  `dislike` INT DEFAULT 0,
  `reg_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`board_id`),
  INDEX `fk_board_member1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_board_member1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafyhome`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_board_post_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `ssafyhome`.`post_category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `ssafyhome`.`comment` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `board_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `content` VARCHAR(200) NOT NULL,
  `like` INT DEFAULT 0,
  `dislike` INT DEFAULT 0,
  `reg_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`),
  CONSTRAINT `fk_comment_board`
    FOREIGN KEY (`board_id`)
    REFERENCES `ssafyhome`.`board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_member`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafyhome`.`member` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- 채팅방 테이블
CREATE TABLE IF NOT EXISTS `ssafyhome`.`chat_room` (
    `room_id` VARCHAR(100) PRIMARY KEY,
    `user1_id` INT NOT NULL,
    `user2_id` INT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_users (user1_id, user2_id),
    FOREIGN KEY (user1_id) REFERENCES member(id),
    FOREIGN KEY (user2_id) REFERENCES member(id))
ENGINE = InnoDB;

-- 채팅 메시지 테이블
CREATE TABLE IF NOT EXISTS `ssafyhome`.`chat_message` (
    `message_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `room_id` VARCHAR(100) NOT NULL,
    `sender_id` INT NOT NULL,
    `content` TEXT NOT NULL,
    `sent_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES chat_room(room_id),
    FOREIGN KEY (sender_id) REFERENCES member(id))
ENGINE = InnoDB;


-- ---------------------------------------------
-- UUID 토큰
CREATE TABLE IF NOT EXISTS `ssafyhome`.`email_verification_token` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL,
    expiry_date DATETIME NOT NULL,
    is_used BOOLEAN DEFAULT FALSE
);
