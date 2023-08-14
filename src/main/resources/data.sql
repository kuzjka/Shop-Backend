insert into users (id, username, password)
values (1, 'admin', '$2a$12$Wdbe4OEBhR/c2Hgbte184uarjDdY56Kb0Hif1d21vzA36XYkGo3rG');
insert into type(id, name)
values (1, 'Car');
insert into type(id, name)
values (2, 'Smartphone');
insert into brand(id, name)
values (1, 'Mercedes');
insert into brand(id, name)
values (2, 'BMW');
insert into brand(id, name)
values (3, 'Apple');
insert into brand(id, name)
values (4, 'Samsung');
insert into brand(id, name)
values (5, 'Audi');
insert into brand(id, name)
values (6, 'Volvo');
insert into type_brand(type_id, brand_id)
values (1, 1);
insert into type_brand(type_id, brand_id)
values (1, 2);
insert into type_brand(type_id, brand_id)
values (2, 3);
insert into type_brand(type_id, brand_id)
values (2, 4);
insert into product (id, name, price, type_id, brand_id)
values (1, 'S600', 200000, 1, 1);
insert into product (id, name, price, type_id, brand_id)
values (2, '750i', 150000, 1, 2);
insert into product (id, name, price, type_id, brand_id)
values (3, 'iPhone 15', 1000, 2, 3);
insert into product (id, name, price, type_id, brand_id)
values (4, 'Galaxy A32', 500, 2, 4);