CREATE DATABASE IF NOT EXISTS jeews;
USE jeews;

DROP TABLE IF EXISTS sys_role_menu;
DROP TABLE IF EXISTS sys_user_organization;
DROP TABLE IF EXISTS sys_user_role;
DROP TABLE IF EXISTS sys_user;
DROP TABLE IF EXISTS sys_organization;
DROP TABLE IF EXISTS sys_menu;
DROP TABLE IF EXISTS sys_role;
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
  photo varchar(100) COMMENT '用户头像',
  login_ip varchar(100) COMMENT '最后登陆IP',
  login_date datetime DEFAULT '0000-00-00 00:00:00' COMMENT '最后登陆时间',
  locked bool DEFAULT FALSE NOT NULL COMMENT '是否锁定',
  created_by bigint COMMENT '创建者',
  created_at datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  updated_by bigint COMMENT '更新者',
  updated_at datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  CONSTRAINT pk_sys_user PRIMARY key(id)
) charset=utf8 ENGINE=InnoDB COMMENT='用户';
CREATE UNIQUE INDEX idx_sys_user_username ON sys_user(username);
CREATE UNIQUE INDEX idx_sys_user_email ON sys_user(email);
CREATE UNIQUE INDEX idx_sys_user_mobile ON sys_user(mobile);

CREATE TABLE sys_organization (
  id bigint auto_increment NOT NULL COMMENT '编号',
  name varchar(100) NOT NULL COMMENT '名称',
  type varchar(20) COMMENT '类型',
  parent_id bigint NOT NULL COMMENT '父级编号',
  parent_ids varchar(100) NOT NULL COMMENT '所有父级编号',
  sort int NOT NULL COMMENT '排序',
  is_show bool DEFAULT FALSE NOT NULL COMMENT '是否显示',
  created_by bigint COMMENT '创建者',
  created_at datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  updated_by bigint COMMENT '更新者',
  updated_at datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  CONSTRAINT pk_sys_organization PRIMARY key(id)
) charset=utf8 ENGINE=InnoDB COMMENT='组织';
CREATE INDEX idx_sys_organization_name ON sys_organization(name);
CREATE INDEX idx_sys_organization_type ON sys_organization(TYPE);
CREATE INDEX idx_sys_organization_parent_id ON sys_organization(parent_id);
CREATE INDEX idx_sys_organization_parent_ids ON sys_organization(parent_ids);

CREATE TABLE sys_menu (
  id bigint auto_increment NOT NULL COMMENT '编号',
  name varchar(100) NOT NULL COMMENT '名称',
  permission varchar(100) COMMENT '权限标识',
  url varchar(200) COMMENT '链接',
  parent_id bigint NOT NULL COMMENT '父级编号',
  parent_ids varchar(100) NOT NULL COMMENT '所有父级编号',
  sort int NOT NULL COMMENT '排序',
  icon varchar(100) COMMENT '图标',
  is_show bool DEFAULT FALSE NOT NULL COMMENT '是否显示',
  created_by bigint COMMENT '创建者',
  created_at datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  updated_by bigint COMMENT '更新者',
  updated_at datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  CONSTRAINT pk_sys_menu PRIMARY key(id)
) charset=utf8 ENGINE=InnoDB COMMENT='资源';
CREATE INDEX idx_sys_menu_name ON sys_menu(name);
CREATE INDEX idx_sys_menu_parent_id ON sys_menu(parent_id);
CREATE INDEX idx_sys_menu_parent_ids ON sys_menu(parent_ids);

CREATE TABLE sys_role (
  id bigint auto_increment NOT NULL COMMENT '编号',
  name varchar(100) NOT NULL COMMENT '名称',
  cnname varchar(100) NOT NULL COMMENT '中文名称',
  available bool DEFAULT TRUE COMMENT '是否可用',
  created_by bigint COMMENT '创建者',
  created_at datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  updated_by bigint COMMENT '更新者',
  updated_at datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  CONSTRAINT pk_sys_role PRIMARY key(id)
) charset=utf8 ENGINE=InnoDB COMMENT='角色';
CREATE INDEX idx_sys_role_name ON sys_role(name);
CREATE INDEX idx_sys_role_cnname ON sys_role(cnname);

