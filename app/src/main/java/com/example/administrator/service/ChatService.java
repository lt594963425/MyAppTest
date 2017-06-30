package com.example.administrator.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.administrator.activity.MyApp;
import com.example.administrator.dao.SmsDao;
import com.example.administrator.utils.NickUtil;
import com.example.administrator.utils.ThreadUtils;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class ChatService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(getBaseContext(), "��������...", 0).show();

		// Chat
		cm = MyApp.conn.getChatManager();
		cm.addChatListener(listener);
		dao = new SmsDao(getBaseContext());
	}

	SmsDao dao = null;
	ChatManager cm;
	ChatManagerListener listener = new ChatManagerListener() {

		// �������� createdLocally true ������������ createChat false ���˹���������
		@Override
		public void chatCreated(Chat chat, final boolean createdLocally) {
			System.out.println("---chatCreated--" + createdLocally);
			if (!createdLocally) {
				chat.addMessageListener(messagelistener);
			}
			// ThreadUtils.runUIThread(new Runnable() {
			// @Override
			// public void run() {
			// Toast.makeText(getBaseContext(), "chatCreated..."+createdLocally,
			// 0).show();
			//
			// }
			// });
		}
	};
	private MessageListener messagelistener = new MessageListener() {

		@Override
		public void processMessage(Chat chat, final Message message) {
			if (!Message.Type.chat.equals(message.getType())) {
				return;
			}
			String fromId = NickUtil.filterAccount(message.getFrom());
			if (!MyApp.account.equals(fromId)) {
				dao.saveReceiverMessage(message);
			}
			ThreadUtils.runUIThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getBaseContext(), message.getBody(), 0).show();
				}
			});
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (cm != null) {
			cm.removeChatListener(listener);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
