/**
  * @filename VersionInterceptor.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 /**
 * @type VersionInterceptor
 * @description 版本拦截器
 * 可以设计为拦截低版本客户端的请求
 * @author qianye.zheng
 */
public class VersionInterceptor extends BaseInterceptor
{
	/*
	 * 可以内置开关，在客户端发布之后的某个时间点启动开关
	 * 开关设置可以放在缓存中，快速开启和关闭，配置文件中
	 * 保存一份长久的配置
	 */

	/**
	 * @description 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception
	{
		//String tk = request.getParameter("tk");
		String ct = request.getParameter("ct");
		String v = request.getParameter("v");
		
		return false;
	}

}
