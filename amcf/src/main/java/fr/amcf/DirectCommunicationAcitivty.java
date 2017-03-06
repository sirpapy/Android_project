package fr.amcf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

import fr.amcf.directchat.ChatAdapter;
import fr.amcf.directchat.ChatMessage;
import fr.amcf.directchat.CommonMethods;
import fr.amcf.directchat.Serveur;
import fr.amcf.directchat.Utils;


public class DirectCommunicationAcitivty extends AppCompatActivity {
    private EditText msg_edittext;
    private String user1 = "user1", user2 = "user2";
    private Random random;
    public static ArrayList<ChatMessage> chatlist;
    public static ChatAdapter chatAdapter;
    ListView msgListView;
    EditText editTextAddress, editTextPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_communication_acitivty);
//        new Chats();
        random = new Random();
        msg_edittext = (EditText) findViewById(R.id.messageEditText);
        msgListView = (ListView) findViewById(R.id.msgListView);
        ImageButton sendButton = (ImageButton) findViewById(R.id.sendMessageButton);

        // ----Set autoscroll of listview when a new message arrives----//
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        chatlist = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, chatlist);
        msgListView.setAdapter(chatAdapter);
        editTextAddress = (EditText) findViewById(R.id.ipEditText);
        editTextAddress.setText(Utils.getIPAddress(true));
        editTextPort = (EditText) findViewById(R.id.portEditText);


        Intent startIntent = new Intent(this, Serveur.class);
        startService(startIntent);

    }


    public void sendTextMessage(View v) {
        String message = msg_edittext.getEditableText().toString();
        if (!message.equalsIgnoreCase("")) {
            ChatMessage chatMessage = new ChatMessage(user1, user2, message, "" + random.nextInt(1000), true);
            chatMessage.setMsgID();
            chatMessage.body = message;
            chatMessage.Date = CommonMethods.getCurrentDate();
            chatMessage.Time = CommonMethods.getCurrentTime();


//            Client myClient = new Client(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), msg_edittext);
//            myClient.execute();


            msg_edittext.setText("");
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();

        }
    }

    public void setReponse(String msg){
        ChatMessage chatMessage = new ChatMessage(user1, user2, msg, "" + random.nextInt(1000), true);
        chatAdapter.add(chatMessage);
        chatAdapter.notifyDataSetChanged();
    }


}
