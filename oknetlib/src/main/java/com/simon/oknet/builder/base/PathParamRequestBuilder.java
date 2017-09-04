package com.simon.oknet.builder.base;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 带参数且可修改请求地址的的请求构造类 （修改地址类似于 基地址https://api.douban.com/v2/book  使用地址：https://api.douban.com/v2/book/122435, 对地址进行拼接）
 * author: Simon
 * created at 2017/9/1 上午10:56
 */

public abstract class PathParamRequestBuilder<T extends PathParamRequestBuilder> extends ParamRequestBuilder<T> {

    //--请求参数（get请求的参数会显示在请求地址中）--
    protected List<String> paths;

    /**
     * 设置请求参数
     *
     * @param paths --请求参数集合
     */
    public T path(@NonNull List<String> paths) {
        this.paths = paths;
        return (T) this;
    }


    /**
     * 添加请求时需要拼接的地址
     *
     * @param path --值
     */
    public T addPath(@NonNull String path) {
        if (paths == null) {
            paths = new ArrayList<>();
        }
        paths.add(path);
        return (T) this;
    }

    //-----------------公用方法--------------------------

    /**
     * 检查paths是否有效
     *
     * @return --true表示有效，false表示无效
     */
    protected boolean checkPaths() {
        return paths != null && !paths.isEmpty();
    }

}
