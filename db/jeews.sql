create database if not exists jeews;
use jeews;

drop table if exists sys_user;

create table sys_user (
  id bigint auto_increment,
  username varchar(100),
  password varchar(100),
  email varchar(100) unique,
  salt varchar(100),
  roles varchar(100),
  locked bool default false,
  constraint pk_sys_user primary key(id)
) charset=utf8 ENGINE=InnoDB;
create unique index idx_sys_user_username on sys_user(username);
create table oauth2_client (
  id bigint auto_increment,
  client_name varchar(100),
  client_id varchar(100),
  client_secret varchar(100),
  constraint pk_oauth2_client primary key(id)
) charset=utf8 ENGINE=InnoDB;
create index idx_oauth2_client_client_id on oauth2_client(client_id);

-- md5 iterations:2 password:123456
insert into sys_user(id, username, password, email, salt, roles, locked)
values(null, "root",'8eb86cd938cd1ecbe9b7316859d4da53', '10086@qq.com', 'b1c33fa8919f5f6378e4fc5e3eb839af', '[admin]', false);

insert into oauth2_client values(1,'chapter17-client','c1ebe466-1cdc-4bd3-ab69-77c3561b9dee','d8346ea2-6017-43ed-ad68-19c0f971738b');
