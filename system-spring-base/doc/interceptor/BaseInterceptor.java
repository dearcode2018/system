package com.plateno.o2omember.webapi.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaseInterceptor implements HandlerInterceptor {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	/**
	 * 返回错误信息
	 * @param request
	 * @param response
	 * @param status
	 * @param msg
	 * @throws IOException 
	 */
	protected void sendErrorResponse( HttpServletResponse response , Integer status , String msg ) throws IOException
	{
		String retJson = String.format("{\"status\": %1$d,\"msg\": \"%2$s\"}", status , msg);
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.addDateHeader("Expires", 1L);
		response.getWriter().write(retJson);
	}
	
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}


}
