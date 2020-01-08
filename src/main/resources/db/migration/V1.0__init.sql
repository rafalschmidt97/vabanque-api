create table account
(
    id           bigint      not null auto_increment,
    email        varchar(50) not null unique,
    password     varchar(60) not null,
    nickname     varchar(50) unique,
    phone_number varchar(30),
    avatar       varchar(2048),
    created_at   datetime(6) not null default current_timestamp(6),
    primary key (id)
) engine = INNODB;

create table debtor
(
    id                  bigint      not null auto_increment,
    creditor_account_id bigint      not null,
    debtor_account_id   bigint      not null,
    amount              varchar(30) not null,
    created_at          datetime(6) not null default current_timestamp(6),
    is_removed          bit(1)      not null default 0,
    removed_at          datetime(6),
    primary key (id),
    foreign key (creditor_account_id) references account (id) on delete cascade,
    foreign key (debtor_account_id) references account (id) on delete cascade
) engine = INNODB;

create table refresh_token
(
    id         bigint      not null auto_increment,
    issued_at  datetime(6) not null default current_timestamp(6),
    expired_at datetime(6) not null,
    token      varchar(50) not null,
    account_id bigint      not null,
    primary key (id),
    foreign key (account_id) references account (id) on delete cascade
) engine = INNODB;
