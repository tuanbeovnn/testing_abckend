CREATE TABLE blog_dev.refresh_token (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    token           VARCHAR(255) NOT NULL UNIQUE,
    user_device_id  UUID NOT NULL REFERENCES user_device(id),
    refresh_count   bigint DEFAULT 0,
    expiry_dt       TIMESTAMP NOT NULL
);