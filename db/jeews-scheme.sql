CREATE DATABASE IF NOT EXISTS jeews;
USE jeews;

DROP TABLE IF EXISTS sys_user;
DROP TABLE IF EXISTS sys_organization;
DROP TABLE IF EXISTS sys_resource;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_role_resource;
DROP TABLE IF EXISTS sys_user_organization;
DROP TABLE IF EXISTS sys_user_role;
DROP TABLE IF EXISTS sys_log;
DROP TABLE IF EXISTS sys_dict;
DROP TABLE IF EXISTS oauth2_client;

CREATE TABLE sys_user (
  id bigint auto_increment NOT NULL COMMENT '编号',
  username varchar(100) NOT NULL COMMENT '用户名',
  password varchar(100) NOT NULL COMMENT '密码',
  salt varchar(100) NOT NULL COMMENT '盐',
  email varchar(100) COMMENT '邮箱',
  mobile varchar(20) COMMENT '手机号码',
  locked bool DEFAULT FALSE NOT NULL COMMENT '是否锁定',
  deleted bool DEFAULT FALSE NOT NULL COMMENT '是否删除',
  CONSTRAINT pk_sys_user PRIMARY key(id)
) charset=utf8 ENGINE=InnoDB COMMENT='用户';
CREATE UNIQUE INDEX idx_sys_user_username ON sys_user(username);
CREATE UNIQUE INDEX idx_sys_user_email ON sys_user(email);
CREATE UNIQUE INDEX idx_sys_user_mobile ON sys_user(mobile);

CREATE TABLE sys_organization (
  id bigint auto_increment NOT NULL COMMENT '编号',
  name varchar(100) NOT NULL COMMENT '名称',
  type varchar(20) NOT NULL COMMENT '类型',
  parent_id bigint NOT NULL COMMENT '父级编号',
  parent_ids varchar(100) NOT NULL COMMENT '所有父级编号',
  sort int NOT NULL COMMENT '排序',
  is_show bool DEFAULT FALSE NOT NULL COMMENT '是否显示',
  CONSTRAINT pk_sys_organization PRIMARY key(id)
) charset=utf8 ENGINE=InnoDB COMMENT='组织';
CREATE INDEX idx_sys_organization_name ON sys_organization(name);
CREATE INDEX idx_sys_organization_type ON sys_organization(TYPE);
CREATE INDEX idx_sys_organization_parent_id ON sys_organization(parent_id);
CREATE INDEX idx_sys_organization_parent_ids ON sys_organization(parent_ids);

CREATE TABLE sys_resource (
  id bigint auto_increment NOT NULL COMMENT '编号',
  name varchar(100) NOT NULL COMMENT '名称',
  permission varchar(100) COMMENT '权限标识',
  url varchar(200) COMMENT '链接',
  parent_id bigint NOT NULL COMMENT '父级编号',
  parent_ids varchar(100) NOT NULL COMMENT '所有父级编号',
  sort int NOT NULL COMMENT '排序',
  icon varchar(100) COMMENT '图标',
  is_show bool DEFAULT FALSE NOT NULL COMMENT '是否显示',
  CONSTRAINT pk_sys_resource PRIMARY key(id)
) charset=utf8 ENGINE=InnoDB COMMENT='资源';
CREATE INDEX idx_sys_resource_name ON sys_resource(name);
CREATE INDEX idx_sys_resource_parent_id ON sys_resource(parent_id);
CREATE INDEX idx_sys_resource_parent_ids ON sys_resource(parent_ids);

CREATE TABLE sys_role (
  id bigint auto_increment NOT NULL COMMENT '编号',
  name varchar(100) NOT NULL COMMENT '名称',
  cnname varchar(100) NOT NULL COMMENT '中文名称',
  available bool DEFAULT TRUE COMMENT '是否可用',
  CONSTRAINT pk_sys_role PRIMARY key(id)
) charset=utf8 ENGINE=InnoDB COMMENT='角色';
CREATE INDEX idx_sys_role_resource_name ON sys_role(name);
CREATE INDEX idx_sys_role_resource_role ON sys_role(cnname);

CREATE TABLE sys_role_resource (
  role_id bigint NOT NULL COMMENT '角色编号',
  resource_id bigint NOT NULL COMMENT '资源编号',
  PRIMARY KEY (role_id, resource_id)
) charset=utf8 ENGINE=InnoDB COMMENT='角色-菜单';

CREATE TABLE sys_user_organization (
  user_id bigint NOT NULL COMMENT '用户编号',
  organization_id bigint NOT NULL COMMENT '组织编号',
  PRIMARY KEY (user_id, organization_id)
) charset=utf8 ENGINE=InnoDB COMMENT='用户-组织';

CREATE TABLE sys_user_role (
  user_id bigint NOT NULL COMMENT '用户编号',
  role_id bigint NOT NULL COMMENT '角色编号',
  PRIMARY KEY (user_id, role_id)
) charset=utf8 ENGINE=InnoDB COMMENT='用户-角色';

CREATE TABLE sys_dict (
  id bigint NOT NULL COMMENT '编号',
  value varchar(100) NOT NULL COMMENT '数据值',
  label varchar(100) NOT NULL COMMENT '标签名',
  TYPE varchar(100) NOT NULL COMMENT '类型',
  description varchar(100) NOT NULL COMMENT '描述',
  CONSTRAINT pk_dict PRIMARY KEY(id)
) charset=utf8 ENGINE=InnoDB COMMENT='字典';

CREATE TABLE sys_log (
  id bigint NOT NULL COMMENT '编号',
  type char(1) DEFAULT '1' COMMENT '日志类型',
  title varchar(255) DEFAULT '' COMMENT '日志标题',
  create_by bigint COMMENT '创建者编号',
  create_date datetime COMMENT '创建时间',
  remote_addr varchar(255) COMMENT '操作IP地址',
  user_agent varchar(255) COMMENT '用户代理',
  request_uri varchar(255) COMMENT '请求URI',
  METHOD varchar(5) COMMENT '操作方式',
  exception text COMMENT '异常信息',
  CONSTRAINT pk_log PRIMARY KEY(id)
) charset=utf8 ENGINE=InnoDB COMMENT='日志';

CREATE TABLE oauth2_client (
  id bigint auto_increment COMMENT '编号',
  client_name varchar(100) COMMENT '客户端名称',
  client_id varchar(100) COMMENT '客户端编号',
  client_secret varchar(100) COMMENT '客户端密钥',
  CONSTRAINT pk_oauth2_client PRIMARY KEY(id)
) charset=utf8 ENGINE=InnoDB COMMENT='oauth客户端';
CREATE INDEX idx_oauth2_client_client_id ON oauth2_client(client_id);
