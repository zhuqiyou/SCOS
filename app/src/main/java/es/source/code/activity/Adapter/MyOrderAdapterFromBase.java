package es.source.code.activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import es.source.code.activity.Bean.OrderFood;
import es.source.code.activity.R;

/**
 * Created by taoye on 2018/10/3.
 */
public class MyOrderAdapterFromBase extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<OrderFood> mDatas;
    private View.OnClickListener mOnClickListener;

    public MyOrderAdapterFromBase(Context context,List<OrderFood> datas,View.OnClickListener onClickListener){
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
            convertView = mInflater.inflate(R.layout.order_food_item, null);

            holder = new ViewHolder();

            holder.title = (TextView) convertView.findViewById(R.id.tv_order_name);
            holder.price = (TextView) convertView.findViewById(R.id.tv_order_price);
            holder.btn = (Button) convertView.findViewById(R.id.btn_order);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        OrderFood f = mDatas.get(position);

        if(f != null){
            holder.title.setText(f.getFood().getFoodName());
            holder.price.setText(Double.toString(f.getFood().getFoodPrice()));
            // 通常将position设置为tag，方便之后判断点击的button是哪一个
            holder.btn.setTag(position);
            holder.btn.setOnClickListener(this.mOnClickListener);
        }

        return convertView;
    }

    static class ViewHolder{
        TextView title;
        TextView price;
        Button btn;
    }
}
