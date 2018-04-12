package com.plateno.o2omember.webapi.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.plateno.o2omember.webapi.json.ResponseHeader;
import com.plateno.o2omember.webapi.json.login.UserLoginInfo;
import com.plateno.o2omember.webapi.service.AccessTokenService;

/**
 * 登录验证拦截器
 *
 */
@Service
public class AuthInterceptor extends BaseInterceptor {
	
	/**
	 * 是否需要做登录验证
	 */
	@Value("${webapi.loginValidate}")
	private boolean loginValidate ;
	
	@Autowired
	private AccessTokenService tokenSrv ;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
//		 if(request.getRequestURI().contains("o2o/api/wx/user"))
//		 {
//			 return true ;
//		 }
//		
		// 登录验证
		if( this.loginValidate )
		{
			boolean login = requestLoginValidate(request , response);
			if(!login) return false ;
		}
		
		return true  ;
	}
	
	/**
	 * 登录验证
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private boolean requestLoginValidate(HttpServletRequest request , HttpServletResponse response ) throws IOException
	{
		String token = request.getParameter("tk") ;
		UserLoginInfo userInfo = tokenSrv.validateAccessLogin(token) ;
		if( StringUtils.isBlank(token) || userInfo == null )
		{
			// 返回未登录信息
			sendErrorResponse(response , ResponseHeader.STATUS_202 , "用户未登录" );
			return false ;
		}
		else
		{
			request.setAttribute("userLoginInfo", userInfo);
			return true ;
		}
	}
	

}