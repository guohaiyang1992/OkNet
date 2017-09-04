package com.simon.oknet.callback;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.simon.oknet.callback.base.ICallback;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * description: gson格式的callback支持自定义类型
 * author: Simon
 * created at 2017/9/4 上午9:05
 */

public abstract class GsonCallback<T> extends ICallback<T> {

    private Type type;

    //--初始化的时候获取泛型类型--
    public GsonCallback() {
        //--反射获取带泛型的class--
        Type paramsClass = getClass().getGenericSuperclass();
        if (paramsClass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        //--获取所有泛型,获取泛型的类型--
        ParameterizedType parameter = (ParameterizedType) paramsClass;
        type = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);
    }

    //--The method not required here--
    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }

    //--具体的转换方法--
    @Override
    protected T transform(@NonNull Response response) {
        //--get body--
        ResponseBody body = response.body();
        try {
            //--get body content--
            String bodyStr = body.string();
            //--convert--
            Gson gson = new Gson();
            return gson.fromJson(bodyStr, type);
        } catch (IOException e) {
            //--handler failure--
            onFailure(response.code(), "Read the response body failed! =>" + e.toString());
        } finally {
            //--close body--
            body.close();
        }
        return null;
    }
}
