create sequence account_seq_id start 1 increment 1;

create table account
(
    id           int8        not null primary key,
    email        varchar(50) not null unique,
    password     varchar(50) not null,
    nickname     varchar(30) unique,
    phone_number varchar(30),
    avatar       varchar(2048)
);
