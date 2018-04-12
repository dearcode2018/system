/**
  * @filename SwitchDao.java
  * @description  
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.dao;

import com.hua.entity.SwitchEntity;

 /**
 * @type SwitchDao
 * @description  
 * @author qianye.zheng
 */
public interface SwitchDao extends CoreDao<String, SwitchEntity>
{
	/**
	 * 
	 * @description 
	 * @param code
	 * @return
	 * @author qianye.zheng
	 */
	public SwitchEntity getByCode(final String code);
	
	/**
	 * 
	 * @description 
	 * @param code
	 * @return
	 * @author qianye.zheng
	 */
	public boolean getSwitchValue(final String code);
	
}
