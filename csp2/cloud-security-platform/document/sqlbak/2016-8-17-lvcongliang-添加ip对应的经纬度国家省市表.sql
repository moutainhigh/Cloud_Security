DROP TABLE IF EXISTS `cs_ipposition`;
CREATE TABLE `cs_ipposition` (
  `ip` varchar(50) NOT NULL COMMENT '主键IP',
  `longitude` varchar(100) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(100) DEFAULT NULL COMMENT '纬度',
  `countryProvince` varchar(255) DEFAULT NULL COMMENT '国家+省',
  `registerTime` datetime DEFAULT NULL COMMENT '入库时间',
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

