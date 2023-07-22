create table driver
(
    id     serial primary key,
    name   varchar(50) not null,
    age    int         not null default 18,
    car_id int references car (id)
);
create table car
(
    id serial primary key,
    manufacturer   varchar(50) not null,
    model   varchar(50) not null,
    price numeric (10,2)
);