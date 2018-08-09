package com.litton.ishirmvp.proxy;

/**
 * Created by littonishir on 2018/8/8.
 */

public interface IMvpProxy {

    void bindPresenter();// 创建绑定Presenter

    void unbindPresenter();// 解绑Presenter
}
