package es.source.code.activity.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import java.io.Serializable;

import es.source.code.activity.Bean.ReadJson;

public class ServerObserverService extends Service {

    private cMessengerHandler mHandler = new cMessengerHandler();
    private final Messenger mMessenger = new Messenger(mHandler); // 服务自己的信使
    private Messenger cMessenger; // 客户端的信使
    private boolean exit = false; // 判断是否退出子线程中的循环

    private Runnable runnable = new Runnable() { // 创建一个线程执行耗时任务
        @Override
        public void run() {
            int fileno  = 1;

            Message message = Message.obtain(); // 要发送的消息对象
            while(!exit){
                try{
                    Bundle bundle = new Bundle();

                    // 这段代码是为了模拟服务器实时更新效果
                    {
                    if(fileno == 0){
                        Log.i("啊啊", "嗯嗯");
                        bundle.putSerializable("Hotdishes",(Serializable) ReadJson.getFoodsFromJSON("update1.json", ServerObserverService.this));
                    }
                    else {
                        Log.i("嗯嗯","啊啊");
                        bundle.putSerializable("Hotdishes", (Serializable) ReadJson.getFoodsFromJSON("update2.json", ServerObserverService.this));
                    }
                    fileno = 1 - fileno; // 下一秒钟换一个Json文件读取
                }

                    message.what = 10;
                    message.setData(bundle);
                    // 使用客户端信使发送消息给客户端，由客户端的Handler对象接收
                    cMessenger.send(message);
                    Thread.sleep(3000); // 休眠1s，作业要求休眠300ms
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 客户端与服务端绑定成功后回调
     * RemoteService通过Messenger对象返回binder
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent){
        Log.i("服务", "已绑定");
        // 返回服务信使的binder，客户端通过binder得到服务端的信使
        return mMessenger.getBinder();
    }
    //解绑
    @Override
    public boolean onUnbind(Intent intent){
        return super.onUnbind(intent);
    }

    /**
     * 做资源的释放
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
        exit = true; // 退出子线程中的循环
        mHandler.removeCallbacksAndMessages(null); // Service销毁时，清除消息队列中未执行完的任务
    }

    // 自定义Handler
    private class cMessengerHandler extends Handler{

        @Override
        public void handleMessage(Message msg){
            cMessenger = msg.replyTo;
            switch(msg.what){
                case 0:
                    exit = true; // 退出子线程中的循环
                    break;
                case 1:
                    try {
                        exit = false;
                        new Thread(runnable).start(); // 开启线程
                        // 销毁线程
                        mHandler.removeCallbacks(runnable);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
