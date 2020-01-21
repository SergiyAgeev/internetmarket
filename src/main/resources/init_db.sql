CREATE SCHEMA `internet_market` DEFAULT CHARACTER SET utf8;

USE internet_market;

CREATE TABLE `internet_market`.`Items`
(
    `item_id`    INT           NOT NULL AUTO_INCREMENT,
    `item_name`  VARCHAR(30)   NOT NULL,
    `item_price` DECIMAL(6, 2) NOT NULL,
    PRIMARY KEY (`item_id`)
);

insert into Items (item_name, item_price)
values ('Apple', 3);
insert into Items (item_name, item_price)
values ('Tomato', 11);
insert into Items (item_name, item_price)
values ('Banana', 9);
