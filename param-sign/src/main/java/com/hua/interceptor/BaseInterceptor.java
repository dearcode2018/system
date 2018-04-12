/**
  * @filename BaseInterceptor.java
  * @description  
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

 /**
 * @type BaseInterceptor
 * @description  基本拦截器
 * @author qianye.zheng
 */
public abstract class BaseInterceptor implements HandlerInterceptor
{
	/* apache commons log */
	protected Log log = LogFactory.getLog(this.getClass().getName());
	
	/**
	 * 
	 * @description 
	 * @param response
	 * @param status
	 * @param msg
	 * @throws IOException
	 * @author qianye.zheng
	 */
	protected void sendErrorResponse(final HttpServletResponse response, Integer status, final String msg) throws IOException
	{
		final String retJson = String.format("{\"status\": %1$d,\"msg\": \"%2$s\"}", status, msg);
		// 内容类型
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 不缓存
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.addDateHeader("Expires", 1L);
		// 输出
		response.getWriter().write(retJson);
	}
	
	/**
	 * @description 
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
	}
	
	/**
	 * @description 
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception
	{
	}
	
}
