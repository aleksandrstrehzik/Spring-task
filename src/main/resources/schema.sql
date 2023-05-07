CREATE TABLE gift_certificate
(
    id               bigserial primary key,
    name             varchar(32)    not null,
    description      text           not null,
    price            Decimal(10, 2) not null,
    duration         integer        not null,
    create_date      timestamp,
    last_update_date timestamp
);

create table tag
(
    id   BIGSERIAL primary key,
    name varchar(60) not null
);

create table gift_certificate_tag
(
    gift_id bigint,
    tag_id  bigint
);

alter table gift_certificate_tag
    add primary key (gift_id, tag_id);

alter table gift_certificate_tag
    add foreign key (gift_id) references gift_certificate;

alter table gift_certificate_tag
    add foreign key (tag_id) references tag;
