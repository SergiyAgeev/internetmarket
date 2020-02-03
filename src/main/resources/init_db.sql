CREATE SCHEMA `internet_market` DEFAULT CHARACTER SET utf8;

CREATE TABLE `internet_market`.`users`
(
    `user_id`          int            NOT NULL AUTO_INCREMENT,
    `user_name`        varchar(255) DEFAULT NULL,
    `user_second_name` varchar(255) DEFAULT NULL,
    `user_age`         int          DEFAULT NULL,
    `user_login`       varchar(255)   NOT NULL,
    `user_password`    varchar(255)   NOT NULL,
    `user_token`       varchar(255) DEFAULT NULL,
    `user_salt`        varbinary(500) NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `user_login_UNIQUE` (`user_login`)
);

CREATE TABLE `internet_market`.`roles`
(
    `role_id`   int         NOT NULL AUTO_INCREMENT,
    `role_name` varchar(45) NOT NULL,
    PRIMARY KEY (`role_id`),
    UNIQUE KEY `role_name_UNIQUE` (`role_name`)
);

CREATE TABLE `internet_market`.`users_roles`
(
    `id`       int NOT NULL AUTO_INCREMENT,
    `users_id` int NOT NULL,
    `role_id`  int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_users_roles_roles_idx` (`role_id`),
    KEY `fk_users_roles_users_idx` (`users_id`),
    CONSTRAINT `fk_users_roles_roles` FOREIGN KEY (`role_id`)
        REFERENCES `roles` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_users_roles_users` FOREIGN KEY (`users_id`)
        REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `internet_market`.`orders`
(
    `order_id` int NOT NULL AUTO_INCREMENT,
    `user_id`  int NOT NULL,
    PRIMARY KEY (`order_id`),
    KEY `fk_orders_1_idx` (`user_id`),
    CONSTRAINT `fk_orders_users` FOREIGN KEY (`user_id`)
        REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `internet_market`.`items`
(
    `item_id`    int           NOT NULL AUTO_INCREMENT,
    `item_name`  varchar(30)   NOT NULL,
    `item_price` decimal(6, 2) NOT NULL,
    PRIMARY KEY (`item_id`)
);

CREATE TABLE `internet_market3773`.`bucket`
(
    `id`      int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_bucket_user_idx` (`user_id`),
    CONSTRAINT `fk_bucket_user` FOREIGN KEY (`user_id`)
        REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `internet_market`.`bucket_items`
(
    `id`        int NOT NULL AUTO_INCREMENT,
    `bucket_id` int NOT NULL,
    `item_id`   int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_bucket_items_bucket_idx` (`bucket_id`),
    KEY `fk_bucket_items_items_idx` (`item_id`),
    CONSTRAINT `fk_bucket_items_bucket` FOREIGN KEY (`bucket_id`)
        REFERENCES `bucket` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_bucket_items_items` FOREIGN KEY (`item_id`)
        REFERENCES `items` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `internet_market`.`orders_items`
(
    `idorders_items_id` int NOT NULL AUTO_INCREMENT,
    `orders_id`         int NOT NULL,
    `item_id`           int NOT NULL,
    PRIMARY KEY (`idorders_items_id`),
    KEY `fk_orders_items_1_idx` (`item_id`),
    KEY `fk_orders_items_2_idx` (`orders_id`),
    CONSTRAINT `fk_orders_items_items` FOREIGN KEY (`orders_id`)
        REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_orders_items_orders` FOREIGN KEY (`item_id`)
        REFERENCES `items` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE
);
