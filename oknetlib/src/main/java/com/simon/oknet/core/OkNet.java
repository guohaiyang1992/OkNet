package com.simon.oknet.core;

import com.simon.oknet.builder.DeleteBuilder;
import com.simon.oknet.builder.GetBuilder;
import com.simon.oknet.builder.PatchBuilder;
import com.simon.oknet.builder.PostBuilder;
import com.simon.oknet.builder.PutBuilder;
import com.simon.oknet.cookies.CookieJarImpl;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * description: OkNet 网络请求核心库
 * author: Simon
 * created at 2017/9/1 上午10:58
 * cookie--wait
 * cache-wait
 */
//--不允许继承--
public final class OkNet {

    //--共用的OkHttpClient--
    private static OkHttpClient client;

    //--不允许初始化--
    private OkNet() {
        throw new AssertionError("you can't init me !");
    }

    //--------------------外部方法-----------------------

    /**
     * 初始化操作
     *
     * @return -- InitBuilder
     */
    public static InitBuilder init() {
        return new InitBuilder();
    }

    /**
     * get请求 (select)
     *
     * @return -- GetBuilder
     */
    public static GetBuilder get() {
        //--运行前校验环境--
        checkEnvironment();
        return new GetBuilder();
    }

    /**
     * post 请求 （create）
     *
     * @return -- PostBuilder
     */
    public static PostBuilder post() {
        //--运行前校验环境--
        checkEnvironment();
        return new PostBuilder();
    }

    /**
     * delete 请求 （delete）
     *
     * @return -- DeleteBuilder
     */
    public static DeleteBuilder delete() {
        //--运行前校验环境--
        checkEnvironment();
        return new DeleteBuilder();
    }

    /**
     * put 请求 （update）
     *
     * @return -- PutBuilder
     */
    public static PutBuilder put() {
        //--运行前校验环境--
        checkEnvironment();
        return new PutBuilder();
    }

    /**
     * patch 请求 （update）
     *
     * @return -- PatchBuilder
     */
    public static PatchBuilder patch() {
        //--运行前校验环境--
        checkEnvironment();
        return new PatchBuilder();
    }

    /**
     * client OkhttpClient
     *
     * @return --返回当前使用的client
     */
    public static OkHttpClient client() {
        //--运行前校验环境--
        checkEnvironment();
        return client;
    }


    /**
     * cancel request by tag
     *
     * @param tag -- request tag
     */
    public static void cancel(Object tag) {
        //--if the client is null it does not need to be canceled--
        if (client == null) {
            return;
        }
        //--cancel queued call--
        Dispatcher dispatcher = client.dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        //--cancel running call--
        for (Call call : dispatcher.runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }

        //--end--
    }

    //--------------------内部方法-----------------------

    /**
     * Check the current environment is normal ,otherwise throw an exception
     */
    private static void checkEnvironment() {
        //--if the client is null throw an exception--
        if (!checkClient()) {
            throw new NullPointerException("you must init!  => OkNet.init()");
        }
    }

    private static boolean checkClient() {
        return client != null;
    }


    //-----------------OkNet的内部初始化构造器--------------------------


    /**
     * description: OkNet 初始化构造器 类
     * author: Simon
     * created at 2017/9/1 下午7:40
     */
    public static class InitBuilder {
        private boolean isLog = false;//是否开启log 日志，默认false
        private long connectTimeout = 3000L;//连接超时 默认3s
        private long readTimeout = 3000L;//读取超时 默认3s
        private CookieJar cookieJar = null;//cookie 缓存实现类

        //--防止外部初始化--
        private InitBuilder() {

        }

        /**
         * 是否开启log
         *
         * @param log
         */
        public InitBuilder isLog(boolean log) {
            isLog = log;
            return this;
        }

        /**
         * 设置连接超时时间
         *
         * @param connectTimeout --连接超时时间 单位是ms
         */
        public InitBuilder connectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * 设置读取超时时间
         *
         * @param readTimeout --读取超时时间 单位是ms
         */
        public InitBuilder readTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        /**
         * 设置cookie jar
         *
         * @param cookieJar --设置cookiejar 实现类 =>(CookiesJarImpl)
         */
        public InitBuilder cookieJar(CookieJar cookieJar) {
            this.cookieJar = cookieJar;
            return this;
        }

        /**
         * 创建方法 --end
         */
        public void build() {

            //--check base data--
            if (connectTimeout <= 0) {
                connectTimeout = 3000L;
            }
            if (readTimeout <= 0) {
                readTimeout = 3000L;
            }

            //--build--

            //--connect & read--
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
            builder.readTimeout(readTimeout, TimeUnit.MILLISECONDS);

            //--log--
            if (isLog) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }

            //--cookies--
            if (cookieJar != null) {
                builder.cookieJar(cookieJar);
            }
            //--build client--
            OkHttpClient client = builder.build();

            //--effective--
            OkNet.client = client; //outside can't change
        }
    }


}
