package com.litton.ishirmvp.retrofit;

import com.litton.ishirmvp.bean.LoginBean;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by littonishir on 2018/8/8.
 * 请求后台访问数据的 接口类
 */
public interface RetrofitService {
    // 接口涉及到解耦，userLogin 方法是没有任何实现代码的
    // 如果有一天要换 GoogleHttp



    @POST(NetUrl.LOGIN)
    Observable<Result<LoginBean>> login(@QueryMap Map<String, Object> map);
}
