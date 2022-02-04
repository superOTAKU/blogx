DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    password_hash VARCHAR(20) NOT NULL,
    create_time DATETIME,
    update_time DATETIME,
    version BIGINT NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  overview TEXT NOT NULL,
  content MEDIUMTEXT NOT NULL,
  deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志位',
  create_time DATETIME,
  update_time DATETIME,
  version BIGINT NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL UNIQUE,
  create_time DATETIME,
  update_time DATETIME,
  version BIGINT NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS `post_category`;
CREATE TABLE post_category(
  post_id BIGINT NOT NULL,
  category_id BIGINT NOT NULL,
  create_time DATETIME,
  update_time DATETIME,
  version BIGINT NOT NULL DEFAULT 0,
  PRIMARY KEY pk(post_id, category_id)
);

