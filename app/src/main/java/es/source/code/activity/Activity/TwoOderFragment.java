package es.source.code.activity.Activity;

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

import es.source.code.activity.Adapter.MyOrderAdapterFromBase;
import es.source.code.activity.Adapter.OrderFoodAdapter;
import es.source.code.activity.Bean.Food;
import es.source.code.activity.Bean.OrderFood;
import es.source.code.activity.R;

public class TwoOderFragment extends Fragment {
    private TextView tv_show;
    private ListView list_view,list_view_btn;
    private List<OrderFood> orderfoodList = new ArrayList<OrderFood>();
    private List<Food> foodList = new ArrayList<Food>();


    public static TwoOderFragment newInstance(List<OrderFood> orderfood){
        TwoOderFragment twoOderFragment = new TwoOderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderfoodlist", (Serializable) orderfood);
        twoOderFragment.setArguments(bundle);
        return twoOderFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container ,Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_foodorder_view,container,false);
    }

    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        initFoods();   // 初始化菜单数据
//        OrderFoodAdapter adapter = new OrderFoodAdapter(getContext(), R.layout.order_food_item, orderfoodList);
//        ListView listView = (ListView) getView().findViewById(R.id.list_order_view);
//        listView.setAdapter(adapter);
       // 初始化按钮
        MyOrderAdapterFromBase myAdapterFromBase = new MyOrderAdapterFromBase(getContext(),orderfoodList,onClickListener);
        ListView list_view_btn = (ListView) getView().findViewById(R.id.list_order_view);
        list_view_btn.setAdapter(myAdapterFromBase);

        list_view_btn.setOnItemClickListener(new OnItemClickHandler());
    }
    private void initFoods() {
        if(getArguments() != null){
            orderfoodList = (List<OrderFood>)getArguments().getSerializable("orderfoodlist");
        }
//        OrderFood apple = new OrderFood("Apple", 80,2,"无");
//        orderfoodList.add(apple);
//        OrderFood banana = new OrderFood("Apple", 80,2,"无");
//        orderfoodList.add(banana);
//        Food orange = new Food("手撕兔肉", 30);
//        foodList.add(orange);
//        Food watermelon = new Food("老醋蜇头",50);
//        foodList.add(watermelon);
//        Food pear = new Food("日式鹅肝",34);
//        foodList.add(pear);
//        Food grape = new Food("口水鸡",80);
//        foodList.add(grape);
//        Food pineapple = new Food("五香牛肉", 46);
//        foodList.add(pineapple);
//        Food strawberry = new Food("菊花豆腐",20);
//        foodList.add(strawberry);
//        Food cherry = new Food("美极螺头",50);
//        foodList.add(cherry);
//        Food mango = new Food("酱香肘花", 60);
//        foodList.add(mango);
//        Food pijiu = new Food("雪花",34);
//        foodList.add(pijiu);
    }


    private class OnItemClickHandler implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent,View view,int position,long id){
            Toast.makeText(getContext(), "点菜成功", Toast.LENGTH_SHORT).show();
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
//           /if_handleFood.addFood(foodList.get(pos));

            String show = "点菜成功";

            Toast.makeText(getContext(), show, Toast.LENGTH_SHORT).show();
        }
    };

}
