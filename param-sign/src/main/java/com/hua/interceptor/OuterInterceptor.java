/**
  * @filename OuterInterceptor.java
  * @description  外部系统拦截器
  * 外部系统范围该系统时进行拦截，配置要拦截或者放行的url
  * 还可以细分为内网和外网，例如内网一般只要检查头即可，对指定的调用系统分配指定header_token值
  * 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 /**
 * @type OuterInterceptor
 * @description  
 * @author qianye.zheng
 */
public class OuterInterceptor extends BaseInterceptor
{

	private static final String X_AUTH_HEADER = "X_AUTH_HEADER";
	
	private static final String X_AUTH_VALUE = "ABCKAJFL23OUIASFK";
	
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
		String xAuthValue = request.getHeader(X_AUTH_HEADER);
		if (X_AUTH_VALUE.equals(xAuthValue))
		{
			return true;
		}
		log.info("preHandle =====> 头部鉴权非法，请求被拦截");
		
		return false;
	}

}
