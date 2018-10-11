package es.source.code.activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import es.source.code.activity.Bean.Food;
import es.source.code.activity.R;

public class FoodAdapter extends ArrayAdapter {
    private final int resourceId;
    public FoodAdapter(Context context, int textViewResourceId, List<Food> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        Food f = (Food) getItem(position);
        View view;
        if(convertView == null){
//            使用Inflater对象来将布局文件解析成一个View
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        }else {
            view  = convertView;
        }
        TextView foodName = (TextView) view.findViewById(R.id.tv_name);
        TextView foodPrice = (TextView) view.findViewById(R.id.tv_price);
        foodName.setText(f.getFoodName());
        foodPrice.setText(Double.toString(f.getFoodPrice()));
        return view;
    }




}