DELETE FROM `role` WHERE 1;

INSERT INTO `role` (`id`, `name`, `type`)
VALUES (1, 'admin', 'primary'),
       (2, 'user', 'info');

DELETE FROM `user` WHERE 1;

INSERT INTO `user` (`id`, `username`, `password`, `avatar`, `nickname`, `email`, `phone`, `role_id`)
VALUES (1, 'admin', 'admin', '', 'Administrator',
        'admin@open-music.com', '12345678901', 1);