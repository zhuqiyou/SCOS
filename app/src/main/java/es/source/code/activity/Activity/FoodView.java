package es.source.code.activity.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.source.code.activity.Bean.Food;
import es.source.code.activity.Bean.OrderFood;
import es.source.code.activity.R;
import es.source.code.activity.interfaces.IF_HandleFood;

public class FoodView extends AppCompatActivity implements IF_HandleFood{
    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private List<OrderFood> orderfoodList = new ArrayList<OrderFood>();
    private TabLayout.Tab one,two,three,four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmain);

        initViews();
        initEvents();
    }

    @Override
     public void addFood(Food food){
        if(food!=null){
            orderfoodList.add(new OrderFood(food));
        }
    }

    //初始化布局
    private void initViews(){
        mTablayout = (TabLayout) findViewById(R.id.tl_main);
        mViewpager = (ViewPager) findViewById(R.id.vp_main);

        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            private String[] mTitles = {"冷菜","热菜","海鲜","酒水"};
            @Override
            public Fragment getItem(int position) {
                switch(position){
                    case 0:
                        return new OneFragment();
                    case 1:
                        return new TwoFragment();
                    case 2:
                        return new ThreeFragment();
                    default:
                        return new FourFragment();
                }
            }
            @Override
            public int getCount() {
                return mTitles.length;
            }
            @Override
            public CharSequence getPageTitle(int position){
                return mTitles[position];
            }
        });
        mTablayout.setupWithViewPager(mViewpager);
        one = mTablayout.getTabAt(0);
        two = mTablayout.getTabAt(1);
        three = mTablayout.getTabAt(2);
        four = mTablayout.getTabAt(3);
        one.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        two.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        three.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        four.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
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
                } else if (tab == mTablayout.getTabAt(2)) {
                    three.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                    mViewpager.setCurrentItem(2);
                } else if (tab == mTablayout.getTabAt(3)) {
                    four.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                    mViewpager.setCurrentItem(3);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == mTablayout.getTabAt(0)) {
                    one.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                } else if (tab == mTablayout.getTabAt(1)) {
                    two.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                } else if (tab == mTablayout.getTabAt(2)) {
                    three.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                } else if (tab == mTablayout.getTabAt(3)) {
                    four.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    //Actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_p:
                Intent intent = new Intent(FoodView.this, FoodOrderView.class);
                intent.putExtra("FoodOrder", (Serializable) orderfoodList);
                startActivity(intent);
//                Toast.makeText(this, "你点击了“已点菜品”按键！", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.write_p:
                Intent intent_order = new Intent(FoodView.this, FoodOrderView.class);
                intent_order.putExtra("FoodOrder", (Serializable) orderfoodList);
                startActivity(intent_order);
//                Toast.makeText(this, "你点击了“查看订单”按键！", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.favo_p:
                Toast.makeText(this, "你点击了“呼叫服务”按键！", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
