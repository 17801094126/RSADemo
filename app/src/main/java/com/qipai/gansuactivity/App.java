package com.qipai.gansuactivity;

import android.app.Application;
import android.preference.Preference;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;

public class App extends Application {

    public static String modulus="modulus";
    public static String exponent="exponent";

    @Override
    public void onCreate() {
        super.onCreate();
        RxRetrofitApp.init(this);
        PreferenceUtil.init(this);
    }
}
