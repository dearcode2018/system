/**
  * @filename SwitchEntity.java
  * @description  
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.entity;

import java.util.Date;

 /**
 * @type SwitchEntity
 * @description  
 * @author qianye.zheng
 */
public class SwitchEntity extends BaseEntity
{
	/**
	 * 开关结构设计，容纳 1000 个开关，一般1000个开关足矣满足各种系统了，一个系统中开关一般不会太多，
	 * 只会在一些重要和关键的地方设置开关，可以容纳 1000个模块，层级关系为3级，项目总开关的关闭会关闭
	 * 整个项目的所有开关，一般需要谨慎操作.
	 * 项目总开关: root (100000)
	 * 模块开关: 
	 * --模块1: 1001-00
	 *   -- 子模块1: 1001-01
	 *   -- 子模块2: 1001-02
	 * --模块2: 1002-00
	 * --模块3: 1003-00
	 * ...
	 * --模块101: 1101-00
	 * ...
	 * 
	 * 开关数据初始化导入之后，以后可以通过系统界面来设置，来进行关闭，也可以通过直接操作表来修改开关的值.
	 */
	
	/*
	 * DAO方法设计
	 * 根据编码进行查询开关值(true/false): 
	 * 1) 若值为false，直接返回
	 * 2) 若值为true，查询父级开关(parentCode不为空) 跳到 步骤1
	 * 3) 若查询到根开关，则根据根开关的值进行返回
	 * 4) 若当前开关是独立开关，则直接跳过2、3步骤，直接返回该开关的值，独立开关不受上级开关的影响，
	 * 但是其值可以影响到它所有下一级开关
	 */
	
	/* 开关编码 */
	private String code;
	
	/* 上级开关编码，根开关则字段为空 */
	private String parentCode;
	
	/* 开关名称 */
	private String name;
	
	/* 开关类型，1-系统，2-业务开关，3-紧急开关,4-... */
	private String type;
	
	/* 开关状态，开-true，关-false，默认是关闭状态 */
	private boolean value = false;
	
	/* 是否独立，独立状态不受上级开挂控制，默认是不独立 */
	private boolean isolated = false;
	
	/* 状态: 有效-1，无效-0 */
	private Integer status;
	
	/* 描述，备用字段 ，描述开关的基本功能 */
	private String description;
	
	/* 备注 */
	private String remark;
	
	/* 创建时间 */
	private Date createDateTime;
	
	/* 更新时间 */
	private Date updateDateTime;

	/**
	 * @return the code
	 */
	public final String getCode()
	{
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public final void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @return the parentCode
	 */
	public final String getParentCode()
	{
		return parentCode;
	}

	/**
	 * @param parentCode the parentCode to set
	 */
	public final void setParentCode(String parentCode)
	{
		this.parentCode = parentCode;
	}

	/**
	 * @return the name
	 */
	public final String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public final String getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public final void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return the value
	 */
	public final boolean getValue()
	{
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public final void setValue(boolean value)
	{
		this.value = value;
	}

	/**
	 * @return the status
	 */
	public final Integer getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public final void setStatus(Integer status)
	{
		this.status = status;
	}

	/**
	 * @return the description
	 */
	public final String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public final void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the remark
	 */
	public final String getRemark()
	{
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public final void setRemark(String remark)
	{
		this.remark = remark;
	}

	/**
	 * @return the createDateTime
	 */
	public final Date getCreateDateTime()
	{
		return createDateTime;
	}

	/**
	 * @param createDateTime the createDateTime to set
	 */
	public final void setCreateDateTime(Date createDateTime)
	{
		this.createDateTime = createDateTime;
	}

	/**
	 * @return the updateDateTime
	 */
	public final Date getUpdateDateTime()
	{
		return updateDateTime;
	}

	/**
	 * @param updateDateTime the updateDateTime to set
	 */
	public final void setUpdateDateTime(Date updateDateTime)
	{
		this.updateDateTime = updateDateTime;
	}

	/**
	 * @return the isolated
	 */
	public final boolean isIsolated()
	{
		return isolated;
	}

	/**
	 * @param isolated the isolated to set
	 */
	public final void setIsolated(boolean isolated)
	{
		this.isolated = isolated;
	}
	
}
