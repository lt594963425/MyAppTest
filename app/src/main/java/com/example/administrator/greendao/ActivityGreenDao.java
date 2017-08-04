package com.example.administrator.greendao;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.administrator.R;
import com.example.administrator.greendao.db.DaoMaster;
import com.example.administrator.greendao.db.DaoSession;
import com.example.administrator.greendao.db.User;
import com.example.administrator.greendao.db.UserDao;

import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;



/**
 * ActivityGreenDao
 * Created by 刘涛 on 2017/6/30 0030.
 */

public class ActivityGreenDao  extends AppCompatActivity {
    private UserDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
        /**
         * @param context :　Context
         * @param name : 数据库名字
         * @param factory : CursorFactroy
         */
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "student.db", null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession session = daoMaster.newSession();
        dao = session.getUserDao();
    }
    public void add(View v) {
        User user = new User(null, "zhangsan", 12, "13112345678");
        User user2 = new User(null, "lisi", 22, "13222345678");
        User user3 = new User(null, "wangwu", 32, "13332345678");
        User user4 = new User(null, "zhaoqi", 42, "13442345678");
        dao.insert(user);
        dao.insert(user2);
        dao.insert(user3);
        dao.insert(user4);
    }

    public void del(View v) {
        dao.deleteByKey(2L);
    }

    public void update(View v) {
        User user4 = new User(4L, "赵琦", 42, "13442345678");
        dao.update(user4);
    }

    public void query(View v) {

        QueryBuilder<User> userQueryBuilder = dao.queryBuilder();
        QueryBuilder<User> builder = userQueryBuilder.where(UserDao.Properties.Age.eq("32"));
        Query<User> build = builder.build();
        List<User> list = build.list();
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            Log.e("MainActivity", user.getName());

        }


    }

}
