/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80034 (8.0.34)
 Source Host           : localhost:3306
 Source Schema         : warrenblog

 Target Server Type    : MySQL
 Target Server Version : 80034 (8.0.34)
 File Encoding         : 65001

 Date: 18/04/2025 13:39:40
*/

USE warrenblog;

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ms_admin
-- ----------------------------
DROP TABLE IF EXISTS `ms_admin`;
CREATE TABLE `ms_admin`
(
    `id`       bigint                                                        NOT NULL AUTO_INCREMENT,
    `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ms_admin_permission
-- ----------------------------
DROP TABLE IF EXISTS `ms_admin_permission`;
CREATE TABLE `ms_admin_permission`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `admin_id`      bigint NOT NULL,
    `permission_id` bigint NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ms_article
-- ----------------------------
DROP TABLE IF EXISTS `ms_article`;
<<<<<<< HEAD
CREATE TABLE `ms_article`
(
    `id`             bigint NOT NULL AUTO_INCREMENT,
    `comment_counts` int NULL DEFAULT NULL COMMENT '评论数量',
    `create_date`    datetime NULL DEFAULT NULL,
    `summary`        varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '简介',
    `title`          varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题',
    `view_counts`    int NULL DEFAULT NULL COMMENT '浏览数量',
    `weight`         int    NOT NULL COMMENT '是否置顶',
    `author_id`      bigint NULL DEFAULT NULL COMMENT '作者id',
    `body_id`        bigint NULL DEFAULT NULL COMMENT '内容id',
    `category_id`    int NULL DEFAULT NULL COMMENT '类别id',
    `likes`          int NULL DEFAULT NULL COMMENT '点赞数',
    `collect`        int NULL DEFAULT NULL COMMENT '收藏数',
    PRIMARY KEY (`id`) USING BTREE
=======
CREATE TABLE `ms_article`  (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `comment_counts` int NULL DEFAULT NULL COMMENT '评论数量',
                               `create_date` datetime NULL DEFAULT NULL,
                               `summary` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '简介',
                               `title` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题',
                               `view_counts` int NULL DEFAULT NULL COMMENT '浏览数量',
                               `weight` int NOT NULL COMMENT '是否置顶',
                               `author_id` bigint NULL DEFAULT NULL COMMENT '作者id',
                               `body_id` bigint NULL DEFAULT NULL COMMENT '内容id',
                               `category_id` int NULL DEFAULT NULL COMMENT '类别id',
                               `likes` int NULL DEFAULT NULL COMMENT '点赞数',
                               `collect` int NULL DEFAULT NULL COMMENT '收藏数',
                               `picture-url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '封面',
                               PRIMARY KEY (`id`) USING BTREE
>>>>>>> 129a09a9d62a77f0914d885c1cb589e7b50629cf
) ENGINE = InnoDB AUTO_INCREMENT = 1903332362394615810 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ms_article_body
-- ----------------------------
DROP TABLE IF EXISTS `ms_article_body`;
CREATE TABLE `ms_article_body`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `content`      longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
    `content_html` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
    `article_id`   bigint NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX          `article_id`(`article_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1903332362470113282 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ms_article_collect
-- ----------------------------
DROP TABLE IF EXISTS `ms_article_collect`;
<<<<<<< HEAD
CREATE TABLE `ms_article_collect`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `article_id`  bigint NOT NULL COMMENT '文章ID',
    `user_id`     bigint NOT NULL COMMENT '用户ID',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_article_user`(`article_id` ASC, `user_id` ASC) USING BTREE,
    INDEX         `user_id`(`user_id` ASC) USING BTREE,
    CONSTRAINT `ms_article_collect_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `ms_article` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `ms_article_collect_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `ms_sys_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '文章收藏表' ROW_FORMAT = Dynamic;
=======
CREATE TABLE `ms_article_collect`  (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                       `article_id` bigint NOT NULL COMMENT '文章ID',
                                       `user_id` bigint NOT NULL COMMENT '用户ID',
                                       `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       UNIQUE INDEX `uk_article_user`(`article_id` ASC, `user_id` ASC) USING BTREE,
                                       INDEX `user_id`(`user_id` ASC) USING BTREE,
                                       CONSTRAINT `ms_article_collect_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `ms_article` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
                                       CONSTRAINT `ms_article_collect_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `ms_sys_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '文章收藏表' ROW_FORMAT = Dynamic;
>>>>>>> 129a09a9d62a77f0914d885c1cb589e7b50629cf

-- ----------------------------
-- Table structure for ms_article_like
-- ----------------------------
DROP TABLE IF EXISTS `ms_article_like`;
CREATE TABLE `ms_article_like`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `article_id`  bigint NOT NULL COMMENT '文章ID',
    `user_id`     bigint NOT NULL COMMENT '用户ID',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_article_user`(`article_id` ASC, `user_id` ASC) USING BTREE,
    INDEX         `user_id`(`user_id` ASC) USING BTREE,
    CONSTRAINT `ms_article_like_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `ms_article` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `ms_article_like_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `ms_sys_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '文章点赞表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ms_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `ms_article_tag`;
CREATE TABLE `ms_article_tag`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `article_id` bigint NOT NULL,
    `tag_id`     bigint NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX        `article_id`(`article_id` ASC) USING BTREE,
    INDEX        `tag_id`(`tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1903332362436558850 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ms_category
-- ----------------------------
DROP TABLE IF EXISTS `ms_category`;
CREATE TABLE `ms_category`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `avatar`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `description`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ms_comment
-- ----------------------------
DROP TABLE IF EXISTS `ms_comment`;
CREATE TABLE `ms_comment`
(
    `id`             bigint                                                        NOT NULL AUTO_INCREMENT,
    `content`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `create_date`    datetime NULL DEFAULT NULL,
    `article_id`     bigint NULL DEFAULT NULL,
    `commentator_id` bigint                                                        NOT NULL,
    `parent_id`      bigint                                                        NOT NULL,
    `to_uid`         bigint                                                        NOT NULL,
    `level`          varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci   NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX            `article_id`(`article_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1911608186206019587 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ms_permission
-- ----------------------------
DROP TABLE IF EXISTS `ms_permission`;
CREATE TABLE `ms_permission`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `path`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ms_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `ms_sys_log`;
CREATE TABLE `ms_sys_log`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `create_date` bigint NULL DEFAULT NULL,
    `ip`          varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
    `method`      varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
    `module`      varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
    `nickname`    varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `operation`   varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
    `params`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
    `time`        bigint NULL DEFAULT NULL,
    `userid`      bigint NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ms_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `ms_sys_user`;
CREATE TABLE `ms_sys_user`
(
    `id`                  bigint NOT NULL AUTO_INCREMENT,
    `account`             varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '账号',
    `admin`               bit(1) NULL DEFAULT NULL COMMENT '是否管理员',
    `avatar`              varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像',
    `create_date`         datetime NULL DEFAULT NULL,
    `deleted`             bit(1) NULL DEFAULT NULL COMMENT '是否删除',
    `email`               varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `last_login`          datetime NULL DEFAULT NULL,
    `mobile_phone_number` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号',
    `nickname`            varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '昵称',
    `password`            varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
    `salt`                varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '加密盐',
    `status`              varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1903763834838450179 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ms_tag
-- ----------------------------
DROP TABLE IF EXISTS `ms_tag`;
CREATE TABLE `ms_tag`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `avatar`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `tag_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
    `create_id` bigint NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1902609599799242798 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

SET
FOREIGN_KEY_CHECKS = 1;


INSERT INTO `ms_category` (`avatar`, `category_name`, `description`)
VALUES
    ('https://cdn-icons-png.flaticon.com/512/226/226777.png', 'Python', 'Python 编程语言相关的教程和资源'),
    ('https://cdn-icons-png.flaticon.com/512/226/226785.png', 'Java', 'Java 编程语言相关的教程和资源'),
    ('https://cdn-icons-png.flaticon.com/512/226/226789.png', 'JavaScript', 'JavaScript 编程语言相关的教程和资源'),
    ('https://cdn-icons-png.flaticon.com/512/226/226770.png', 'C++', 'C++ 编程语言相关的教程和资源'),
    ('https://cdn-icons-png.flaticon.com/512/226/226767.png', 'PHP', 'PHP 编程语言相关的教程和资源');