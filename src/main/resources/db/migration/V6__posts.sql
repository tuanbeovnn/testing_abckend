CREATE TABLE blog_dev.posts
(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title       TEXT         NOT NULL,
    content     TEXT         NOT NULL,
    status      VARCHAR(255) NOT NULL,
    approved    BOOLEAN      NOT NULL DEFAULT false,
    category_id UUID      NOT NULL REFERENCES categories(id),
    user_id     UUID      NOT NULL REFERENCES users(id),
    created_date       TIMESTAMP,
    modified_date      TIMESTAMP,
    created_by         VARCHAR(20),
    modified_by        VARCHAR(20)
);