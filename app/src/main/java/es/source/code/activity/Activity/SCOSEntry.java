package es.source.code.activity.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import es.source.code.activity.R;

public class SCOSEntry extends ActionBarActivity {
    private static final int MIN_DISTANCE = 100; // 最小滑动距离
    private static final int MIN_VELOCITY = 10; // 最小滑动速度
    GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);

        mGestureDetector = new GestureDetector(this, mGestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }


    GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x = e1.getX() - e2.getX();
            if (x > MIN_DISTANCE && Math.abs(velocityX) > MIN_VELOCITY) {

                Intent intent = new Intent();
                intent.setClass(SCOSEntry.this, MainActivity.class);
                intent.putExtra("message", "FromEntry");
                SCOSEntry.this.startActivity(intent);
                finish();
            }
            return false;

        }

    };
}
