package es.source.code.activity.br;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.Service;

import es.source.code.activity.service.ServerObserverService;
import es.source.code.activity.service.UpdateService;

public class DeviceStartedListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context,Intent intent){
        String action = intent.getAction();
        if(action.equals(intent.getAction())){
            Intent serviceIntent = new Intent(context, UpdateService.class);
            context.startService(serviceIntent);
        }
        else {
            NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context
                    .NOTIFICATION_SERVICE);
            notifyManager.cancel(intent.getIntExtra("uhiu", 0));
        }
    }
}