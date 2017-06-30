package com.example.administrator.fragmenttabhost;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘涛 on 2017/6/2 0002.
 * 文本框与输入框输入监听,电话号码格式判断,
 */

public class MyTextWatcher implements TextWatcher {
    private List<TextView> mList; //需要判空的textview与editText的集合
    private boolean hasEmpty = true; //判断是否有空值

    public List<TextView> getmList() {
        return mList;
    }

    public MyTextWatcher() {
        mList = new ArrayList<>();
    }

    public boolean isEmpty() {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getText().toString().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 添加textview与edittext,并添加事件
     * @param textView
     */
    public void addTextView(TextView textView) {
        mList.add(textView);
        textView.addTextChangedListener(this);
    }

    public void deleteTextView(TextView textView){
        mList.remove(textView);
    }


    /**
     * 获取空值状态
     *
     * @return
     */
    public boolean getHasEmpty() {
        return hasEmpty;
    }

    /**
     * edittext使用,用于,edittext获取焦点时,提示文字消失
     *
     * @param mInfo
     */
    public static void setEditText(EditText mInfo) {
        mInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("获取焦点", "输入框获取焦点");
                EditText _v = (EditText) v;
                if (!hasFocus) {// 失去焦点
                    _v.setHint(_v.getTag().toString());
                } else {
                    String hint = _v.getHint().toString();
                    _v.setTag(hint);
                    _v.setHint("");
                }
            }
        });
    }

    /**
     * 电话号码监听
     *
     * @param context
     * @param mInfo
     */
    public static void setPhoneEdit(final Context context, final EditText mInfo) {
        mInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.e("电话号码获取焦点", "电话号码输入框获取焦点");
                EditText _v = (EditText) view;
                if (!b) {// 失去焦点
                    _v.setHint(_v.getTag().toString());
                    if (!isMobileNO(mInfo.getText().toString())) {
                        Toast.makeText(context, "电话号码格式错误", Toast.LENGTH_SHORT).show();
                        mInfo.setText("");
                    }
                } else {
                    String hint = _v.getHint().toString();
                    _v.setTag(hint);
                    _v.setHint("");
                }
            }
        });
    }

    /**
     * 验证码监听
     *
     * @param context
     * @param mInfo
     */
    public static void setCodeEdit(final Context context, final EditText mInfo) {
        mInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.e("验证码获取焦点", "验证码输入框获取焦点");
                EditText _v = (EditText) view;
                if (!b) {// 失去焦点
                    _v.setHint(_v.getTag().toString());
                    if (mInfo.getText().toString().length() != 6) {
                        Toast.makeText(context, "请输入6位验证码", Toast.LENGTH_SHORT).show();
                        mInfo.setText("");
                    }
                } else {
                    String hint = _v.getHint().toString();
                    _v.setTag(hint);
                    _v.setHint("");
                }
            }
        });

    }

    /**
     *验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        hasEmpty = isEmpty();
    }
}