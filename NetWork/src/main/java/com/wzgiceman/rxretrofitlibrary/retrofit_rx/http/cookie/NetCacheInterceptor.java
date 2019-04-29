package com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie;

import android.util.Log;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.CookieDbUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by pc on 2018/11/24.
 */

public class NetCacheInterceptor implements Interceptor{
        private CookieDbUtil dbUtil;
        private String baseUrl;
        public NetCacheInterceptor(String baseUrl)
        {
            dbUtil= CookieDbUtil.getInstance();
            this.baseUrl=baseUrl;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                ResponseBody body = response.body();
                BufferedSource source = body.source();
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.buffer();
                Charset charset = Charset.defaultCharset();
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
            String bodyString = buffer.clone().readString(charset);
            try {
                JSONObject jsonObject=new JSONObject(bodyString);
                String ret = jsonObject.optString("ret");
                if (ret.equals("000000")){
                    String url = request.url().toString();
                    int index=url.indexOf("?");
                    String keyDb= url.substring(baseUrl.length(), index);
                    Log.e("CoinTabInter",url);
                    Log.e("CoinTabInter",keyDb);
                    CookieResulte resulte= dbUtil.queryCookieBy(keyDb);
                    long time=System.currentTimeMillis();
                /*保存和更新本地数据*/
                    if(resulte==null){
                        resulte  =new CookieResulte(keyDb,bodyString,time);
                        dbUtil.saveCookie(resulte);
                    }else{
                        resulte.setResulte(bodyString);
                        resulte.setTime(time);
                        dbUtil.updateCookie(resulte);
                    }
                }
            } catch (Exception e) {
            }
            return response;
        }
}
