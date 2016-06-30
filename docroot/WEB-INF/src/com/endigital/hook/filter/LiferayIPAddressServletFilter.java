package com.endigital.hook.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class LiferayIPAddressServletFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("Destory method being called");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		System.out.println("DoFilter method being called");
		String clientIPAddress = request.getRemoteAddr();
		
		String url = req.getQueryString().split("=")[1];
		
		System.out.println(url);
		System.out.println("Request client IP Address"+clientIPAddress+",Time"+new Date().toString());
		
		resp.sendRedirect("http://localhost:8080/?url="+url);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("Init method being called");
		String initParamValue = config.getInitParameter("initparam");
		System.out.println("initParamValue:"+initParamValue);
	}

}
