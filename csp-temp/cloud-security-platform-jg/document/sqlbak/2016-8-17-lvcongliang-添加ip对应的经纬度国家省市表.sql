DROP TABLE IF EXISTS `cs_ipposition`;
CREATE TABLE `cs_ipposition` (
  `ip` varchar(50) NOT NULL COMMENT '主键IP',
  `longitude` varchar(100) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(100) DEFAULT NULL COMMENT '纬度',
  `countryProvince` varchar(255) DEFAULT NULL COMMENT '国家+省',
  `registerTime` datetime DEFAULT NULL COMMENT '入库时间',
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `cs_ipposition` VALUES ('0.0.0.0', null, null, null, '2016-08-17 16:13:12');
INSERT INTO `cs_ipposition` VALUES ('101.200.234.126', '120.1614', '30.2936', '中国浙江省', '2016-08-17 16:17:13');
INSERT INTO `cs_ipposition` VALUES ('101.201.222.199', '116.46269', '40.107033', '中国北京市', '2016-08-17 16:12:45');
INSERT INTO `cs_ipposition` VALUES ('107.151.226.203', '-104.6664', '41.3274', '美国怀俄明州 ', '2016-08-17 16:18:29');
INSERT INTO `cs_ipposition` VALUES ('115.28.141.225', '120.1614', '30.2936', '中国浙江省', '2016-08-17 16:18:29');
INSERT INTO `cs_ipposition` VALUES ('117.34.95.245', '108.9286', '34.2583 ', '中国陕西省', '2016-08-17 16:18:30');
INSERT INTO `cs_ipposition` VALUES ('119.81.225.154', '103.8', '1.3667', '新加坡', '2016-08-17 16:18:30');
INSERT INTO `cs_ipposition` VALUES ('120.24.83.45', '120.1614', '30.2936 ', '中国广东省', '2016-08-17 16:05:08');
INSERT INTO `cs_ipposition` VALUES ('219.141.189.183', '116.3883', '39.9289', '中国北京市', '2016-08-17 16:06:28');
INSERT INTO `cs_ipposition` VALUES ('219.142.69.76', '116.46269', '40.107033', '中国北京市', '2016-08-17 16:18:30');