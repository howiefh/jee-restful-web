-- md5 iterations:2 password:u12345
INSERT INTO sys_user(id, username, password, salt, email, mobile, photo, login_ip, login_date, locked, created_by, created_at, updated_by, updated_at)
VALUES (1, "root",'49bc3813bdba3b3dc785a4e04e5bbfa2', '7253abca3e23f318c25f6e675d09525d', '10086@qq.com', '13100000000', NULL, NULL, NULL, FALSE, 0, now(), 0, now());

INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (1, '用户管理', 'user', '/users/', 0, '0', 1, NULL, 1, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (2, '查看', 'user:view', '/users/{id}', 1, '0,1', 2, NULL, 1, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (3, '创建', 'user:create', '/users/', 1, '0,1', 3, NULL, 1, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (4, '修改', 'user:update', '/users/{id}', 1, '0,1', 4, NULL, 1, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (5, '删除', 'user:delete', '/users/{id}', 1, '0,1', 5, NULL, 1, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (6, '角色管理', 'role', '/roles/', 0, '0', 6, NULL, 1, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (7, '查看', 'role:view', '/roles/{id}', 1, '0,6', 7, NULL, 1, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (8, '创建', 'role:create', '/roles/', 1, '0,6', 8, NULL, 1, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (9, '修改', 'role:update', '/roles/{id}', 1, '0,6', 9, NULL, 1, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (10, '删除', 'role:delete', '/roles/{id}', 1, '0,6', 10, NULL, 1, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (11, '组织管理', 'organization', '/organizations/', 0, '0', 11, NULL, 1, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (12, '查看', 'organization:view', '/organizations/{id}', 1, '0,11', 12, NULL, 1, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (13, '创建', 'organization:create', '/organizations/', 1, '0,11', 13, NULL, 1, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (14, '修改', 'organization:update', '/organizations/{id}', 1, '0,11', 14, NULL, 1, 1, now(), 1, now());
INSERT INTO sys_menu(id, name, permission, url, parent_id, parent_ids, sort, icon, is_show, created_by, created_at, updated_by, updated_at)
VALUES (15, '删除', 'organization:delete', '/organizations/{id}', 1, '0,11', 15, NULL, 1, 1, now(), 1, now());

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
VALUES (1, 6);
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (1, 7);
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (1, 8);
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (1, 9);
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (1, 10);
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (1, 11);
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (1, 12);
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (1, 13);
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (1, 14);
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (1, 15);
INSERT INTO sys_role_menu(role_id, menu_id)
VALUES (2, 2);

INSERT INTO sys_user_role(user_id, role_id)
VALUES (1, 1);

INSERT INTO sys_user_organization(user_id, organization_id)
VALUES (1, 1);

INSERT INTO oauth2_client
VALUES(1, 'web_client', 'c1ebe466-1cdc-4bd3-ab69-77c3561b9dee', 'd8346ea2-6017-43ed-ad68-19c0f971738b');
