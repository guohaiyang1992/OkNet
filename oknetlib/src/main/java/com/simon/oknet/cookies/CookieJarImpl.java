package com.simon.oknet.cookies;

import com.simon.oknet.cookies.store.ICookieStore;
import com.simon.oknet.utils.ObjectUtils;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * description: cookieJar的实现类，根据传入的cookieStore做出不同的操作
 * author: Simon
 * created at 2017/9/4 上午11:12
 */
public class CookieJarImpl implements CookieJar {

    //--cookie store 接口--
    private ICookieStore cookieStore;

    /**
     * 初始化CookieJarImpl
     *
     * @param cookieStore --传入具体的 cookieStore 你可以根据ICookieStore 自己实现，也可使用两个实现类 MemoryCookieStore (内存缓存)、PersistentCookieStore（sp缓存）
     */
    public CookieJarImpl(ICookieStore cookieStore) {
        ObjectUtils.checkNotNullErr(cookieStore, "cookieStore can not be null.");
        this.cookieStore = cookieStore;
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.add(url, cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        return cookieStore.get(url);
    }

    public ICookieStore getCookieStore() {
        return cookieStore;
    }
}
