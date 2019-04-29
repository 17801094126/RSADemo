package com.wzgiceman.rxretrofitlibrary.retrofit_rx.intercepter;

import android.text.TextUtils;
import android.util.Log;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.SignUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SignIntercepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Map<String,String> map = new TreeMap<>();
        Set<String> names = request.url().queryParameterNames();
        for(String name : names)
        {
            map.put(name,request.url().queryParameter(name));
        }
        if(request.body() instanceof FormBody)
        {
            FormBody formBody = (FormBody) request.body();
            formBody.contentType().charset(Charset.forName("utf-8"));//
            for (int i = 0; i < formBody.size(); i++) {
                String value = formBody.value(i);
                map.put(formBody.name(i)+"",value);
            }
        }
        Log.i("sign",map.toString());
        String url = request.url().toString();
        String newUrl = "";
        if(url.contains("app/version"))
        {
            newUrl = url.replace("version=02","");
        }
        long time = System.currentTimeMillis();
        Request.Builder builder = request.newBuilder()
                .addHeader("sign", SignUtil.createSign(map, time + ""))
                .addHeader("timeStamp", time + "");
        if(!TextUtils.isEmpty(newUrl))
        {
            builder.url(newUrl);
        }
        return chain.proceed(builder.build());
    }
}
