DROP TABLE IF EXISTS `comment`, `post`, `role`, `user`, `menu`, `authority`;

CREATE TABLE IF NOT EXISTS `role` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL UNIQUE,
    `type` enum('primary', 'success', 'info', 'warning', 'danger') NULL DEFAULT 'info',
    `label` varchar(255) NOT NULL UNIQUE DEFAULT '',
    `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted` int NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user` (
    `id` int NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `password` char(32) NOT NULL,
    `avatar` varchar(255) NULL DEFAULT '',
    `nickname` varchar(255) NULL DEFAULT '',
    `email` varchar(255) NULL DEFAULT '',
    `phone` varchar(255) NULL DEFAULT '',
    `status` enum('active', 'frozen', 'deactivated', 'other') NULL DEFAULT 'active',
    `role_id` int NULL DEFAULT 2, -- 2 is the normal user role
    `registered_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted` int NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`role_id`) REFERENCES `role`(`id`),
    UNIQUE (`username`),
    CHECK ( `phone` REGEXP '^$|^[0-9]{11}$' ),
    CHECK ( `email` REGEXP '^$|^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$' )
);

CREATE TABLE IF NOT EXISTS `menu` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL UNIQUE,
    `route` varchar(255) NOT NULL DEFAULT '',
    `icon` varchar(255) NULL DEFAULT '',
    `title` varchar(255) NULL DEFAULT '',
    `hidden` bool NULL DEFAULT FALSE,
    `type` enum('menu', 'operation') NULL DEFAULT 'menu',
    `parent_id` int NULL DEFAULT 0,
    `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted` int NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`parent_id`) REFERENCES `menu`(`id`)
);

CREATE TABLE IF NOT EXISTS `authority` (
    `role_id` int NOT NULL,
    `menu_id` int NOT NULL,
    `authorized_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`role_id`, `menu_id`),
    FOREIGN KEY (`role_id`) REFERENCES `role`(`id`),
    FOREIGN KEY (`menu_id`) REFERENCES `menu`(`id`)
);

CREATE TABLE IF NOT EXISTS `post` (
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `title` varchar(255) NOT NULL,
                                      `content` text NOT NULL,
                                      `author_id` int NOT NULL,
                                      `published_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
                                      `deleted` int NULL DEFAULT 0,
                                      PRIMARY KEY (`id`),
                                      FOREIGN KEY (`author_id`) REFERENCES `user`(`id`)
);

CREATE TABLE IF NOT EXISTS `comment` (
                                         `id` int NOT NULL AUTO_INCREMENT,
                                         `content` text NOT NULL,
                                         `post_id` int NOT NULL,
                                         `author_id` int NOT NULL,
                                         `published_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
                                         `deleted` int NULL DEFAULT 0,
                                         PRIMARY KEY (`id`),
                                         FOREIGN KEY (`post_id`) REFERENCES `post`(`id`),
                                         FOREIGN KEY (`author_id`) REFERENCES `user`(`id`)
);