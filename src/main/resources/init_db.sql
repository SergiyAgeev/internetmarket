create schema if not exists internet_market collate utf8_general_ci;

create table if not exists items
(
    item_id    int auto_increment
        primary key,
    item_name  varchar(30)   not null,
    item_price decimal(6, 2) not null
);

create table if not exists roles
(
    role_id   int auto_increment
        primary key,
    role_name varchar(45) not null,
    constraint role_name_UNIQUE
        unique (role_name)
);

create table if not exists users
(
    user_id          int auto_increment
        primary key,
    user_name        varchar(255)   null,
    user_second_name varchar(255)   null,
    user_age         int            null,
    user_login       varchar(255)   not null,
    user_password    varchar(255)   not null,
    user_salt        varbinary(500) NOT NULL,
    user_token       varchar(255)   null,
    constraint user_login_UNIQUE
        unique (user_login)
);

create table if not exists bucket
(
    id      int auto_increment
        primary key,
    user_id int not null,
    constraint fk_bucket_user
        foreign key (user_id) references users (user_id)
);

create index fk_bucket_user_idx
    on bucket (user_id);

create table if not exists bucket_items
(
    id        int auto_increment
        primary key,
    bucket_id int not null,
    item_id   int not null,
    constraint fk_bucket_items_bucket
        foreign key (bucket_id) references bucket (id),
    constraint fk_bucket_items_items
        foreign key (item_id) references items (item_id)
);

create index fk_bucket_items_bucket_idx
    on bucket_items (bucket_id);

create index fk_bucket_items_items_idx
    on bucket_items (item_id);

create table if not exists orders
(
    order_id int auto_increment
        primary key,
    user_id  int not null,
    constraint fk_orders_users
        foreign key (user_id) references users (user_id)
);

create index fk_orders_1_idx
    on orders (user_id);

create table if not exists orders_items
(
    idorders_items_id int auto_increment
        primary key,
    orders_id         int not null,
    item_id           int not null,
    constraint fk_orders_items_items
        foreign key (orders_id) references orders (order_id),
    constraint fk_orders_items_orders
        foreign key (item_id) references items (item_id)
);

create index fk_orders_items_1_idx
    on orders_items (item_id);

create index fk_orders_items_2_idx
    on orders_items (orders_id);

create table if not exists users_roles
(
    id       int auto_increment
        primary key,
    users_id int not null,
    role_id  int not null,
    constraint fk_users_roles_roles
        foreign key (role_id) references roles (role_id),
    constraint fk_users_roles_users
        foreign key (users_id) references users (user_id)
);

create index fk_users_roles_roles_idx
    on users_roles (role_id);

create index fk_users_roles_users_idx
    on users_roles (users_id);
