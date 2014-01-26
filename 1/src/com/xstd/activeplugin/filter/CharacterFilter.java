package com.xstd.activeplugin.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CharacterFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request,HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		response.setContentType("text/html;charset=utf-8");
		
		
		String contextEncoding = getFilterConfig().getInitParameter("context-encoding");
		request.setCharacterEncoding(contextEncoding);	
		
		RequestProxy proxy = new RequestProxy(request, getFilterConfig());
		HttpServletRequest myrequest = proxy.newRequestProxy();
		
		chain.doFilter(myrequest, response);
		
	}

}

class RequestProxy implements InvocationHandler {
	private HttpServletRequest request;
	private FilterConfig filterConfig;
	
	public RequestProxy(HttpServletRequest request, FilterConfig filterConfig) {
		super();
		this.request = request;
		this.filterConfig = filterConfig;
	}
	
	public HttpServletRequest newRequestProxy() {
		return (HttpServletRequest) Proxy.newProxyInstance(this.request.getClass().getClassLoader()
								, this.request.getClass().getInterfaces()
								, this);
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if("getParameter".equals(method.getName())) {
			String value = (String) method.invoke(this.request, args);
			
			if(value==null) {
				return null;
			}
			
			if("get".equalsIgnoreCase(this.request.getMethod())) {
				try {
					value = new String(value.getBytes(this.filterConfig.getInitParameter("server-encoding")), this.filterConfig.getInitParameter("context-encoding"));
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
			}
			
			return value;
		}
		return method.invoke(this.request, args);
	}
}
