package com.example.administrator.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.activity.ChatActivity;
import com.example.administrator.fragmenttabhost.R;
import com.example.administrator.provider.SmsProvider;
import com.example.administrator.utils.NickUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


//Fragment ����Activity
public class SessionFragment extends BaseFragment {
	// ���ص�ǰFragment����ʾ���� onCreate setContentView
	
	private ListView sessionlistview;
	@Override
	public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// inflate ��layout xml�ļ�ת����View���� �ڲ�ʵ�� LayoutInflater����������
		View view = View.inflate(getActivity(), R.layout.fragment_session, null);
		sessionlistview=(ListView) view.findViewById(R.id.sessionlistview);
		setAdapterOrNotify();
		getActivity().getContentResolver().registerContentObserver(SmsProvider.SMS_URI, true, observer);
		return view;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().getContentResolver().unregisterContentObserver(observer);
	}
	private class MyObserver extends ContentObserver
	{

		public MyObserver(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
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
	private MyObserver observer=new MyObserver(new Handler());
	private CursorAdapter adapter = null;

	private void setAdapterOrNotify() {

		if (adapter == null) {
			final Cursor c = getActivity().getContentResolver().query(SmsProvider.SMS_SESSON_URI, null, null, null, null);
			if (c.getCount() < 1) {
				return;
			}

			adapter = new CursorAdapter(getActivity(), c, true) {

				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					ViewHolder holder = null;
					if (convertView == null) {
						convertView = View.inflate(getActivity(), R.layout.item_session, null);
						holder = new ViewHolder(convertView);
						convertView.setTag(holder);
					} else {
						holder = (ViewHolder) convertView.getTag();
					}
					// �Ż�
					c.moveToPosition(position);// list.get(position)

					String session_id = c.getString(c.getColumnIndex(SmsProvider.SMS.SESSION_ID));
					String body = c.getString(c.getColumnIndex(SmsProvider.SMS.BODY));
					String nick= NickUtil.getNick(getActivity(), session_id);
					holder.nick.setText(nick);
					holder.account.setText(body);
					return convertView;

				}

				// converView==null; ����inflate
				@Override
				public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
					return null;
				}

				// convertView!=null findViewById set
				@Override
				public void bindView(View arg0, Context arg1, Cursor arg2) {

				}
			};
			
			sessionlistview.setAdapter(adapter);
			
			sessionlistview.setOnItemClickListener(new ListView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					
					Cursor cursor=adapter.getCursor();
					cursor.moveToPosition(position);// list.get(position)

					String session_id = cursor.getString(cursor.getColumnIndex(SmsProvider.SMS.SESSION_ID));
					String nick=NickUtil.getNick(getActivity(), session_id);
					Intent intent=new Intent(getActivity(),ChatActivity.class);
					intent.putExtra("account", session_id);
					intent.putExtra("nick", nick);
					startActivity(intent);
					
				}
			});
		} else {
			// adapter.notifyDataSetChanged();
			// c.requery();
			adapter.getCursor().requery();
		}

	}

	static class ViewHolder {
		@BindView(R.id.head)
		ImageView head;
		@BindView(R.id.nick)
		TextView nick;
		@BindView(R.id.account)
		TextView account;

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
