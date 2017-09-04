package com.simon.oknet.builder.base;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.simon.oknet.callback.convert.AdapterCallback;
import com.simon.oknet.callback.base.ICallback;
import com.simon.oknet.core.OkNet;
import com.simon.oknet.utils.ExceptionUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;

/**
 * description:  基础的请求构造类
 * author: Simon
 * created at 2017/9/1 上午10:55
 */

public abstract class RequestBuilder<T extends RequestBuilder> {
    //--网址--
    protected String url;

    //--tag（用于取消请求）--
    protected Object tag;

    //--header(键值对)--
    protected Map<String, String> headers;

    /**
     * 真正的执行方法
     *
     * @param callback --回调方法
     */
    public void excute(@NonNull ICallback callback) {
        //--create adapter callback--
        AdapterCallback adapterCallback = new AdapterCallback(callback);
        try {
            //--check url--
            if (!checkUrl(url)) {
                ExceptionUtils.throwIllegalArgumentException("url can't null or empty!");
            }
            //--create request build--
            Request.Builder builder = new Request.Builder().url(url);

            //--append params --
            configParams(builder);

            //--append headers--
            if (checkHeaders()) {
                appendHeaders(builder, headers);
            }
            //--set tag--
            if (checkTag()) {
                builder.tag(tag);
            }
            //----
            //--build request--
            Request request = builder.build();

            //--excute--
            OkNet.client().newCall(request).enqueue(adapterCallback);

        } catch (Exception e) {
            //--throw err--
            adapterCallback.onFailureRunInMain(0, e.toString());
        }

    }

    /**
     * 此处用于初始化 params参数以及设置当前的请求方法
     *
     * @param builder --请求的builder，注意：**get方法需要重新修改url&& 初始化 params参数以及设置当前的请求方法**
     */
    protected abstract void configParams(@NonNull Request.Builder builder);


    //---------------参数设置----------------------------

    /**
     * set url
     *
     * @param url --请求地址
     */
    public T url(@NonNull String url) {
        this.url = url;
        return (T) this;
    }

    /**
     * set tag
     *
     * @param tag --用于取消的tag
     */
    public T tag(@NonNull Object tag) {
        this.tag = tag;
        return (T) this;
    }

    /**
     * set headers
     *
     * @param headers --请求头
     */
    public T headers(@NonNull Map<String, String> headers) {
        this.headers = headers;
        return (T) this;
    }

    /**
     * add header （name=zz）
     *
     * @param key --键 (name)
     * @param val --值（zz）
     */
    public T addHeader(@NonNull String key, @NonNull String val) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, val);
        return (T) this;
    }

    /**
     * append headers to request
     *
     * @param builder --请求的builder
     * @param headers --请求头数据集合
     */
    protected void appendHeaders(@NonNull Request.Builder builder, @NonNull Map<String, String> headers) {

        //--check value--
        if (headers == null || headers.isEmpty() || builder == null) return;

        //--构建headers 的builder--
        Headers.Builder headerBuilder = new Headers.Builder();

        //--遍历添加header--
        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }

        //--build && set--
        builder.headers(headerBuilder.build());

    }

    //-----------------公用方法--------------------------

    /**
     * 检查请求的地址 true表示符合 false 表示不符合
     *
     * @param url --待请求的地址
     */
    protected boolean checkUrl(String url) {
        return !TextUtils.isEmpty(url);
    }

//    public void printer(String msg) {
//        Log.e(getClass().getSimpleName())
//    }

    /**
     * 检查请求头是否有效
     *
     * @return --true 表示有效 false 表示无效
     */
    protected boolean checkHeaders() {
        return headers != null && !headers.isEmpty();
    }

    /**
     * 检查 tag是否有效
     *
     * @return --true表示有效 false 表示无效
     */
    protected boolean checkTag() {
        return tag != null;
    }
}
