/**
 * RequestParamsHelper.java
 * Copyright © 2015 http://www.plateno.cc. All rights reserved.
 * 2015年6月25日 下午5:12:13
 */
package com.plateno.o2omember.core.utilites;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author fity
 */
public class RequestParamsHelperV1 {
	/**
	 * 用于存放请求参数
	 */
	private Map<String, String> params = new HashMap<String, String>();
	private boolean beginWithSmall = true;
	private List<String> names = new ArrayList<String>();
	/**
	 * 用于进行MD5的key
	 */
	private String key = "";
	
	public RequestParamsHelperV1() {
		super();
	}
	
	public RequestParamsHelperV1(String key) {
		this.key = key;
	}
	
	/**
	 * 以一对请求参数的name及value为构造参数
	 * @param name
	 * @param value
	 */
	public RequestParamsHelperV1(String name, String value) {
		this.params.put(name, value);
	}
	
	/**
	 * 以请求参数的Map集合为构造参数
	 * @param param
	 */
	public RequestParamsHelperV1(Map<String, String> param) {
		this.params.putAll(param);
	}
	
	/**
	 * 添加一对请求参数，如果原来已经设置了同名的请求参数，则会旧的同名参数的值会被覆盖
	 * @param name
	 * @param value
	 * @return
	 */
	public RequestParamsHelperV1 addParameter(String name, String value) {
		this.params.put(name, value);
		return this;
	}
	
	/**
	 * 将一个Map添加到请求参数
	 * @param param
	 * @return
	 */
	public RequestParamsHelperV1 addParameter(Map<String, String> param) {
		this.params.putAll(param);
		return this;
	}
	
	/**
	 * 移除一个请求参数
	 * @param name
	 * @return
	 */
	public RequestParamsHelperV1 removeParameter(String name) {
		this.params.remove(name);
		return this;
	}
	
	/**
	 * 获取所有请求参数
	 * @return
	 */
	public Map<String, String> getParams() {
		return this.params;
	}
	
	/**
	 * 获取一个请求参数
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		return this.params.get(name);
	}
	
	/**
	 * 将参数名按升序(默认)或降序排列
	 * @return
	 */
	public RequestParamsHelperV1 sort() {
		TreeSet<String> names = new TreeSet<String>();
		names.addAll(this.params.keySet());
		Object[] ns = names.toArray();
		this.names.clear();
		if (beginWithSmall) {
			for (int i = 0; i < ns.length; i++) {
				this.names.add(ns[i].toString());
			}
		} else {
			for (int i = ns.length - 1; i >= 0; i--) {
				this.names.add(ns[i].toString());
			}
		}
		return this;
	}
	
	/**
	 * 将参数名按升序(true)或降序排列
	 * @param beginWithSmall true则参数名按字符串升序排列
	 * @return
	 */
	public RequestParamsHelperV1 sort(boolean beginWithSmall) {
		this.beginWithSmall = beginWithSmall;
		return this.sort();
	}
	
	public String getSortedNamesAsString() {
		if (this.names.isEmpty()) {
			this.sort();
		}
		StringBuffer sb = new StringBuffer();
		for (String name : this.names) {
			sb.append(name);
		}
		return sb.toString();
	}

	/**
	 * 将参数名按字符串升序排序后，取参数值拼接在一起后的字符串
	 * @return
	 */
	public String getSortedValuesAsString() {
		if (this.names.isEmpty()) {
			this.sort();
		}
		StringBuffer sb = new StringBuffer();
		for (String name : this.names) {
			sb.append(this.params.get(name));
		}
		return sb.toString();
	}
	
	/**
	 * 获取排序后的参数值的MD5字符串(全部为大写)
	 * @return
	 */
	public String getSortedValuesAsMD5() {
		String values = this.getSortedValuesAsString();
		String src = values + this.key;
		String md5Str = this.hex(this.md5(src));
		return md5Str.toUpperCase();
	}
	
	/**
	 * MD5摘要算法
	 * @param src
	 * @return
	 */
	private byte[] md5(String src) {
		byte[] result = new byte[]{};
		try {			
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = md.digest(src.getBytes("utf-8"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	/**
	 * 字节数组转换为16进制字符串
	 * @param b
	 * @return
	 */
	private String hex(byte[] b) {
		StringBuffer str = new StringBuffer();
		int digital;
		for (int i = 0; i < b.length; i++) {
			digital = b[i];
			if(digital < 0) {
				digital += 256;
			}
			if(digital < 16){
				str.append("0");
			}
			str.append(Integer.toHexString(digital));
		}
		return str.toString();
 	}

	public String getKey() {
		return key;
	}

	public RequestParamsHelperV1 setKey(String key) {
		if (null != key) {
			this.key = key;
		}
		return this;
	}
	
}
