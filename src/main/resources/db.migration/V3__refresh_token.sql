CREATE TABLE refresh_token (
    id              serial PRIMARY KEY,
    token           varchar(255) NOT NULL UNIQUE,
    user_device_id  integer NOT NULL UNIQUE REFERENCES user_device(id),
    refresh_count   bigint DEFAULT 0,
    expiry_dt       timestamp NOT NULL
);