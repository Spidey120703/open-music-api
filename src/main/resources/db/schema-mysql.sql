DROP TABLE IF EXISTS `post`, `role`, `user`;

CREATE TABLE IF NOT EXISTS `role` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL UNIQUE,
    `type` enum('primary', 'success', 'info', 'warning', 'danger') NULL DEFAULT 'info',
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user` (
    `id` int NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `avatar` varchar(255) NULL DEFAULT '',
    `nickname` varchar(255) NULL DEFAULT '',
    `email` varchar(255) NULL DEFAULT '',
    `phone` varchar(255) NULL DEFAULT '',
    `status` enum('active', 'frozen', 'deactivated', 'other') NULL DEFAULT 'active',
    `role_id` int NULL DEFAULT 2, -- 2 is the normal user role
    `registered_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`role_id`) REFERENCES `role`(`id`),
    UNIQUE (`username`),
    CHECK ( `phone` REGEXP '^[0-9]{11}$' ),
    CHECK ( `email` REGEXP '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$' )
);

CREATE TABLE IF NOT EXISTS `post` (
    `id` int NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    `content` text NOT NULL,
    `published_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
    `author_id` int NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`author_id`) REFERENCES `user`(`id`)
);