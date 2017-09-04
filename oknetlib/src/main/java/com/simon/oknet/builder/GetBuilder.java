package com.simon.oknet.builder;

import android.text.TextUtils;

import com.simon.oknet.builder.base.PathParamRequestBuilder;

import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * description: 用于处理get请求
 * author: Simon
 * created at 2017/9/1 上午11:00
 * get=>select
 * check url check params
 */

public class GetBuilder extends PathParamRequestBuilder<GetBuilder> {

    /**
     * 配置 get请求
     *
     * @param builder --请求的builder，注意：**get方法需要重新修改url&& 初始化 params参数以及设置当前的请求方法**
     */
    @Override
    protected void configParams(Request.Builder builder) {
        //--append paths--
        if (checkPaths()) {
            url = appendPath(url, paths);
        }
        //--append params--
        if (checkParams()) {
            url = appendParams(url, params);
        }
        //--change url and set the request method to get--
        builder.url(url).get();
    }

    private String appendPath(String url, List<String> paths) {
        //--如果网址参数不正确或者未传入对应数据则返回原url--
        if (TextUtils.isEmpty(url) || paths == null || paths.isEmpty()) {
            return url;
        }
        //--验证通过后开始拼接--
        StringBuilder urlBuilder = new StringBuilder(url);
        //--如果最后的字符是 /需要删除--
        if (urlBuilder.lastIndexOf("/") == urlBuilder.length() - 1) {
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        //--append path--
        for (String path : paths) {
            if (!TextUtils.isEmpty(path)) {
                urlBuilder.append("/").append(path);
            }
        }
        //--返回最终结果--
        return urlBuilder.toString();

    }

    /**
     * get请求：添加参数到url
     * 类似于： 原链接=> http://www.baidu.com/s  添加参数后 => http://www.baidu.com/s?name=ghy&age=11
     *
     * @param url    --原始链接
     * @param params --参数
     * @return --拼接后的url
     */
    private String appendParams(String url, Map<String, String> params) {

        //--如果url 或者params 不符合规范则返回原来的url--
        if (TextUtils.isEmpty(url) || params == null || params.isEmpty()) {
            return url;
        }

        //--拼接url（get请求的参数是拼接在请求地址中）--
        //--create StringBuilder--
        StringBuilder urlBuilder = new StringBuilder(url);
        //--if "/" is last it is need to delete--
        if (urlBuilder.lastIndexOf("/") == urlBuilder.length() - 1) {
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        //--append url--
        urlBuilder.append("?");
        for (String key : params.keySet()) {
            urlBuilder.append(key).append("=").append(params.get(key)).append("&");
        }

        //--delete last "&"--
        urlBuilder = urlBuilder.deleteCharAt(urlBuilder.length() - 1);

        //--return end url--
        return urlBuilder.toString();
    }
}
