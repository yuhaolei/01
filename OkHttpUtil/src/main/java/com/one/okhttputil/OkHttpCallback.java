package com.one.okhttputil;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;

/**
 * Created by daniu on 15/10/16.
 * 抽象类，用于请求成功后的回调
 */
public abstract class  OkHttpCallback<T> {
    //这是请求数据的返回类型，包含常见的（Bean，List等）
    Type mType;

    public OkHttpCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    /**
     * 通过反射想要的返回类型
     *
     * @param subclass
     * @return
     */
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    /**
     * 在请求之前的方法，一般用于加载框展示
     *
     * @param request
     */
    public void onBefore(Request request) {
    }

    /**
     * 在请求之后的方法，一般用于加载框隐藏
     */
    public void onAfter() {
    }

    public abstract void onSuccess(T response);
    public abstract void onFailure(JuHeBaseBean<T> entity, String message);
}
