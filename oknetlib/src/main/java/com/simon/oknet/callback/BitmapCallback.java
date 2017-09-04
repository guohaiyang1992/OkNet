package com.simon.oknet.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.simon.oknet.callback.base.ICallback;

import okhttp3.Response;


/**
 * description: bitmap 类型的
 * author: Simon
 * created at 2017/9/4 上午10:34
 */
public abstract class BitmapCallback extends ICallback<Bitmap> {

    //--The method not required here--
    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }

    //--解析bitmap--
    @Override
    protected Bitmap transform(@NonNull Response response) {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }
}
