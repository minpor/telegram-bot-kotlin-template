create table users
(
    id        int8 primary key not null,
    step_code varchar(100),
    text      varchar(100),
    accept    varchar(3)
);