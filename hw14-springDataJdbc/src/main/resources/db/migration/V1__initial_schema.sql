create table client
(
    id    bigserial   not null primary key,
    name varchar(50)
);

create table phone
(
    id bigserial      not null primary key,
    number varchar(50),
    client_id      bigserial references client(id)
);

create table address
(
    id           bigserial   not null primary key,
    street         varchar(50) not null,
    client_id      bigserial references client(id)
);