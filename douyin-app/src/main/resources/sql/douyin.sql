CREATE TABLE `user` (
    `user_id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(40) DEFAULT NULL,
    `follow_count` int DEFAULT '0',
    `follower_count` int DEFAULT '0',
    `password` char(40) DEFAULT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `video` (
     `video_id` bigint unsigned NOT NULL AUTO_INCREMENT,
     `user_id` bigint DEFAULT NULL,
     `play_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
     `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
     `favorite_count` int DEFAULT '0',
     `comment_count` int DEFAULT '0',
     `title` longtext,
     `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `relation` (
    `relation_id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `follower_id` bigint DEFAULT NULL,
    `followee_id` bigint DEFAULT NULL,
    `is_deleted` tinyint DEFAULT '0',
    PRIMARY KEY (`relation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `favorite` (
    `favorite_id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `user_id` bigint DEFAULT NULL,
    `video_id` bigint DEFAULT NULL,
    `is_deleted` tinyint DEFAULT '0',
    PRIMARY KEY (`favorite_id`),
    UNIQUE KEY `ux_favorite_user_id_video_id` (`user_id`,`video_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `comment` (
   `comment_id` bigint unsigned NOT NULL AUTO_INCREMENT,
   `user_id` bigint DEFAULT NULL,
   `video_id` bigint DEFAULT NULL,
   `content` varchar(40) DEFAULT NULL,
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `is_deleted` tinyint DEFAULT '0',
   PRIMARY KEY (`comment_id`),
   KEY `create_time` (`create_time`),
   KEY `video_id` (`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;