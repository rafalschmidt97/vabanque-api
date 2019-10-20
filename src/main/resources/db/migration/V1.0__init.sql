create table account
(
    id           bigserial   not null primary key,
    email        varchar(50) not null unique,
    password     varchar(60) not null,
    nickname     varchar(50) unique,
    phone_number varchar(30),
    avatar       varchar(2048),
    created_at   timestamp   not null default current_timestamp
);

create table refresh_token
(
    id         bigserial   not null primary key,
    issued_at  timestamp   not null default current_timestamp,
    expired_at timestamp   not null,
    token      varchar(50) not null,
    account_id int8        not null references account (id) on delete cascade
);

create table debtor
(
    id                  bigserial   not null primary key,
    creditor_account_id int8        not null references account (id) on delete cascade,
    debtor_account_id   int8        not null references account (id) on delete cascade,
    amount              varchar(30) not null,
    created_at          timestamp   not null default current_timestamp,
    is_removed          boolean     not null default false,
    removed_at          timestamp
);
