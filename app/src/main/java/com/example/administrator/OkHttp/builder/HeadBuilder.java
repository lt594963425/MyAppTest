package com.example.administrator.OkHttp.builder;


import com.example.administrator.OkHttp.OkHttpUtils;
import com.example.administrator.OkHttp.request.OtherRequest;
import com.example.administrator.OkHttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
