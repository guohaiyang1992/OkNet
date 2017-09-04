package com.simon.oknet.builder;

import com.simon.oknet.builder.base.RequestBuilder;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * description: 用于处理patch请求
 * author: Simon
 * created at 2017/9/1 下午8:11
 */
public class PatchBuilder extends RequestBuilder<PatchBuilder> {
    //--patch media_type--
    private static final String PATCH_MEDIA_TYPE = "text/plain;charset=utf-8";

    @Override
    protected void configParams(Request.Builder builder) {
        //--Set the request method to patch--
        builder.patch(RequestBody.create(MediaType.parse(PATCH_MEDIA_TYPE), ""));
    }
}
