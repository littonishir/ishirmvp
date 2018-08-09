package com.litton.ishirmvp;

import com.litton.ishirmvp.base.BaseModel;
import com.litton.ishirmvp.bean.LoginBean;
import com.litton.ishirmvp.retrofit.RetrofitHelper;

import java.util.Map;

import rx.Observable;

/**
 * * Created by littonishir on 2018/8/8.
 */

public class UserInfoModel extends BaseModel implements UserInfoContract.UserInfoModel{
    // Model 获取数据
    @Override
    public Observable<LoginBean> getUsers(Map token){
        return RetrofitHelper.create().getRetrofitService()
                .login(token)
                // .subscribeOn().observeOn().subscribe()
                // Subscriber 封装一下
                // 第二个坑 , 坑我们 返回值都是一个泛型，转换返回值泛型
                .compose(RetrofitHelper.<LoginBean>transformer());
    }

}
