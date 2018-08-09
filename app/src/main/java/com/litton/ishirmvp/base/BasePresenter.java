package com.litton.ishirmvp.base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * Created by littonishir on 2018/8/8.
 */

public class BasePresenter<V extends BaseView, M extends BaseModel> {

    /**
     * Presenter 持有 View 和 Model 的引用
     * View:动态代理处理返回
     * Model:反射动态创建对象
     */

    private V mView;
    private V mProxyView;
    private M mModel;

    public void attach(V view) {
        this.mView = view;
        mProxyView = (V) Proxy.newProxyInstance(mView.getClass().getClassLoader(), mView.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 执行这个方法 ，调用的是被代理的对象 mView
                if (mView == null) return null;
                // 没解绑执行的是原始被代理 View 的方法
                return method.invoke(mView, args);
            }
        });

        // 动态创建我们的 Model ， 获取 Class 对象
        Type[] params = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        try {
            // 最好要判断一下类型
            mModel = (M) ((Class) params[1]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void detach() {
        /**
         * 解绑
         * 不解绑 Presenter 持有 Activity 有可能导致内存泄漏
         */
        this.mView = null;
    }

    public M getModel() {
        return mModel;
    }

    public V getView() {
        return mProxyView;
    }
}
