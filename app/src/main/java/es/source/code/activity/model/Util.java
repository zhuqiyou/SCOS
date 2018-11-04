package es.source.code.activity.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class Util {

    private String name;
    private String  loginState;
    public static boolean saveInfo(Context context, String name, String loginState){
        SharedPreferences sp=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sp.edit();
        edit.putString("username",name);
        edit.putString("loginState",loginState);
        edit.commit();
        return true;
    }
    //使用Map<>方法从data.xml文件中获取数据
    public static Map<String,String> getInfo(Context context){
        SharedPreferences sp=context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String name=sp.getString("username", null);
        String loginState = sp.getString("loginState", null);
        Map<String,String> userMap=new HashMap<String, String>();
        userMap.put("name",name);
        userMap.put("loginState",loginState);
        return userMap;
    }

}