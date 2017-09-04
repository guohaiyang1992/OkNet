package com.simon.oknet.cookies.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * description:  内存缓存cookies
 * author: Simon
 * created at 2017/9/4 上午11:16
 */
public class MemoryCookieStore implements ICookieStore {

    //--所有cookies的内存缓存--
    private final HashMap<String, List<Cookie>> allCookies = new HashMap<>();

    /**
     * 添加cookies
     *
     * @param url     --请求网址
     * @param cookies --新的cookies
     */
    @Override
    public void add(HttpUrl url, List<Cookie> cookies) {
        //--获取旧的cookies--
        List<Cookie> oldCookies = allCookies.get(url.host());
        //--如果旧的不为null,则遍历移除 新的出现的--
        if (oldCookies != null) {
            Iterator<Cookie> itNew = cookies.iterator();
            Iterator<Cookie> itOld = oldCookies.iterator();
            while (itNew.hasNext()) {
                String va = itNew.next().name();
                while (va != null && itOld.hasNext()) {
                    String v = itOld.next().name();
                    if (v != null && va.equals(v)) {
                        itOld.remove();
                    }
                }
            }
            //--然后添加新的 cookies 到原有内容--
            oldCookies.addAll(cookies);
        } else {
            allCookies.put(url.host(), cookies);
        }


    }

    /**
     * 获取对应的 url的 cookie
     *
     * @param uri 对应的url
     * @return 获取url 集合
     */
    @Override
    public List<Cookie> get(HttpUrl uri) {
        List<Cookie> cookies = allCookies.get(uri.host());
        if (cookies == null) {
            cookies = new ArrayList<>();
            allCookies.put(uri.host(), cookies);
        }
        return cookies;

    }

    /**
     * 移除所有缓存
     *
     * @return
     */
    @Override
    public boolean removeAll() {
        allCookies.clear();
        return true;
    }

    /**
     * 获取所有缓存
     *
     * @return
     */
    @Override
    public List<Cookie> getCookies() {
        List<Cookie> cookies = new ArrayList<>();
        Set<String> httpUrls = allCookies.keySet();
        for (String url : httpUrls) {
            cookies.addAll(allCookies.get(url));
        }
        return cookies;
    }


    /**
     * 移除对应的cookies
     *
     * @param uri
     * @param cookie
     * @return
     */
    @Override
    public boolean remove(HttpUrl uri, Cookie cookie) {
        List<Cookie> cookies = allCookies.get(uri.host());
        if (cookie != null) {
            return cookies.remove(cookie);
        }
        return false;
    }


}
