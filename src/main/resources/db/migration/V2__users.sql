CREATE TABLE blog_dev.users (
    id UUID  PRIMARY KEY DEFAULT uuid_generate_v4(),
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NOT NULL,
    active   boolean NOT NULL DEFAULT false,
    provider VARCHAR(20) NOT NULL DEFAULT 'LOCAL'
);