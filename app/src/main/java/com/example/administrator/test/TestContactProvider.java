package com.example.administrator.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.test.InstrumentationTestCase;

import com.example.administrator.MyApplication;
import com.example.administrator.provider.ContactProvider;
import com.example.administrator.provider.SmsProvider;
import com.example.administrator.utils.NickUtil;


public class TestContactProvider extends InstrumentationTestCase {

    public void testInsert() {
        Context context = MyApplication.getContext();
        ContentValues values = new ContentValues();
        values.put(ContactProvider.CONTACT.ACCOUNT, "老王 @itheima.com");
        values.put(ContactProvider.CONTACT.NICK, "老王");
        values.put(ContactProvider.CONTACT.AVATAR, 0);
        values.put(ContactProvider.CONTACT.SORT, NickUtil.getNick("老王"));
        context.getContentResolver().insert(ContactProvider.CONTACT_URI, values);
    }

    public void testUpdate() {
        Context context = MyApplication.getContext();
        ContentValues values = new ContentValues();
        values.put(ContactProvider.CONTACT.ACCOUNT, "老王 @itheima.com");
        values.put(ContactProvider.CONTACT.NICK, "老王222");
        values.put(ContactProvider.CONTACT.AVATAR, 0);
        values.put(ContactProvider.CONTACT.SORT, NickUtil.getNick("老王22"));
        context.getContentResolver().update(ContactProvider.CONTACT_URI, values, ContactProvider.CONTACT.ACCOUNT + "=?", new String[]{"老王 @itheima.com"});
    }

    public void testDelete() {
        Context context = MyApplication.getContext();
        context.getContentResolver().delete(ContactProvider.CONTACT_URI, ContactProvider.CONTACT.ACCOUNT + "=?", new String[]{"老王 @itheima.com"});
    }

    public void testQuery() {
        Context context = MyApplication.getContext();
        Cursor c = context.getContentResolver().query(ContactProvider.CONTACT_URI, null, null, null, ContactProvider.CONTACT.SORT + " ASC");// A-->Z
        while (c.moveToNext()) {
            for (int i = 0; i < c.getColumnCount(); i++) {
                System.out.print(c.getString(i) + "  ");
            }
            System.out.println();
        }
        c.close();
    }

    public void testSession() {
        Context context = MyApplication.getContext();
        Cursor c = context.getContentResolver().query(SmsProvider.SMS_SESSON_URI, null, null, null, null);
        while (c.moveToNext()) {
            for (int i = 0; i < c.getColumnCount(); i++) {
                System.out.print(c.getString(i) + "  ");
            }
            System.out.println();
        }
        c.close();
    }
}
