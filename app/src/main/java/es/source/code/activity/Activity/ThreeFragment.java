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

import es.source.code.activity.Adapter.FoodAdapter;
import es.source.code.activity.Adapter.MyAdapterFromBase;
import es.source.code.activity.Bean.Food;
import es.source.code.activity.R;
import es.source.code.activity.interfaces.IF_HandleFood;

/**
 * Created by taoye on 2018/10/3.
 */
public class ThreeFragment extends Fragment {
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
        Food apple = new Food(R.drawable.shuizhuyu,"大虾",123,2);
        foodList.add(apple);
        Food banana = new Food(R.drawable.shuizhuyu,"麻辣小龙虾",322,6);
        foodList.add(banana);
        Food orange = new Food(R.drawable.shuizhuyu,"正宗烤鱼", 200,89);
        foodList.add(orange);
        Food watermelon = new Food(R.drawable.shuizhuyu,"酸菜鱼", 100,65);
        foodList.add(watermelon);
        Food pear = new Food(R.drawable.shuizhuyu,"水煮鱼",21,4);
        foodList.add(pear);
        Food grape = new Food(R.drawable.shuizhuyu,"红烧螃蟹",352,5);
        foodList.add(grape);
        Food pineapple = new Food(R.drawable.shuizhuyu,"甲鱼",1000,6);
        foodList.add(pineapple);
        Food strawberry = new Food(R.drawable.shuizhuyu,"黑鱼",23,6);
        foodList.add(strawberry);
        Food cherry = new Food(R.drawable.shuizhuyu,"草鱼", 43,6);
        foodList.add(cherry);
        Food mango = new Food(R.drawable.shuizhuyu,"鲇鱼",3,6);
        foodList.add(mango);
    }


    private class OnItemClickHandler implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent,View view,int position,long id){
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
            // 调用接口
            if_handleFood.addFood(foodList.get(pos));

            String fName = foodList.get(pos).getFoodName();
            String fPrice = Double.toString(foodList.get(pos).getFoodPrice());

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
