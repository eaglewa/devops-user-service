DROP TABLE if exists USER;

CREATE TABLE USER (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    age INTEGER(2),
    is_deleted BOOLEAN,
    last_modified_by VARCHAR(50),
    last_modified_time DATETIME
);

INSERT INTO USER VALUES(1, '张三', 20, '0', 'admin', '2020-10-01 20:00:00');
INSERT INTO USER VALUES(2, '李四', 21, '0', 'admin', '2020-10-01 20:00:00');
INSERT INTO USER VALUES(3, '王五', 22, '0', 'admin', '2020-10-01 20:00:00');
