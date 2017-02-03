package upem.univ.fr.amcf;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActiviry extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activiry);
    }

    public void startSendSMSActivity(View v) {
        startActivity(new Intent(HomeActiviry.this, SMSSender.class));
    }

    public void startListSMSActivity(View v) {
        startActivity(new Intent(HomeActiviry.this, SMSList.class));
    }



}
