package com.litton.ishirmvp.retrofit.gsonconverter;

/**
 * Created by littonishir on 2018/8/8.
 */

public class ErrorHandle {

    public static class ServiceError extends Throwable {
        String errorCode;
        public ServiceError(String errorCode, String errorMsg) {
            super(errorMsg);
            this.errorCode = errorCode;
        }
    }
}
