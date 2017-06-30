package com.example.administrator.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.fragmenttabhost.R;
import com.example.administrator.utils.ThreadUtils;


public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);
		ThreadUtils.runInThread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					
					startActivity(new Intent(getBaseContext(),LoginActivity.class));
					finish();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}
}