CREATE TABLE sys_role_menu (
  role_id bigint NOT NULL COMMENT '角色编号',
  menu_id bigint NOT NULL COMMENT '资源编号',
  CONSTRAINT pk_sys_role_menu PRIMARY KEY (role_id, menu_id),
  CONSTRAINT fk_sys_role_menu_sys_role_id FOREIGN KEY (role_id) REFERENCES sys_role (id) ON DELETE CASCADE,
  CONSTRAINT fk_sys_role_menu_sys_menu_id FOREIGN KEY (menu_id) REFERENCES sys_menu (id) ON DELETE CASCADE
) charset=utf8 ENGINE=InnoDB COMMENT='角色-菜单';

CREATE TABLE sys_user_organization (
  user_id bigint NOT NULL COMMENT '用户编号',
  organization_id bigint NOT NULL COMMENT '组织编号',
  CONSTRAINT pk_sys_user_organization PRIMARY KEY (user_id, organization_id),
  CONSTRAINT fk_sys_user_organization_sys_user_id FOREIGN KEY (user_id) REFERENCES sys_user (id) ON DELETE CASCADE,
  CONSTRAINT fk_sys_user_organization_sys_organization_id FOREIGN KEY (organization_id) REFERENCES sys_organization (id) ON DELETE CASCADE
) charset=utf8 ENGINE=InnoDB COMMENT='用户-组织';

CREATE TABLE sys_user_role (
  user_id bigint NOT NULL COMMENT '用户编号',
  role_id bigint NOT NULL COMMENT '角色编号',
  CONSTRAINT pk_sys_user_role PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_sys_user_role_sys_user_id FOREIGN KEY (user_id) REFERENCES sys_user (id) ON DELETE CASCADE,
  CONSTRAINT fk_sys_user_role_sys_role_id FOREIGN KEY (role_id) REFERENCES sys_role (id) ON DELETE CASCADE
) charset=utf8 ENGINE=InnoDB COMMENT='用户-角色';

CREATE TABLE sys_dict (
  id bigint NOT NULL COMMENT '编号',
  value varchar(100) NOT NULL COMMENT '数据值',
  label varchar(100) NOT NULL COMMENT '标签名',
  type varchar(100) NOT NULL COMMENT '类型',
  description varchar(100) NOT NULL COMMENT '描述',
  created_by bigint COMMENT '创建者',
  created_at datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  updated_by bigint COMMENT '更新者',
  updated_at datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  CONSTRAINT pk_dict PRIMARY KEY(id)
) charset=utf8 ENGINE=InnoDB COMMENT='字典';

