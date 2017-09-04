package com.simon.oknet.callback.convert;

import com.simon.oknet.callback.base.ICallback;
import com.simon.oknet.utils.ThreadUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * description: 将OkHttp的请求结果转化为自定义Callback的适配类
 * author: Simon
 * created at 2017/9/1 下午3:17
 */

public class AdapterCallback implements Callback {

    //--自定义的callback--
    private ICallback callback;

    public AdapterCallback(ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        onFailureRunInMain(0, e.toString());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            onSuccessRunInMain(response);
        } else {
            onFailureRunInMain(response.code(), "response code: " + response.code() + " \n response msg：" + response.message());
        }

    }

    public boolean checkCallback() {
        return callback != null;
    }


    /**
     * callback failure in main thread
     *
     * @param code   --错误码
     * @param errMsg --错误信息
     */
    public void onFailureRunInMain(final int code, final String errMsg) {
        ThreadUtils.runInMain(new Runnable() {
            @Override
            public void run() {
                if (checkCallback()) {
                    callback.onFailure(code, errMsg);
                }
            }
        });
    }


    /**
     * callback success in main thread
     *
     * @param response --响应信息
     */
    public void onSuccessRunInMain(final Response response) {
        ThreadUtils.runInMain(new Runnable() {
            @Override
            public void run() {
                if (checkCallback()) {
                    callback.onSuccess(response);
                }
            }
        });
    }


}
