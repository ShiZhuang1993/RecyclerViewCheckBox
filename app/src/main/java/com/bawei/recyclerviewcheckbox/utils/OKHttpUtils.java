package com.bawei.recyclerviewcheckbox.utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类用途：封装OKHTTP
 * 作者：ShiZhuangZhuang
 * 时间：2017/5/11 19:01
 */

public class OKHttpUtils {
    private OkHttpClient client;
    //防止多个线程同时反问时的一个交互 设置一个关键字
    private volatile static OKHttpUtils manger;
    //获得类名
    private final String TAG = OKHttpUtils.class.getSimpleName();
    //声明一个handler
    private Handler handler;
    //提交json数据
    private static final MediaType Json = MediaType.parse("application/json;charset=utf-8");
    //提交字符串
    private static final MediaType str = MediaType.parse("text/x-markdown;charset=utf-8");


    private OKHttpUtils() {
        client = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }

    //单列模式获取对象
    public static OKHttpUtils getInstance() {
        // OKHttpUtils okHttpUtils = null;
        if (manger == null) {
            synchronized (OKHttpUtils.class) {
                manger = new OKHttpUtils();
            }
        }
        return manger;
    }

    /**
     * 同步请求 在安卓开发中不常用，因为会出现UI阻塞线程造成ANR异常
     *
     * @param url
     * @return
     */

    public String syncGetByUrl(String url) {
        //实列化一个Request的请求
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();//同步数据请求
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 异步请求数据返回的是字符串
     *
     * @param url
     * @param callBack
     */
    public void asyncJsonStringByUrl(String url, final Func1 callBack) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    getJsonString(response.body().string(), callBack);
                }
            }
        });
    }

    /**
     * 返回json字符串
     *
     * @param jsonValue
     * @param callBack
     */
    public void getJsonString(final String jsonValue, final Func1 callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(jsonValue);
                }
            }
        });
    }

    /**
     * 返回byte数组
     *
     * @param data
     * @param callBack
     */
    public void getJsonByte(final byte[] data, final Func2 callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(data);
                }
            }
        });
    }

    /**
     * 返回json对象
     *
     * @param jsonValue
     * @param callBack
     */
    public void getJsonObj(final String jsonValue, final Func4 callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onResponse(new JSONObject(jsonValue));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public interface Func1 {
        void onResponse(String result);
    }

    public interface Func2 {
        void onResponse(byte[] result);
    }

    public interface Func3 {
        void onResponse(Bitmap result);
    }

    public interface Func4 {
        void onResponse(JSONObject result);
    }
}
