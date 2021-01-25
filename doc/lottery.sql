/*
 Navicat Premium Data Transfer

 Source Server         : mcw.zone
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 117.78.8.245:3306
 Source Schema         : lottery

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/01/2021 17:32:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lottery
-- ----------------------------
DROP TABLE IF EXISTS `lottery`;
CREATE TABLE `lottery`  (
  `lottery_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '抽奖id',
  `lottery_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '抽奖名字',
  `state` int(0) NULL DEFAULT 1 COMMENT '1：还可以修改，0：不能再修改',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '抽奖密码',
  PRIMARY KEY (`lottery_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 179 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

/*
 Navicat Premium Data Transfer

 Source Server         : mcw.zone
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 117.78.8.245:3306
 Source Schema         : lottery

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/01/2021 17:32:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`  (
  `staff_id` int(0) NOT NULL AUTO_INCREMENT,
  `staff_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工号',
  `staff_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名字',
  `lottery_id` int(0) NULL DEFAULT NULL COMMENT '所属抽奖',
  `state` int(0) NULL DEFAULT NULL COMMENT '0：未中奖，任可继续抽奖，1：已中奖，移除奖池',
  PRIMARY KEY (`staff_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1609 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


/*
 Navicat Premium Data Transfer

 Source Server         : mcw.zone
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 117.78.8.245:3306
 Source Schema         : lottery

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/01/2021 17:32:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for prize
-- ----------------------------
DROP TABLE IF EXISTS `prize`;
CREATE TABLE `prize`  (
  `prize_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '奖品id',
  `prize_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '奖品名字',
  `num` int(0) NULL DEFAULT NULL COMMENT '数量',
  `lottery_id` int(0) NULL DEFAULT NULL COMMENT '抽奖id',
  `staff_name` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '获奖员工',
  `count` int(0) NULL DEFAULT NULL COMMENT '总数',
  `prize_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '奖项',
  PRIMARY KEY (`prize_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 236 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
