DELETE FROM `role` WHERE 1;

INSERT INTO `role` (`id`, `name`, `type`, `label`)
VALUES (1, 'admin', 'danger', '管理员'),
       (2, 'user', 'primary', '用户'),
       (3, 'reviewer', 'success', '信息审核');


DELETE FROM `user` WHERE 1;

INSERT INTO `user` (`id`, `username`, `password`, `avatar`, `nickname`, `email`, `phone`, `role_id`)
VALUES (1, 'admin', MD5('admin'), '', 'Administrator',
        'admin@open-music.com', '12345678901', 1);


DELETE FROM `menu` WHERE 1;

SET SESSION sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

INSERT INTO `menu` (`id`, `name`, `route`, `icon`, `title`, `hidden`, `type`, `parent_id`)
VALUES (0, 'root', '/', '', '根目录',
        false, 'menu', null);

SET SESSION sql_mode = DEFAULT;

-- 路由初始配置备份
INSERT INTO open_music.menu (id, name, route, icon, title, hidden, type, parent_id)
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
    (10, 'music-song', '/music/song', 'mdi:playlist-music', '歌曲管理', 0, 'menu', 9),
    (11, 'music-artist', '/music/artist', 'mdi:microphone', '艺人管理', 0, 'menu', 9),
    (12, 'music-album', '/music/album', 'mdi:album', '专辑管理', 0, 'menu', 9),
    (13, 'content', '/content', 'mdi:book-open', '内容审核', 0, 'menu', 0),
    (14, 'content-post', '/content/post', 'mdi:book-open', '动态审核', 0, 'menu', 13),
    (15, 'content-comment', '/content/comment', 'mdi:comment-text', '评论审核', 0, 'menu', 13),
    (16, 'content-share', '/content/share', 'mdi:folder', '共享审核', 1, 'menu', 13),
    (17, 'account', '/account', 'mdi:account', '账户中心', 0, 'menu', 0),
    (18, 'account-info', '/account/info', 'mdi:account', '账户信息', 0, 'menu', 17),
    (19, 'account-password', '/account/password', 'mdi:shield-lock', '密码管理', 0, 'menu', 17),
    (20, 'account-device', '/account/device', 'mdi:desktop-mac', '设备管理', 1, 'menu', 17),
