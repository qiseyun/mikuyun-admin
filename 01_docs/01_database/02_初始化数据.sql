-- 角色
INSERT INTO mk_sys_role (id, role_name, role_code, role_desc) VALUES (1, '超级管理员', 'super_admin', '超级管理员');

-- 管理后台用户 密码:123456
INSERT INTO mk_sys_user (id, username, password, real_name, salt, phone, avatar, dept_id, lock_flag, email) VALUES (1, 'miku', 'dbkxLI9tR9mrHo1NDfEoIw==', '超级管理员', 'mikuyun', '18180731964', '', 0, 0, 'xxxx@qq.com');

-- 系统权限
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (1, '系统配置', 'system:config:page', 0, 0, -1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (2, '系统配置新增', 'system:config:add', 1, 0, 1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (3, '系统配置编辑', 'system:config:edit', 1, 0, 1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (4, '系统用户管理', 'system:user:page', 0, 0, -1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (5, '系统用户新增', 'system:user:add', 4, 0, 1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (6, '系统用户编辑', 'system:user:edit', 4, 0, 1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (7, '系统用户删除', 'system:user:delete', 4, 0, 1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (8, '角色管理', 'system:role:page', 0, 0, -1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (9, '角色新增', 'system:role:add', 8, 0, 1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (10, '角色编辑', 'system:role:edit', 8, 0, 1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (11, '角色删除', 'system:role:delete', 8, 0, 1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (12, '角色权限设置', 'system:role:permission', 8, 0, 1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (13, '权限管理', 'system:menu:page', 0, 0, -1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (14, '权限新增', 'system:menu:add', 13, 0, 1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (15, '权限编辑', 'system:menu:edit', 13, 0, 1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (16, '权限删除', 'system:menu:delete', 13, 0, 1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (17, '城市地区管理', 'system:region:page', 0, 0, -1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (18, '用户角色编辑', 'system:user:edit_role', 4, 0, 1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (19, '系统配置删除', 'system:config:delete', 1, 0, 1, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (20, '城市地区管理_view', 'system:region:page_view', 17, 0, 0, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (21, '系统配置_view', 'system:config:page_view', 1, 0, 0, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (22, '系统用户管理_view', 'system:user:page_view', 4, 0, 0, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (23, '角色管理_view', 'system:role:page_view', 8, 0, 0, '');
INSERT INTO mk_sys_menu (id, name, permission, parent_id, keep_alive, type, `describe`) VALUES (24, '权限管理_view', 'system:menu:page_view', 13, 0, 0, '');

-- 用户角色
INSERT INTO mk_sys_user_role (user_id, role_id) VALUES (1, 1);

-- 角色权限
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 1);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 2);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 3);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 4);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 5);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 6);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 7);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 8);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 9);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 10);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 11);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 12);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 13);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 14);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 15);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 16);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 17);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 18);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 19);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 20);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 21);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 22);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 23);
INSERT INTO mk_sys_role_menu (role_id, menu_id) VALUES (1, 24);


