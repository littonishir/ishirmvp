package com.litton.ishirmvp.bean;

/**
 * Created by littonishir on 2018/8/8.
 */

public class BaseResult {


    /**
     * 基础返回类：与后台约定好数据返回格式
     * data : 用户名和密码不匹配，请重新登录
     * status : 0
     */

    private transient String data;
    private int status;

    public String getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public boolean isOk() {
        return status==1;
    }

}
