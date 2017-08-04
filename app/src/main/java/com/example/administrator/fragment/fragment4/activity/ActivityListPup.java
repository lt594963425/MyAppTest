package com.example.administrator.fragment.fragment4.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.R;
import com.example.administrator.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityListPup extends BaseActivity {
    private EditText et_number;
    private MyAdapter adaptet;
    private PopupWindow pw;
    private List<String> listNumber;
    private  ListView lv;
    private  ImageButton ib_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadd);
        setTitle("下拉列表");
        setBarBack();
        et_number = (EditText) findViewById(R.id.et_number);
        ib_number = (ImageButton) findViewById(R.id.ib_number);
    }

    public void showNumber(View view) {
        listNumber = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            listNumber.add("12345678" + i);
        }
        System.out.print("被点击了.........");
        //自定义一个ListView 并加入到PopupWindow
        lv = new ListView(this);
        lv.setDividerHeight(1);
        lv.setBackgroundResource(R.drawable.bg_cameral_album);
        lv.setVerticalScrollBarEnabled(false);//
        adaptet = new MyAdapter();
        lv.setAdapter(adaptet);
        //弹出一个popuwindow窗口，设置其显示内容和宽高度
        pw = new PopupWindow(lv,et_number.getWidth(),1000);
        pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//**************设置背景图片***************
        pw.setFocusable(true);
        //设置显示的位置
        pw.setOutsideTouchable(true);
        pw.showAsDropDown(et_number, 0, -5);
       /* ScaleAnimation sa = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);
        sa.setInterpolator(new LinearInterpolator());
        sa.setDuration(500);
        lv.startAnimation(sa);*/
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listNumber.size();
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            numberHolder holder = null;

            if(convertView == null) {
                holder = new numberHolder();
                convertView = View.inflate(ActivityListPup.this, R.layout.item_number, null);
                holder.tv_item_number = (TextView) convertView.findViewById(R.id.tv_item_number);
                holder.item_delete = (ImageButton) convertView.findViewById(R.id.item_delete);
                convertView.setTag(holder);
            }else {
                holder= (numberHolder) convertView.getTag();
            }

            holder.tv_item_number.setText(listNumber.get(position));
            holder.tv_item_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_number.setText(listNumber.get(position));
                }
            });
            holder.item_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listNumber.remove(position);
                    adaptet.notifyDataSetChanged();
                    if (listNumber.size() == 0){
                        pw.dismiss();
                    }
                }
            });
            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }
    class numberHolder{
        TextView tv_item_number;
        ImageButton item_delete;

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
