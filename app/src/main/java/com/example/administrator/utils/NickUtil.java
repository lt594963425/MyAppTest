package com.example.administrator.utils;

import android.content.Context;
import android.database.Cursor;
import com.example.administrator.provider.ContactProvider.CONTACT;
import com.example.administrator.activity.MyApp;
import com.example.administrator.provider.ContactProvider;

import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;

public class NickUtil {

	public static String getNick(String nick) {
		// PinyinHelper.convertToPinyinString(����, ����, ��ʽ);ge shi
		return PinyinHelper.convertToPinyinString(nick, "", PinyinFormat.WITHOUT_TONE).toUpperCase();
	}
	
	//����@itheima.com/Sparck 2.63
	public static String filterAccount(String account) {
		account=	account.substring(0,account.indexOf("@"));
		account=account+"@"+ MyApp.SERVICE_NAME;
		return account;
	}

	public static String getNick(Context context, String account) {
		String nick = "";
		Cursor c = context.getContentResolver().query(ContactProvider.CONTACT_URI, null, CONTACT.ACCOUNT + "=?", new String[] { account }, null);

		if (c.moveToFirst()) {
			nick = c.getString(c.getColumnIndex(CONTACT.NICK));
		}
		c.close();

		if (nick == null || "".equals(nick)) {
			nick = account.substring(0, account.indexOf("@"));
		}
		return nick;
	}
}
