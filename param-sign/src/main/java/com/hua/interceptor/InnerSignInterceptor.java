/**
  * @filename InnerSignInterceptor.java
  * @description  
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hua.util.RequestParamsHelper;
import com.hua.util.StringUtil;


 /**
 * @type InnerSignInterceptor
 * @description  
 * @author qianye.zheng
 */
public class InnerSignInterceptor extends BaseInterceptor
{

	private String androidAppKey = "a_123";
	
	private String iosAppKey = "i_123";
	
	/* api secret */
	private String apiSecret = "api_20160125";
	
	/* 用户密钥，每个用户不同，此处作为示例，写成常量 */
	private String userSecrect = "u_123456";
	
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
		final Map<String, String> params = getCommonParams(request);
		boolean flag = validate(params, response);
		if (!flag)
		{
			log.info("preHandle =====> 签名验证不合法，请求被拦截");
		}
		
		return flag;
	}

	/**
	 * 
	 * @description 获取公共参数
	 * @param request
	 * @return
	 * @author qianye.zheng
	 */
	private Map<String, String> getCommonParams(final HttpServletRequest request)
	{
		final Map<String, String> params = new HashMap<String, String>();
		// 客户端类型 0-安卓，1-IOS
		params.put("ct", request.getParameter("ct"));
		// 版本数
		params.put("v", request.getParameter("v"));
		// 时间戳
		params.put("t", request.getParameter("t"));
		// token
		params.put("tk", request.getParameter("tk"));
		// 客户端签名
		params.put("s", request.getParameter("s"));
		//APP_KEY
		//params.put("ak", request.getParameter("ak"));
		//api_secret
		//params.put("as", request.getParameter("as"));
		// 应用名称
		params.put("app", request.getParameter("app"));
		// 安卓设备的IMEI 或IOS设备的UUID
		params.put("m", request.getParameter("m"));
		// 10 位随机数
		params.put("r", request.getParameter("r"));
		
		return params;
	}
	
	/**
	 * 
	 * @description 
	 * @param params
	 * @return
	 * @author qianye.zheng
	 */
	private Map<String, String> addKey(final Map<String, String> params)
	{
		final String ct = params.get("ct");
		switch (ct)
		{
		case "0":
		{
			params.put("ak", androidAppKey);
			
			break;
		}
		case "1":
		{
			params.put("ak", iosAppKey);
			
			break;
		}
		
		default: break;
		}
		
		return params;
	}
	
	/**
	 * 
	 * @description 签名校验
	 * @param params
	 * @param response
	 * @return
	 * @author qianye.zheng
	 */
	private boolean validate(final Map<String, String> params, 
			final HttpServletResponse response) throws Exception
	{
		boolean flag = false;
		final RequestParamsHelper helper = new RequestParamsHelper(addKey(params));
		// 检查是否超时
		final String clientTime = params.get("t");
		final long serverTime = new Date().getTime();
		if (StringUtil.isEmpty(clientTime) || (serverTime - Long.parseLong(clientTime) > 3600000))
		{ // 大于一个小时
			flag = false;
			sendErrorResponse(response, 201 , "参数签名错误");
			log.error("[validateV2]请求超时");
			
			return flag;
		}
		// 模拟从缓存或其他地方获取userSecret
		if (StringUtil.isEmpty(userSecrect))
		{
			flag = false;
			this.sendErrorResponse(response, 201 , "参数签名错误");
			
			return flag;
		}
		helper.setApiSecret(apiSecret);
		helper.setUserSecret(userSecrect);
		// 获取客户端签名
		final String clientSign = helper.getParameter("s");
		final String serverSign = helper.removeParameter("s")
				.removeParameter("tk").sort().getSortedValuesAsSha1();
		if (!serverSign.equals(clientSign))
		{
			flag = false;
			log.error("[validateV2]签名验证不通过");
			this.sendErrorResponse(response, 201 , "参数签名错误");
			
			return flag;
		}
		flag = true;
		
		return flag;
	}
	
}
