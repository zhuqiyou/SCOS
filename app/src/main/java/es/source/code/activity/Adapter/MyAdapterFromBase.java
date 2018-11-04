package es.source.code.activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import es.source.code.activity.Bean.Food;
import es.source.code.activity.R;

/**
 * Created by taoye on 2018/10/3.
 */
public class MyAdapterFromBase extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Food> mDatas;
    private View.OnClickListener mOnClickListener;

    public MyAdapterFromBase(Context context,List<Food> datas,View.OnClickListener onClickListener){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mDatas = datas;
        mOnClickListener = onClickListener;
    }

    @Override
    public int getCount(){
        return (mDatas != null?mDatas.size():0);
    }

    @Override
    public Object getItem(int position){
        return (mDatas != null?mDatas.get(position):null);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.food_item, null);

            holder = new ViewHolder();

            holder.img = (ImageView) convertView.findViewById(R.id.iv_item_food);
            holder.title = (TextView) convertView.findViewById(R.id.tv_name);
            holder.price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.number = (TextView) convertView.findViewById(R.id.tv_number);
            holder.btn = (Button) convertView.findViewById(R.id.btn_order);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Food f = mDatas.get(position);
        if(f != null){
            holder.img.setImageResource(f.getImg());
            holder.title.setText(f.getFoodName());
            holder.price.setText(Double.toString(f.getFoodPrice()));
            holder.number.setText(Double.toString(f.getNumber()));
            // 通常将position设置为tag，方便之后判断点击的button是哪一个
            holder.btn.setTag(position);
            holder.btn.setOnClickListener(this.mOnClickListener);
        }
        return convertView;
    }

    static class ViewHolder{
        ImageView img;
        TextView title;
        TextView price;
        TextView number;
        Button btn;
    }
}
