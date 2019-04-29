package com.qipai.gansuactivity.NetWork;

import com.qipai.gansuactivity.NetWork.RetrofitUtils;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.RetryWhenNetworkException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.OnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.subscribers.FragmentProgressSubscriber;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.subscribers.ProgressNoCacheSubscriber;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.subscribers.ProgressSubscriber;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.subscribers.ProgressSubscriberApi;

import java.lang.ref.SoftReference;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HttpFactory {
    public static Retrofit retrofit;

    /**
     * 默认不配置缓存
     * 针对于返回格式不是200
     */
    public static void createObserverable(Observable observable, final OnNextListener listener){
        /*rx处理*/
        observable.retryWhen(new RetryWhenNetworkException())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onNext(Object o) {
                        listener.onNext(o);
                    }
                });
    }

    /**
     * 默认缓存
     * 缓存Key值请求地址
     * @param serviceClz
     * @param <S>
     * @return
     */
    public static  <S>S createRetrofitService(Class<S> serviceClz) {
        retrofit= RetrofitUtils.getInstance();
        return retrofit.create(serviceClz);
    }

    /**getNoCacheStringInstance
     * 默认不使用缓存
     * @param serviceClz
     * @param <S>
     * @return
     */
    public static  <S>S createNoCacheRetrofitService(Class<S> serviceClz) {
        retrofit= RetrofitUtils.getInstance();
        return retrofit.create(serviceClz);
    }
}
