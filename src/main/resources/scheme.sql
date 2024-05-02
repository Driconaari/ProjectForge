create table users
(
    id       bigint               not null
        primary key,
    username varchar(255)         not null,
    email    varchar(255)         not null,
    password varchar(255)         not null,
    roles    varchar(255)         not null,
    isAdmin  tinyint(1)           not null,
    is_admin tinyint(1) default 0 null
);

