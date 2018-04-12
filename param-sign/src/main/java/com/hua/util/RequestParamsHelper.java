/**
  * @filename RequestParamsHelper.java
  * @description  
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.codec.digest.DigestUtils;

 /**
 * @type RequestParamsHelper
 * @description  请求参数帮助工具
 * @author qianye.zheng
 */
public final class RequestParamsHelper
{
	
	/* 存放请求参数 */
	private Map<String, String> params = new HashMap<String, String>();
	
	/* 参数名称集合 */
	private List<String> names = new ArrayList<String>();
	
	/* API 密钥 */
	private String apiSecret = "";
	
	/* 用户密钥 */
	private String userSecret;
	
	/**
	 * @description 构造方法
	 * @author qianye.zheng
	 */
	public RequestParamsHelper()
	{
	}
	
	/**
	 * 
	 * @description 构造方法
	 * @param apiSecret API 密钥
	 * @author qianye.zheng
	 */
	public RequestParamsHelper(final String apiSecret)
	{
		this.apiSecret = apiSecret;
	}
	
	/**
	 * 
	 * @description 构造方法
	 * @param param
	 * @author qianye.zheng
	 */
	public RequestParamsHelper(Map<String, String> param) {
		this.params.putAll(param);
	}
	
	/**
	 * 
	 * @description 构造方法
	 * @param name
	 * @param value
	 * @author qianye.zheng
	 */
	public RequestParamsHelper(String name, String value)
	{
		this.params.put(name, value);
	}
	
	/**
	 * 
	 * @description 添加一对请求参数，如果原来已经设置了同名的请求参数，则会旧的同名参数的值会被覆盖
	 * @param name
	 * @param value
	 * @return
	 * @author qianye.zheng
	 */
	public RequestParamsHelper addParameter(String name, String value)
	{
		this.params.put(name, value);
		
		return this;
	}
	
	/**
	 * 
	 * @description 添加多个请求参数，如果原来已经设置了同名的请求参数，则会旧的同名参数的值会被覆盖
	 * @param param
	 * @return
	 * @author qianye.zheng
	 */
	public RequestParamsHelper addParameter(Map<String, String> param) {
		this.params.putAll(param);
		
		return this;
	}
	
	/**
	 * 
	 * @description 移除一个请求参数
	 * @param name
	 * @return
	 * @author qianye.zheng
	 */
	public RequestParamsHelper removeParameter(final String name)
	{
		this.params.remove(name);
		
		return this;
	}
	
	/**
	 * 
	 * @description 获取一个请求参数
	 * @param name
	 * @return
	 * @author qianye.zheng
	 */
	public String getParameter(final String name)
	{
		return params.get(name);
	}
	
	/**
	 * 
	 * @description 获取所有请求参数
	 * @return
	 * @author qianye.zheng
	 */
	public Map<String, String> getParams()
	{
		return this.params;
	}
	
	/**
	 * 
	 * @description 将参数名按照指定顺序排列
	 * @param isAscending 是否升序
	 * @return
	 * @author qianye.zheng
	 */
	public RequestParamsHelper sort(final boolean isAscending)
	{
		final SortedSet<String> sortedNames = new TreeSet<String>();
		// 加入后排序
		sortedNames.addAll(params.keySet());
		// 清除names
		names.clear();
		Object[] ns = sortedNames.toArray();
		if (isAscending)
		{ // 升序排序
			for (int i = 0; i < ns.length; i++)
			{
				names.add(ns[i].toString());
			}
		} else
		{ // 降序
			for (int i = ns.length; i >= 0; i--)
			{
				names.add(ns[i].toString());
			}
		}
		
		return this;
	}
	
	/**
	 * 
	 * @description 升序排列
	 * @return
	 * @author qianye.zheng
	 */
	public RequestParamsHelper sort()
	{
		return sort(true);
	}
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	public String getSortedNamesAsString()
	{
		if (names.isEmpty())
		{
			sort();
		}
		final StringBuilder builder = StringUtil.getBuilder();
		for (String name : names)
		{
			builder.append(name);
		}
		
		return builder.toString();
	}
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	public String getSortedValuesAsString()
	{
		if (names.isEmpty())
		{
			sort();
		}
		final StringBuilder builder = StringUtil.getBuilder();
		for (String name : names)
		{
			builder.append(params.get(name));
		}
		
		return builder.toString();
	}
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	public String getSortedValuesAsSha1()
	{
		final String values = getSortedValuesAsString();
		final StringBuilder builder = StringUtil.getBuilder();
		builder.append(apiSecret);
		if (StringUtil.isNotEmpty(userSecret))
		{ // 用户密钥
			builder.append("&" + userSecret);
		}
		builder.append(values);
		final String result = DigestUtils.sha1Hex(builder.toString());
		
		return result;
	}

	/**
	 * @return the apiSecret
	 */
	public final String getApiSecret()
	{
		return apiSecret;
	}

	/**
	 * @param apiSecret the apiSecret to set
	 */
	public final void setApiSecret(String apiSecret)
	{
		this.apiSecret = apiSecret;
	}

	/**
	 * @return the userSecret
	 */
	public final String getUserSecret()
	{
		return userSecret;
	}

	/**
	 * @param userSecret the userSecret to set
	 */
	public final void setUserSecret(String userSecret)
	{
		this.userSecret = userSecret;
	}
	
}
