package com.example.administrator.greendao;
import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;
/**
 *
 * Created by liu_tao on 2016/4/17.
 */
public class CustomGenerater {
    public static void main(String[] args) {

        /**
         * 第一个参数: 指定数据库的版本
         * 第二个参数: 指定这个工具帮我们生成的java代码的包名
         */
        Schema schema = new Schema(1, "com.alpha.demogreendao.db");
        //  addEntity方法传的值就是数据库中要创建的表的名称
        Entity entity = schema.addEntity("User");
        entity.addIdProperty();// 添加ID字段
        entity.addStringProperty("name");// 添加String字段,字段的名字叫做name
        entity.addIntProperty("age");// 添加Int字段,字段的名字叫做age
        entity.addStringProperty("tel");// 添加String字段,字段的名字叫做tel

        try {
            // 第二个参数就是指定生成的文件在本地磁盘的存储路径
            new DaoGenerator().generateAll(schema, "D:\\WorkSpace\\OSDemo\\DemogreenDAO\\app\\src\\main\\java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
