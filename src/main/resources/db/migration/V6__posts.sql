CREATE TABLE blog_dev.posts
(
    id          serial PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    content     TEXT         NOT NULL,
    approved    BOOLEAN      NOT NULL DEFAULT false,
    category_id integer      NOT NULL UNIQUE REFERENCES category(id),
    user_id     integer      NOT NULL UNIQUE REFERENCES users(id)
);