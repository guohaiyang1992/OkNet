package com.simon.oknet.callback;

import android.support.annotation.NonNull;

import com.simon.oknet.callback.base.ICallback;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * description: StringCallback 用于接收返回结果为String的请求
 * author: Simon
 * created at 2017/9/1 下午8:43
 */

public abstract class StringCallback extends ICallback<String> {

    //--The method not required here--
    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }

    /**
     * You must override this method to convert the response to the type you want(T).
     *
     * @param response --请求返回的内容
     * @return --返回请求想要的内容
     */
    @Override
    protected String transform(@NonNull Response response) {
        //--get response string--
        ResponseBody responseBody = response.body();
        String result = EMPTY;
        try {
            result = responseBody.string();
        } catch (Exception e) {
            onFailure(response.code(), "Read the response body failed! =>" + e.toString());
        } finally {
            responseBody.close();
        }
        return result;
    }


}
