package com.simon.oknet.builder.base;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * description: 带参数的请求构造类
 * author: Simon
 * created at 2017/9/1 上午10:56
 */

public abstract class ParamRequestBuilder<T extends ParamRequestBuilder> extends RequestBuilder<T> {

    //--请求参数（get请求的参数会显示在请求地址中）--
    protected Map<String, String> params;

    /**
     * 设置请求参数
     *
     * @param params --请求参数集合
     */
    public T params(@NonNull Map<String, String> params) {
        this.params = params;
        return (T) this;
    }


    /**
     * 添加请求参数
     *
     * @param key --键
     * @param val --值
     */
    public T addParams(@NonNull String key, @NonNull String val) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, val);
        return (T) this;
    }

    //-----------------公用方法--------------------------

    /**
     * 检查参数是否有效
     *
     * @return --true表示有效，false表示无效
     */
    protected boolean checkParams() {
        return params != null && !params.isEmpty();
    }

}
