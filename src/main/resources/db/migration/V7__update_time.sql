ALTER TABLE blog_dev.categories
    ADD COLUMN created_date TIMESTAMP,
    ADD COLUMN modified_date TIMESTAMP,
    ADD COLUMN created_by VARCHAR(20),
    ADD COLUMN modified_by VARCHAR(20);

ALTER TABLE posts
    ADD COLUMN created_date TIMESTAMP,
    ADD COLUMN modified_date TIMESTAMP,
    ADD COLUMN created_by VARCHAR(20),
    ADD COLUMN modified_by VARCHAR(20);

ALTER TABLE refresh_token
    ADD COLUMN created_date TIMESTAMP,
    ADD COLUMN modified_date TIMESTAMP,
    ADD COLUMN created_by VARCHAR(20),
    ADD COLUMN modified_by VARCHAR(20);

ALTER TABLE user_device
    ADD COLUMN created_date TIMESTAMP,
    ADD COLUMN modified_date TIMESTAMP,
    ADD COLUMN created_by VARCHAR(20),
    ADD COLUMN modified_by VARCHAR(20);

ALTER TABLE users
    ADD COLUMN created_date TIMESTAMP,
    ADD COLUMN modified_date TIMESTAMP,
    ADD COLUMN created_by VARCHAR(20),
    ADD COLUMN modified_by VARCHAR(20);





