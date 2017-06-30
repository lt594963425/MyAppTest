package com.example.administrator.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.fragmenttabhost.R;
import com.example.administrator.service.ChatService;
import com.example.administrator.service.ImService;
import com.example.administrator.service.PushService;
import com.example.administrator.ui.activity.MainActivity;
import com.example.administrator.utils.ThreadUtils;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class LoginActivity extends Activity implements View.OnClickListener {

    EditText account;
    EditText pwd;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account= (EditText) findViewById(R.id.account);
        pwd= (EditText) findViewById(R.id.pwd);
        login= (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        account.setText("101");
        pwd.setText("test");
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                try {

                    // 192.168.20.71 5222
                    // 1.����ͨ��
                    ConnectionConfiguration configuration = new ConnectionConfiguration(MyApp.HOST, MyApp.PORT);
                    // Debug��Ϣ
                    configuration.setDebuggerEnabled(true);
                    // ʹ������
                    configuration.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);//
                    conn = new XMPPConnection(configuration);
                    conn.connect();
                    // 2.���ò���
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    XMPPConnection conn;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    String username;
    String password;
    boolean flag = false;
    @Override
    public void onClick(View view) {
        // Toast.makeText(getBaseContext(), "�ұ� ���� ", 0).show();
        username = account.getText().toString().trim();
        password = pwd.getText().toString().trim();

        ThreadUtils.runInThread(new Runnable() {

            @Override
            public void run() {

                try {
                    conn.login(username, password);
                    flag = true;
                } catch (XMPPException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    flag = true;//直接登录成功
                }catch (IllegalStateException e){
                    e.printStackTrace();
                    flag = true;//直接登录成功
                }
                ThreadUtils.runUIThread(new Runnable() {
                    @Override
                    public void run() {
                        // ������
                        if (flag) {
                            Toast.makeText(getBaseContext(), "登录成功", Toast.LENGTH_SHORT).show();
                            startService(new Intent(getBaseContext(), ImService.class));
                            startService(new Intent(getBaseContext(), PushService.class));
                            startService(new Intent(getBaseContext(), ChatService.class));
                            startActivity(new Intent(getBaseContext(), MainActivity.class));
                            // �˺�
                            MyApp.conn = conn;// ��¼�ɹ�������Ӵ���һ�������û�
                            MyApp.username = username;
                            MyApp.account = username + "@" + MyApp.SERVICE_NAME;
                            // ͨ��
                            finish();
                        } else {
                            Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }



}
