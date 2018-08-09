package com.litton.ishirmvp;

import android.widget.TextView;

import com.litton.ishirmvp.base.BaseActivity;
import com.litton.ishirmvp.bean.LoginBean;

import java.util.HashMap;
import java.util.Map;


// 这个地方可以放个泛型，方便 1 对 1 ，去掉了
public class MainActivity extends BaseActivity<UserInfoPresenter> implements UserInfoContract.UserInfoView{
    private TextView mUserInfoTv;

    // 抛出一个问题留在这里，多 Presenter 怎么处理，dagger ，自己写 Dagger 处理

    // 一个 View 里面肯定会有多个 Presneter 的情况，怎么处理 Dagger 处理 有时间会补一下 ，自己写一个注入
    // 对个 new  自己手动去 attach  和 detach
//    @InjectPresenter
//    private UserInfoPresenter mPresenter1;
//    @InjectPresenter
    private UserInfoPresenter mPresenter;


    @Override
    public void onLoading() {
        // 加载进度条
    }

    @Override
    public void onError(String errorCode, String errorMsg) {
        mUserInfoTv.setText(errorMsg);

    }


    @Override
    public void onSucceed(LoginBean userInfo) {
        // 成功 这个时候 Activity 有可能会被关闭掉，有可能会异常崩溃（一般不会）
        // 1. 可以判断界面还在不在(试一试)
        // 2. 采用解绑（通用）
        if (null!=userInfo){
            mUserInfoTv.setText(userInfo.getLogo());
        }
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    protected void initData() {
//        Class<? extends UserInfoPresenter> aClass = mPresenter1.getClass();
//        UserInfoPresenter presenter = getPresenter();
//        Map<String,String> map = new HashMap<>();
//        map.put("username","wjd");
//        map.put("password","1");

        Map<String, Object> map = new HashMap<>();

//        map.put("phone”,””);
        map.put("phone","188112261328");
        map.put("password", "F6470D658E1BAA72159935E17FCA9D22");
        map.put("termType", "900001");
        map.put("deviceId", "0f0931d253ddd604");
        map.put("version", "2.0.2");
        map.put("deviceToken", "AtkhMNAS1KLRpOMjq1neUq3oa02FIaBDIfBgRliK4Ysf");
        getPresenter().getUsers(map);
    }

    @Override
    protected void initView() {
        mUserInfoTv = (TextView) findViewById(R.id.user_info_tv);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }
}
