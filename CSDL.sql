create database shoes_store;
use shoes_store;
desc user;

select * from user;
insert into user(full_name, email ,password,  phone_number, status)
values
('Trần Mạnh Đạt', 'dattran2003.ttn@gmail.com','$2a$12$6Wmp3.gHxVV24rZgeycN/.J.UvaLAVoxg8K6ReYLkEUSxITCN0t.q', '0827666910', 1);

CREATE TABLE Category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE Brand (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME
);
desc category;
CREATE TABLE brand (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME
);
desc Brand;
CREATE TABLE Product (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2),
    created_at DATETIME,
    updated_at DATETIME,
    is_deleted BIT(1),
    status VARCHAR(20),
    version_name VARCHAR(100),
    brand_id INT,
    category_id INT,
    FOREIGN KEY (brand_id) REFERENCES Brand(id),
    FOREIGN KEY (category_id) REFERENCES Category(id)
);

-- Insert dữ liệu sample
INSERT INTO brand (name, created_at, updated_at)
VALUES 
    ('Nike', NOW(), NOW()),
    ('Adidas', NOW(), NOW()),
    ('Converse', NOW(), NOW()),
    ('Jordan', NOW(), NOW()),
    ('Lacoste', NOW(), NOW());

INSERT INTO category (name, created_at, updated_at)
VALUES 
    ('Sneakers', NOW(), NOW()),
    ('Running Shoes', NOW(), NOW()),
    ('Casual Shoes', NOW(), NOW()),
    ('Basketball Shoes', NOW(), NOW()),
    ('Loafers', NOW(), NOW());

INSERT INTO product (name, description, price, brand_id, category_id, is_deleted, status, version, created_at, updated_at)
VALUES 
    ('Giày thể thao Nike Air Max', 'Mô tả cho giày thể thao Nike Air Max', 150.99, 1, 1, false, 'Active', '1.0', NOW(), NOW()),  -- Category ID 1: Sneakers
    ('Giày chạy Adidas Ultraboost', 'Mô tả cho giày chạy Adidas Ultraboost', 120.50, 2, 2, false, 'Active', '1.0', NOW(), NOW()), -- Category ID 2: Running Shoes
    ('Giày đế bệt Converse Chuck Taylor', 'Mô tả cho giày đế bệt Converse Chuck Taylor', 80.00, 3, 3, false, 'Active', '1.0', NOW(), NOW()), -- Category ID 3: Casual Shoes
    ('Giày bóng rổ Jordan Retro', 'Mô tả cho giày bóng rổ Jordan Retro', 180.75, 4, 4, false, 'Active', '1.0', NOW(), NOW()), -- Category ID 4: Basketball Shoes
    ('Giày lười Lacoste', 'Mô tả cho giày lười Lacoste', 90.25, 5, 5, false, 'Active', '1.0', NOW(), NOW()); -- Category ID 5: Loafers

select * from category;
select * from product;
desc product;
select * from product_images;
select distinct * from product_size;
select * from  brand;
delete from product where id>=21;
UPDATE `shoes_store`.`product` SET `is_deleted` = 1 WHERE (`id` = '20');

desc cart_item;
select * from cart_item;
desc user;
select * from order_;
desc order_;
select * from order_item;

select * from user;
use shoes_store;
select * from favorite;
truncate table favorite;


select * from comment;