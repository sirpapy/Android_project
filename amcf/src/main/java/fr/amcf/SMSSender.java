package fr.amcf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import upem.univ.fr.amcf.R;

public class SMSSender extends AppCompatActivity {
    EditText numberEditText,messageEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smssender);
        numberEditText = (EditText) findViewById(R.id.numberEditText);
        messageEditText = (EditText) findViewById(R.id.messageEditText);
    }

    public void goBack(View v) {
        this.finish();
    }


    public void sendSMSClick(View v) {
        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        sendSMS();
    }

    protected void sendSMS() {
        String toPhoneNumber = numberEditText.getText().toString();
        String smsMessage = messageEditText.getText().toString();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);
            Toast.makeText(getApplicationContext(),"SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Sending SMS failed."+e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
