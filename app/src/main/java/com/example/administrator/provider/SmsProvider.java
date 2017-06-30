package com.example.administrator.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

public class SmsProvider extends ContentProvider {

	private static final String authority = "com.example.administrator.provider.SmsProvider";

	// �١�����Provider �������� Authority
	// �ڡ���Ʊ�

	public static class SMS implements BaseColumns// _id
	{

		public static final String FROM_ID = "from_id";// ����
		public static final String FROM_NICK = "from_nick";
		public static final String FROM_AVATAR = "from_avatar";
		public static final String BODY = "body";
		public static final String TYPE = "type";// chat
		public static final String TIME = "time";
		public static final String STATUS = "status";
		public static final String UNREAD = "unread";

		public static final String SESSION_ID = "session_id";
		public static final String SESSION_NAME = "session_name";

	}

	public static final String DB = "sms.db";
	public static final String TABLE = "sms";
	// �ۡ�ִ�д��������

	private SQLiteDatabase db = null;
	private MyOpenHelper mMyOpenHelper;

	private class MyOpenHelper extends SQLiteOpenHelper {
		public MyOpenHelper(Context context) {
			// super(������, ���ݿ���, �α깤�� Ĭ��Ϊnull, �汾��);
			super(context, DB, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table sms (_id integer  primary key autoincrement,session_id text,session_name text,from_id text,from_nick text,from_avatar integer ,body text,type text ,unread integer,status text,time long)");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public boolean onCreate() {
		mMyOpenHelper = new MyOpenHelper(getContext());
		return mMyOpenHelper == null ? false : true;
	}

	public static final Uri SMS_URI = Uri.parse("content://" + authority + "/sms");
	public static final Uri SMS_SESSON_URI = Uri.parse("content://" + authority + "/session");
	private static final int SMS = 0;
	private static final int SMS_SESSION = 1;
	private static UriMatcher uris = null;
	static {
		uris = new UriMatcher(UriMatcher.NO_MATCH);
		uris.addURI(authority, "sms", SMS);
		uris.addURI(authority, "session", SMS_SESSION);
	}

	// �ܡ�CRUD:��� ��ѯ���м�¼
	// �ݡ������ַUriMatcher
	// content://SmsProvider/sms
	// content://SmsProvider/sms/10
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		switch (uris.match(uri)) {
		case SMS:
			db = mMyOpenHelper.getWritableDatabase();
			long id = db.insert(TABLE, "", values);
			if (id != -1) {

				uri = ContentUris.withAppendedId(uri, id);
				// getContext().getContentResolver().notifyChange(uri,
				// null���еĹ۲���);
				getContext().getContentResolver().notifyChange(uri, null);
			}
			break;
		}
		return uri;
	}

	// ��ѯ
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Cursor c = null;
		switch (uris.match(uri)) {
		case SMS:
			db = mMyOpenHelper.getWritableDatabase();
			c = db.query(TABLE, null, selection, selectionArgs, null, null, sortOrder);
			break;
		case SMS_SESSION:
			db = mMyOpenHelper.getWritableDatabase();
			// select * from sms group by session_id order by time desc;
			c = db.query(TABLE, null, selection, selectionArgs, "session_id", null, "time DESC");
			break;
		}

		return c;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
