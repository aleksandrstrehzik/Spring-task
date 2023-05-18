--liquibase formatted sql

--changeSet sasha:1
CREATE TABLE gift_certificate
(
    id               bigserial primary key,
    name             varchar(32)    not null unique,
    description      text           not null,
    price            Decimal(10, 2) not null,
    duration         integer        not null,
    create_date      timestamp,
    last_update_date timestamp
);

--changeSet sasha:2
CREATE TABLE tag
(
    id   BIGSERIAL primary key,
    name varchar(60) not null unique
);

--changeSet sasha:3
create table gift_certificate_tag
(
    gift_id bigint,
    tag_id  bigint
);

--changeSet sasha:4
alter table gift_certificate_tag
    add primary key (gift_id, tag_id),
    add foreign key (gift_id) references gift_certificate,
    add foreign key (tag_id) references tag;

--changeSet sasha:5
create table users
(
    id   BIGSERIAL primary key,
    name varchar(30) not null unique
);

--changeSet sasha:6
create table orders
(
    id       BIGSERIAL primary key,
    purchase timestamp,
    price    decimal(10, 2),
    user_id  bigint,
    gift_id  bigint
);

--changeSet sasha:7
alter table orders
    add foreign key (user_id) references users,
    add foreign key (gift_id) references gift_certificate;