CREATE TABLE user_device
(
    id                int          NOT NULL AUTO_INCREMENT,
    device_type       varchar(255),
    device_id         varchar(255) NOT NULL,
    is_refresh_active boolean default false,
    PRIMARY KEY (id)
);
ALTER TABLE user_device ADD FOREIGN KEY (id) REFERENCES  users(id);