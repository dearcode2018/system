/**
  * @filename AuthInterceptor.java
  * @description  内部鉴权
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 /**
 * @type AuthInterceptor
 * @description  
 * @author qianye.zheng
 */
public class AuthInterceptor extends BaseInterceptor
{

	private String token = "AKDSKWE123SD2334";
	
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
		String tk = request.getParameter("tk");
		if (token.equals(tk))
		{
			return true;
		}
		
		log.info("preHandle =====> tk为空，请求被拦截");
		
		return false;
	}

}
