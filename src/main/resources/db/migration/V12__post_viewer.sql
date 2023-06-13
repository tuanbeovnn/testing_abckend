CREATE TABLE blog_dev.viewers
(
    id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    post_id       UUID    NOT NULL REFERENCES posts (id),
    user_id       UUID    NOT NULL REFERENCES users (id),
    view_ct       INTEGER NOT NULL DEFAULT 0,
    rating        INTEGER NOT NULL DEFAULT 0,
    feeling_desc  VARCHAR(20) NULL,
    created_date  TIMESTAMP,
    modified_date TIMESTAMP,
    created_by    VARCHAR(20),
    modified_by   VARCHAR(20)
);