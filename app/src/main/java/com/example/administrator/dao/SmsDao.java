package com.example.administrator.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.administrator.provider.SmsProvider;
import com.example.administrator.provider.SmsProvider.SMS;
import com.example.administrator.utils.NickUtil;

import org.jivesoftware.smack.packet.Message;


public class SmsDao {
	private Context context;
	public SmsDao(Context context) {
		this.context = context;
	}

	public void saveSendMessage(Message msg) {
		ContentValues values=new ContentValues();
		String account=msg.getFrom();
		values.put(SMS.FROM_ID, account);
		String nick= NickUtil.getNick(context, account);
		String sessionName=NickUtil.getNick(context, msg.getTo());
		values.put(SMS.FROM_NICK, nick);
		values.put(SMS.FROM_AVATAR, 0);
		values.put(SMS.BODY, msg.getBody());
		values.put(SMS.TIME, System.currentTimeMillis());
		values.put(SMS.TYPE, Message.Type.chat.toString());
		values.put(SMS.STATUS, "");
		values.put(SMS.UNREAD, 0);
		values.put(SMS.SESSION_ID, msg.getTo());
		values.put(SMS.SESSION_NAME, sessionName);
		this.context.getContentResolver().insert(SmsProvider.SMS_URI, values);

	}
	public void saveReceiverMessage(Message msg) {
		//Сͷ@itheima.com-->����@itheima.com
		String from=NickUtil.filterAccount(msg.getFrom());
		ContentValues values=new ContentValues();
		values.put(SMS.FROM_ID, from);
		String nick=NickUtil.getNick(context, from);
		values.put(SMS.FROM_NICK, nick);
		values.put(SMS.FROM_AVATAR, 0);
		values.put(SMS.BODY, msg.getBody());
		values.put(SMS.TIME, System.currentTimeMillis());
		values.put(SMS.TYPE, Message.Type.chat.toString());
		values.put(SMS.STATUS, "");
		values.put(SMS.UNREAD, 0);
		values.put(SMS.SESSION_ID, from);
		values.put(SMS.SESSION_NAME, nick);
		this.context.getContentResolver().insert(SmsProvider.SMS_URI, values);
	}
}
