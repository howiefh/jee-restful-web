create database if not exists jeews;
use jeews;

drop table if exists test_user;

create table test_user (
  id bigint auto_increment,
  username varchar(100) unique,
  password varchar(100),
  email varchar(100) unique,
  salt varchar(100),
  roles varchar(100),
  locked bool default false,
  constraint pk_test_user primary key(id)
) charset=utf8 ENGINE=InnoDB;

-- md5 iterations:2 password:123456
insert into test_user(id, username, password, email, salt, roles, locked)
values(null, "测试",'8eb86cd938cd1ecbe9b7316859d4da53', '10086@qq.com', 'b1c33fa8919f5f6378e4fc5e3eb839af', 'admin', false);
