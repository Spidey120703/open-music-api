DROP TABLE IF EXISTS
    `comment`, `post`,
    `role`, `user`,
    `menu`, `permission`,
    `artist`, `album`, `song`, `artist_album`, `artist_song`;

CREATE TABLE IF NOT EXISTS `role`
(
    `id`         int                                                      NOT NULL AUTO_INCREMENT,
    `name`       varchar(255)                                             NOT NULL UNIQUE,
    `type`       enum ('primary', 'success', 'info', 'warning', 'danger') NULL            DEFAULT 'info',
    `label`      varchar(255)                                             NOT NULL UNIQUE DEFAULT '',
    `created_at` datetime                                                 NULL            DEFAULT CURRENT_TIMESTAMP,
    `deleted`    int                                                      NULL            DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user`
(
    `id`            int                                               NOT NULL AUTO_INCREMENT,
    `username`      varchar(255)                                      NOT NULL,
    `password`      char(128)                                         NOT NULL,
    `avatar`        varchar(1023)                                     NULL DEFAULT '',
    `nickname`      varchar(255)                                      NULL DEFAULT '',
    `email`         varchar(255)                                      NULL DEFAULT '',
    `phone`         varchar(255)                                      NULL DEFAULT '',
    `status`        enum ('active', 'frozen', 'deactivated', 'other') NULL DEFAULT 'active',
    `role_id`       int                                               NULL DEFAULT 2, -- 2 is the normal user role
    `registered_at` datetime                                          NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted`       int                                               NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
    UNIQUE (`username`),
    CHECK ( `phone` REGEXP '^$|^[0-9]{11}$' ),
    CHECK ( `email` REGEXP '^$|^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$' )
);

CREATE TABLE IF NOT EXISTS `menu`
(
    `id`         int                        NOT NULL AUTO_INCREMENT,
    `name`       varchar(255)               NOT NULL UNIQUE,
    `route`      varchar(255)               NOT NULL DEFAULT '',
    `icon`       varchar(255)               NULL     DEFAULT '',
    `title`      varchar(255)               NULL     DEFAULT '',
    `hidden`     bool                       NULL     DEFAULT FALSE,
    `type`       enum ('menu', 'operation') NULL     DEFAULT 'menu',
    `parent_id`  int                        NULL     DEFAULT 0,
    `created_at` datetime                   NULL     DEFAULT CURRENT_TIMESTAMP,
    `deleted`    int                        NULL     DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`)
);

CREATE TABLE IF NOT EXISTS `permission`
(
    `role_id`    int      NOT NULL,
    `menu_id`    int      NOT NULL,
    `granted_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`role_id`, `menu_id`),
    FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
    FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
);

-- 音乐信息

CREATE TABLE IF NOT EXISTS `artist`
(
    `id`         int          NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) NOT NULL,
    `nickname`   varchar(255) NULL,
    `cover`      varchar(255) NULL,
    `genre`      varchar(255) NULL,
    `bio`        text,
    `created_at` datetime     NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted`    int          NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `album`
(
    `id`           int                                                            NOT NULL AUTO_INCREMENT,
    `title`        varchar(255)                                                   NOT NULL,
    `cover`        varchar(255)                                                   NULL,
    `genre`        varchar(255)                                                   NULL,
    `release_date` date                                                           NULL,
    `total_discs`  int                                                            NOT NULL,
    `total_tracks` int                                                            NOT NULL,
    `duration`     int                                                            NULL,
    `type`         enum ('album', 'ep', 'single', 'compilation', 'live', 'other') NOT NULL DEFAULT 'other',
    `bio`          text,
    `artist_names` varchar(255)                                                   NOT NULL DEFAULT '',
    `created_at`   datetime                                                       NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted`      int                                                            NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `song`
(
    `id`           int          NOT NULL AUTO_INCREMENT,
    `disc_number`  int          NOT NULL DEFAULT 1,
    `track_number` int          NOT NULL DEFAULT 1,
    `title`        varchar(255) NOT NULL,
    `cover`        varchar(255) NULL,
    `genre`        varchar(255) NULL,
    `release_date` date         NULL,
    `lyric`        text,
    `bio`          text,
    `duration`     int          NULL,
    `album_id`     int          NULL,
    `album_name`   varchar(255) NOT NULL DEFAULT '',
    `artist_names` varchar(255) NOT NULL DEFAULT '',
    `created_at`   datetime     NULL     DEFAULT CURRENT_TIMESTAMP,
    `deleted`      int          NULL     DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`album_id`) REFERENCES `album` (`id`),
    UNIQUE (`disc_number`, `track_number`)
);

CREATE TABLE IF NOT EXISTS `artist_album`
(
    `artist_id`     int      NOT NULL,
    `album_id`      int      NOT NULL,
    `associated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`artist_id`) REFERENCES `artist` (`id`),
    FOREIGN KEY (`album_id`) REFERENCES `album` (`id`),
    PRIMARY KEY (`artist_id`, `album_id`)
);

CREATE TABLE IF NOT EXISTS `artist_song`
(
    `artist_id`     int      NOT NULL,
    `song_id`       int      NOT NULL,
    `associated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`artist_id`) REFERENCES `artist` (`id`),
    FOREIGN KEY (`song_id`) REFERENCES `song` (`id`),
    PRIMARY KEY (`artist_id`, `song_id`)
);

-- 动态与评论

CREATE TABLE IF NOT EXISTS `post`
(
    `id`           int          NOT NULL AUTO_INCREMENT,
    `title`        varchar(255) NOT NULL,
    `content`      text         NOT NULL,
    `author_id`    int          NOT NULL,
    `published_at` datetime     NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted`      int          NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`author_id`) REFERENCES `user` (`id`)
);

CREATE TABLE IF NOT EXISTS `comment`
(
    `id`           int                      NOT NULL AUTO_INCREMENT,
    `content`      text                     NOT NULL,
    `replied_type` enum ('post', 'comment') NOT NULL,
    `replied_id`   int                      NOT NULL,
    `author_id`    int                      NOT NULL,
    `published_at` datetime                 NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted`      int                      NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    -- FOREIGN KEY (`replied_id`) REFERENCES `post`(`id`),
    -- FOREIGN KEY (`replied_id`) REFERENCES `comment`(`id`),
    FOREIGN KEY (`author_id`) REFERENCES `user` (`id`)
);