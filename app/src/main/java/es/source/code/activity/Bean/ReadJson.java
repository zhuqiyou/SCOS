package es.source.code.activity.Bean;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadJson {

    // 从json文件中读取数据，并返回一个包含Food对象的List
    public static List<Food> getFoodsFromJSON(String filename, Context context){
        List<Food> foodList = new ArrayList<>();
        Food food;
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(context.getAssets().open(filename),"utf-8");
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while((line = br.readLine())!=null){
                stringBuilder.append(line);
            }
            br.close();
            inputStreamReader.close();

            JSONObject foodsjson = new JSONObject(stringBuilder.toString());
            JSONArray foodsArray = foodsjson.getJSONArray("food");
            for(int i = 0;i<foodsArray.length();i++){
                JSONObject foodObject = foodsArray.getJSONObject(i);
                food = foodGson(foodObject.toString()); // 将json文件中的每一行反序列化为Food对象
                foodList.add(food);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return foodList;
    }

    // 泛型。使用Gson反序列化
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type){
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }
    // 将String反序列化为对应的Food对象
    public static Food foodGson(String json){
        return parseJsonWithGson(json, Food.class);
    }
}