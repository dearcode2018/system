package com.plateno.o2omember.webapi.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.plateno.o2omember.core.utilites.RequestParamsHelperV1;
import com.plateno.o2omember.webapi.json.ResponseHeader;

/**
 * 签名验证拦截器(外部接口使用)
 *
 */
@Service
public class OuterSignInterceptor  extends BaseInterceptor {
	
	/**
	 * 是否需要做认证签名
	 */
	@Value("${webapi.signValidate}")
	private boolean singValidate ;
	/**
	 * 用于验证签名的密钥
	 */
	@Value("${webapi.seckey}")
	private String seckey;
	/**
	 * 是否全参数检验
	 */
	@Value("${webapi.validAll}")
	private boolean validAll;
	/**
	 * 要排除检验的请求参数
	 */
	@Value("${webapi.signParams.exclude}")
	private String excludeParams;

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
//		 if(request.getRequestURI().contains("o2o/api/wx/user"))
//		 {
//			 return true ;
//		 }
		
		// 签名验证
		if (this.singValidate) {
			Map<String, String> params = new HashMap<String, String>();
			if (validAll) {
				params = this.getAllParams(request);
			} else {
				params = this.getCommonParams(request);
			}
			return this.validate(params, response);
		}
		
		return true;
	}
	
	/**
	 * 获取固定参数
	 * @param request
	 * @return
	 */
	private Map<String, String> getCommonParams(HttpServletRequest request) {		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("ct", request.getParameter("ct"));
		params.put("v", request.getParameter("v"));
		params.put("t", request.getParameter("t"));
		params.put("tk", request.getParameter("tk"));
		params.put("s", request.getParameter("s"));

		return params;
	}
	
	/**
	 * 获取全部参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings(value = {"unchecked"})
	private Map<String, String> getAllParams(HttpServletRequest request) {
		Enumeration<String> names = request.getParameterNames();
		Map<String, String> params = new HashMap<String, String>();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			// 不进行检验的请求参数
			if (this.excludeParams != null && this.excludeParams.contains(name)) {
				continue;
			}
			String[] values = request.getParameterValues(name);
			params.put(name, values[0]);
		}
		
		return params;
	}
	
	private boolean validate(Map<String, String> params, HttpServletResponse response) throws Exception {
		RequestParamsHelperV1 helper = new RequestParamsHelperV1(params);
		String clientSign = helper.getParameter("s");
		String serverSign = helper.setKey(seckey).removeParameter("s").sort().getSortedValuesAsMD5();
		if (!serverSign.equals(clientSign)) {
			logger.error("[validate]签名验证不通过，请求参数为：{}，服务端校验值为：{}", params, serverSign);
			this.sendErrorResponse(response, ResponseHeader.STATUS_201 , "参数签名错误");
			return false;
		}
		logger.info("[validate]请求参数为：{}，服务端校验值为：{}", params, serverSign);
		return true;
	}
	
}