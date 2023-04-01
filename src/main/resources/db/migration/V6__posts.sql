CREATE TABLE blog_dev.posts
(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title       VARCHAR(255) NOT NULL,
    content     TEXT         NOT NULL,
    approved    BOOLEAN      NOT NULL DEFAULT false,
    category_id UUID      NOT NULL REFERENCES categories(id),
    user_id     UUID      NOT NULL REFERENCES users(id)
);