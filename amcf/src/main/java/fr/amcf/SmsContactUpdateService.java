package fr.amcf;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SmsContactUpdateService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String message;
        String body;
        message = intent.getStringExtra("message");
        body = intent.getStringExtra("body");
//        messages.add(new Message(message, MessageType.SMS));
        return super.onStartCommand(intent, flags, startId);
    }
}