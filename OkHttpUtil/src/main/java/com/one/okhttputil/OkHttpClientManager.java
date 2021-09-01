package com.one.okhttputil;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpClientManager {
    private static final String TAG = "OkHttpClientManager";

    private static OkHttpClientManager mInstance;
    private static OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    //默认的请求回调类
    private final OkHttpCallback<String> DEFAULT_RESULT_CALLBACK = new OkHttpCallback<String>() {
        @Override
        public void onSuccess(String response) {
        }

        @Override
        public void onFailure(JuHeBaseBean<String> entity, String message) {

        }
    };

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(false)// 失败后是否重试
                .cookieJar(CookieJar.NO_COOKIES)
                .build();
        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    /**
     * 取消所有请求请求
     */
    public void cancelAll() {
        if (mOkHttpClient == null) return;
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            call.cancel();
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            call.cancel();
        }
    }

    /**
     * 根据Tag取消请求
     */
    public void cancelTag(Object tag) {
        if (tag == null || mOkHttpClient == null) return;
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /**
     * 异步get请求
     *
     * @param url
     * @param callback
     * @param tag
     */
    public void getAsyn(String url, final OkHttpCallback callback, Object tag) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .tag(tag)
                .build();
        deliveryResult(callback, request);
    }

    /**
     * 通用基础的异步的post请求
     *
     * @param url
     * @param callback
     * @param tag
     */
    public void postAsyn(String url, Map<String, String> params, final OkHttpCallback callback, Object tag) {
//        Map<String,Object> param = new HashMap<>();
//        param.putAll(params);
//        postAsynObject(url,param,callback,tag);
        Log.e("one.yu","one.yu postAsyn");
        Request request = buildPostRequest(url, params, null, null, tag);
        deliveryResult(callback, request);
    }

    /**
     * 请求参数表单创建(请求参数转为json字符串，字段名为data)
     *
     * @param url
     * @param params
     * @param tag
     * @return
     */
    private Request buildPostRequest(String url, Map<String, String> params, String headKey, String headValue, Object tag) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            Iterator<String> iterator = params.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next();
                builder.add(key, params.get(key));
            }
//            if (AppMgrUtils.getInstance().isDev()) {// 如果为开发版，则打印请求信息、
            if (true) {// 如果为开发版，则打印请求信息、
                String data = mGson.toJson(params);
                Log.i("one.yu","url:" + url + "\n requestParams:\n" + data);
            }
        }
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url)
                .post(builder.build())
                .tag(tag);
        // 添加token
        requestBuilder.addHeader("token", "token_value");
        // 添加header
        if (!TextUtils.isEmpty(headKey) && !TextUtils.isEmpty(headValue)) {
            requestBuilder.addHeader(headKey, headValue);
        }
        return requestBuilder.build();
    }

//    public void postAsynObject(String url, Map<String, Object> params, final OkHttpCallback callback, Object tag) {
//        postAsynHeadObject(url,params,null,null,callback,tag);
//    }

//    /**
//     * 含header的请求
//     * @param url
//     * @param params
//     * @param headKey
//     * @param headValue
//     * @param callback
//     * @param tag
//     */
//    public void postAsynHead(String url, Map<String, String> params, String headKey, String headValue, final OkHttpCallback callback, Object tag) {
//        Map<String,Object> param = new HashMap<>();
//        param.putAll(params);
//        postAsynHeadObject(url,param,headKey,headValue,callback,tag);
//    }
//
//    public void postAsynHeadObject(String url, Map<String, Object> params, String headKey, String headValue, final OkHttpCallback callback, Object tag){
//        Request request = buildPostFormRequest(url, params, headKey, headValue, tag);
//        deliveryResult(callback, request);
//    }

    /**
     * 传参下载文件
     *
     * @param url
     * @param params
     * @param fileDir
     * @param fileName
     * @param callback
     * @param tag
     */
    public void postAsynDownload(String url, Map<String, String> params, String fileDir, String fileName, final OkHttpDownLoadCallback callback, Object tag) {
//        Map<String,Object> param = new HashMap<>();
//        param.putAll(params);
//        Request request = buildPostFormRequest(url, param, null, null, tag);
        Request request = buildPostRequest(url, params, null, null, tag);
        downloadResult(fileDir, fileName, callback, request);
    }

    /**
     * 不传参get下载文件
     *
     * @param url
     * @param fileDir
     * @param fileName
     * @param callback
     * @param tag
     */
    public void getAsynDownload(String url, String fileDir, String fileName, final OkHttpDownLoadCallback callback, Object tag) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        if (true) {
            Log.i("oneyu","getAsynDownload url:"+ url);
        }
        downloadResult(fileDir, fileName, callback, request);
    }

    /**
     * 请求参数表单创建(请求参数转为json字符串，字段名为data)
     *
     * @param url
     * @param params
     * @param tag
     * @return
     */
    private Request buildPostFormRequest(String url, Map<String, Object> params, String headKey, String headValue, Object tag) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params == null) {
            builder.add("data", "");
        } else {
            String data = mGson.toJson(params);
            builder.add("data", data);
            if (true) {
                Log.i(TAG,"url:" + url + "\n requestParams:\n" + data);
            }
        }
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url)
                .post(builder.build())
                .tag(tag);
        // 添加token
