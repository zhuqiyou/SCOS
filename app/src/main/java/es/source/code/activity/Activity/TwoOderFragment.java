package es.source.code.activity.Activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
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
    private Button btn_zhangdan;


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
        btn_zhangdan=(Button)getView().findViewById(R.id.btn_foodorder_view_order);
        btn_zhangdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyClass().execute();
//                Toast.makeText(getContext(), "积分增加", Toast.LENGTH_SHORT).show();
            }

        });
        initFoods();
         // 初始化菜单数据
        OrderFoodAdapter adapter = new OrderFoodAdapter(getContext(), R.layout.order_food_item, orderfoodList);
        ListView listView = (ListView) getView().findViewById(R.id.list_order_view);
        listView.setAdapter(adapter);
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
        Food orange = new Food("手撕兔肉", 30);
        orderfoodList.add(new OrderFood(orange));
        Food huashneg = new Food("老醋花生", 30);
        orderfoodList.add(new OrderFood(huashneg));
        Food zhouzi = new Food("水晶肘子", 40);
        orderfoodList.add(new OrderFood(zhouzi));
        Food koushuiji = new Food("口水鸡", 80);
        orderfoodList.add(new OrderFood(koushuiji));
        Food yu = new Food("水煮鱼", 56);
        orderfoodList.add(new OrderFood(yu));
        Food dachang = new Food("拉炒大肠", 123);
        orderfoodList.add(new OrderFood(dachang));
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

    class  MyClass extends AsyncTask<String,Integer,Void> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }
        @Override
        protected Void doInBackground(String... strings) {
            SystemClock.sleep(6000);
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Toast.makeText(getContext(), "积分增加", Toast.LENGTH_SHORT).show();
        }
    }

}
