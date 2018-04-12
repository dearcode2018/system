package com.plateno.o2omember.webapi.interceptor;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.plateno.o2omember.core.utilites.RequestParamsHelperV1;
import com.plateno.o2omember.core.utilites.RequestParamsHelperV2;
import com.plateno.o2omember.core.utilites.StringUtil;
import com.plateno.o2omember.webapi.json.ResponseHeader;
import com.plateno.o2omember.webapi.service.AccessTokenService;

/**
 * 签名验证拦截器
 *
 */
@Service
public class SignInterceptor  extends BaseInterceptor {
	
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
	
	/**
	 * andriod app_key
	 */
	private String andriodAppKey;
	
	/**
	 * ios app_key
	 */
	private String iosAppKey;
	
	/**
	 * ios api_secret
	 */
	private String apiSecret;
	
	public void setAndriodAppKey(String andriodAppKey) {
		this.andriodAppKey = andriodAppKey;
	}

	public void setIosAppKey(String iosAppKey) {
		this.iosAppKey = iosAppKey;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}
	
	@Autowired
	private AccessTokenService tokenService;

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
//		 if(request.getRequestURI().contains("o2o/api/wx/user"))
//		 {
//			 return true ;
//		 }
		 
		// 签名验证
		if (this.singValidate) {
			Map<String, String> params = new HashMap<String, String>();
			String ct = StringUtil.trimToEmpty(request.getParameter("ct"));
			//IOS支持新接口后可去掉判断及V1相关接口
			if("0".equals(ct)){//andriod
				params = this.getCommonParamsV2(request);
				return this.validateV2(params, response);
			}else if("1".equals(ct)){//兼容IOS旧版
				if (validAll) {
					params = this.getAllParamsV1(request);
				} else {
					params = this.getCommonParamsV1(request);
				}
				return this.validateV1(params, response);
			}else{
				logger.error("[preHandle]tk:{},s:{}",request.getParameter("tk"),request.getParameter("s"));
				return false;
			}
		}
		
		return true;
	}
	
	//#########################################ANDRIOD##############################################
	
	/**
	 * 获取固定参数
	 * @param request
	 * @return
	 */
	private Map<String, String> getCommonParamsV2(HttpServletRequest request) {		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("ct", request.getParameter("ct"));
		params.put("v", request.getParameter("v"));
		params.put("t", request.getParameter("t"));
		params.put("tk", request.getParameter("tk"));
		params.put("s", request.getParameter("s"));
//		params.put("ak", request.getParameter("ak"));//APP_KEY
//		params.put("as", request.getParameter("as"));//api_secret
		params.put("app", request.getParameter("app"));
		params.put("m", request.getParameter("m"));
		params.put("r", request.getParameter("r"));

		return params;
	}
	
	private Map<String, String> addApiKey(Map<String, String> params){
		String ct = params.get("ct");
		if("0".equals(ct)){
			params.put("ak", andriodAppKey);
		}else{
			params.put("ak", iosAppKey);
		}
		params.put("as", apiSecret);
		return params;
	}
	
	private boolean validateV2(Map<String, String> params, HttpServletResponse response) throws Exception {
		RequestParamsHelperV2 helper = new RequestParamsHelperV2(addApiKey(params));
		//校验是否请求超时
		String time = params.get("t");
		long serverTime = new Date().getTime();
		if(StringUtil.isEmpty(time)||(serverTime-Long.parseLong(time))>3600000){//一小时前
			logger.error("[validateV2]请求超时：clientTime:{},serverTime:{}",time,serverTime);
			this.sendErrorResponse(response, ResponseHeader.STATUS_201 , "参数签名错误");
			return false;
		}
		String token = params.get("tk");
		String userScrect = "";
		//未登录SHA1(api_secret+implode(“”,array_values(ksort(common_params))))
		//已登录SHA1(api_secret+“&”+user_screct+implode(“”,array_values(ksort(common_params))))
		if(!StringUtil.isEmpty(token)){
			userScrect = tokenService.getUserSecretByToken(token);
			if(StringUtil.isEmpty(userScrect)){
				logger.error("[validateV2]tk：{}，userScrect为空", token);
				this.sendErrorResponse(response, ResponseHeader.STATUS_201 , "参数签名错误");
				return false;
			}
		}
		String clientSign = helper.getParameter("s");
		//common_params包括 :ct、v、t、ak、as、app、m、r
		String serverSign = helper.setApiSecret(apiSecret).setUserScrect(userScrect)
				.removeParameter("s").removeParameter("tk")
				.sort().getSortedValuesAsSHA1();
		if (!serverSign.equals(clientSign)) {
			logger.error("[validateV2]签名验证不通过，请求参数为：{}，服务端校验值为：{}", params, serverSign);
			this.sendErrorResponse(response, ResponseHeader.STATUS_201 , "参数签名错误");
			return false;
		}
		
		//不要打印到日志
		params.remove("ak");
		params.remove("as");
		logger.info("[validateV2]请求参数为：{}，服务端校验值为：{}", params, serverSign);
		return true;
	}
	
	//#########################################兼容IOS##############################################
	
	/**
	 * 获取固定参数
	 * @param request
	 * @return
	 */
	private Map<String, String> getCommonParamsV1(HttpServletRequest request) {		
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
	private Map<String, String> getAllParamsV1(HttpServletRequest request) {
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
	
	private boolean validateV1(Map<String, String> params, HttpServletResponse response) throws Exception {
		RequestParamsHelperV1 helper = new RequestParamsHelperV1(params);
		String clientSign = helper.getParameter("s");
		String serverSign = helper.setKey(seckey).removeParameter("s").sort().getSortedValuesAsMD5();
		if (!serverSign.equals(clientSign)) {
			logger.error("请求参数签名验证不通过，请求参数为：{}，服务端校验值为：{}", params, serverSign);
			this.sendErrorResponse(response, ResponseHeader.STATUS_201 , "参数签名错误");
			return false;
		}
		logger.debug("请求参数为：{}，服务端校验值为：{}", params, serverSign);
		return true;
	}
	
}