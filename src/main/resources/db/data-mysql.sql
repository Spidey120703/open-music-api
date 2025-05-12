DELETE FROM `role` WHERE 1;

INSERT INTO `role` (`id`, `name`, `type`, `label`)
VALUES
    (1, 'admin', 'danger', '管理员'),
    (2, 'user', 'primary', '用户'),
    (3, 'reviewer', 'success', '信息审核');


DELETE FROM `user` WHERE 1;

INSERT INTO `user` (`id`, `username`, `password`, `avatar`, `nickname`, `email`, `phone`, `role_id`)
VALUES (1, 'admin', '$2a$10$zptXJWZeHi3l.ZwPAMYwLOyo8O99hagBPKg8B7sUcQLA16SVhnNMG', '', 'Administrator',
        'admin@open-music.com', '12345678901', 1);


DELETE FROM `menu` WHERE 1;

SET SESSION sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

INSERT INTO `menu` (`id`, `name`, `route`, `icon`, `title`, `hidden`, `type`, `parent_id`)
VALUES (0, 'root', '/', '', '根目录', false, 'menu', null);

SET SESSION sql_mode = DEFAULT;

-- 路由初始配置备份
INSERT INTO `menu` (`id`, `name`, `route`, `icon`, `title`, `hidden`, `type`, `parent_id`)
VALUES
    (1, 'dashboard', '/dashboard', 'mdi:view-dashboard', '仪表盘', 0, 'menu', 0),
    (2, 'perm', '/perm', 'mdi:lock', '权限管理', 0, 'menu', 0),
    (3, 'perm-user', '/perm/user', 'mdi:account-group', '用户管理', 0, 'menu', 2),
    (4, 'perm-role', '/perm/role', 'mdi:key', '角色管理', 0, 'menu', 2),
    (5, 'sys', '/sys', 'mdi:cog', '系统管理', 0, 'menu', 0),
    (6, 'sys-menu', '/sys/menu', 'mdi:menu', '菜单配置', 0, 'menu', 5),
    (7, 'sys-home-page', '/sys/home-page', 'mdi:home', '首页配置', 0, 'menu', 5),
    (8, 'sys-log', '/sys/log', 'mdi:text-box', '日志管理', 0, 'menu', 5),
    (9, 'music', '/music', 'mdi:music', '音乐管理', 0, 'menu', 0),
    (10, 'music-artist', '/music/artist', 'mdi:microphone', '艺人管理', 0, 'menu', 9),
    (11, 'music-album', '/music/album', 'mdi:album', '专辑管理', 0, 'menu', 9),
    (12, 'music-song', '/music/song', 'mdi:playlist-music', '歌曲管理', 0, 'menu', 9),
    (13, 'content', '/content', 'mdi:book-open', '内容审核', 0, 'menu', 0),
    (14, 'content-post', '/content/post', 'mdi:book-open', '动态审核', 0, 'menu', 13),
    (15, 'content-comment', '/content/comment', 'mdi:comment-text', '评论审核', 0, 'menu', 13),
    (16, 'content-shared', '/content/shared', 'mdi:folder', '共享审核', 1, 'menu', 13),
    (17, 'account', '/account', 'mdi:account', '账户中心', 0, 'menu', 0),
    (18, 'account-info', '/account/info', 'mdi:account', '账户信息', 0, 'menu', 17),
    (19, 'account-password', '/account/password', 'mdi:shield-lock', '密码管理', 0, 'menu', 17),
    (20, 'account-device', '/account/device', 'mdi:desktop-mac', '设备管理', 1, 'menu', 17);


