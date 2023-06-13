CREATE TABLE blog_dev.viewers
(
    id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    post_id       UUID    NOT NULL REFERENCES posts (id),
    view_ct       INTEGER NOT NULL DEFAULT 0
);