package com.example.administrator.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.fragmenttabhost.R;

public class ActivityTwo extends AppCompatActivity {
    private android.os.Handler handler = new android.os.Handler();
    private Toolbar toolbar;
    DrawerLayout drawerlayout;
    private   RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        //Toolbar和drawerLayout的联合使用 ActionBarDrawerToggle()
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
         rv = (RecyclerView) findViewById(R.id.rvw);
        //设置导航图标
        toolbar.setLogo(R.drawable.ic_dialog_email);
        toolbar.setTitle("刘涛");
        toolbar.setSubtitle("刘媛");
        toolbar.inflateMenu(R.menu.menu);
        ActionBarDrawerToggle toggle  = new ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.app_name,R.string.app_name);
        toggle.syncState();//同步

        drawerlayout.addDrawerListener(toggle);
        GridLayoutManager gm = new GridLayoutManager(this, 2);
        //布局方向
        gm.setOrientation(GridLayoutManager.VERTICAL);
        //绑定布局管理器
        rv.setLayoutManager(gm);
        //适配器
        rv.setAdapter(new MyRecyclerView());
        final SwipeRefreshLayout mRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRefresh.setProgressBackgroundColorSchemeColor(Color.GRAY);
        mRefresh.setColorSchemeColors(Color.BLUE,Color.GREEN,Color.RED);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(ActivityTwo.this,"正在努力加载",Toast.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //隐藏进度条
                        mRefresh.setRefreshing(false);
                    }
                },3000);
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.find:
                        Toast.makeText(ActivityTwo.this,"查找",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.jy:
                        Toast.makeText(ActivityTwo.this,"加好友",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.local:
                        Toast.makeText(ActivityTwo.this,"定位",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sao:
                        Toast.makeText(ActivityTwo.this,"扫描",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }
    class MyRecyclerView extends RecyclerView.Adapter{
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_rv, parent, false);
            return new MyViewHolder(view);
        }
        //绑定数据
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder mh = (MyViewHolder) holder;
            mh.setDate(position);
        }
        @Override
        public int getItemCount() {
            return 100;
        }
    }
    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvs;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvs = (TextView) itemView.findViewById(R.id.tv);
        }
        public void setDate(final int position){
            tvs.setText("我是第"+position+"位置");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ActivityTwo.this,"点击"+position,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
