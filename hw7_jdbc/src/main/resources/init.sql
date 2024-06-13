drop table if exists products;

create table if not exists products
(
    id     bigserial primary key,
    title  varchar(100),
    price  int(100)
);
