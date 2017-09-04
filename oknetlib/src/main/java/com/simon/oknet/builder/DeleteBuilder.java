package com.simon.oknet.builder;

import com.simon.oknet.builder.base.RequestBuilder;

import okhttp3.Request;

/**
 * description: 用于处理delete 请求,不需要传参数
 * author: Simon
 * created at 2017/9/1 下午7:30
 * <p>
 * delete->delete
 */

public class DeleteBuilder extends RequestBuilder<DeleteBuilder> {
    /**
     * 配置 delete请求
     *
     * @param builder --请求的builder，注意：**get方法需要重新修改url&& 初始化 params参数以及设置当前的请求方法**
     */

    @Override
    protected void configParams(Request.Builder builder) {
        //--Set the request method to delete--
        builder.delete();
    }
}
