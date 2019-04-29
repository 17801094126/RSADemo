package com.qipai.gansuactivity.NetWork;

import com.qipai.gansuactivity.Callback;
import com.qipai.gansuactivity.LoginEntity;
import com.qipai.gansuactivity.RSAEntity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.OnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.SignUtil;

import java.util.Map;
import java.util.TreeMap;

import okhttp3.ResponseBody;


public class HttpHelper {

    public static void getRSA(final Callback.RSACallback callback){

        RetrofitService retrofitService = HttpFactory.createRetrofitService(RetrofitService.class);
        HttpFactory.createObserverable(retrofitService.getRSAEntity(), new OnNextListener<RSAEntity>() {
            @Override
            public void onNext(RSAEntity data) {
                callback.getRSASuccess(data);
            }

            @Override
            public void onError(Throwable error) {
                super.onError(error);
                callback.getRSAError(error.toString());
            }
        });
    }


    public static void getLogin(String userName, String password, final Callback.LoginCallback callback){
        RetrofitService retrofitService = HttpFactory.createRetrofitService(RetrofitService.class);


        Map<String,String> map=new TreeMap<>();
        map.put("userAlias",userName);
        map.put("password",password);
        map.put("key","USN3N388FJAZV08899AN2UUSASSD");
        String sign = SignUtil.createSign(map, String.valueOf(System.currentTimeMillis()));
        map.put("sign",sign);
        map.remove("key");
        HttpFactory.createObserverable(retrofitService.getLoginEntity(map), new OnNextListener<ResponseBody>() {

            @Override
            public void onNext(ResponseBody data) {
                callback.getLoginSuccess(data);
            }

            @Override
            public void onError(Throwable error) {
                super.onError(error);
                callback.getLoginError(error.toString());
            }
        });
    }
}
