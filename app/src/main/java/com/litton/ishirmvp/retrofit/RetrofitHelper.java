package com.litton.ishirmvp.retrofit;

import android.util.Log;

import com.litton.ishirmvp.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Emitter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * auth : littonishir
 * date : 2018/4/24
 * gith : https://github.com/littonishir
 */
public class RetrofitHelper {
    private static RetrofitHelper mInstance = new RetrofitHelper();
    Retrofit retrofit;
    RetrofitService retrofitService;

    private RetrofitHelper() {
        /**
         *1.创建 Retrofit 的实例对象
         *2.设置服务器主机,注意：BASE_URL必须以/结尾，否则报错
         *3.配置 Gson 解析器,内部会使用 Gson 库来解析 JavaBean
         *4.添加 rxjava2 适配器
         *5.添加 Http 拦截器,拦截后台返回的数据
         */
        retrofit = new Retrofit.Builder()
                .baseUrl(NetUrl.BASE_URL)
                .addConverterFactory(GsonDConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
        /**
         * 6.获取 RetrofitService 业务接口的实现类对象,其中内部是通过动态代理来创建接口实现类对象的
         */
        retrofitService = retrofit.create(RetrofitService.class);
    }

    public static RetrofitHelper create() {
        return mInstance;
    }

    public  RetrofitService getRetrofitService() {
        return retrofitService;
    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message ->
                    Log.e("TAG", message));
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    public static <T> Observable.Transformer<Result<T>, T> transformer() {
        return new Observable.Transformer<Result<T>, T>() {
            @Override
            public Observable<T> call(Observable<Result<T>> resultObservable) {
                return resultObservable.flatMap(new Func1<Result<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(Result<T> tResult) {
                        // 解析不同的情况返回
                        if(tResult.isOk()){
                            // 返回成功
                            return createObservable(tResult.data);
                        }else {
                            // 返回失败
                            return Observable.error(new ErrorHandle.ServiceError("",tResult.getData()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    private static <T> Observable<T> createObservable(final T data) {
        return Observable.create(new Action1<Emitter<T>>() {
            @Override
            public void call(Emitter<T> tEmitter) {
                tEmitter.onNext(data);
                tEmitter.onCompleted();
            }
        }, Emitter.BackpressureMode.NONE);
    }


}
