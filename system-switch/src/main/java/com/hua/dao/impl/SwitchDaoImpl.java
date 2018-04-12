/**
  * @filename SwitchDaoImpl.java
  * @description  
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hua.dao.SwitchDao;
import com.hua.entity.SwitchEntity;
import com.hua.mapper.SwitchMapper;
import com.hua.util.StringUtil;

 /**
 * @type SwitchDaoImpl
 * @description  
 * @author qianye.zheng
 */
@Repository
public class SwitchDaoImpl extends CoreDaoImpl<String, SwitchEntity> implements
		SwitchDao
{
	
	@Resource
	private SwitchMapper mapper;

	/**
	 * @description 构造方法
	 * @author qianye.zheng
	 */
	public SwitchDaoImpl()
	{
		super.setCoreMapper(mapper);
	}

	/**
	 * @description 
	 * @param code
	 * @return
	 * @author qianye.zheng
	 */
	@Override
	public SwitchEntity getByCode(String code)
	{
		return mapper.getByCode(code);
	}
	
	/**
	 * 
	 * @description 
	 * @param code
	 * @return
	 * @author qianye.zheng
	 */
	public boolean getSwitchValue(final String code)
	{
		/*
		 * DAO方法设计
		 * 根据编码进行查询开关值(true/false): 
		 * 1) 若值为false，直接返回
		 * 2) 若值为true，查询父级开关(parentCode不为空) 跳到 步骤1
		 * 3) 若查询到根开关，则根据根开关的值进行返回
		 * 4) 若当前开关是独立开关，则直接跳过2、3步骤，直接返回该开关的值
		 */
		boolean flag = false;
		final SwitchEntity entity = getSwitch(code);
		if (null != entity)
		{
			flag = entity.getValue();
		}
		
		return flag;
	}
	
	/**
	 * 
	 * @description 递归调用获取 开关对象
	 * @param code
	 * @return
	 * @author qianye.zheng
	 */
	private SwitchEntity getSwitch(final String code)
	{
		/*
		 * DAO方法设计
		 * 根据编码进行查询开关值(true/false): 
		 * 1) 若值为false，直接返回
		 * 2) 若值为true，查询父级开关(parentCode不为空) 跳到 步骤1
		 * 3) 若查询到根开关，则根据根开关的值进行返回
		 * 4) 若当前开关是独立开关，则直接跳过2、3步骤，直接返回该开关的值
		 */
		/*
		 * 当前结点如果是独立开关，则直接返回其对象，若不是且开关值为true 需要追溯上一级结点
		 * 返回最终可以决定该开关状态的结点对象
		 */
		final SwitchEntity entity = mapper.getByCode(code);
		if (null == entity)
		{
			return null;
		}
		
		if (entity.isIsolated())
		{ // 独立的直接返回当前结点的结果
			return entity;
		}
		
		if (StringUtil.isEmpty(entity.getParentCode()))
		{ // 当前结点是 root 结点，直接返回
			return entity;
		}
		
		if (entity.getValue())
		{ // 当前结点开关是true，需要检查上一级开关
			// 获取父类编码
			final String  parentCode = entity.getParentCode();
			
			return getSwitch(parentCode);
		}
		
		// 返当前结点对象
		return entity;
	}
	
}
