package com.example.administrator.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by 刘涛  on 2017/5/27 0027.
 * 封装和ui相关的操作
 */
public class UIUtils {
    /**
     * 得到上下文
     */
    public static Context getContext() {
        return getContext();
    }

    /**
     * 得到Resource对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中的字符串信息
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 得到String.xml中的字符串数组信息
     */
    public static String[] getStrings(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到Color.xml中的颜色信息
     */
    public static int getColor(int resId) {
        // 同时兼容高、低版本
        return ContextCompat.getColor(getContext(),resId);
    }

    /**
     * 得到应用程序包名
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    public static void openAnimator(AppCompatActivity activity) {
        activity.overridePendingTransition(R.anim.zoom_enter,R.anim.no_anim);
    }
    /**
     * dip-->px
     */
    public static int dip2Px(int dip) {
        // px/dp = density
        //取得当前手机px和dp的倍数关系
        float density = getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + .5f);
        return px;
    }

    public static int px2Dip(int px) {
        // px/dp = density
        //取得当前手机px和dp的倍数关系
        float density = getResources().getDisplayMetrics().density;
        int dip = (int) (px / density + .5f);
        return dip;
    }


    public final static int LEFT = 0, RIGHT = 1, TOP = 3, BOTTOM = 4;

    public static void addIcon(TextView view, int resId, int orientation) {
        addIcon(view, resId, orientation, 16);
    }

    public static void addIcon(TextView view, int resId, int orientation, int padding) {
        if (view == null) return;
        VectorDrawableCompat drawable = VectorDrawableCompat.create(getContext().getResources(), resId, null);
        switch (orientation) {
            case LEFT:
                view.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                break;
            case RIGHT:
                view.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                break;
            case TOP:
                view.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                break;
            case BOTTOM:
                view.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
                break;
        }
        view.setCompoundDrawablePadding(UIUtils.dip2Px(padding));
    }

    /**
     * @param v1 你要控制显示和隐藏内容的View
     * @param v2  你所点击的可见与不可见的View
     * @param sid1 不可见View
     * @param sid2 可见View
     */
    private static boolean flag;

    public static void isLook(TextView v1, ImageView v2, int sid1, int sid2) {
        v1.setHorizontallyScrolling(true);//不可换行
        if (flag == true) {
            v1.setTransformationMethod(PasswordTransformationMethod.getInstance());
            flag = false;
            v2.setImageResource(sid1);
        } else {
            v1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            flag = true;
            v2.setImageResource(sid2);
        }
    }

    /**
     * 保存历史号码
     *
     * @param context
     * @param field
     * @param text
     */
    public static void saveHistory(Context context, String field, String text) {
        SharedPreferences sp = context.getSharedPreferences("phone_history", 0);
        String phonehistory = sp.getString(field, "nothing");
        if (!phonehistory.contains(text + ",")) {
            StringBuilder sb = new StringBuilder(phonehistory);
            sb.insert(0, text + ",");
            sp.edit().putString("history", sb.toString()).commit();
        }
    }

    //保存卡号
    public static void saveCardHistory(Context context, String field, String text) {
        SharedPreferences sp = context.getSharedPreferences("card_history", 0);
        String phonehistory = sp.getString(field, "");
        if (!phonehistory.contains(text + ",")) {
            StringBuilder sb = new StringBuilder(phonehistory);
            sb.insert(0, text + ",");
            sp.edit().putString("cardhistory", sb.toString()).commit();
        }
    }

    //初始化用户号码
    public static void initAutoComplete(Context context, String field, AutoCompleteTextView auto) {
        SharedPreferences sp = context.getSharedPreferences("phone_history", 0);
        initAutoTextView(context, field, auto, sp);
    }

    //初始化用户号码
    public static void initAutoCompleteCard(Context context, String field, AutoCompleteTextView auto) {
        SharedPreferences sp = context.getSharedPreferences("card_history", 0);
        initAutoTextView(context, field, auto, sp);
    }

    private static void initAutoTextView(Context context, String field, AutoCompleteTextView auto, SharedPreferences sp) {
        String longhistory = sp.getString(field, " ");
        String[] hisArrays = longhistory.split(",");
        if (hisArrays.length < 2) {
            return;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, hisArrays);
        if (hisArrays.length > 50) {
            String[] newArrays = new String[50];
            System.arraycopy(hisArrays, 0, newArrays, 0, 50);
            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, newArrays);
        }
        auto.setAdapter(adapter);
        auto.setDropDownHeight(350);
        auto.setThreshold(1);
        auto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                AutoCompleteTextView view = (AutoCompleteTextView) v;
                if (hasFocus) {
                    view.showDropDown();
                }
            }
        });
    }

    /**
     * 判断手机号码是否正确
     */
    public static boolean isMobileNO(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) return false;
        String regExp = "((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(15([0-3]|[5-9]))|(18[0-9]))\\d{8}$";//;
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

//    //存
//    public static void saveInfoList(Context con, List<OrderRechargeInfo> list) {
//        SharedPreferences sp = con.getSharedPreferences("SP_INFO_List", Activity.MODE_PRIVATE);
//        Gson gson = new Gson();
//        String jsonStr = gson.toJson(list); //将List转换成Json
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("KEY_INFO_list", jsonStr); //存入json串
//        editor.commit();  //提交
//    }
//
//    //取或查
//    @NonNull
//    public static List<OrderRechargeInfo> getInfoList(Context con) {
//        List<OrderRechargeInfo> infoList = new ArrayList<>();
//        SharedPreferences sp = con.getSharedPreferences("SP_INFO_List", Activity.MODE_PRIVATE);
//        Gson gson = new Gson();
//        String infoListString = sp.getString("KEY_INFO_list", "");
//        if (!TextUtils.isEmpty(infoListString)) {
//            List<OrderRechargeInfo> temp = gson.fromJson(infoListString, new TypeToken<List<OrderRechargeInfo>>() {
//            }.getType());
//            if (temp != null) infoList.addAll(temp);
//        }
//        return infoList;
//    }

    public static void clearDetialData(Context con) {
        SharedPreferences sp = con.getSharedPreferences("SP_INFO_List", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

//    //删除
//    public static void delete(Context con, int position) {
//        SharedPreferences sp = con.getSharedPreferences("SP_INFO_List", Activity.MODE_PRIVATE);
//        String InfoListStringJson = sp.getString("KEY_INFO_list", "");
//        if (InfoListStringJson != "")  //防空判断
//        {
//            Gson gson = new Gson();
//            List<OrderRechargeInfo> peopleList = gson.fromJson(InfoListStringJson, new TypeToken<List<OrderRechargeInfo>>() {
//            }.getType()); //1.2. 取出并转换成List
//            peopleList.remove(position); //3.移除第position个的javabean
//            String jsonStr = gson.toJson(peopleList); //4.将删除完的List转换成Json
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString("KEY_INFO_list", jsonStr); //存入json串
//            editor.commit();  //提交
//        }
//    }

    /**
     * 限定Xiao数点
     */
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });

    }


    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            } else {
                device_id = getRandomString(15);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}