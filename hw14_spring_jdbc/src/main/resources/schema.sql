drop table if exists books;

create table books
(
    id        bigint primary key auto_increment,
    name      varchar(255),
    author    varchar(255),
    style     varchar(255)
);
