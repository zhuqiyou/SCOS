package es.source.code.activity.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import es.source.code.activity.Activity.FoodDetailed;
import es.source.code.activity.Bean.Food;
import es.source.code.activity.R;

public class UpdateService extends IntentService {
    private final static String TAG = "UpdateService";
    private final static int REQUEST_CODE = 0; // 通知的请求码
    private final static int NOTIFICATION_ID = 1; // 通知的ID

    private final Food food = new Food(R.drawable.wuxiangniurou, "淡化玉米羹",67.0,43 );

    // IntentService的子类的构造方法不能有参数，而且必须调用弗雷德构造方法
    public UpdateService(){
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        while(true){
            // Math.random()*(100-1+1) 表示产生1到100的int型随机数
            int flag = (int)(Math.random() * (100 - 1 + 1));
            Log.i(TAG, "-" + flag);
            if(flag > 97){ // 产生随机数，模拟更新通知
                notifyUpdate();
            }
            try{
                Thread.sleep(500);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送通知
     */
    private void notifyUpdate(){
        Notification.Builder notificationBuilder = new Notification.Builder(UpdateService.this);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = notificationBuilder.build();

        notificationBuilder.setContentTitle("菜品更新");
        notificationBuilder.setContentText("新品上架：" + food.getFoodName() + ",价格：" + food.getFoodPrice() + "备注：" + "描述描述");
        notificationBuilder.setSmallIcon(R.drawable.chankan);
        notificationBuilder.setWhen(System.currentTimeMillis());

        // 通知的点击响应事件，自动撤销
        Intent intent = new Intent(UpdateService.this, FoodDetailed.class);

        // 将菜品对象传递给FoodDetailed
        intent.putExtra("binderdata", food);

        PendingIntent nextPage_pi = PendingIntent.getActivity(UpdateService.this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(nextPage_pi);
        notificationBuilder.setAutoCancel(true);

        notification.defaults = Notification.DEFAULT_VIBRATE;

        notificationManager.notify(NOTIFICATION_ID, notification); // 通知ID相同，则会更新通知，而不是产生新通知
    }
}
