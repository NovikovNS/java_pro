drop table if exists products;

create table if not exists products
(
    id     bigserial primary key,
    title  varchar(100),
    price  int(100)
);
--
-- insert into products (id, title, price) values (1, 'title1', 2);
-- insert into products (id, title, price) values (2, 'title2', 3);
