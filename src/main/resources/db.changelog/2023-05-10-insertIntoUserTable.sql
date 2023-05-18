--liquibase formatted sql

--changeSet sasha:1
insert into users(name)
values ('Andrei'),
       ('Sergei'),
       ('Ekaterina'),
       ('Aliona'),
       ('Pavel');
