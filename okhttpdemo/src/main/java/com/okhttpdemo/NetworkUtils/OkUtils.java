package com.okhttpdemo.NetworkUtils;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

/**
 * Created by Administrator on 2017/6/12.
 */

public class OkUtils {
    public static OkHttpClient client = new OkHttpClient();

    /**
     * 获取请求对象
     * @param url
     * @return
     */
    private static Request getRequestFromUrl(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        return request;
    }

    /**
     * 获取响应对象
     * @param url
     * @return
     * @throws IOException
     */
    private static Response getResponseFromUrl(String url) throws IOException {
        Request request = getRequestFromUrl(url);
        Response response = client.newCall(request).execute();
        return response;
    }

    /**
     * 获取响应体
     * @param url
     * @return
     * @throws IOException
     */
    private static ResponseBody getResponseBodyFromUrl(String url) throws IOException {
        Response response = getResponseFromUrl(url);
        if(response.isSuccessful()){
            return response.body();
        }
        return null;
    }

    /**
     * 返回响应体内的字符串
     * @param url
     * @return
     * @throws IOException
     */
    public static String loadStringFromUrl(String url) throws IOException {
        ResponseBody body = getResponseBodyFromUrl(url);
        if(body!=null){
            return body.string();
        }
        return null;
    }

    /**
     * 隐式开启线程
     * @param url
     * @param callback
     */
    public static void GET_NETWORK(String url, Callback callback){
        Request request = getRequestFromUrl(url);
        client.newCall(request).enqueue(callback);
    }
}
