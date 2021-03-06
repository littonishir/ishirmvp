package com.litton.ishirmvp.retrofit.gsonconverter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.litton.ishirmvp.bean.BaseResult;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    /**
     * 针对数据返回成功、错误不同类型字段处理
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            BaseResult baseResult = gson.fromJson(response, BaseResult.class);
//            boolean ok = ;
//            int status = baseResult.getStatus();
            if (baseResult.isOk()) {
                //nor
                return gson.fromJson(response, type);
            } else {
                //err
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(response);
                String data = jsonObject.get("data").toString();
                throw new IOException(data);
            }

        } finally {
            value.close();
        }
    }
}
