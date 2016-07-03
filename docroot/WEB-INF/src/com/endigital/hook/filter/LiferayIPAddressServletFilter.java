package com.endigital.hook.filter;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;

import com.endigital.hook.util.UserTokenUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.Encryptor;

public class LiferayIPAddressServletFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		try {
			Company company = PortalUtil.getCompany(req);
			
			
			//查看cookie中是否有用户登录信息，如果有的话直接获取用户名和密码，生成token,携带token直接重定向到站点页面。
			//如果cookie中没有用户登录信息的话，那么显示登录界面，输入用户名和密码，生成token，同样携带token直接重定向到站点页面
			Cookie[] cookies = req.getCookies();
			
			String url = req.getQueryString().split("=")[1];
			System.out.println(url);
			
			//判断cookie是否为空，为空的话，那么就直接显示门户的登录界面
			if(cookies == null || cookies.length == 0){
				
				resp.sendRedirect("http://localhost:8080/?url="+url);
				
			}
			//cookie不为空
			else{
				boolean flag = false;           //标志位判断cookie中是否存在已经登录的用户
				
				User user = null;
				
				for(Cookie c : cookies){
					if(c.getName().equals("ID")){
						flag = true;  
						String userID = new String(Hex.decodeHex(c.getValue().toCharArray()));
						String name = Encryptor.decrypt(company.getKeyObj(), userID);
						user = UserLocalServiceUtil.getUser(Long.valueOf(name));
					}
				}
				
				//flag为true的话,说明已经有用户登录
				if(flag){
					
					 String username = user.getScreenName();
					//那么在url后面直接添加token，跳转到站点界面
					String token = UserTokenUtil.getToken(username);
					resp.sendRedirect("http://"+url+"/?token="+token);
					
				}else{
					//flag为false的话，那么说明用户之前没有登录过，之前跳转到门户的登录界面上。
					resp.sendRedirect("http://localhost:8080/?url="+url);
					
				}
			}
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

}