CREATE TABLE sys_log (
  id bigint NOT NULL COMMENT '编号',
  type char(1) DEFAULT '1' COMMENT '日志类型',
  title varchar(255) DEFAULT '' COMMENT '日志标题',
  create_by bigint COMMENT '创建者编号',
  create_date datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  remote_addr varchar(255) COMMENT '操作IP地址',
  user_agent varchar(255) COMMENT '用户代理',
  request_uri varchar(255) COMMENT '请求URI',
  method varchar(5) COMMENT '操作方式',
  exception varchar(255) COMMENT '异常信息',
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

DROP TABLE IF EXISTS sys_role_menu_audit;
DROP TABLE IF EXISTS sys_user_role_audit;
DROP TABLE IF EXISTS sys_user_organization_audit;
DROP TABLE IF EXISTS sys_organization_audit;
DROP TABLE IF EXISTS sys_menu_audit;
DROP TABLE IF EXISTS sys_role_audit;
DROP TABLE IF EXISTS sys_user_audit;
DROP TABLE IF EXISTS sys_dict_audit;
DROP TRIGGER IF EXISTS tgr_sys_dict_delete;
DROP TRIGGER IF EXISTS tgr_sys_user_delete;
DROP TRIGGER IF EXISTS tgr_sys_role_delete;
DROP TRIGGER IF EXISTS tgr_sys_menu_delete;
DROP TRIGGER IF EXISTS tgr_sys_organization_delete;
DROP TRIGGER IF EXISTS tgr_sys_user_organization_delete;
DROP TRIGGER IF EXISTS tgr_sys_user_role_delete;
DROP TRIGGER IF EXISTS tgr_sys_role_menu_delete;
-- 角色菜单审计表，存放删除的数据记录
CREATE TABLE sys_role_menu_audit SELECT * FROM sys_role_menu WHERE 1 = 2;
ALTER TABLE sys_role_menu_audit ADD PRIMARY KEY (id);
ALTER TABLE sys_role_menu_audit ADD deleted_at datetime NOT NULL COMMENT '删除时间';

-- 删除角色菜单表的数据后触发此触发器，将删除的数据以及当前时间加入审计表中
DELIMITER $$
CREATE TRIGGER tgr_sys_role_menu_delete
AFTER DELETE ON sys_role_menu 
FOR EACH ROW
BEGIN
    INSERT INTO sys_role_menu_audit (role_id, menu_id, deleted_at)
    VALUES (OLD.role_id, OLD.menu_id, now());
END;
$$
DELIMITER ;

-- 用户角色审计表，存放删除的数据记录
CREATE TABLE sys_user_role_audit SELECT * FROM sys_user_role WHERE 1 = 2;
ALTER TABLE sys_user_role_audit ADD PRIMARY KEY (id);
ALTER TABLE sys_user_role_audit ADD deleted_at datetime NOT NULL COMMENT '删除时间';

-- 删除用户角色表的数据后触发此触发器，将删除的数据以及当前时间加入审计表中
DELIMITER $$
CREATE TRIGGER tgr_sys_user_role_delete
AFTER DELETE ON sys_user_role 
FOR EACH ROW
BEGIN
    INSERT INTO sys_user_role_audit (user_id, role_id, deleted_at)
    VALUES (OLD.user_id, OLD.role_id, now());
END;
$$
DELIMITER ;

-- 用户组织审计表，存放删除的数据记录
CREATE TABLE sys_user_organization_audit SELECT * FROM sys_user_organization WHERE 1 = 2;
ALTER TABLE sys_user_organization_audit ADD PRIMARY KEY (id);
ALTER TABLE sys_user_organization_audit ADD deleted_at datetime NOT NULL COMMENT '删除时间';

-- 删除用户组织表的数据后触发此触发器，将删除的数据以及当前时间加入审计表中
DELIMITER $$
CREATE TRIGGER tgr_sys_user_organization_delete
AFTER DELETE ON sys_user_organization 
FOR EACH ROW
BEGIN
    INSERT INTO sys_user_organization_audit (user_id, organization_id, deleted_at)
    VALUES (OLD.user_id, OLD.organization_id, now());
END;
$$
DELIMITER ;

-- 组织审计表，存放删除的数据记录
CREATE TABLE sys_organization_audit SELECT * FROM sys_organization WHERE 1 = 2;
ALTER TABLE sys_organization_audit ADD PRIMARY KEY (id);
ALTER TABLE sys_organization_audit ADD deleted_at datetime NOT NULL COMMENT '删除时间';

-- 删除组织表的数据后触发此触发器，将删除的数据以及当前时间加入审计表中
DELIMITER $$
CREATE TRIGGER tgr_sys_organization_delete
AFTER DELETE ON sys_organization 
FOR EACH ROW
BEGIN
    INSERT INTO sys_organization_audit (id, name, type, parent_id, parent_ids, sort, is_show, created_by, created_at, updated_by, updated_at, deleted_at)
    VALUES (OLD.id, OLD.name, OLD.type, OLD.parent_id, OLD.parent_ids, OLD.sort, OLD.is_show, OLD.created_by, OLD.created_at, OLD.updated_by, OLD.updated_at, now());
END;
$$
DELIMITER ;

-- 菜单审计表，存放删除的数据记录
CREATE TABLE sys_menu_audit SELECT * FROM sys_menu WHERE 1 = 2;
ALTER TABLE sys_menu_audit ADD PRIMARY KEY (id);
ALTER TABLE sys_menu_audit ADD deleted_at datetime NOT NULL COMMENT '删除时间';

-- 删除菜单表的数据后触发此触发器，将删除的数据以及当前时间加入审计表中
DELIMITER $$
CREATE TRIGGER tgr_sys_menu_delete
AFTER DELETE ON sys_menu 
FOR EACH ROW
BEGIN
    INSERT INTO sys_menu_audit (id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at, deleted_at)
    VALUES (OLD.id, OLD.name, OLD.permission, OLD.url, OLD.parent_id, OLD.parent_ids, OLD.sort, OLD.icon, OLD.is_show, OLD.created_by, OLD.created_at, OLD.updated_by, OLD.updated_at, now());
END;
$$
DELIMITER ;

-- 角色审计表，存放删除的数据记录
CREATE TABLE sys_role_audit SELECT * FROM sys_role WHERE 1 = 2;
ALTER TABLE sys_role_audit ADD PRIMARY KEY (id);
ALTER TABLE sys_role_audit ADD deleted_at datetime NOT NULL COMMENT '删除时间';

-- 删除角色表的数据后触发此触发器，将删除的数据以及当前时间加入审计表中
DELIMITER $$
CREATE TRIGGER tgr_sys_role_delete
AFTER DELETE ON sys_role 
FOR EACH ROW
BEGIN
    INSERT INTO sys_role_audit (id, name, cnname, available, created_by, created_at, updated_by, updated_at, deleted_at)
    VALUES (OLD.id, OLD.name, OLD.cnname, OLD.available, OLD.created_by, OLD.created_at, OLD.updated_by, OLD.updated_at, now());
END;
$$
DELIMITER ;

-- 用户审计表，存放删除的数据记录
CREATE TABLE sys_user_audit SELECT * FROM sys_user WHERE 1 = 2;
ALTER TABLE sys_user_audit ADD PRIMARY KEY (id);
ALTER TABLE sys_user_audit ADD deleted_at datetime NOT NULL COMMENT '删除时间';

-- 删除用户表的数据后触发此触发器，将删除的数据以及当前时间加入审计表中
DELIMITER $$
CREATE TRIGGER tgr_sys_user_delete
AFTER DELETE ON sys_user 
FOR EACH ROW
BEGIN
    INSERT INTO sys_user_audit (id, username, password, salt, email, mobile, photo, login_ip, login_date, locked, created_by, created_at, updated_by, updated_at, deleted_at)
    VALUES (OLD.id, OLD.username, OLD.password, OLD.salt, OLD.email, OLD.mobile, OLD.photo, OLD.login_ip, OLD.login_date, OLD.locked, OLD.created_by, OLD.created_at, OLD.updated_by, OLD.updated_at, now());
END;
$$
DELIMITER ;

-- 字典审计表，存放删除的数据记录
CREATE TABLE sys_dict_audit SELECT * FROM sys_dict WHERE 1 = 2;
ALTER TABLE sys_dict_audit ADD PRIMARY KEY (id);
ALTER TABLE sys_dict_audit ADD deleted_at datetime NOT NULL COMMENT '删除时间';

-- 删除字典表的数据后触发此触发器，将删除的数据以及当前时间加入审计表中
DELIMITER $$
CREATE TRIGGER tgr_sys_dict_delete
AFTER DELETE ON sys_dict 
FOR EACH ROW
BEGIN
    INSERT INTO sys_dict_audit (id, value, label, type, description, created_by, created_at, updated_by, updated_at, deleted_at)
    VALUES (OLD.id, OLD.value, OLD.label, OLD.type, OLD.description, OLD.created_by, OLD.created_at, OLD.updated_by, OLD.updated_at, now());
END;
$$
DELIMITER ;
