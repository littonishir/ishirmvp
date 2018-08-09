package com.litton.ishirmvp.proxy;


import com.litton.ishirmvp.base.BaseView;

/**
 * Created by littonishir on 2018/8/8.
 */

public class ActivityProxyImpl<V extends BaseView> extends MvpProxyImpl<V> implements ActivityProxy {
    public ActivityProxyImpl(V view) {
        super(view);
    }
}
