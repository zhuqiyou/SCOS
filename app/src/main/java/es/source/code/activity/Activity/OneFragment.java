package es.source.code.activity.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import es.source.code.activity.Adapter.FoodAdapter;
import es.source.code.activity.Adapter.MyAdapterFromBase;
import es.source.code.activity.Bean.Food;
import es.source.code.activity.Bean.OrderFood;
import es.source.code.activity.R;
import es.source.code.activity.interfaces.IF_HandleFood;

public class OneFragment extends Fragment {

    private ListView list_view,list_view_btn;
    private List<Food> foodList = new ArrayList<Food>();

    private List<OrderFood> orderfoodList = new ArrayList<OrderFood>();
    Double pricesum=0.0;

    //声明接口
    private IF_HandleFood if_handleFood;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        if_handleFood = (IF_HandleFood) activity;
    }


    private TextView tv_show;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_listview,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // Button btn_order = (Button) getView(). findViewById(R.id.btn_order);

        initFoods();   // 初始化菜单数据
        //初始化按钮
        MyAdapterFromBase myAdapterFromBase = new MyAdapterFromBase(getContext(),foodList,onClickListener);
        ListView list_view_btn = (ListView) getView().findViewById(R.id.list_view);
        list_view_btn.setAdapter(myAdapterFromBase);

        list_view_btn.setOnItemClickListener(new OnItemClickHandler());
    }
    private void initFoods() {
        Food apple = new Food("老醋花生",30.0,R.drawable.laocuhuasheng);
        foodList.add(apple);
        Food banana = new Food("水晶肘子", 40,R.drawable.shuijingzhouzi);
        foodList.add(banana);
        Food orange = new Food("手撕兔肉", 30,R.drawable.shousiturou);
        foodList.add(orange);
        Food watermelon = new Food("老醋蜇头",50,R.drawable.laocuzhetou);
        foodList.add(watermelon);
        Food grape = new Food("口水鸡",80,R.drawable.koushuiji);
        foodList.add(grape);
        Food pineapple = new Food("五香牛肉", 46,R.drawable.wuxiangniurou);
        foodList.add(pineapple);
        Food strawberry = new Food("菊花豆腐",20,R.drawable.jvhuadoufu);
        foodList.add(strawberry);
        Food cherry = new Food("美极螺头",50,R.drawable.meijiluotou);
        foodList.add(cherry);
        Food mango = new Food("酱香肘花", 60,R.drawable.jiangxiangzhouhua);
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
//                Intent intent = new Intent("action.text");
//                intent.putExtra("text",pricesum);
//                sendBroadcast(intent);

            String show = "点菜成功";
//            Intent intent = new Intent(getContext(), FoodOrderView.class);
//                intent.putExtra("FoodOrder", (Serializable) orderfoodList);
//                startActivity(intent);

            Toast.makeText(getContext(), show, Toast.LENGTH_SHORT).show();
            btn.setText("退点");
        }
    };
}
