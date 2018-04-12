/**
  * @filename SwitchMapper.java
  * @description  
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.mapper;

import com.hua.entity.SwitchEntity;

 /**
 * @type SwitchMapper
 * @description  
 * @author qianye.zheng
 */
public interface SwitchMapper extends CoreMapper<String, SwitchEntity>
{
	/**
	 * 
	 * @description 
	 * @param code
	 * @return
	 * @author qianye.zheng
	 */
	public SwitchEntity getByCode(final String code);
}
