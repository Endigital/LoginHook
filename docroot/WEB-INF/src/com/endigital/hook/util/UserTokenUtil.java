package com.endigital.hook.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class UserTokenUtil {
	
	public static String getToken(String username){
		String strTimestamp = Long.toString(System.currentTimeMillis()/1000);
		String key="AjAPOJSOJQadpojOAJojposjpoJ09W20KNOWQJIO21N2";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update((username+strTimestamp+key).getBytes());
			BigInteger hash = new BigInteger(1,md.digest());
			String hd = hash.toString(16);
			while(hd.length()<32){
				hd="0"+hd;
			}
			return base64encode(username+'&'+strTimestamp+'&'+hd);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String base64encode(String s){
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(s.getBytes());
	}
	
}
