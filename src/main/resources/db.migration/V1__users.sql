CREATE TABLE users
(
    id       int                   NOT NULL AUTO_INCREMENT,
    email    varchar(255)          NOT NULL,
    password varchar(255)          NOT NULL,
    name     varchar(255)          NOT NULL,
    active   boolean default false NOT NULL,
    PRIMARY KEY (id)
);