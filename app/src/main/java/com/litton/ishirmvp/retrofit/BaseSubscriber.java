package com.litton.ishirmvp.retrofit;

import rx.Subscriber;

/**
 * Created by littonishir on 2018/8/8.
 */

public  abstract class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {}

    protected abstract void onError(String errorCode, String errorMessage);

    @Override
    public void onError(Throwable e) {
        // e ：网络异常，解析异常，结果处理过程中异常
        e.printStackTrace();
        if(e instanceof ErrorHandle.ServiceError){
            ErrorHandle.ServiceError serviceError = (ErrorHandle.ServiceError) e;
            onError("",serviceError.getMessage());
        }else {
            // 自己处理
            String message = e.getMessage();
            onError("",message);

        }
    }
}
