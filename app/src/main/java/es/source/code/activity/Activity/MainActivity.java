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

import java.util.ArrayList;
import java.util.List;

import es.source.code.activity.Adapter.MyGridViewAdapter;
import es.source.code.activity.Adapter.MyViewPagerAdapter;
import es.source.code.activity.Bean.ProductListBean;
import es.source.code.activity.R;
import es.source.code.activity.model.User;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private Context context;

    private ViewGroup points;//小圆点指示器
    //private ImageView[] ivPoints;//小圆点图片集合
    private ViewPager viewPager;
    private int totalPage;//总的页数
    private int mPageSize = 8;//每页显示的最大数量
    private List<ProductListBean> listDatas;//总的数据源
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    private int currentPage;//当前页

    private String[] proName = {"点菜", "查看订单", "登陆/注册", "系统帮助"};
    private int[] img = {R.drawable.diancai, R.drawable.login, R.drawable.chankan, R.drawable.help};


//    private Button btn_Mlogin;
//    Button btn_diancai, btn_chakan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
        initDate();
//        btn_Mlogin = (Button) findViewById(R.id.button5);
//        btn_Mlogin.setOnClickListener(this);

        //初始化控件
        iniViews();
        //模拟数据源
        setDatas();
        LayoutInflater inflater = LayoutInflater.from(this);
        //总的页数，取整（这里有三种类型：Math.ceil(3.5)=4:向上取整，只要有小数都+1  Math.floor(3.5)=3：向下取整  Math.round(3.5)=4:四舍五入）
        totalPage = (int) Math.ceil(listDatas.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<>();
        for (int i = 0; i < totalPage; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) inflater.inflate(R.layout.gridview, viewPager, false);
            gridView.setAdapter(new MyGridViewAdapter(this, listDatas, i, mPageSize));
            //每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList.add(gridView);
        }
        //设置ViewPager适配器
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(viewPagerList);

        viewPager.setAdapter(myViewPagerAdapter);
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
//        if (data.equals("FromEntry")) {
//            btn_diancai =(Button) findViewById(img[0]);
//            btn_diancai.setVisibility(View.VISIBLE);
//            btn_chakan = (Button) findViewById(img[1]);
//            btn_chakan.setVisibility(View.VISIBLE);
//        }
//        if (!(data.equals("FromEntry"))) {
//            btn_diancai = (Button) findViewById(img[0]);
//            btn_diancai.setVisibility(View.INVISIBLE);
//            btn_chakan = (Button) findViewById(img[1]);
//            btn_chakan.setVisibility(View.INVISIBLE);
//        }
//        if (data.equals("LoginSuccess")) {
//            btn_diancai = (Button) findViewById(img[0]);
//            btn_diancai.setVisibility(View.VISIBLE);
//            btn_chakan = (Button) findViewById(img[1]);
//            btn_chakan.setVisibility(View.VISIBLE);
//
//        }
        if (data.equals("RegisterSuccess")) {
            Context context = getApplicationContext();
            Toast.makeText(context, "欢迎您成为 SCOS 新用户 ", Toast.LENGTH_SHORT).show();

        }
//        //LoginOrRegister跳到MainActivity
////        String data1 = intent.getStringExtra("message");
////        if(data1 == ""){}
////        else if(data)
//    }
    }
}
