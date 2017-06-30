package com.example.administrator.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dao.SmsDao;
import com.example.administrator.fragmenttabhost.R;
import com.example.administrator.provider.SmsProvider;
import com.example.administrator.utils.MyTime;
import com.example.administrator.utils.ThreadUtils;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends Activity {

	private String toAccount = "";
	private String toNick = "";
	@BindView(R.id.title)
	TextView title;
	@BindView(R.id.chatlistview)
	ListView chatlistview;
	@BindView(R.id.input)
	EditText input;


	private MyObserver mMyObserver=new MyObserver(new Handler());
	private class MyObserver extends ContentObserver {

		public MyObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			setAdapterOrNotify();
		}

		@Override
		public void onChange(boolean selfChange, Uri uri) {
			setAdapterOrNotify();
		}

	}

	@OnClick(R.id.send)
	public void send(View view) {
		final String body = input.getText().toString().trim();
		if ("".equals(body)) {
			Toast.makeText(getBaseContext(), "��Ϣ����Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}
		input.setText("");

		ThreadUtils.runInThread(new Runnable() {

			@Override
			public void run() {
				try {
					// �ۡ�����Message
					Message msg = new Message();
					msg.setType(Message.Type.chat);
					msg.setBody(body);
					msg.setFrom(MyApp.account);// @itheima.com
					msg.setTo(toAccount);
					dao.saveSendMessage(msg);
					currChat.sendMessage(msg);
					// �ܡ�����Chat sendmessage
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (currChat != null) {
			currChat.removeMessageListener(listener);
		}
		getContentResolver().unregisterContentObserver(mMyObserver);
	}

	private Chat currChat;

	private SmsDao dao = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		getContentResolver().registerContentObserver(SmsProvider.SMS_URI, true, mMyObserver);
		
		dao = new SmsDao(getBaseContext());
		ButterKnife.bind(this);
		Intent intent = getIntent();
		toAccount = intent.getStringExtra("account");
		toNick = intent.getStringExtra("nick");
		title.setText("��" + toNick + "������...");

		ThreadUtils.runInThread(new Runnable() {
			@Override
			public void run() {
				// �١�����Chat����
				if (currChat == null) {
					// �ڡ�����ChatManager
					ChatManager cm = MyApp.conn.getChatManager();
					// currChat=cm.createChat(�˺�, ��Ϣ������);
					currChat = cm.createChat(toAccount, null);
					currChat.addMessageListener(listener);
				}

			}
		});

		setAdapterOrNotify();

	}

	private CursorAdapter adapter = null;

	private void setAdapterOrNotify() {
		// TODO Auto-generated method stub
		if (adapter == null) {

			final Cursor c = getContentResolver().query(SmsProvider.SMS_URI, null, null, null, SmsProvider.SMS.TIME + " ASC");
			if (c.getCount() < 1) {
				return;
			}
			adapter = new CursorAdapter(this, c) {

				// ������ͼ���� 0 1
				@Override
				public int getViewTypeCount() {
					return 2;
				}

				// ���� position�������� 0���� from=me 1 ���� from !=me
				@Override
				public int getItemViewType(int position) {
					c.moveToPosition(position);
					String fromId = c.getString(c.getColumnIndex(SmsProvider.SMS.FROM_ID));
					if (MyApp.account.equals(fromId)) {
						return 0;
					}
					return 1;
				}

				// ��������ͼ ��ʾָ���±������
				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					int type = getItemViewType(position);
					if (type == 0) {// ������ͼ
						ViewHolder holder1 = null;
						if (convertView == null) {
							convertView = View.inflate(getBaseContext(), R.layout.item_chat_send, null);
							holder1 = new ViewHolder(convertView);
							convertView.setTag(holder1);
						} else {
							holder1 = (ViewHolder) convertView.getTag();
						}

						c.moveToPosition(position);
						long time = c.getLong(c.getColumnIndex(SmsProvider.SMS.TIME));
						String timeStr = MyTime.getTime(time);
						String body = c.getString(c.getColumnIndex(SmsProvider.SMS.BODY));
						holder1.time.setText(timeStr);
						holder1.content.setText(body);
						return convertView;

					} else if (type == 1) {// ����
						ViewHolder holder2 = null;
						if (convertView == null) {
							convertView = View.inflate(getBaseContext(), R.layout.item_chat_receive, null);
							holder2 = new ViewHolder(convertView);
							convertView.setTag(holder2);
						} else {
							holder2 = (ViewHolder) convertView.getTag();
						}
						c.moveToPosition(position);
						long time = c.getLong(c.getColumnIndex(SmsProvider.SMS.TIME));
						String timeStr = MyTime.getTime(time);
						String body = c.getString(c.getColumnIndex(SmsProvider.SMS.BODY));
						holder2.time.setText(timeStr);
						holder2.content.setText(body);
						return convertView;
					}
					return convertView;
				}

				@Override
				public View newView(Context context, Cursor cursor, ViewGroup parent) {
					return null;
				}

				@Override
				public void bindView(View view, Context context, Cursor cursor) {

				}
			};

			chatlistview.setAdapter(adapter);
		} else {
			adapter.getCursor().requery();
		}

		// ��������
		if (adapter.getCursor().getCount() >= 1) {
			chatlistview.setSelection(adapter.getCursor().getCount() - 1);
		}

	}

	static class ViewHolder {
		@BindView(R.id.time)
		TextView time;
		@BindView(R.id.content)
		TextView content;
		@BindView(R.id.head)
		ImageView head;

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}

	private MessageListener listener = new MessageListener() {

		// ������Ϣ
		@Override
		public void processMessage(Chat chat, final Message message) {
			if (Message.Type.chat.equals(message.getType())) {
				System.out.println(message.toXML());
				dao.saveReceiverMessage(message);
				ThreadUtils.runUIThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(getBaseContext(), message.getBody(), Toast.LENGTH_SHORT).show();

					}
				});
			}

		}
	};

}
