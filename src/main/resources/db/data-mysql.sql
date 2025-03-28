DELETE FROM `role` WHERE 1;

INSERT INTO `role` (`id`, `name`, `type`, description)
VALUES (1, 'admin', 'danger', '管理员'),
       (2, 'user', 'primary', '用户');

DELETE FROM `user` WHERE 1;

INSERT INTO `user` (`id`, `username`, `password`, `avatar`, `nickname`, `email`, `phone`, `role_id`)
VALUES (1, 'admin', 'admin', '', 'Administrator',
        'admin@open-music.com', '12345678901', 1);