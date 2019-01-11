/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : gayun

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2018-03-01 18:23:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pro_scheduling_funds
-- ----------------------------
DROP TABLE IF EXISTS `pro_scheduling_funds`;
CREATE TABLE `pro_scheduling_funds` (
  `ID` varchar(100) NOT NULL,
  `PROJECT_ID` varchar(100) NOT NULL,
  `LEVEL_ID` varchar(255) DEFAULT NULL,
  `DAY_MONEY` varchar(255) DEFAULT NULL,
  `MONTH_MONEY` varchar(255) DEFAULT NULL COMMENT '本月累计到位资金',
  `YEAR_MONEY` varchar(255) DEFAULT NULL COMMENT '本年到位资金',
  `TOTAL_MONEY` varchar(255) DEFAULT NULL COMMENT '累计到位资金',
  `UNIMPLMENT_MONEY` varchar(255) DEFAULT NULL COMMENT '未到位资金',
  `STATUS` int(255) DEFAULT '0' COMMENT '统计的时候看是否通过，0未审核，1不通过，2通过',
  `CREATE_TIME` varchar(50) DEFAULT NULL,
  `UPDATE_TIME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pro_scheduling_funds
-- ----------------------------
INSERT INTO `pro_scheduling_funds` VALUES ('04652acc82ac4dd3981f91de5b0a4b51', '2018522800000004', 'a72d2d838342b4eda806fd32448bc703e', '22', '33', '34', '35', '456', '0', '2018-02-09 10:56:48', '2018-02-09 10:56:48');
INSERT INTO `pro_scheduling_funds` VALUES ('071835e28eb64518b1a683aa171fd84a', '2018522800000005', 'a8a083264c4c945acb9401808a9318b73', '22', '22', '44', '66', '478', '0', '2018-02-09 11:50:08', '2018-02-09 11:50:08');
INSERT INTO `pro_scheduling_funds` VALUES ('1230ec98545b4fae8fdc821978238496', '2018522800000001', 'a26a488cebead4fa6bc6684bacc717453', '22', '55', '55', '77', '390', '3', '2018-02-26 17:44:10', '2018-02-26 17:46:04');
INSERT INTO `pro_scheduling_funds` VALUES ('183bb17d776646478664c9f5bc4b8118', '2018522800000001', 'a26a488cebead4fa6bc6684bacc717453', '22', '44', '44', '66', '434', '0', '2018-02-09 10:37:46', '2018-02-09 10:37:46');
INSERT INTO `pro_scheduling_funds` VALUES ('22b4c4fc59de4e33ad08bf20a815b645', '2018522800000002', 'a1ab0bc7a3fa64db7ac5375476cbbb287', '22', '33', '34', '8888', '55', '0', '2018-02-05 23:17:10', '2018-02-05 23:17:10');
INSERT INTO `pro_scheduling_funds` VALUES ('2c62a1f202af4df98dd3e0442fd2b42d', '2018522800000002', 'a1ab0bc7a3fa64db7ac5375476cbbb287', '22', '33', '46', '44', '55', '0', '2018-01-31 19:52:58', '2018-01-31 19:52:58');
INSERT INTO `pro_scheduling_funds` VALUES ('2d5c9d87cb9b461da670a975e074d1c0', '2018522800000002', 'a1ab0bc7a3fa64db7ac5375476cbbb287', '22', '77', '66', '88', '11', '0', '2018-02-12 10:01:13', '2018-02-12 10:09:19');
INSERT INTO `pro_scheduling_funds` VALUES ('2ee18c5fde59427589ddc49edff36d7e', '2018522800000013', 'a9388ce0d898a488cbbacf7416fe13c44', '1', '4', '3', '5', '493', '0', '2018-02-04 22:20:58', '2018-02-04 22:20:58');
INSERT INTO `pro_scheduling_funds` VALUES ('3a0924d6edd6432e98ae70b82cbff466', '2018522800000004', 'a72d2d838342b4eda806fd32448bc703e', '11', '11', '12', '13', '489', '0', '2018-02-04 21:59:42', '2018-02-04 21:59:42');
INSERT INTO `pro_scheduling_funds` VALUES ('498d3c51b3a146069f45b0980e94cfaa', '2018522800000003', 'a679cd4fde7b04b8fb76e91cea1d622a2', '11', '187', '209', '231', '-479', '0', '2018-02-12 11:49:21', '2018-02-12 11:49:21');
INSERT INTO `pro_scheduling_funds` VALUES ('57a4ab40f08e4aea80c62338536c014d', '2018522800000003', 'a679cd4fde7b04b8fb76e91cea1d622a2', '22', '22', '44', '66', '478', '0', '2018-02-09 11:31:53', '2018-02-09 11:31:53');
INSERT INTO `pro_scheduling_funds` VALUES ('59315b947c3c4df299463bf52358a44e', '2018522800000002', 'a1ab0bc7a3fa64db7ac5375476cbbb287', '22', '33', '2', '44', '55', '0', '2018-02-04 22:24:56', '2018-02-04 22:24:56');
INSERT INTO `pro_scheduling_funds` VALUES ('5c16d364df3f40029b5cc0f5b12e2f43', '2018522800000001', 'a26a488cebead4fa6bc6684bacc717453', '11', '33', '33', '55', '445', '0', '2018-02-12 11:04:17', '2018-02-12 11:05:42');
INSERT INTO `pro_scheduling_funds` VALUES ('610b84021b5a46e08f79835160120fd7', '2018522800000001', 'a26a488cebead4fa6bc6684bacc717453', '22', '44', '24', '25', '434', '0', '2018-02-04 17:29:36', '2018-02-04 17:29:36');
INSERT INTO `pro_scheduling_funds` VALUES ('6be7c542dab34cd8a80fc881755cb680', '2018522800000013', 'a9388ce0d898a488cbbacf7416fe13c44', '22', '25', '25', '27', '472', '0', '2018-02-05 23:45:48', '2018-02-05 23:45:48');
INSERT INTO `pro_scheduling_funds` VALUES ('6d8c01a8471246d5b53f8954ca61d166', '2018522800000002', 'a1ab0bc7a3fa64db7ac5375476cbbb287', '11', '132', '121', '143', '-341', '0', '2018-02-11 09:59:28', '2018-02-11 15:15:09');
INSERT INTO `pro_scheduling_funds` VALUES ('783dd1a011664dfcb370cef90325838e', '2018522800000013', 'a98758aee7ed148e8b54cffc72621f004', '22', '25', '25', '26', '970', '0', '2018-02-09 11:07:14', '2018-02-09 11:07:14');
INSERT INTO `pro_scheduling_funds` VALUES ('7c07e713e4844067b0fa1c8e3b4d5c18', '2018522800000003', 'a679cd4fde7b04b8fb76e91cea1d622a2', '22', '176', '198', '220', '-292', '0', '2018-02-11 15:25:04', '2018-02-11 17:28:31');
INSERT INTO `pro_scheduling_funds` VALUES ('86cd7ff23a744e42a011a68326ed26ec', '2018522800000013', 'a98758aee7ed148e8b54cffc72621f004', '22', '25', '25', '26', '970', '0', '2018-02-05 23:45:48', '2018-02-05 23:45:48');
INSERT INTO `pro_scheduling_funds` VALUES ('871588067c844c9bb0aa7a68216dbbb7', '2018522800000001', 'a26a488cebead4fa6bc6684bacc717453', '22', '44', '44', '66', '434', '0', '2018-02-11 10:20:33', '2018-02-11 10:20:33');
INSERT INTO `pro_scheduling_funds` VALUES ('88505e27f169481a8e3f4743f1a4d52d', '2018522800000002', 'a1ab0bc7a3fa64db7ac5375476cbbb287', '22', '33', '22', '44', '55', '0', '2018-01-30 18:05:55', '2018-01-30 18:05:55');
INSERT INTO `pro_scheduling_funds` VALUES ('88cbfeab639b453283ca5941c5d41747', '2018522800000001', 'a26a488cebead4fa6bc6684bacc717453', '11', '33', '33', '55', '445', '0', '2018-02-05 23:42:58', '2018-02-05 23:42:58');
INSERT INTO `pro_scheduling_funds` VALUES ('8c83233708464d52874e4f56aaf42558', '2018522800000013', 'a9388ce0d898a488cbbacf7416fe13c44', '3', '3', '3', '5', '497', '0', '2018-01-30 17:01:17', '2018-01-30 17:01:17');
INSERT INTO `pro_scheduling_funds` VALUES ('9a7abc4d194640809ab1ca3c303b9d10', '2018522800000005', 'a8a083264c4c945acb9401808a9318b73', '22', '44', '66', '88', '434', '3', '2018-02-26 10:21:56', '2018-02-26 10:21:56');
INSERT INTO `pro_scheduling_funds` VALUES ('a3ec922304364288b06d7fa1976cfdb8', '2018522800000002', 'a1ab0bc7a3fa64db7ac5375476cbbb287', '22', '55', '44', '66', '88', '0', '2018-02-09 10:51:42', '2018-02-09 10:51:42');
INSERT INTO `pro_scheduling_funds` VALUES ('cad11de83ad44cad94cb02f7ebe043a2', '2018522800000001', 'a26a488cebead4fa6bc6684bacc717453', '22', '22', '22', '44', '478', '0', '2018-02-01 21:43:26', '2018-02-01 21:43:26');
INSERT INTO `pro_scheduling_funds` VALUES ('cc11d9eabc5b4d2b8469a603a232dd16', '2018522800000013', 'a9388ce0d898a488cbbacf7416fe13c44', '22', '25', '25', '27', '472', '0', '2018-02-09 11:07:14', '2018-02-09 11:07:14');
INSERT INTO `pro_scheduling_funds` VALUES ('d1558c10afb54092927cc12c834f81b8', '2018522800000001', 'a26a488cebead4fa6bc6684bacc717453', '22', '44', '44', '66', '434', '0', '2018-02-27 09:28:19', '2018-02-27 09:28:19');
INSERT INTO `pro_scheduling_funds` VALUES ('fb89cc498fa3416681d0867f12a29574', '2018522800000013', 'a98758aee7ed148e8b54cffc72621f004', '2', '2', '2', '3', '998', '0', '2018-01-30 17:01:28', '2018-01-30 17:01:28');
INSERT INTO `pro_scheduling_funds` VALUES ('ffcf7f6f55c043a1ab905f65aa8750b0', '2018522800000013', 'a98758aee7ed148e8b54cffc72621f004', '1', '3', '3', '4', '995', '0', '2018-02-04 22:20:58', '2018-02-04 22:20:58');
