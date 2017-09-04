package com.simon.oknet.builder;

import com.simon.oknet.builder.base.RequestBuilder;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * description: 用于处理 put请求
 * author: Simon
 * created at 2017/9/1 下午8:02
 * put->update
 */

public class PutBuilder extends RequestBuilder<PutBuilder> {
    //--put media_type--
    private static final String PUT_MEDIA_TYPE = "text/plain;charset=utf-8";

    @Override
    protected void configParams(Request.Builder builder) {
        //--Set the request method to put--
        builder.put(RequestBody.create(MediaType.parse(PUT_MEDIA_TYPE), ""));
    }
}
