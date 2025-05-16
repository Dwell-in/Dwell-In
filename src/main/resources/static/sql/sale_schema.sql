use ssafyhome;

DROP TABLE IF EXISTS property_safety_mapping;
DROP TABLE IF EXISTS safety_feature;
DROP TABLE IF EXISTS property_option_mapping;
DROP TABLE IF EXISTS property_option;
DROP TABLE IF EXISTS property_price;
DROP TABLE IF EXISTS property;

CREATE TABLE IF NOT EXISTS property (
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
    
    FOREIGN KEY (apt_seq) REFERENCES houseinfos(apt_seq),
    FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE IF NOT EXISTS property_price (
    property_id BIGINT PRIMARY KEY,
    sale_price BIGINT,
    deposit BIGINT,
    monthly_rent BIGINT,
    management_fee INT,
    FOREIGN KEY (property_id) REFERENCES property(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS property_option (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS property_option_mapping (
    property_id BIGINT,
    option_id BIGINT,
    PRIMARY KEY (property_id, option_id),
    FOREIGN KEY (property_id) REFERENCES property(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (option_id) REFERENCES property_option(id)
);

CREATE TABLE IF NOT EXISTS safety_feature (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS property_safety_mapping (
    property_id BIGINT,
    safety_id BIGINT,
    PRIMARY KEY (property_id, safety_id),
    FOREIGN KEY (property_id) REFERENCES property(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (safety_id) REFERENCES safety_feature(id)
);

