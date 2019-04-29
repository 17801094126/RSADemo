package com.qipai.gansuactivity;

import okhttp3.ResponseBody;

public interface Callback {

    interface RSACallback{
        void getRSASuccess(RSAEntity rsaEntity);
        void getRSAError(String error);
    }
    interface LoginCallback{
        void getLoginSuccess(ResponseBody loginEntity);
        void getLoginError(String error);
    }
}
