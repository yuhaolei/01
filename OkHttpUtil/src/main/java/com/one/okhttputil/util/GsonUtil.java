package com.one.okhttputil.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.one.okhttputil.adapter.DoubleDefault0Adapter;
import com.one.okhttputil.adapter.IntegerDefault0Adapter;
import com.one.okhttputil.adapter.LongDefault0Adapter;
import com.one.okhttputil.adapter.StringDefaultAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * 描 述: Gson辅助类
 */
public class GsonUtil {
    private static Gson gson;
    
    
    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(long.class, new LongDefault0Adapter())
                .registerTypeAdapter(String.class, new StringDefaultAdapter())
                .create();
    }
    
    
    public static Gson getGson() {
        return gson;
    }
    
    
    /**
     * Json str 2 obj t.
     *
     * @param <T>     the type parameter
     * @param jsonStr the json str
     * @param tClass  the t class
     * @return the t
     * @author Created by liuwenjie on 2018/11/01 15:09
     */
    public static <T> T jsonStr2Obj(String jsonStr, Class<T> tClass) {
        
        return gson.fromJson(jsonStr, tClass);
    }
    
    
    /**
     * Json str 2 obj t.
     *
     * @param <T>     the type parameter
     * @param jsonStr the json str
     * @param tClass  the t class
     * @return the t
     * @author Created by liuwenjie on 2018/11/01 15:09
     */
    public static <T> T jsonStr2Obj(String jsonStr, Type tClass) {
        
        return gson.fromJson(jsonStr, tClass);
    }
    
    
    /**
     * Obj 2 json str string.
     *
     * @param <T>   the type parameter
     * @param model the model
     * @return the string
     * @author Created by liuwenjie on 2018/11/01 15:09
     */
    public static <T> String obj2JsonStr(T model) {
        return gson.toJson(model);
    }
    
    
    /**
     * Json str 2 obj list array list.
     *
     * @param <T>     the type parameter
     * @param jsonStr the json str
     * @param tClass  the t class
     * @return the array list
     * @author Created by liuwenjie on 2018/11/01 15:09
     */
    public static <T> ArrayList<T> jsonStr2ObjList(String jsonStr, Class<T> tClass) {
        Type type = TypeToken.getParameterized(ArrayList.class, tClass).getType();
        return gson.fromJson(jsonStr, type);
    }
    
    
    /**
     * Json str 2 obj array t [ ].
     *
     * @param <T>     the type parameter
     * @param jsonStr the json str
     * @param tClass  the t class
     * @return the t [ ]
     * @author Created by liuwenjie on 2018/11/01 15:09
     */
    public static <T> T[] jsonStr2ObjArray(String jsonStr, Class<T> tClass) {
        Type type = TypeToken.getArray(tClass).getType();
        return gson.fromJson(jsonStr, type);
    }
    
    
    /**
     * Obj list 2 json str string.
     *
     * @param <T>  the type parameter
     * @param list the list
     * @return the string
     * @author Created by liuwenjie on 2018/11/01 15:09
     */
    public static <T> String objList2JsonStr(ArrayList<T> list) {
        return gson.toJson(list);
    }
    
    /**
     * Obj array 2 json str string.
     *
     * @param <T>        the type parameter
     * @param modelArray the model array
     * @return the string
     * @author Created by liuwenjie on 2018/11/01 15:09
     */
    public static <T> String objArray2JsonStr(T... modelArray) {
        return gson.toJson(modelArray);
    }
    
    
    /**
     * Format json string.
     *
     * @param json the json
     * @return the string
     * @author Created by liuwenjie on 2018/11/01 15:09
     */
    public static String formatJson(String json) {
        if(json == null) {
            return "null";
        }
        try {
            if(json.startsWith("[") && json.endsWith("]")) { // 数组
                JSONArray jsonArray = new JSONArray(json);
                return formatArrayJson(jsonArray);
            }
            else if(json.startsWith("{") && json.endsWith("}")) {
                JSONObject jsonObj = new JSONObject(json);
                return formatObjJson(jsonObj);
            }
            return json;
        } catch(Exception e) {
            return json;
        }
    }
    
    
    private static String formatObjJson(JSONObject jsonObject) throws JSONException {
        
        return jsonObject.toString(4);
    }
    
    private static String formatArrayJson(JSONArray jsonArray) throws JSONException {
        return jsonArray.toString(4);
    }
    
    
    /**
     * Json str 2 map hash map.
     *
     * @param <K>     the type parameter
     * @param <V>     the type parameter
     * @param jsonStr the json str
     * @param kClass  the k class
     * @param vClass  the v class
     * @return the hash map
     * @author Created by liuwenjie on 2018/11/01 15:09
     */
    public static <K, V> HashMap<K, V> jsonStr2Map(String jsonStr, Class<K> kClass, Class<V> vClass) {
        TypeToken typeToken = new TypeToken<HashMap<K, V>>() {
        };
        return gson.fromJson(jsonStr, typeToken.getType());
    }
    
    /**
     * Map 2 json string.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map the map
     * @return the string
     * @author Created by liuwenjie on 2018/11/01 15:09
     */
    public static <K, V> String map2Json(HashMap<K, V> map) {
        return gson.toJson(map);
    }
    
    
}
