package es.source.code.activity.Activity;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import es.source.code.activity.Bean.Food;
import es.source.code.activity.Bean.OrderFood;
import es.source.code.activity.R;
import es.source.code.activity.interfaces.IF_HandleFood;
import es.source.code.activity.model.User;
import es.source.code.activity.service.ServerObserverService;

public class FoodView extends AppCompatActivity implements IF_HandleFood{
    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private List<OrderFood> orderfoodList = new ArrayList<OrderFood>();
    private TabLayout.Tab one,two,three,four;
    private Menu update;
    private boolean isBound = false;
    private Messenger mMessenger; // 自己的信使
    private Messenger rMessenger; //  远程服务的信使

    User user;
    private String dataBoundle;
//    private Button btn_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmain);
        Intent data = getIntent();
        dataBoundle = data.getStringExtra("user");
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
    public boolean onCreateOptionsMenu(Menu menu) {//onCreateOptionmenu()
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//onOptionItemselected
        switch (item.getItemId()) {
            case R.id.user_p://已点菜品
                Intent intent = new Intent(FoodView.this, FoodOrderView.class);
                intent.putExtra("FoodOrder", (Serializable) orderfoodList);
                startActivity(intent);
                break;
            case R.id.write_p://查看订单
                Intent intent_order = new Intent(FoodView.this, FoodOrderView.class);
                intent_order.putExtra("FoodOrder", (Serializable) orderfoodList);
                startActivity(intent_order);
                break;
            case R.id.menu_update:
                try {
                    invalidateOptionsMenu();
                    isStart = true;
                    Toast.makeText(this, "开始启动", Toast.LENGTH_SHORT).show();
                    Message message = Message.obtain();
                    message.what = 1;
                    message.replyTo = mMessenger;
                    rMessenger.send(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.menu_stop:
                try {
                    invalidateOptionsMenu();
                    isStart = false;
//                    Toast.makeText(this, "开始启动", Toast.LENGTH_SHORT).show();
                    Message message = Message.obtain();
                    message.what = 0;
                    message.replyTo = mMessenger;
                    rMessenger.send(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

        }return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra("FoodOrder",(Serializable)orderfoodList);
        setResult(0,intent);
        finish();
    }

    private Handler mHandler = new Handler() {//处理各种细节
        // 这里接收远程服务通过此Activity的信使发送过来的消息
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 10){
                Log.i("qq", "qq");
                Bundle bundle = msg.getData();
                List<Food> foodList = (List<Food>) bundle.getSerializable("Hotdishes");
                EventBus.getDefault().post(foodList);
            }
        }
    };

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent(FoodView.this, ServerObserverService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {//绑定
            Log.i("绑定", "连接建立");
            rMessenger = new Messenger(service);
            mMessenger = new Messenger(mHandler);
            isBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {//解绑
            Log.d("解绑", "连接断开");
            rMessenger = null;
            isBound = false;
        }
    };
    // 在onStop()方法中解绑
    @Override
    protected void onStop(){
        super.onStop();
        if(isBound){
            unbindService(serviceConnection);
            isBound = false;
        }
    }
    private boolean isStart = false;
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!isStart){
            menu.findItem(R.id.menu_update).setVisible(true);
            menu.findItem(R.id.menu_stop).setVisible(false);
        }else{
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_update).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
