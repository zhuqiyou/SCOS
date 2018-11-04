package es.source.code.activity.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import es.source.code.activity.Adapter.FoodAdapter;
import es.source.code.activity.Adapter.MyAdapterFromBase;
import es.source.code.activity.Bean.Food;
import es.source.code.activity.R;
import es.source.code.activity.interfaces.IF_HandleFood;

/**
 * Created by taoye on 2018/10/3.
 */
public class TwoFragment extends Fragment {
    private TextView tv_show;

    private ListView list_view,list_view_btn;
    private List<Food> foodList = new ArrayList<Food>();

    //声明接口
    private IF_HandleFood if_handleFood;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        if_handleFood = (IF_HandleFood) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container ,Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_listview,container,false);
    }

    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initFoods();
        //初始化菜单
        MyAdapterFromBase myAdapterFromBase = new MyAdapterFromBase(getContext(),foodList,onClickListener);
        list_view_btn = (ListView) getView().findViewById(R.id.list_view);
        list_view_btn.setAdapter(myAdapterFromBase);
        list_view_btn.setOnItemClickListener(new OnItemClickHandler());
    }

    private void initFoods() {
        Food suan = new Food(R.drawable.jiaohuaji,"蒜爆肉",30,123);
        foodList.add(suan);
        Food yu = new Food(R.drawable.jiaohuaji,"鱼香肉丝", 32,23);
        foodList.add(yu);
        Food orange = new Food(R.drawable.jiaohuaji,"木须肉", 43,67);
        foodList.add(orange);
        Food watermelon = new Food(R.drawable.jiaohuaji,"酸菜鱼", 45,56);
        foodList.add(watermelon);
        Food pear = new Food(R.drawable.jiaohuaji,"水煮鱼",56,2);
        foodList.add(pear);
        Food grape = new Food(R.drawable.jiaohuaji,"地锅鸡",89,45);
        foodList.add(grape);
        Food pineapple = new Food(R.drawable.jiaohuaji,"叫花鸡",89,6);
        foodList.add(pineapple);
        Food strawberry = new Food(R.drawable.jiaohuaji,"拉炒大肠",123,1);
        foodList.add(strawberry);
        Food cherry = new Food(R.drawable.jiaohuaji,"宫爆牛肚", 56,4);
        foodList.add(cherry);
        Food mango = new Food(R.drawable.jiaohuaji,"番茄炒鸡蛋",67,5);
        foodList.add(mango);
    }


    private class OnItemClickHandler implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent,View view,int position,long id){
//            Toast.makeText(getContext(), "点菜成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(),FoodDetailed.class);
            intent.putExtra("foodlist",(Serializable)foodList);
            intent.putExtra("index",position);
            startActivity(intent);
        }
    }
    private View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            Button btn = (Button) view;
            int pos = (Integer) btn.getTag();

            String fName = foodList.get(pos).getFoodName();
            String fPrice = Double.toString(foodList.get(pos).getFoodPrice());
            // 调用接口
            if_handleFood.addFood(foodList.get(pos));

            String show = "点菜成功";

            Toast.makeText(getContext(), show, Toast.LENGTH_SHORT).show();
        }
    };

    /*
接收
 */
    @Override
    public void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onReceiveEvent(List<Food> foodList){
        Log.i("显示", "执行");
        MyAdapterFromBase myAdapterFromBase = new MyAdapterFromBase(getContext(),foodList,onClickListener);
        list_view_btn.setAdapter(myAdapterFromBase);
    }

}