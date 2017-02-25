package fr.amcf;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import fr.amcf.contactdata.ContactProviders;
import fr.amcf.contactview.Contact;
import fr.amcf.message.Message;
import fr.amcf.message.MessageType;

/**
 * Created by Sirpapy on 29/01/2017.
 */
public class IncomingSmsReceiver extends BroadcastReceiver {
    final SmsManager sms = SmsManager.getDefault();
    NotificationActivity notificationActivity;
    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieves a map of extended data from the intent.
        notificationActivity = new NotificationActivity();
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                    Toast.makeText(context, "SmsReceiver senderNum: " + senderNum + "; message: " + message, Toast.LENGTH_LONG).show();
                    createNotification(context,message);
                    Intent smsServiceIntent = new Intent();
                    smsServiceIntent.putExtra("message",message);
                    Contact c = ContactProviders.getByPhoneNumber(senderNum);
                    c = new Contact.Builder(c).addMessage(new Message(message, MessageType.SMS)).build();

                    // Show alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "senderNum: " + senderNum + ", message: " + message, duration);
                    toast.show();

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }

    }
    public void createNotification(Context context, String message){
        // Set Notification Title
        String strtitle = "NEW SMS";
        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(context, NotificationActivity.class);
        // Send data to NotificationView Class
        intent.putExtra("title", strtitle);
        intent.putExtra("text", message);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context)
                // Set Icon
                .setSmallIcon(R.drawable.notification)
                // Set Ticker Message
                .setTicker(message)
                // Set Title
                .setContentTitle("New SMS")
                // Set Text
                .setContentText(message)
                // Add an Action Button below Notification
                .addAction(R.drawable.notification, "Action Button", pIntent)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Dismiss Notification
                .setAutoCancel(true);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());
    }



}
