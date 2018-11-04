package es.source.code.activity.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.source.code.activity.Adapter.MyGridViewAdapter;
import es.source.code.activity.Adapter.MyViewPagerAdapter;
import es.source.code.activity.Bean.OrderFood;
import es.source.code.activity.Bean.ProductListBean;
import es.source.code.activity.R;
import es.source.code.activity.model.User;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private Context context;

    private ViewGroup points;//小圆点指示器
    private ViewPager viewPager;
    private int totalPage;//总的页数
    private int mPageSize = 8;//每页显示的最大数量
    private List<ProductListBean> listDatas;//总的数据源
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    private int currentPage;//当前页
    private List<OrderFood> orderFoods;

    private String[] proName = {"点菜", "查看订单", "登陆/注册", "系统帮助"};
    private int[] img = {R.drawable.diancai, R.drawable.chankan, R.drawable.login, R.drawable.help};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDate();
        //初始化控件
        iniViews();
        //模拟数据源
        setDatas();

        orderFoods = new ArrayList<OrderFood>();
        LayoutInflater inflater = LayoutInflater.from(this);
        //总的页数，取整（这里有三种类型：Math.ceil(3.5)=4:向上取整，只要有小数都+1  Math.floor(3.5)=3：向下取整  Math.round(3.5)=4:四舍五入）
        totalPage = (int) Math.ceil(listDatas.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<>();
        for (int i = 0; i < totalPage; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) inflater.inflate(R.layout.gridview, viewPager, false);
            MyGridViewAdapter myGridViewAdapter = new MyGridViewAdapter(this, listDatas, i, mPageSize);
            gridView.setAdapter(myGridViewAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int i = img[position];
                    if (i == R.drawable.diancai) {    //点菜
                        Intent intent = new Intent(MainActivity.this, FoodView.class);
                        intent.putExtra("user", "user");
                        MainActivity.this.startActivityForResult(intent, 0);
                    }
                    if (i == R.drawable.chankan) {   //订单
                        Intent intent = new Intent(MainActivity.this, FoodOrderView.class);
                        intent.putExtra("FoodOrder", (Serializable) orderFoods);
                        MainActivity.this.startActivityForResult(intent, 0);
                    }
                    if (i == R.drawable.login) {//登录
                        Intent intent = new Intent(MainActivity.this, LoginOrRegister.class);
                        MainActivity.this.startActivityForResult(intent, 0);
                    }
                    if (i == R.drawable.help) {  //系统帮助
                        Intent intent = new Intent(MainActivity.this, SCOSHelper.class);
                        MainActivity.this.startActivityForResult(intent, 0);
                    }
                }

            });
            //每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList.add(gridView);
        }

        //设置ViewPager适配器
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(viewPagerList);

        viewPager.setAdapter(myViewPagerAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data){
        super.onActivityResult(requestCode,resultcode,data);
        switch(resultcode){
            case 0:
                if(data.getSerializableExtra("FoodOrder")!= null){
                    orderFoods = (List<OrderFood>) data.getSerializableExtra("FoodOrder");
                }
                break;
            default:
                break;
        }
    }

    private void iniViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //初始化小圆点指示器
        points = (ViewGroup) findViewById(R.id.points);
    }

    private void setDatas() {
        listDatas = new ArrayList<>();
        for (int i = 0; i < proName.length; i++) {
            listDatas.add(new ProductListBean(proName[i], img[i]));
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        switch (img[position]) {
            case R.drawable.diancai:

                break;
        }
    }

    User user = new User();

    private void initDate() {
        Intent intent = getIntent();
        //SCOSEntry跳到MainActivity
        String data = intent.getStringExtra("message");
        if (data.equals("RegisterSuccess")) {
            Context context = getApplicationContext();
            Toast.makeText(context, "欢迎您成为 SCOS 新用户 ", Toast.LENGTH_SHORT).show();

        }
    }
}
