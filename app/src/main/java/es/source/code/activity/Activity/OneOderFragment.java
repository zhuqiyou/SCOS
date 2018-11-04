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
import es.source.code.activity.Bean.OrderFood;
import es.source.code.activity.R;


public class OneOderFragment extends Fragment {
    private TextView tv_show;
    private ListView list_view,list_view_btn;
    private List<OrderFood> orderfoodList = new ArrayList<OrderFood>();

    public static OneOderFragment newInstance(List<OrderFood> orderfood){
        OneOderFragment oneOderFragment = new OneOderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderfoodlist", (Serializable) orderfood);
        oneOderFragment.setArguments(bundle);
        return oneOderFragment;
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
        OrderFoodAdapter adapter = new OrderFoodAdapter(getContext(), R.layout.order_food_item, orderfoodList);
        ListView listView = (ListView) getView().findViewById(R.id.list_order_view);
        listView.setAdapter(adapter);
    }
    private void initFoods() {
        if(getArguments() != null){
            orderfoodList = (List<OrderFood>)getArguments().getSerializable("orderfoodlist");
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            Button btn = (Button) view;
            int pos = (Integer) btn.getTag();
            String fName = orderfoodList.get(pos).getFood().getFoodName();
            String fPrice = Double.toString(orderfoodList.get(pos).getFood().getFoodPrice());
            String show = "退点成功";
            Toast.makeText(getContext(), show, Toast.LENGTH_SHORT).show();
        }
    };
}
