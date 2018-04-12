/**
  * @filename SwitchController.java
  * @description  
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller.sys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hua.constant.CacheConstant;
import com.hua.constant.UriConstant;
import com.hua.controller.BaseController;
import com.hua.util.ReadProperties;
import com.hua.util.StringUtil;

 /**
 * @type SwitchController
 * @description  开关控制器
 * @author qianye.zheng
 */
@Controller
@RequestMapping(UriConstant.API + "sys/switch")
public class SwitchController extends BaseController
{
	
	private boolean debug;
	
	/* 紧急开关，打开此开关开启应急功能 */
	private boolean urgentSwitch = false;
	
	public static final String CONFIG_PATH = "/conf/properties/switch.properties";
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@RequestMapping("/v1")
	public boolean getSwitchV1()
	{
		// 读取缓存
		String value = redisService.boundValueOps(CacheConstant.SWITCH.URGENT_SWITCH_KEY).get();
		if (StringUtil.isEmpty(value))
		{// 缓存为空，读取配置文件或者其他数据源
			ReadProperties readProp = new ReadProperties(CONFIG_PATH);
			value = readProp.getProperty("webapi.urgent.switch");
			redisService.boundValueOps(CacheConstant.SWITCH.URGENT_SWITCH_KEY).set(value);
		}
		if (StringUtil.isNotEmpty(value))
		{
			urgentSwitch = Boolean.valueOf(value);
		}
		
		return urgentSwitch;
	}
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@RequestMapping("/v2")
	public boolean getSwitchV2()
	{
		// 读取缓存
		String value = redisService.boundValueOps(CacheConstant.SWITCH.URGENT_SWITCH_KEY).get();
		if (StringUtil.isEmpty(value))
		{// 缓存为空，读取配置文件或者其他数据源
			ReadProperties readProp = new ReadProperties(CONFIG_PATH);
			value = readProp.getProperty("webapi.urgent.switch");
			redisService.boundValueOps(CacheConstant.SWITCH.URGENT_SWITCH_KEY).set(value);
		}
		if (StringUtil.isNotEmpty(value))
		{
			urgentSwitch = Boolean.valueOf(value);
		}
		
		return urgentSwitch;
	}
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@RequestMapping("/v3")
	public boolean getSwitchV3()
	{
		// 读取缓存
		String value = redisService.boundValueOps(CacheConstant.SWITCH.URGENT_SWITCH_KEY).get();
		if (StringUtil.isEmpty(value))
		{// 缓存为空，读取配置文件或者其他数据源
			ReadProperties readProp = new ReadProperties(CONFIG_PATH);
			value = readProp.getProperty("webapi.urgent.switch");
			redisService.boundValueOps(CacheConstant.SWITCH.URGENT_SWITCH_KEY).set(value);
		}
		if (StringUtil.isNotEmpty(value))
		{
			urgentSwitch = Boolean.valueOf(value);
		}
		
		return urgentSwitch;
	}
}