INSERT INTO `menu` (`id`, `name`, `title`, `type`, `parent_id`)
VALUES
    -- 用户管理
    (21,'perm:user:list','获取用户列表','operation',3),
    (22,'perm:user:get','获取用户详情','operation',3),
    (23,'perm:user:add','增加用户信息','operation',3),
    (24,'perm:user:edit','修改用户信息','operation',3),
    (25,'perm:user:delete','删除用户信息','operation',3),
    -- 角色管理
    (26,'perm:role:list','获取角色列表','operation',4),
    (27,'perm:role:get','获取角色详情','operation',4),
    (28,'perm:role:add','增加角色信息','operation',4),
    (29,'perm:role:edit','修改角色信息','operation',4),
    (30,'perm:role:delete','删除角色信息','operation',4),
    (31,'perm:role:grant','授予角色权限','operation',4),
    -- 菜单管理
    (32,'sys:menu:list','获取菜单列表','operation',6),
    (33,'sys:menu:get','获取菜单条目详情','operation',6),
    (34,'sys:menu:add','增加菜单条目信息','operation',6),
    (35,'sys:menu:edit','修改菜单条目信息','operation',6),
    (36,'sys:menu:delete','删除菜单条目信息','operation',6),
    -- 艺人信息管理
    (40,'music:artist:add','增加艺人信息','operation',10),
    (41,'music:artist:edit','修改艺人信息','operation',10),
    (42,'music:artist:delete','删除艺人信息','operation',10),
    -- 专辑信息管理
    (43,'music:album:add','增加专辑信息','operation',11),
    (44,'music:album:edit','修改专辑信息','operation',11),
    (45,'music:album:delete','删除专辑信息','operation',11),
    -- 歌曲信息管理
    (37,'music:song:add','增加歌曲信息','operation',12),
    (38,'music:song:edit','修改歌曲信息','operation',12),
    (39,'music:song:delete','删除歌曲信息','operation',12),
    -- 动态审核管理
    (46,'content:post:list','获取动态审核列表','operation',14),
    (47,'content:post:get','获取动态审核详情','operation',14),
    (48,'content:post:process','处理动态审核信息','operation',14),
    (49,'content:post:delete','删除专辑审核信息','operation',14),
    -- 评论审核管理
    (50,'content:comment:list','获取评论审核列表','operation',15),
    (51,'content:comment:get','获取评论审核详情','operation',15),
    (52,'content:comment:process','处理评论审核信息','operation',15),
    (53,'content:comment:delete','删除评论审核信息','operation',15),
    -- 共享审核管理
    (54,'content:shared:list','获取共享审核列表','operation',16),
    (55,'content:shared:get','获取共享审核详情','operation',16),
    (56,'content:shared:process','处理共享审核信息','operation',16),
    (57,'content:shared:delete','删除共享审核信息','operation',16);

INSERT INTO `permission`(`role_id`, `menu_id`)
VALUES
    (1,  1),
    (1,  2),
    (1,  3),
    (1,  4),
    (1,  5),
    (1,  6),
    (1,  7),
    (1,  8),
    (1,  9),
    (1, 10),
    (1, 11),
    (1, 12),
    (1, 13),
    (1, 14),
    (1, 15),
    (1, 16),
    (1, 17),
    (1, 18),
    (1, 19),
    (1, 20),
    (1, 21),
    (1, 22),
    (1, 23),
    (1, 24),
    (1, 25),
    (1, 26),
    (1, 27),
    (1, 28),
    (1, 29),
    (1, 30),
    (1, 31),
    (1, 32),
    (1, 33),
    (1, 34),
    (1, 35),
    (1, 36),
    (1, 37),
    (1, 38),
    (1, 39),
    (1, 40),
    (1, 41),
    (1, 42),
    (1, 43),
    (1, 44),
    (1, 45),
    (1, 46),
    (1, 47),
    (1, 48),
    (1, 49),
    (1, 50),
    (1, 51),
    (1, 52),
    (1, 53),
    (1, 54),
    (1, 55),
    (1, 56),
    (1, 57),
    (2, 17),
    (2, 18),
    (2, 19),
    (2, 20),
    (3, 1),
    (3, 9),
    (3, 10),
    (3, 11),
    (3, 12),
    (3, 13),
    (3, 14),
    (3, 15),
    (3, 16),
    (3, 17),
    (3, 18),
    (3, 19),
    (3, 20),
    (3, 37),
    (3, 38),
    (3, 39),
    (3, 40),
    (3, 41),
    (3, 42),
    (3, 43),
    (3, 44),
    (3, 45),
    (3, 46),
    (3, 47),
    (3, 48),
    (3, 50),
    (3, 51),
    (3, 52),
    (3, 54),
    (3, 55),
    (3, 56);

