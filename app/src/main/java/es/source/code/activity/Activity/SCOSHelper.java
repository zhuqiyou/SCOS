package es.source.code.activity.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.List;
import es.source.code.activity.Adapter.HelpGridviewAdapter;
import es.source.code.activity.Adapter.HelpViewPageAdapter;
import es.source.code.activity.Bean.HelpListBean;
import es.source.code.activity.R;

public class SCOSHelper extends AppCompatActivity {
    private Button btn_phone;

    private ViewGroup points;//小圆点指示器
    private ViewPager viewPager;
    private int totalPage;//总的页数
    private int mPageSize = 8;//每页显示的最大数量
    private List<HelpListBean> listDatas;//总的数据源
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    private int currentPage;//当前页

    private String[] HelpName = {"用户使用协议","关于系统","电话人工帮助","短信帮助","邮件帮助"};
    private int [] HelpImg = {R.drawable.usertp,R.drawable.syshelp,R.drawable.phone,R.drawable.massage, R.drawable.email};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoshelp_main);

        ActivityCompat.requestPermissions(SCOSHelper.this, new String[]{"android.permission.CALL_PHONE",}, 1);
        //初始化控件
        iniViews();
        //模拟数据源
        setDatas();
        LayoutInflater inflater = LayoutInflater.from(this);
        totalPage = (int) Math.ceil(listDatas.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<>();
        for(int i=0;i<totalPage;i++){
            //每个页面都是inflate出一个新实例
            GridView HelpGridView = (GridView) inflater.inflate(R.layout.help_gridview,viewPager,false);
            HelpGridviewAdapter HelpGridviewAdapter = new HelpGridviewAdapter(this, listDatas, i, mPageSize);
            //每一个GridView作为一个View对象添加到ViewPager集合中
            HelpGridView.setAdapter(HelpGridviewAdapter);
            HelpGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int i = HelpImg[position];
                    if (i == R.drawable.phone) {    //拨打电话
                        dialPhoneNumber("17865162882");
                    }
                    if (i == R.drawable.massage) {
                        Intent intent = new Intent();
                        intent.setClass(SCOSHelper.this,MassageActivity.class);
                        startActivity(intent);
                    }
                    if (i == R.drawable.email) {
                        Intent intent = new Intent();
                        intent.setClass(SCOSHelper.this,EmailActivity.class);
                        startActivity(intent);
                    }
                }

            });
            viewPagerList.add(HelpGridView);
        }
        //设置ViewPager适配器
        viewPager.setAdapter(new HelpViewPageAdapter(viewPagerList));
    }

    private void iniViews() {
        viewPager = (ViewPager) findViewById(R.id.help_viewPager);
        //初始化小圆点指示器
        points = (ViewGroup) findViewById(R.id.help_points);
    }

    private void setDatas() {
        listDatas = new ArrayList<>();
        for(int i=0;i<HelpName.length;i++){
            listDatas.add(new HelpListBean(HelpName[i],HelpImg[i]));
        }
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}