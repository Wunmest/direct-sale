package com.wunmest.filter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wunmest.domain.XResponse;

public class ResponseInterceptor extends HandlerInterceptorAdapter {

	private static Log log = LogFactory.getLog(ResponseInterceptor.class);
	private NamedThreadLocal<Long> startTimeTHreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		getHeadersInfo(request);
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		//�?��执行方法是否�?@ResponseBody 注解, 如果被注�? 要求返回参数必须�?XResponse, 否则可以考虑用视图解析器返回
		if(handlerMethod.getMethodAnnotation(ResponseBody.class) != null){
			if(handlerMethod.getReturnType().getParameterType() != XResponse.class){
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/json;charset=utf-8");
				response.getWriter().write(new XResponse(1, "服务器返回参数类型不正确.").toString());
				throw new Exception(handlerMethod.getMethod() + "返回参数类型不正确");
			}
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception{
		
	}
	
	/**
	 * request headers info
	 * */
	private void getHeadersInfo(HttpServletRequest request){
		Enumeration headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()){
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			log.debug(key + ":" + value);
		}
	}
}
