--liquibase formatted sql

--changeSet sasha:1
            insert into gift_certificate_tag(gift_id, tag_id)
            values (1, 1),
                   (1, 2),
                   (2, 3),
                   (2, 5),
                   (3, 3),
                   (3, 5),
                   (3, 2),
                   (4, 1),
                   (4, 4),
                   (5, 2),
                   (6, 1),
                   (6, 2),
                   (6, 4),
                   (6, 5),
                   (7, 1);