package com.endigital.hook.action;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import sun.misc.BASE64Encoder;

import com.endigital.hook.util.UserTokenUtil;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;


public class ExampleStrutsPortletAction extends BaseStrutsPortletAction {
	
	private String url = null;

	@Override
	public void processAction(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("11111111");
		
		System.out.println("url in processAction"+url);
		
		//如果url不为空的话，说明是在其他页面携带url过来的，那么直接在该url后面加上token，进行页面跳转
		if(url != null){
			String login = ParamUtil.getString(actionRequest, "login");
			System.out.println("username:"+login);
			
			String token = UserTokenUtil.getToken(login);
			System.out.println(token);
			PortalUtil.getHttpServletResponse(actionResponse).sendRedirect("http://"+url+"/?"+token);
			url = null;
		}
		//如果为空的话，那么就按照正常的登录过程进行
		else{
			originalStrutsPortletAction.processAction(originalStrutsPortletAction, portletConfig, actionRequest,actionResponse);
		}
		
	}

	@Override
	public String render(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse) throws Exception {
		
		ThemeDisplay td = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		System.out.println("URLCurrent:"+td.getURLCurrent());
		
		System.out.println("before get the url value:"+url);
		
		if(td.getURLCurrent().contains("url=")){
			url = td.getURLCurrent().split("url=")[1];
			System.out.println(url);
		}

		System.out.println("after get the url value:"+url);
		return originalStrutsPortletAction.render(null, portletConfig, renderRequest, renderResponse); 
	}
}
