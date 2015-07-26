-- md5 iterations:2 password:123456
INSERT INTO sys_user(id, username, password, salt, email, mobile, photo, login_ip, login_date, locked, created_by, created_at, updated_by, updated_at)
VALUES (1, "root",'8eb86cd938cd1ecbe9b7316859d4da53', 'b1c33fa8919f5f6378e4fc5e3eb839af', '10086@qq.com', '13100000000', NULL, NULL, NULL, FALSE, 0, now(), 0, now());

INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (1, '用户管理', 'user', '/users/', 0, '0', 1, NULL, 0, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (2, '查看', 'user:view', '/users/{id}', 1, '0,1', 2, NULL, 0, 1, now(), 1, now()); 
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (3, '创建', 'user:create', '/users/', 1, '0,1', 3, NULL, 0, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (4, '修改', 'user:update', '/users/{id}', 1, '0,1', 4, NULL, 0, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (5, '删除', 'user:delete', '/users/{id}', 1, '0,1', 5, NULL, 0, 1, now(), 1, now());

INSERT INTO sys_role(id, name, cnname, available, created_by, created_at, updated_by, updated_at)
VALUES (1, 'admin', '管理员', 1, 1, now(), 1, now());
INSERT INTO sys_role(id, name, cnname, available, created_by, created_at, updated_by, updated_at)
VALUES (2, 'normal_user', '普通用户', 1, 1, now(), 1, now());

INSERT INTO sys_organization(id, name, type, parent_id, parent_ids, sort, is_show, created_by, created_at, updated_by, updated_at)
VALUES (1, '总部', NULL, 0, '0', 0, 1, 1, now(), 1, now());

INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (1, 1); 
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (1, 2); 
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (1, 3); 
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (1, 4); 
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (1, 5); 
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (2, 2); 

INSERT INTO sys_user_role
VALUES (1, 1); 

INSERT INTO oauth2_client
VALUES(1, 'web_client', 'c1ebe466-1cdc-4bd3-ab69-77c3561b9dee', 'd8346ea2-6017-43ed-ad68-19c0f971738b');
