package com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.HttpTimeException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.OnNextListener;

import java.lang.ref.SoftReference;
import java.util.Map;

import rx.functions.Func1;

/**
 * 请求数据统一封装类
 * Created by WZG on 2016/7/16.
 */
public class BaseApi<T> implements Func1<BaseResultEntity<T>, T> {
    //rx生命周期管理
    private SoftReference<RxAppCompatActivity> rxAppCompatActivity;
    /*回调*/
    private SoftReference<OnNextListener> listener;
    /*是否能取消加载框*/
    private boolean cancel;
    /*是否显示加载框*/
    private boolean showProgress;
    /*是否需要缓存处理*/
    private boolean cache;
    /*基础url*/
    private String baseUrl = "https://www.izaodao.com/Api/";
    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
    private String method="";
    /*超时时间-默认6秒*/
    private int connectionTime = 6;
    /*有网情况下的本地缓存时间默认60秒*/
    private int cookieNetWorkTime = 60;
    /*无网络的情况下本地缓存时间默认30天*/
    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;
    /* 失败后retry次数*/
    private int retryCount = 1;
    /*失败后retry延迟*/
    private long retryDelay = 100;
    /*失败后retry叠加延迟*/
    private long retryIncreaseDelay = 10;
    /*缓存url-可手动设置*/
    private String cacheUrl;

    public BaseApi(OnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        setListener(listener);
        setRxAppCompatActivity(rxAppCompatActivity);
        setShowProgress(true);
        setCache(true);
    }

    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public void setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public void setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
    }


    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUrl() {
        /*在没有手动设置url情况下，简单拼接*/
        if (null == getCacheUrl() || "".equals(getCacheUrl())) {
            return getBaseUrl() + getMethod();
        }
        return getCacheUrl();
    }

    public void setRxAppCompatActivity(RxAppCompatActivity rxAppCompatActivity) {
        this.rxAppCompatActivity = new SoftReference(rxAppCompatActivity);
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public SoftReference<OnNextListener> getListener() {
        return listener;
    }

    public void setListener(OnNextListener listener) {
        this.listener = new SoftReference(listener);
    }


    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
    }

    public long getRetryIncreaseDelay() {
        return retryIncreaseDelay;
    }

    public void setRetryIncreaseDelay(long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
    }

    /*
         * 获取当前rx生命周期
         * @return
         */
    public RxAppCompatActivity getRxAppCompatActivity() {
        return rxAppCompatActivity.get();
    }

    @Override
    public T call(BaseResultEntity<T> httpResult) {
        if (httpResult.getRet().equals("0")) {
            throw new HttpTimeException(httpResult.getMsg());
        }
        return httpResult.getData();
    }


    public String getCacheUrl() {
        return cacheUrl;
    }

    public void setCacheUrl(String cacheUrl) {
        this.cacheUrl = cacheUrl;
    }
    public static class Builder {
        public Map<String, String> params;
        public boolean isShowProgress = true;
        public boolean isCancel = true;
        public boolean isCache = true;
        public String Method = "mycache";
        public int CookieNetWorkTime = 60;
        public int CookieNoNetWorkTime = 24 * 60 * 60;
        public String flag;
        public String api;
        /* 失败后retry次数*/
        private int retryCount = 1;
        /*失败后retry延迟*/
        private long retryDelay = 100;
        /*失败后retry叠加延迟*/
        private long retryIncreaseDelay = 10;

        public Builder setRetryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public Builder setRetryDelay(long retryDelay) {
            this.retryDelay = retryDelay;
            return this;
        }

        public Builder setRetryIncreaseDelay(long retryIncreaseDelay) {
            this.retryIncreaseDelay = retryIncreaseDelay;
            return this;
        }

        public Builder setApi(String api) {
            this.api = api;
            return this;
        }

        OnNextListener listener;
        RxAppCompatActivity rxAppCompatActivity;

        public Builder setListener(OnNextListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setRxAppCompatActivity(RxAppCompatActivity rxAppCompatActivity) {
            this.rxAppCompatActivity = rxAppCompatActivity;
            return this;
        }

        public Builder setParams(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder setFlag(String flag) {
            this.flag = flag;
            return this;
        }

        public Builder setShowProgress(boolean showProgress) {
            isShowProgress = showProgress;
            return this;
        }

        public Builder setCancel(boolean cancel) {
            isCancel = cancel;
            return this;
        }

        public Builder setCache(boolean cache) {
            isCache = cache;
            return this;
        }

        public Builder setMethod(String method) {
            Method = method;
            return this;
        }

        public Builder setCookieNetWorkTime(int cookieNetWorkTime) {
            CookieNetWorkTime = cookieNetWorkTime;
            return this;
        }

        public Builder setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
            CookieNoNetWorkTime = cookieNoNetWorkTime;
            return this;
        }

        public BaseApi build() {
            if(listener != null)
            {
                return new BaseApi<>(listener,rxAppCompatActivity);
            }
            return null;
        }
    }
}
