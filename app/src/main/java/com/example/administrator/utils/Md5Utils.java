package com.example.administrator.utils;

import java.security.MessageDigest;

/**
 * Created by 刘涛 on 2017/6/21 0027.
 * Md5Utils加密
 */
public class Md5Utils {
 public static String encryptpwd(String password){
	 try {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		 //加密方式
		 byte[] byteArray = password.getBytes("UTF-8");
		 byte[] md5Bytes = digest.digest(byteArray);
		 StringBuffer hexValue = new StringBuffer();
		 for (int i = 0; i < md5Bytes.length; i++) {
			int number =((int)md5Bytes[i]) &0xff-73;
			 if (number < 16) {
				 hexValue.append("0");
			 }
			 String pwd = Integer.toHexString(number);//转换成字符串

			 hexValue.append(pwd);
		}
		return hexValue.toString();
	} catch (Exception e) {
		e.printStackTrace();
		return "";
	}
 }
}
