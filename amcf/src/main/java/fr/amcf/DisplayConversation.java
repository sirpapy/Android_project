package fr.amcf;

import android.app.Activity;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import fr.amcf.contactview.Contact;
import fr.amcf.direct_chat_package.ChatAdapter;
import fr.amcf.direct_chat_package.ChatMessage;
import fr.amcf.message.Message;
import fr.amcf.message.MessageAdapter;
import fr.amcf.message.MessageType;

/**
 * Created by frech on 22/02/2017.
 */

public class DisplayConversation extends AppCompatActivity {

    private EditText messageEdit;
    private ListView messageContainer;
    private Button sendBtn;
    boolean isMine = true;
    private ArrayAdapter<Message> adapter;
    private ArrayList<Message> messageHistory;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_conversation);

        /*Bundle b = getIntent().getExtras();
        contact = (Contact) b.get("contact");*/

        messageContainer = (ListView) findViewById(R.id.messageContainer);
        messageEdit = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.sendButton);
        messageHistory = new ArrayList<>();
        adapter = new MessageAdapter(this,R.layout.item_chat_left,messageHistory);
        messageContainer.setAdapter(adapter);

        if(savedInstanceState != null){
            messageEdit.setText(savedInstanceState.getString("messageToSend"));
           // contact = (Contact) savedInstanceState.get("contact");
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messageEdit.getText().toString().trim().equals("")){
                    Toast.makeText(DisplayConversation.this, "Please input some text...", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                sendSMS();
                Message msg = new Message(messageEdit.getText().toString(),MessageType.SMS,System.currentTimeMillis());
                messageHistory.add(msg);
                adapter.notifyDataSetChanged();
                messageEdit.setText("");
                if (isMine) {
                    isMine = false;
                } else {
                    isMine = true;
                }
            }
        });
    }

    public void goBack(View v) {
        this.finish();
    }



    protected void sendSMS() {
        String toPhoneNumber = contact.getPrimaryPhoneNumber();
        String smsMessage = messageEdit.getText().toString();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);
            Toast.makeText(getApplicationContext(),"SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Sending SMS failed."+e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("messageToSend",messageEdit.getText().toString());
    }

}
