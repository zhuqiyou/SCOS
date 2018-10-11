package es.source.code.activity.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class myFragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> list;
    String[] title;

    public myFragmentAdapter(FragmentManager fm, String[] title){
        super(fm);
        list = new ArrayList<>();
        this.title = title;
    }

    public myFragmentAdapter(FragmentManager fm, String[] title, List<Fragment> list){
        super(fm);
        this.list = list;
        this.title = title;
    }

    public void add(Fragment fragment){
        list.add(fragment);
    }
    @Override
    public Fragment getItem(int position){
        return list.get(position);
    }

    @Override
    public int getCount(){
        return list != null?list.size():0;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return title != null?title[position]:null;
    }

    @Override
    public void destroyItem(ViewGroup container,int position,Object object){};
}