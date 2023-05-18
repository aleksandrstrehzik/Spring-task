--liquibase formatted sql

--changeSet sasha:1
insert into tag(name)
values ('awfully'),
       ('relax'),
       ('for_all'),
       ('unforgettably'),
       ('calmness');
