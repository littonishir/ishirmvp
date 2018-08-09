package com.litton.ishirmvp.proxy;

import com.litton.ishirmvp.base.BasePresenter;
import com.litton.ishirmvp.base.BaseView;
import com.litton.ishirmvp.inject.InjectPresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by littonishir on 2018/8/8.
 * IMvpProxy的实现
 */
public class MvpProxyImpl<V extends BaseView> implements IMvpProxy {
    private V mView;
    private List<BasePresenter> mPresenters;

    public MvpProxyImpl(V view) {
        this.mView = view;
        mPresenters = new ArrayList<>();
    }

    @Override
    public void bindPresenter() {
        // 反射注入 Presenter
        Field[] fields = mView.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if (injectPresenter != null) {
                // 创建注入
                Class<? extends BasePresenter> presenterClass;
                // 判断类型 获取继承的父类，若是 继承 BasePresenter 获取Class创建对象 否则抛异常
                String name = field.getType().getSuperclass().getName();
                if (name.contains("BasePresenter")) {
                    presenterClass = (Class<? extends BasePresenter>) field.getType();
                } else {
                    // 乱七八糟一些注入
                    throw new RuntimeException("No support inject presenter type " + field.getType().getName());
                }

                try {
                    // 创建 Presenter 对象
                    BasePresenter basePresenter = presenterClass.newInstance();
                    basePresenter.attach(mView);
                    // 设置
                    field.setAccessible(true);
                    field.set(mView, basePresenter);
                    mPresenters.add(basePresenter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void unbindPresenter() {
        // 解绑
        for (BasePresenter presenter : mPresenters) {
            presenter.detach();
        }
        mView = null;
    }
}
