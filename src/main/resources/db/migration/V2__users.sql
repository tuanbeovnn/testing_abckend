CREATE TABLE blog_dev.users (
    id       serial PRIMARY KEY,
    email    varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    name     varchar(255) NOT NULL,
    active   boolean NOT NULL DEFAULT false
);