CREATE TABLE user_device (
    id                serial PRIMARY KEY,
    user_id           integer NOT NULL REFERENCES users(id),
    device_type       varchar(255),
    device_id         varchar(255) NOT NULL,
    is_refresh_active boolean DEFAULT false
);