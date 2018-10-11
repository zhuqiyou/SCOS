package es.source.code.activity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.source.code.activity.Adapter.myFragmentAdapter;
import es.source.code.activity.Bean.OrderFood;
import es.source.code.activity.R;

public class FoodOrderView extends AppCompatActivity {
    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private TabLayout.Tab one,two;
    private List<OrderFood> orderFood = new ArrayList<OrderFood>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order_view);

        initViews();
        initEvents();
    }
    //初始化布局
    private void initViews(){
        Intent intent = getIntent();

        if((List<OrderFood>) intent.getSerializableExtra("FoodOrder")!=null ){
            orderFood = (List<OrderFood>) intent.getSerializableExtra("FoodOrder");

        }

        mTablayout = (TabLayout) findViewById(R.id.order_tl_main);
        mViewpager = (ViewPager) findViewById(R.id.order_vp_main);
        String[] mTitles = {"已下菜单","未下菜单"};
        myFragmentAdapter myFragmentAdapter = new myFragmentAdapter(getSupportFragmentManager(),mTitles);
        myFragmentAdapter.add(OneOderFragment.newInstance(orderFood));
        myFragmentAdapter.add(new TwoOderFragment());
        mViewpager.setAdapter(myFragmentAdapter);


        mTablayout.setupWithViewPager(mViewpager);

        one = mTablayout.getTabAt(0);
        two = mTablayout.getTabAt(1);

        one.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        two.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));

    }

    private void initEvents() {
        // 添加这个tab选择事件只是为了切换图片。不添加也可以实现页面的滑动和tab的切换，但图片不换变化
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == mTablayout.getTabAt(0)) {
                    one.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                    mViewpager.setCurrentItem(0);
                } else if (tab == mTablayout.getTabAt(1)) {
                    two.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                    mViewpager.setCurrentItem(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == mTablayout.getTabAt(0)) {
                    one.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                } else if (tab == mTablayout.getTabAt(1)) {
                    two.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                }
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
