package com.simon.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.simon.oknet.callback.GsonCallback;
import com.simon.oknet.callback.StringCallback;
import com.simon.oknet.cookies.CookieJarImpl;
import com.simon.oknet.cookies.store.MemoryCookieStore;
import com.simon.oknet.core.OkNet;
import com.simon.test.bean.Book;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkNet.init().cookieJar(new CookieJarImpl(new MemoryCookieStore())).isLog(true).connectTimeout(10000).readTimeout(10000).build();
//        ObjectUtils.checkNotNullErr(null, "测试null 检查");
//        OkNet.get().addParams("wd", "郭海洋").url(null).excute(new StringCallback() {
//            @Override
//            public void onSuccess(String result) {
//                Log.v("test", "onSuccess:请求结果运行在：" + Thread.currentThread().getName());
//                Log.v("test", "onSuccess:请求结果是：" + result);
//            }
//
//            @Override
//            public void onFailure(int statusCode, String errMsg) {
//                Log.v("test", "onFailure:请求结果运行在：" + Thread.currentThread().getName());
//                Log.v("test", "onFailure:请求结果是：" + statusCode + ":" + errMsg);
//            }
//        });


        testPathAndParams();
//        testGsonAndPath("1220562");
        OkNet.client().cache()


    }

    private void testPathAndParams() {
        //     http://www.baidu.com/s?wd="测试内容"
        OkNet.get().url("http://www.baidu.com").addPath("s").addParams("wd", "android 探索之路").excute(new StringCallback() {
            @Override
            public void onSuccess(@NonNull String result) {
                Log.v("test", result);
            }

            @Override
            public void onFailure(int statusCode, String errMsg) {

            }
        });
    }

    private void testGsonAndPath(String bookId) {
//        https://api.douban.com/v2/book/1220562
        OkNet.get().url("https://api.douban.com/v2/book").addPath(bookId).excute(new GsonCallback<Book>() {
            @Override
            public void onSuccess(@NonNull Book result) {
                Log.v("test", result.toString());
            }

            @Override
            public void onFailure(int statusCode, String errMsg) {
                Log.v("test", "err:" + statusCode + ":" + errMsg);
            }
        });
    }


}
