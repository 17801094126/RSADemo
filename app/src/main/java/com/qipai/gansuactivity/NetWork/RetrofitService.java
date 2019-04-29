package com.qipai.gansuactivity.NetWork;


import com.qipai.gansuactivity.LoginEntity;
import com.qipai.gansuactivity.RSAEntity;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface RetrofitService {

    @GET("/get/core/RSA")
    Observable<RSAEntity> getRSAEntity();

    @POST("/userLogin")
    Observable<ResponseBody> getLoginEntity(@QueryMap Map<String, String> map);
}
