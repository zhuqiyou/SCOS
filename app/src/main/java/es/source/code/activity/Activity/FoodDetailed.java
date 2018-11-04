package es.source.code.activity.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.List;
import es.source.code.activity.Bean.Food;
import es.source.code.activity.R;

public class FoodDetailed extends AppCompatActivity {
    GestureDetector mGestureDetector;
    private static final int MIN_DISTANCE = 100; // 最小滑动距离
    private static final int MIN_VELOCITY = 10; // 最小滑动速度

    private List<Food> foodList;
    private  int index;
    private Food food;
    private EditText et_name;
    private EditText et_price;

    private ImageView iv_food;
    private  int img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detailed);
        et_name = (EditText) findViewById(R.id.food_item_name);
        iv_food = (ImageView) findViewById(R.id.imageView);
        et_price=(EditText) findViewById(R.id.food_item_price);

        Intent intent = getIntent();
        foodList = (List<Food>) intent.getSerializableExtra("foodlist");
        index = intent.getIntExtra("index", 0);

        food = foodList.get(index);
        String name = food.getFoodName();
        et_name.setText(name);
        Double price =food.getFoodPrice();
        et_price.setText(Double.toString(price));

        img =food.getImg();
        if(img != 0){
            iv_food.setImageResource(img);
        }else{
            iv_food.setImageResource(R.mipmap.ic_launcher);
        }


        GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {

            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float x = e1.getX() - e2.getX();
                float x1 = e2.getX() - e1.getX();
                if (x > MIN_DISTANCE && Math.abs(velocityX) > MIN_VELOCITY) {
                    if (index<=foodList.size()-1) {
                        index++;
                        food = foodList.get(index);
                        String name = food.getFoodName();
                        et_name.setText(name);
                        Double price = food.getFoodPrice();
                        et_price.setText(Double.toString(price));
                        img =food.getImg();
//                        et_img.(Integer.toString(img));
                        if(img != 0){
                            iv_food.setImageResource(img);
                        }else{
                            iv_food.setImageResource(R.mipmap.ic_launcher);
                        }


                    }
                    else {
                        Toast.makeText(FoodDetailed.this, "已是最后一个",Toast.LENGTH_SHORT).show();
                    }
                }
                if (x1 > MIN_DISTANCE && Math.abs(velocityX) > MIN_VELOCITY) {
                    if (index > 0) {
                        index--;
                        food = foodList.get(index);
                        String name = food.getFoodName();
                        et_name.setText(name);
                        Double price = food.getFoodPrice();
                        et_price.setText(Double.toString(price));
                        img =food.getImg();
                        if(img != 0){
                            iv_food.setImageResource(img);
                        }else{
                            iv_food.setImageResource(R.mipmap.ic_launcher);
                        }
                    }
                    else {
                        Toast.makeText(FoodDetailed.this, "已是第一个",Toast.LENGTH_SHORT).show();
                    }
                }
                return false;

            }

        };

        mGestureDetector = new GestureDetector(this, mGestureListener);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);

    }
}
