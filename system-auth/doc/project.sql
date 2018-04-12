

/* 若存在先删除 */
DROP TABLE IF EXISTS `T_ACCESS_AUTH_CONFIG`;
/* 创建 访问鉴权配置表 */
CREATE TABLE `T_ACCESS_AUTH_CONFIG` (
	`ID` integer NOT NULL AUTO_INCREMENT comment '主键-自增长',
	`SOURCE` varchar(256) NOT NULL comment '调用来源，系统名称，或系统-模块名称',
	`ACCESS_CODE` varchar(16) comment '访问编号，让调用方携带该参数来识别不同的配置',	
	`AUTH_KEY` varchar(64) DEFAULT 'X-AUTH-HEADER' comment '鉴权头key',		
	`AUTH_VALUE` varchar(512) comment '鉴权值，一般是放在头部的token',	
 	`STATUS` tinyint DEFAULT 1 COMMENT '状态, 0-无效，1-有效',			
	`REMARK` varchar(1000) COMMENT '备注',		
	`EXPIRE_DATE` timestamp NULL DEFAULT CURRENT_TIMESTAMP() comment '失效日期，为空表示永不失效',	 
	`CREATE_DT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP() comment '创建时间',	
	 PRIMARY KEY (`ID`)
) ENGINE=InnoDB COMMENT '访问鉴权配置表' DEFAULT CHARSET=UTF8;


INSERT INTO T_ACCESS_AUTH_CONFIG (SOURCE, ACCESS_CODE, AUTH_VALUE, REMARK) 
VALUES ('A系统APP', '100001', 'AFKJLKWEOIK02LKJAM23OKASK', 'A系统访问');

INSERT INTO T_ACCESS_AUTH_CONFIG (SOURCE, ACCESS_CODE, AUTH_VALUE, EXPIRE_DATE, REMARK) 
VALUES ('B系统APP', '100002', 'ADFDASFASDFASDFASDFADSF', '2016-02-04 12:27:33', 'B系统访问');


