package es.source.code.activity.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import es.source.code.activity.Adapter.MyAdapterFromBase;
import es.source.code.activity.Bean.Food;
import es.source.code.activity.Bean.OrderFood;
import es.source.code.activity.R;
import es.source.code.activity.interfaces.IF_HandleFood;

public class OneFragment extends Fragment {

    private ListView list_view,list_view_btn;
    private List<Food> foodList = new ArrayList<Food>();

    private TextView tv_show;

    private List<OrderFood> orderfoodList = new ArrayList<OrderFood>();
    Double pricesum=0.0;

    //声明接口
    private IF_HandleFood if_handleFood;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        if_handleFood = (IF_HandleFood) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_listview,container,false);
    }
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


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initFoods();   // 初始化菜单数据
        //初始化按钮
        MyAdapterFromBase myAdapterFromBase = new MyAdapterFromBase(getContext(),foodList,onClickListener);
        list_view_btn = (ListView) getView().findViewById(R.id.list_view);
        list_view_btn.setAdapter(myAdapterFromBase);
        list_view_btn.setOnItemClickListener(new OnItemClickHandler());

//        list_view_btn = (ListView) getView().findViewById(R.id.list_view);
//        list_view_btn.setAdapter(myAdapterFromBase);
//
//        list_view_btn.setOnItemClickListener(new OnItemClickHandler());
    }
    private void initFoods() {
        Food apple = new Food(R.drawable.laocuhuasheng,"老醋花生",30.0,12);
        foodList.add(apple);
        Food banana = new Food(R.drawable.shuijingzhouzi,"水晶肘子", 40,13);
        foodList.add(banana);
        Food orange = new Food(R.drawable.shousiturou,"手撕兔肉", 30,45);
        foodList.add(orange);
        Food watermelon = new Food(R.drawable.laocuzhetou,"老醋蜇头",50,34);
        foodList.add(watermelon);
        Food grape = new Food(R.drawable.koushuiji,"口水鸡",80,88);
        foodList.add(grape);
        Food pineapple = new Food(R.drawable.wuxiangniurou,"五香牛肉", 46,56);
        foodList.add(pineapple);
        Food strawberry = new Food(R.drawable.jvhuadoufu,"菊花豆腐",20,1);
        foodList.add(strawberry);
        Food cherry = new Food(R.drawable.meijiluotou,"美极螺头",50,3);
        foodList.add(cherry);
        Food mango = new Food(R.drawable.jiangxiangzhouhua,"酱香肘花", 60,1);
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

                double fPrice1 = Double.parseDouble(Double.toString(foodList.get(pos).getFoodPrice()));
                pricesum=pricesum+fPrice1;

            String show = "点菜成功";

            Toast.makeText(getContext(), show, Toast.LENGTH_SHORT).show();
            btn.setText("退点");
        }
    };
}
