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

public class ContactProvider extends ContentProvider {
	private static final String TABLE = "contacts";
	private static final String authority = "com.example.administrator.provider.ContactProvider";
	public static final Uri CONTACT_URI = Uri.parse("content://" + authority + "/"+TABLE);
	// ��ṹ
	// BaseColumn s _id
	public static class CONTACT implements BaseColumns {
		public static final String ACCOUNT = "account";
		public static final String NICK = "nick";
		public static final String AVATAR = "avatar";
		public static final String SORT = "sort";// ƴ��
	}


	private static final String DB = "contact3.db";
	private String sql = "create table contacts(_id integer primary key autoincrement,account text,nick text,avatar integer,sort Text)";

	private MyHelper mMyHelper = null;

	private class MyHelper extends SQLiteOpenHelper {

		public MyHelper(Context context) {
			super(context, DB, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}

	private SQLiteDatabase db = null;

	@Override
	public boolean onCreate() {
		mMyHelper = new MyHelper(getContext());
		return mMyHelper == null ? false : true;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	// content://contact/contacts
	// content://contact/contacts/1
	// ContentUris 1.+id 2. id
	private static UriMatcher uris = null;

	private static final int CONTACT_CODE = 0;
	static {
		uris = new UriMatcher(UriMatcher.NO_MATCH);
		uris.addURI(authority, TABLE, CONTACT_CODE);
	}

	// ���һ����ϵ��
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		switch (uris.match(uri)) {
		case CONTACT_CODE:
			System.out.println("insert---");
			db = mMyHelper.getWritableDatabase();
			long id = db.insert(TABLE, "", values);
			if (id != -1) {
				uri = ContentUris.withAppendedId(uri, id);
				getContext().getContentResolver().notifyChange(uri, null);//null���еĹ۲���
			}
			break;
		}
		return uri;
	}

	// ɾ�� ����ɾ���ļ�¼��
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		switch (uris.match(uri)) {
		case CONTACT_CODE:
			System.out.println("delete---");
			db = mMyHelper.getWritableDatabase();
			count = db.delete(TABLE, selection, selectionArgs);
			if(count>0)
			{
				getContext().getContentResolver().notifyChange(uri, null);//null���еĹ۲���
			}
			break;
		}
		return count;
	}

	// ���� �޸�һ����¼ �ǳ�
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int count = 0;
		switch (uris.match(uri)) {
		case CONTACT_CODE:
			System.out.println("update---");
			db = mMyHelper.getWritableDatabase();
			count = db.update(TABLE, values, selection, selectionArgs);
			if(count>0)
			{
				getContext().getContentResolver().notifyChange(uri, null);//null���еĹ۲���
			}
			break;
		}
		return count;
	}
	// projection���� select nick from contacts
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Cursor c = null;
		switch (uris.match(uri)) {
		case CONTACT_CODE:
			db = mMyHelper.getReadableDatabase();
			System.out.println("query---");
			c = db.query(TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		}
		return c;
	}

}
