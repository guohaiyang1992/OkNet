package com.simon.oknet.callback.base;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Response;

/**
 * description: 异步调用 的callback 接口
 * author: Simon
 * created at 2017/9/1 下午1:51
 */

public abstract class ICallback<T> {
    //--空字符串--
    protected static final String EMPTY = "";

    //--成功(响应)--
    public abstract void onSuccess(@NonNull T result);

    //--失败(响应码：200 400 等 ; 错误信息)--
    public abstract void onFailure(int statusCode, String errMsg);

    //--进度（用于下载，当前的下载数量，总体的）--
    public abstract void onProgress(long currentBytes, long totalBytes);

    //--这个转换方法由具体的callback 根据需求实现--
    protected abstract T transform(@NonNull Response response);

    //--真正的接收服务器返回内容--
    public void onSuccess(@NonNull Response response) {
        //--转换后传递给请求者--
        onSuccess(transform(response));
    }

}
