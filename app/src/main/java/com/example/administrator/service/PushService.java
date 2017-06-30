package com.example.administrator.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.administrator.activity.MyApp;
import com.example.administrator.utils.ThreadUtils;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class PushService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(getBaseContext(), "���ͷ����...", 0).show();

		// MyApp.conn.addPacketListener(�������� , ���� );
		MyApp.conn.addPacketListener(new PacketListener() {

			@Override
			public void processPacket(final Packet packet) {
				System.out.println(packet.toXML());
				if ("itheima.com".equals(packet.getFrom())) {
					ThreadUtils.runUIThread(new Runnable() {
						@Override
						public void run() {
							Message msg = (Message) packet;
							// TODO Auto-generated method stub
							Toast.makeText(getBaseContext(), "�̳�:" + msg.getBody(), 0).show();
						}
					});
				}
				// ??-?? ??:??:??.???: INFO/<unknown>(<unknown>): <message
				// from="itheima.com"><body>ddd</body><thread>B312Y0</thread></message>

			}
		}, null);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
