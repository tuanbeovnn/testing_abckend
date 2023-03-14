CREATE TABLE refresh_token
(
    id             int          NOT NULL AUTO_INCREMENT,
    token          varchar(255) NOT NULL UNIQUE,
    refresh_count  BIGINT,
    expiry_dt      date,
    user_device_id int UNIQUE  NOT NULL foreign key references user_device(id),
    PRIMARY KEY (id)
);
ALTER TABLE refresh_token ADD FOREIGN KEY (id) REFERENCES  user_device(id);