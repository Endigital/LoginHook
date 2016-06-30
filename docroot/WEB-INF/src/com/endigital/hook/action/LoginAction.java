package com.endigital.hook.action;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

public class LoginAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
			throws ActionException {
		// TODO Auto-generated method stub
	
		try {
			Company company = PortalUtil.getCompany(request);
			System.out.println("锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷Hook Action");
			String url = request.getParameter("url");
			System.out.println(url);
			Cookie[] cookies = request.getCookies();
			for(Cookie c : cookies){
				System.out.println(c.getName()+" : "+c.getValue());
				if(c.getName().equals("SCREEN_NAME")){
					String userID = new String(Hex.decodeHex(c.getValue().toCharArray()));
					System.out.println("锟斤拷锟街ｏ拷"+userID);
					String name = Encryptor.decrypt(company.getKeyObj(), userID);
					System.out.println(name);
				}
				if(c.getName().equals("PASSWORD")){
					String userID = new String(Hex.decodeHex(c.getValue().toCharArray()));
					System.out.println("锟斤拷锟诫："+userID);
					String name = Encryptor.decrypt(company.getKeyObj(), userID);
					System.out.println(name);
				}
			}
		} catch (DecoderException | EncryptorException | PortalException | SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
