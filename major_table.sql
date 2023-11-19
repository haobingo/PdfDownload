CREATE TABLE `major_table` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `major` varchar(255) DEFAULT NULL COMMENT '专业名称',
  `major_version` varchar(255) DEFAULT NULL COMMENT '专业版本',
  `is_load` int(1) unsigned zerofill DEFAULT NULL COMMENT '是否上传，0表示未上传，1表示已上传',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;