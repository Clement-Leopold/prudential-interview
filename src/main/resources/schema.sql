CREATE TABLE t_car_stock(
  auto_id INTEGER auto_increment PRIMARY KEY,
  car_model  INTEGER not null,
  stock integer not null);

create table t_car_rent (
  auto_id INTEGER  auto_increment primary key,
  id varchar(255) not null,
  username varchar(256) not null,
  car_model tinyint  not null,
  reserve_seconds INTEGER  not null,
  returned tinyint not null default 0);

create table t_car_model(
   car_model INTEGER not null,
   car_model_name varchar(100) not null);

