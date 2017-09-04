package com.simon.oknet.builder;

import android.text.TextUtils;

import com.simon.oknet.builder.base.ParamRequestBuilder;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * description: 用于处理post请求
 * author: Simon
 * created at 2017/9/1 下午4:07
 * <p>
 * post->create
 */

public class PostBuilder extends ParamRequestBuilder<PostBuilder> {

    //--json 参数--
    private String jsonParams = null;

    //--json media type--
    private static final String JSON_MEDIA_TYPE = "application/json; charset=utf-8";


    /**
     * 需要上传的json 参数，此数据和params 不可共存，此处会覆盖params
     *
     * @param jsonParams --json格式的参数
     */
    public PostBuilder jsonParams(String jsonParams) {
        this.jsonParams = jsonParams;
        return this;
    }

    /**
     * 配置post请求
     *
     * @param builder --请求的builder，注意：**get方法需要重新修改url&& 初始化 params参数以及设置当前的请求方法**
     */
    @Override
    protected void configParams(Request.Builder builder) {
        //--append params--
        if (checkJsonParams()) {
            //--append json params by requestbody--
            RequestBody body = RequestBody.create(MediaType.parse(JSON_MEDIA_TYPE), jsonParams);
            builder.post(body);
        } else {//This params can be empty,so don't need to 【checkParams】. if the params is empty so post empty.
            //--append normal params by formbody(表单）--
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            appendParams(bodyBuilder, params);
            FormBody body = bodyBuilder.build();
            builder.post(body);
        }
    }

    private void appendParams(FormBody.Builder bodyBuilder, Map<String, String> params) {
        //--check value--
        if (bodyBuilder == null || params == null || params.isEmpty()) {
            return;
        }
        //--append params--
        for (String key : params.keySet()) {
            bodyBuilder.add(key, params.get(key));
        }
    }

    //------------------公共方法-------------------------

    /**
     * 检查json 参数
     *
     * @return --true 表示符合，false 表示不符合
     */
    public boolean checkJsonParams() {
        return !TextUtils.isEmpty(jsonParams);
    }


}
