
package com.example.administrator.ui.activity;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.fragmenttabhost.R;
import com.example.administrator.utils.Chinese;
import com.example.administrator.utils.DragLayout;
import com.example.administrator.utils.MyLinnerLayout;


public class ActivityOne extends Activity {
    private static final int PER = 0;
    private LinearLayout main;
    private LinearLayout menu;
    private ListView lv_main;
    private ListView lv_menu;
    private ImageView iv_header;
    private DragLayout dl;
    private MyLinnerLayout ller_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        lv_main = (ListView) findViewById(R.id.lv_main);
        lv_menu = (ListView) findViewById(R.id.lv_leftmenu);
        dl = (DragLayout) findViewById(R.id.dl);
        lv_main.setAdapter(new ArrayAdapter<String>(this,R.layout.item_tv, Chinese.NAMES){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView)view).setTextColor(Color.BLACK);
                return view;
            }
        });
        lv_menu.setAdapter(new ArrayAdapter<String>(this, R.layout.item_tv,Chinese.sCheeseStrings));
        iv_header = (ImageView) findViewById(R.id.iv_header);
        iv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.open();
            }
        });

        dl.setDragChangedListener(new DragLayout.onDragChangedListener() {
            @Override
            public void onOpen() {
                Toast.makeText(ActivityOne.this,"打开",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onClose() {
                Toast.makeText(ActivityOne.this,"关闭",Toast.LENGTH_LONG).show();
                ObjectAnimator animator = ObjectAnimator.ofFloat(iv_header, "translationX", 15f);
                animator.setInterpolator(new CycleInterpolator(4));
                animator.setDuration(500);
                animator.start();
            }

            @Override
            public void onGraging(float percent) {
            }
        });
        ller_main = (MyLinnerLayout) findViewById(R.id.ller_main);
        ller_main.setDragLayout(dl);
    }
}
