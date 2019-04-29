package com.qipai.gansuactivity.NetWork;

import com.qipai.gansuactivity.BuildConfig;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 获取网络请求Retrofit
 */
public class RetrofitUtils {
    private static Retrofit retrofit;
    private static Retrofit cacheRetrofit;
    private RetrofitUtils() {
    }

    /**
     * 实例化Retrofit
     * 默认使用缓存
     * 请求地址作为缓存key
     */
    public static Retrofit getInstance() {
        if (cacheRetrofit==null){
            synchronized (RetrofitUtils.class){
                if (cacheRetrofit==null){
                    /*创建retrofit对象*/
                    cacheRetrofit = new Retrofit.Builder()
                            .client(getNoCacheOkHttpClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .baseUrl(BuildConfig.BASE_URL)
                            .build();
                }
            }
        }
        return cacheRetrofit;
    }

    /**
     * 默认使用GreenDao缓存
     * @return
     */
    private static OkHttpClient getNoCacheOkHttpClient(){
        return new OkHttpClient.Builder()
                .certificatePinner(new CertificatePinner.Builder()
                        .add("sbbic.com", "sha1/C8xoaOSEzPC6BgGmxAt/EAcsajw=")
                        .add("closedevice.com", "sha1/T5x9IXmcrQ7YuQxXnxoCmeeQ84c=")
                        .build())
                .build();
    }

}