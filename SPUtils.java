package com.example.lidan.myapplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * SharedPreference封装类
 */
public class SPUtils {

    /**保存在手机里面的文件名*/
    public static final String FILE_NAME = "share_data";
    /**SharedPreferences.Editor对象*/
    private static SharedPreferences.Editor sEditor;
    /**SharedPreferences对象*/
    private static SharedPreferences sSharedPreferences;

    /**
     * 初始化SharedPreferences对象及Editor对象
     * @param context 上下文
     */
    public static void init(Context context) {

        sSharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sEditor = sSharedPreferences.edit();
    }

    /**
     * 保存数据
     * @param key 键名
     * @param object 值
     */
    public static void put(String key, Object object) {

        if (object instanceof String) {
            sEditor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            sEditor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            sEditor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            sEditor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            sEditor.putLong(key, (Long) object);
        } else {
            sEditor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(sEditor);
    }

    /**
     * 得到保存数据
     * @param key 键名
     * @param defaultObject 默认值
     * @return 保存的数据
     */
    public static Object get(String key, Object defaultObject) {

        if (defaultObject instanceof String)
        {
            return sSharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sSharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sSharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sSharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sSharedPreferences.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     * @param key 键名
     */
    public static void remove(String key) {

        sEditor.remove(key);
        SharedPreferencesCompat.apply(sEditor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {

        sEditor.clear();
        SharedPreferencesCompat.apply(sEditor);
    }

    /**
     * 查询某个key是否已经存在
     * @param key 键名
     * @return 是否存在
     */
    public static boolean contains(String key) {

        return sSharedPreferences.contains(key);
    }

    /**
     * 返回所有的键值对
     * @return 所有的键值对
     */
    public static Map<String, ?> getAll() {

        return sSharedPreferences.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     *
     */
    private static class SharedPreferencesCompat {

        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod() {

            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {

            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {

            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {

            } catch (IllegalAccessException e) {

            } catch (InvocationTargetException e) {

            }

            editor.commit();
        }
    }

}
