package com.throne.emm.net.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.throne.emm.net.result.BaseResult;

import android.util.Log;

public class NetRequestUtil {
    /**
     * 是否使用SSL请求，根据基础URL判断
     */
    private boolean useSSL = false;
    /**
     * 请求超时时间
     */
    private int timeOut = 10000;
    /**
     * 用户代理，指浏览器
     */
    private String userAgent="Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36";

    private String url;
    
    private BaseResult mBaseResult;

    public NetRequestUtil(BaseResult mBaseResult) {
        this.mBaseResult=mBaseResult;
    }


    public void doPost(String url,RequestParams params) {
        AsyncHttpClient mAsyncHttpClient = getHttpClient();
        if (params != null) {
            mAsyncHttpClient.post(url, params, getmAsyncHttpResponseHandler());
        } else {
            mAsyncHttpClient.post(url, getmAsyncHttpResponseHandler());
        }
    }


    public void doGet(String url,RequestParams params) {
        AsyncHttpClient mAsyncHttpClient = getHttpClient();
        if (params != null) {
            mAsyncHttpClient.get(url, params, getmAsyncHttpResponseHandler());
        } else {
            mAsyncHttpClient.get(url, getmAsyncHttpResponseHandler());
        }
    }

    protected AsyncHttpClient getHttpClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setUserAgent(userAgent);
        client.setTimeout(timeOut);
        return client;
    }

    private AsyncHttpResponseHandler getmAsyncHttpResponseHandler() {
        AsyncHttpResponseHandler handle = new AsyncHttpResponseHandler() {
            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                Log.i("yxd", "onFailure");
                mBaseResult.postFailure();
            }

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                mBaseResult.dataAnalysis(content);
                Log.i("yxd", "onSuccess");
            }
        };
        return handle;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
