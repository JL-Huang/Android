package com.example.myapplication.ContentProviderDemo;

/**
 * 创建时间：2021-8-27 10:30
 * 作者：huangjingliang003
 * 描述：基于SharedPreferences的ContentProvider,解决SharedPreferences跨进程不可用的问题。
 * 可以保存String,boolean,int,long,double等类型的数据
 * 其余类如果可以用JsonUtil.jsonFromObject()转换为json格式字符串则也可以保存
 * 当前ContentProvider已在AndroidManifest中注册
 */

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
;import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SPCrossingProcessUtil extends ContentProvider {
    private static final String CONTENT = "content://";
    private static final String AUTHORITY = "com.lianjia.common.vr.util";
    private static final String SEPARATOR = "/";
    private static final String CONTENT_URI = CONTENT + AUTHORITY;
    private static final String TYPE_STRING = "string";
    private static final String TYPE_INTEGER = "integer";
    private static final String TYPE_BOOLEAN = "boolean";
    private static final String TYPE_FLOAT = "float";
    private static final String TYPE_LONG = "long";
    private static final String TYPE_OTHER = "other";
    private static final String DEFAULT_BUNDLE_KEY_NAME = "key";
    private static final String DEFAULT_BUNDLE_VALUE_NAME = "value";
    private static final String DEFAULT_BUNDLE_VALUE_NAME_RETURN = "valueReturn";
    private static final String METHOD_PUT = "put";
    private static final String METHOD_GET = "get";
    private static final String TABLE_NAME = "SharedPreferences";
    private static final String METHOD_REMOVE = "remove";
    private static final String METHOD_CLEAR = "clear";


    private static Context context;

    private static void checkContext() {
        if (context == null) {
//            context = VrBase.getApplicationContext();
        }
    }

    private static String getTypeName(@NonNull Object object) {
        if (object == null) {
            throw new IllegalStateException("不能判断空对象的类型！");
        }
        if (object instanceof String) {
            return TYPE_STRING;
        } else if (object instanceof Integer) {
            return TYPE_INTEGER;
        } else if (object instanceof Boolean) {
            return TYPE_BOOLEAN;
        } else if (object instanceof Float) {
            return TYPE_FLOAT;
        } else if (object instanceof Long) {
            return TYPE_LONG;
        } else {
            return TYPE_OTHER;
        }
    }

    private static void bundlePut(Bundle bundle, String name, Object object) {
        switch (getTypeName(object)) {
            case TYPE_BOOLEAN: {
                bundle.putBoolean(name, (Boolean) object);
                break;
            }
            case TYPE_INTEGER: {
                bundle.putInt(name, (Integer) object);
                break;
            }
            case TYPE_FLOAT: {
                bundle.putFloat(name, (Float) object);
                break;
            }
            case TYPE_LONG: {
                bundle.putLong(name, (Long) object);
                break;
            }
            case TYPE_STRING: {
                bundle.putString(name, (String) object);
                break;
            }
            case TYPE_OTHER: {
//                bundle.putString(name, (String) JsonUtil.jsonFromObject(object));
                break;
            }
        }
    }

    private static Object bundleGet(Bundle bundle, String name, String type) {
        switch (type) {
            case TYPE_BOOLEAN: {
                return bundle.getBoolean(name);
            }
            case TYPE_INTEGER: {
                return bundle.getInt(name);
            }
            case TYPE_FLOAT: {
                return bundle.getFloat(name);
            }
            case TYPE_LONG: {
                return bundle.getLong(name);
            }
            case TYPE_STRING: {
                return bundle.getString(name);
            }
            case TYPE_OTHER: {
                return bundle.getString(name);
            }
            default:
                return null;
        }
    }

    public static void put(String key, @NonNull Object value) {
        if (value == null) return;
        String typePut = getTypeName(value);
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TABLE_NAME + SEPARATOR + key);
//        if (TYPE_OTHER.equals(typePut) && "{}".equals(JsonUtil.jsonFromObject(value)))
//            throw new IllegalStateException("不支持存储该类型变量");
        Bundle bundle = new Bundle();
        bundlePut(bundle, DEFAULT_BUNDLE_KEY_NAME, key);
        bundlePut(bundle, DEFAULT_BUNDLE_VALUE_NAME, value);
        cr.call(uri, METHOD_PUT, typePut, bundle);
    }

    public synchronized static Object get(String key, @NonNull Object defaultValue) {
        if (defaultValue == null) return null;
        String typeGet = getTypeName(defaultValue);
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TABLE_NAME + SEPARATOR + key);
        Bundle bundle = new Bundle();
        bundlePut(bundle, DEFAULT_BUNDLE_KEY_NAME, key);
        bundlePut(bundle, DEFAULT_BUNDLE_VALUE_NAME, defaultValue);
        Bundle bundleReturn = cr.call(uri, METHOD_GET, typeGet, bundle);
        Object dataInBundleReturn = bundleGet(bundleReturn, DEFAULT_BUNDLE_VALUE_NAME_RETURN, getTypeName(defaultValue));
//        Object o = JsonUtil.objectFromJson((String) dataInBundleReturn, String[].class);
//        return typeGet == TYPE_OTHER ? JsonUtil.objectFromJson((String) dataInBundleReturn, defaultValue.getClass()) : dataInBundleReturn;
        return null;//凑数
    }

    public synchronized static void remove(String key) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TABLE_NAME + SEPARATOR + key);
        cr.call(uri, METHOD_REMOVE, key, null);
    }

    public synchronized static void clear() {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TABLE_NAME + SEPARATOR + "#");
        cr.call(uri, METHOD_CLEAR, null, null);
    }

    @Override
    public boolean onCreate() {
        SPCrossingProcessUtil.checkContext();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        switch (method) {
            case METHOD_PUT: {
                String keySP = (String) bundleGet(extras, DEFAULT_BUNDLE_KEY_NAME, TYPE_STRING);
                Object valueSP = bundleGet(extras, DEFAULT_BUNDLE_VALUE_NAME, arg);
//                SPUtils.put(keySP, valueSP);
                break;
            }
            case METHOD_GET: {
                String keySP = (String) bundleGet(extras, DEFAULT_BUNDLE_KEY_NAME, TYPE_STRING);
                Object defaultValueSP = bundleGet(extras, DEFAULT_BUNDLE_VALUE_NAME, arg);
//                Object result = SPUtils.get(keySP, defaultValueSP);
                Bundle bundle = new Bundle();
//                bundlePut(bundle, DEFAULT_BUNDLE_VALUE_NAME_RETURN, result);
                return bundle;
            }
            case METHOD_REMOVE: {
//                SPUtils.remove(arg);
                break;
            }
            case METHOD_CLEAR: {
//                SPUtils.clear();
                break;
            }
        }
        return super.call(method, arg, extras);
    }

}