//        String token = AppMgrUtils.getInstance().getToken();
        String token = "";
        if (!TextUtils.isEmpty(token)) {
            requestBuilder.addHeader("token", token);
        }
        // 添加header
        if (!TextUtils.isEmpty(headKey) && !TextUtils.isEmpty(headValue)) {
            requestBuilder.addHeader(headKey, headValue);
        }
        return requestBuilder.build();
    }

    /**
     * 发送json请求
     *
     * @param url
     * @param jsonParams
     * @param callback
     */
    public void postJsonAsyn(String url, String jsonParams, final OkHttpCallback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8")
                , jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        deliveryResult(callback, request);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param pathName
     * @param fileName
     * @param callback
     */
    public void uploadFile(String url, String pathName, String fileName, OkHttpCallback callback) {
        //判断文件类型
        MediaType MEDIA_TYPE = MediaType.parse(judgeType(pathName));
        //创建文件参数
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(MEDIA_TYPE.type(), fileName, RequestBody.create(MEDIA_TYPE, new File(pathName)));
        //发出请求参数
        Request request = new Request.Builder()
//                .header("Authorization", "Client-ID " + "9199fdef135c122")
                .url(url)
                .post(builder.build())
                .build();
        deliveryResult(callback, request);
    }


    /**
     * 根据文件路径判断MediaType
     *
     * @param path
     * @return
     */
    private static String judgeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * @param destFileDir  下载的文件储存目录
     * @param destFileName 下载文件名称
     * @param listener     下载监听
     */

    public void downloadResult(final String destFileDir, final String destFileName, final OkHttpDownLoadCallback listener, final Request request) {

        //异步请求
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败监听回调
                listener.onDownloadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;

                //储存下载文件的目录
                File dir = new File(destFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, destFileName);

                try {

                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    if (true) {
                        Log.i(TAG,"file size:" + (total * 1.0f) / 1024 + " KB");
                    }
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        //下载中更新进度条
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    //下载完成
                    listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    listener.onDownloadFailed(e);
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {

                    }
                }
            }
        });
    }


    /**
     * 请求回调处理方法并传递返回值
     *
     * @param callback Map类型请求参数
     * @param request  Request请求
     */
    private void deliveryResult(OkHttpCallback callback, final Request request) {
        if (callback == null)
            callback = DEFAULT_RESULT_CALLBACK;
        final OkHttpCallback resCallBack = callback;
        //UI thread
        callback.onBefore(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedStringCallback(request, "网络错误"+ e.toString(), resCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String responseMessage = response.message();
                    final String responseBody = response.body().string();
                    if (true) {// 开发版，打印结果信息
                        Log.i(TAG,"onResponse responseBody: " + responseBody);
                    }
                    if (resCallBack.mType == String.class) {
                        sendSuccessResultCallback(responseBody, resCallBack);
                    } else {
                        Object o = mGson.fromJson(responseBody, resCallBack.mType);
                        sendSuccessResultCallback(o, resCallBack);
                    }
                } catch (IOException | com.google.gson.JsonParseException e) {
                    sendFailedStringCallback(response.request(), "JSON解析错误", resCallBack);
                }
            }
        });
    }

    /**
     * 处理请求成功的回调信息方法
     *
     * @param object   服务器响应信息
     * @param callback 回调类
     */
    private void sendSuccessResultCallback(final Object object, final OkHttpCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(object);
                callback.onAfter();
            }
        });
    }

    private void sendFailedStringCallback(final Request request, final String message, final OkHttpCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
//                callback.onFailure(null, e.getMessage());
                callback.onFailure(null, message);
            }
        });
    }
}