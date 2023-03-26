ALTER TABLE blog_dev.category ADD created_date DATE;
ALTER TABLE blog_dev.category ADD modified_date DATE;
ALTER TABLE blog_dev.category ADD created_by VARCHAR(255);
ALTER TABLE blog_dev.category ADD modified_by VARCHAR(255);

ALTER TABLE blog_dev.posts ADD created_date DATE;
ALTER TABLE blog_dev.posts ADD modified_date DATE;
ALTER TABLE blog_dev.posts ADD created_by VARCHAR(255);
ALTER TABLE blog_dev.posts ADD modified_by VARCHAR(255);

ALTER TABLE blog_dev.refresh_token ADD created_date DATE;
ALTER TABLE blog_dev.refresh_token ADD modified_date DATE;
ALTER TABLE blog_dev.refresh_token ADD created_by VARCHAR(255);
ALTER TABLE blog_dev.refresh_token ADD modified_by VARCHAR(255);

ALTER TABLE blog_dev.user_device ADD created_date DATE;
ALTER TABLE blog_dev.user_device ADD modified_date DATE;
ALTER TABLE blog_dev.user_device ADD created_by VARCHAR(255);
ALTER TABLE blog_dev.user_device ADD modified_by VARCHAR(255);

ALTER TABLE blog_dev.users ADD created_date DATE;
ALTER TABLE blog_dev.users ADD modified_date DATE;
ALTER TABLE blog_dev.users ADD created_by VARCHAR(255);
ALTER TABLE blog_dev.users ADD modified_by VARCHAR(255);



