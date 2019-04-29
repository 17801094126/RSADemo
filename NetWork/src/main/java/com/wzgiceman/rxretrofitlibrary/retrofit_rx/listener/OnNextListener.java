package com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener;

import rx.Observable;

/**
 * Created by pc on 2018/11/24.
 */

public abstract class OnNextListener<T> {
    /**
     * 成功后回调方法
     */
    public abstract void onNext(T data);

    /**
     * 緩存回調結果
     * @param string
     */
    public void onCacheNext(String string){

    }

    /**
     * 成功后的ober返回，扩展链接式调用
     * @param observable
     */
    public void onNext(Observable observable){

    }

    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     */
    public  void onError(Throwable error){

    }

    /**
     * 取消回調
     */
    public void onCancel(){

    }
}
