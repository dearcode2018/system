
/* UUID.randomUUID().toString().replaceAll("-", "") */

/* 若存在先删除 */
DROP TABLE IF EXISTS `T_SWITCH_CONFIG`;
/* 创建 开关配置表 */
CREATE TABLE `T_SWITCH_CONFIG` (
	`ID` integer NOT NULL AUTO_INCREMENT comment '主键-自增长',
	`CODE` varchar(20) NOT NULL comment '开关编码',	
	`PARENT_CODE` varchar(20) comment '上级开关编码，根开关则字段为空',	
	`NAME` varchar(256) NOT NULL comment '开关名称',	
	`TYPE` varchar(10) comment '开关类型，1-系统，2-业务开关，3-紧急开关,4-...',	
	`VALUE` varchar(10) NOT NULL default 'false' comment '开关状态，开-true，关-false，默认是关闭状态',	
	`ISOLATED` varchar(10) NOT NULL default 'false' comment '是否独立，独立状态不受上级开挂控制，默认是不独立',		
 	`STATUS` tinyint DEFAULT 1 COMMENT '状态, 0-无效，1-有效',
	`DESCRIPTION` varchar(256) NULL comment '描述，备用字段 ，描述开关的基本功能',						
	`REMARK` varchar(1000) COMMENT '备注',		
	`CREATE_DT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP() comment '创建时间',	
	`UPDATE_DT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP() comment '更新时间',		
	 PRIMARY KEY (`ID`)
) ENGINE=InnoDB COMMENT '开关配置表' DEFAULT CHARSET=UTF8;

/* 初始数据 */
INSERT INTO T_SWITCH_CONFIG (CODE, PARENT_CODE, NAME, TYPE, VALUE, DESCRIPTION) 
VALUES ('100000', '', '系统总开关', '1', 'true', 'root开关');

INSERT INTO T_SWITCH_CONFIG (CODE, PARENT_CODE, NAME, TYPE, VALUE, DESCRIPTION) 
VALUES ('1001-00', '100000', '模块1开关', '2', 'true', '模块1开关');
INSERT INTO T_SWITCH_CONFIG (CODE, PARENT_CODE, NAME, TYPE, VALUE, DESCRIPTION) 
VALUES ('1001-01', '1001-00', '模块1.1开关', '2', 'true', '模块1.1开关');
INSERT INTO T_SWITCH_CONFIG (CODE, PARENT_CODE, NAME, TYPE, VALUE, DESCRIPTION) 
VALUES ('1001-02', '1001-00', '模块1.2开关', '2', 'true', '模块1.2开关');

INSERT INTO T_SWITCH_CONFIG (CODE, PARENT_CODE, NAME, TYPE, VALUE, DESCRIPTION) 
VALUES ('1002-00', '100000', '模块2开关', '2', 'true', '模块2开关');
INSERT INTO T_SWITCH_CONFIG (CODE, PARENT_CODE, NAME, TYPE, VALUE, DESCRIPTION) 
VALUES ('1002-01', '1002-00', '模块2.1开关', '2', 'true', '模块2.1开关');
INSERT INTO T_SWITCH_CONFIG (CODE, PARENT_CODE, NAME, TYPE, VALUE, DESCRIPTION) 
VALUES ('1002-02', '1002-00', '模块2.2开关', '2', 'true', '模块2.2开关');
INSERT INTO T_SWITCH_CONFIG (CODE, PARENT_CODE, NAME, TYPE, VALUE, ISOLATED, DESCRIPTION) 
VALUES ('1002-03', '1002-00', '模块2.3开关(独立)', '2', 'true', 'true', '模块2.3开关(独立开关)');



/* 若存在先删除 */
DROP TABLE IF EXISTS `T_SWITCH_CONFIG_SIMPLE`;
/* 创建 简单开关配置表，直接修改表的形式，数据放在缓存中 */
CREATE TABLE `T_SWITCH_CONFIG_SIMPLE` (
	`ID` integer NOT NULL AUTO_INCREMENT comment '主键-自增长',
	`NAME` varchar(256) NOT NULL comment '开关名称',	
	`CODE` varchar(20) NOT NULL comment '开关编码',		
	`VALUE` varchar(10) NOT NULL default 'false' comment '开关状态，开-true，关-false，默认是关闭状态',	
 	`STATUS` tinyint DEFAULT 1 COMMENT '状态, 0-无效，1-有效',			
	`REMARK` varchar(1000) COMMENT '备注',		
	`CREATE_DT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP() comment '创建时间',	
	 PRIMARY KEY (`ID`)
) ENGINE=InnoDB COMMENT '简单开关配置表' DEFAULT CHARSET=UTF8;





