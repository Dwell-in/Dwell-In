CREATE TABLE IF NOT EXISTS `ssafyhome`.`property` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    apt_seq VARCHAR(20) NOT NULL,             
    member_id INT NOT NULL,                
    title VARCHAR(255) NOT NULL,
    type ENUM('매매', '전세', '월세') NOT NULL,
    net_area REAL,                            -- 전용면적
    supply_area REAL,                         -- 공급면적
    floor INT,
    total_floor INT,
    rooms INT,
    bathrooms INT,
    direction VARCHAR(20),
    elevator BOOLEAN,
    parking_available BOOLEAN,
    immediate_move_in BOOLEAN,
    structure_type VARCHAR(20),
    description TEXT,
    view_count INT DEFAULT 0,
    registered_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    picture MEDIUMBLOB DEFAULT NULL,
    
    FOREIGN KEY (apt_seq) REFERENCES houseinfos(apt_seq),
    FOREIGN KEY (member_id) REFERENCES member(id)
);
-- ALTER TABLE `ssafyhome`.`property`
-- ADD COLUMN `picture` MEDIUMBLOB DEFAULT NULL;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`property_price` (
    property_id BIGINT PRIMARY KEY,
    sale_price BIGINT,
    deposit BIGINT,
    monthly_rent BIGINT,
    management_fee INT,
    FOREIGN KEY (property_id) REFERENCES property(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `ssafyhome`.`property_option` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS `ssafyhome`.`property_option_mapping` (
    property_id BIGINT,
    option_id BIGINT,
    PRIMARY KEY (property_id, option_id),
    FOREIGN KEY (property_id) REFERENCES property(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (option_id) REFERENCES property_option(id)
);

CREATE TABLE IF NOT EXISTS `ssafyhome`.`safety_feature` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS `ssafyhome`.`property_safety_mapping` (
    property_id BIGINT,
    safety_id BIGINT,
    PRIMARY KEY (property_id, safety_id),
    FOREIGN KEY (property_id) REFERENCES property(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (safety_id) REFERENCES safety_feature(id)
);

-- 옵션 및 보안시설
INSERT INTO `ssafyhome`.`property_option` (name) VALUES ('붙박이장');
INSERT INTO `ssafyhome`.`property_option` (name) VALUES ('천장형 에어컨');
INSERT INTO `ssafyhome`.`property_option` (name) VALUES ('식탁');
INSERT INTO `ssafyhome`.`property_option` (name) VALUES ('세탁기');
INSERT INTO `ssafyhome`.`property_option`(name) VALUES ('냉장고');
INSERT INTO `ssafyhome`.`property_option`(name) VALUES ('전자레인지');
INSERT INTO `ssafyhome`.`property_option`(name) VALUES ('TV');
INSERT INTO `ssafyhome`.`property_option`(name) VALUES ('가스레인지');
INSERT INTO `ssafyhome`.`property_option`(name) VALUES ('인덕션');
INSERT INTO `ssafyhome`.`property_option`(name) VALUES ('신발장');
INSERT INTO `ssafyhome`.`safety_feature` (name) VALUES ('비디오폰');
INSERT INTO `ssafyhome`.`safety_feature` (name) VALUES ('인터폰');
INSERT INTO `ssafyhome`.`safety_feature` (name) VALUES ('CCTV');
INSERT INTO `ssafyhome`.`safety_feature` (name) VALUES ('현관 보안');
INSERT INTO `ssafyhome`.`safety_feature` (name) VALUES ('경비실');
INSERT INTO `ssafyhome`.`safety_feature` (name) VALUES ('카드키');
INSERT INTO `ssafyhome`.`safety_feature` (name) VALUES ('무인택배함');
