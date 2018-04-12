


/* 若存在先删除 */
DROP TABLE IF EXISTS `T_BUSINESS_EXCEPTION_LOG`;
/* 创建 业务异常日志表 */
CREATE TABLE `T_BUSINESS_EXCEPTION_LOG` (
	`ID` integer NOT NULL AUTO_INCREMENT comment '主键-自增长',
	`NAME` varchar(256) NOT NULL comment '业务名称',
	`PARAM` varchar(256) comment '业务参数',	
	`LEVEL` varchar(10) NOT NULL DEFAULT 'WARN' comment '异常级别，DEBUG/INFO/WARN/ERROR/SYSTEM',	
	`DETAIL` TEXT COMMENT '异常详情，可存储大文本',		
	`REMARK` varchar(1000) COMMENT '异常备注',		
	`CREATE_DT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP() comment '异常发生时间',	
	 PRIMARY KEY (`ID`)
) ENGINE=InnoDB COMMENT '业务异常日志表' DEFAULT CHARSET=UTF8;

/* 若存在先删除 */
DROP TABLE IF EXISTS `T_INTERFACE_INVOKE_LOG`;
/* 创建 接口调用日志表 */
CREATE TABLE `T_INTERFACE_INVOKE_LOG` (
	`ID` integer NOT NULL AUTO_INCREMENT comment '主键-自增长',
	`UNION_VALUE` varchar(256) NOT NULL comment '唯一值，比如模块名称-参数值等等',	
	`LAUNCH_NAME` varchar(128) NOT NULL comment '发起接口名称',	
	`CALLBACK_NAME` varchar(256) NOT NULL comment '回调接口名称',	
	`REQUEST_PARAM` varchar(256) comment '接口请求参数',	
	`RESPONSE_PARAM` TEXT COMMENT '接口响应参数，可存储大文本',
	`CALLBACK_PARAM` TEXT COMMENT '回调接口参数，可存储大文本',	
	`INVOKE_DT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP() comment '接口调用时间',	
	`CALLBACK_DT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP() comment '接口回调时间，如果没有回调，则该时间为空',		
	`REMARK` varchar(1000) COMMENT '备注',		
	`CREATE_DT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP() comment '接口调用时间',	
	 PRIMARY KEY (`ID`)
) ENGINE=InnoDB COMMENT '接口调用日志表' DEFAULT CHARSET=UTF8;



