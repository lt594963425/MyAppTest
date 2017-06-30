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
import com.example.administrator.provider.ContactProvider;
import com.example.administrator.provider.ContactProvider.CONTACT;

import butterknife.BindView;
import butterknife.ButterKnife;


//Fragment ����Activity
public class ContactFragment extends BaseFragment {

	// FragmentActivity��ɾ����
	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().getContentResolver().unregisterContentObserver(mMyObserver);
	}

	ListView contactlistview;

	private MyObserver mMyObserver = new MyObserver(new Handler());

	private class MyObserver extends ContentObserver {

		public MyObserver(Handler handler) {
			super(handler);
		}

		// �Ͱ汾 2.2
		@Override
		public void onChange(boolean selfChange) {
			System.out.println("----�Ͱ汾 2.2---");
			setAdapterOrNotify();
		}

		// �߰汾 4.0
		@Override
		public void onChange(boolean selfChange, Uri uri) {
			System.out.println("----�߰汾 4.0---");
			setAdapterOrNotify();
		}

	}

	// ���ص�ǰFragment����ʾ���� onCreate setContentView
	@Override
	public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// true����·�������� false ������
		// content://contact/contacts
		// content://contact/contacts/1
		getActivity().getContentResolver().registerContentObserver(ContactProvider.CONTACT_URI, true, mMyObserver);
		// inflate ��layout xml�ļ�ת����View���� �ڲ�ʵ�� LayoutInflater����������
		View view = View.inflate(getActivity(), R.layout.fragment_contact, null);
		contactlistview = (ListView) view.findViewById(R.id.contactlistview);
		setAdapterOrNotify();
		return view;
	}

	private CursorAdapter adapter = null;

	private void setAdapterOrNotify() {

		if (adapter == null) {
			final Cursor c = getActivity().getContentResolver().query(ContactProvider.CONTACT_URI, null, null, null, CONTACT.SORT + " ASC");// A-->Z
			if (c.getCount() < 1) {
				return;
			}
			adapter = new CursorAdapter(getActivity(), c, true) {

				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					ViewHolder holder = null;

					if (convertView == null) {
						convertView = View.inflate(getActivity(), R.layout.item_buddy, null);
						holder = new ViewHolder(convertView);
						convertView.setTag(holder);
					} else {
						holder = (ViewHolder) convertView.getTag();
					}
					// �Ż�
					c.moveToPosition(position);// list.get(position)

					String account = c.getString(c.getColumnIndex(CONTACT.ACCOUNT));
					String nick = c.getString(c.getColumnIndex(CONTACT.NICK));

					holder.account.setText(account);
					holder.nick.setText(nick);
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
			contactlistview.setAdapter(adapter);
			contactlistview.setOnItemClickListener(new ListView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					adapter.getCursor().moveToPosition(position);
					//����  account nick
					Cursor cursor=adapter.getCursor();
					String account = cursor.getString(cursor.getColumnIndex(CONTACT.ACCOUNT));
					String nick = cursor.getString(cursor.getColumnIndex(CONTACT.NICK));
					
					Intent intent=new Intent(getActivity(),ChatActivity.class);
					intent.putExtra("account", account);
					intent.putExtra("nick", nick);
					startActivity(intent);
				
				}
			});
		} else {
			// adapter.notifyDataSetChanged();
//			 c.requery();
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
