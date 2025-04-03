-- 角色
INSERT INTO mk_sys_role (id, role_name, role_code, role_desc) VALUES (1, '超级管理员', 'super_admin', '系统超级管理员');
INSERT INTO mk_sys_role (id, role_name, role_code, role_desc) VALUES (2, '管理员', 'admin', '系统管理员');


-- 管理后台用户
INSERT INTO mk_sys_user (id, username, password, real_name, salt, phone, avatar, dept_id, lock_flag, email, user_type) VALUES (1, 'miku', '7Ce71kR0ofkgW7E+5GIiYg==', '初音未来', 'qiseyun', '18180731964', '', 0, 0, '2388581855@qq.com', 2);



-- 用户-角色
INSERT INTO mk_sys_user_role (user_id, role_id) VALUES (1, 1);
