create table users
(
    id        int8 primary key not null,
    step_code varchar(100), -- код этапа
    text      varchar(100), -- произвольный текст
    accept    varchar(3) -- данные из кнопок
);