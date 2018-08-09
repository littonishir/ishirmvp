package com.litton.ishirmvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.litton.ishirmvp.proxy.ActivityProxy;
import com.litton.ishirmvp.proxy.ActivityProxyImpl;

/**
 * Created by littonishir on 2018/8/8.
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    /**
     * Activity 持用 Presenter 的两种方式
     * 1. 因为每个 Activity 的 Presenter 都不一样 创建 P，交给子类自己创建(适用于一个View对应一个Presenter的情况)
     * 2. 定义注解 (InjectPresenter) 创建代理对象为我们动态创建 Presenter.(适用于 one to one ，Many to one)
     */

    private P mPresenter;
    private ActivityProxy mMvpProxy;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        mPresenter = createPresenter();
        mPresenter.attach(this);
        mMvpProxy = createMvpProxy();
        mMvpProxy.bindPresenter();

        initView();
        initData();
    }

    /**
     * 创建 Mvp 的代理
     *
     * @return
     */
    private ActivityProxy createMvpProxy() {
        if (mMvpProxy == null) {
            mMvpProxy = new ActivityProxyImpl<>(this);
        }
        return mMvpProxy;
    }

    protected abstract P createPresenter();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void setContentView();

    public P getPresenter() {
        return mPresenter;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
        mMvpProxy.unbindPresenter();
    }

}
