create table if not exists test1(id serial);
create table if not exists test2(id serial);

insert into test1(id) values (1), (2), (3);
insert into test2(id) values (4), (5), (6);
