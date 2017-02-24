package upem.univ.fr.amcf.direct_chat_package;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import upem.univ.fr.amcf.R;

/**
 * Created by Sirpapy on 17/02/2017.
 */

public class Chats extends Fragment implements View.OnClickListener {

    private EditText msg_edittext;
    private String user1 = "user1", user2 = "user2";
    private Random random;
    public static ArrayList<ChatMessage> chatlist;
    public static ChatAdapter chatAdapter;
    ListView msgListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_direct_communication_acitivty, container, false);
        random = new Random();
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Chats");
        msg_edittext = (EditText) view.findViewById(R.id.messageEditText);
        msgListView = (ListView) view.findViewById(R.id.msgListView);
        ImageButton sendButton = (ImageButton) view.findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(this);

        // ----Set autoscroll of listview when a new message arrives----//
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        chatlist = new ArrayList<>();
        chatAdapter = new ChatAdapter(getActivity(), chatlist);
        msgListView.setAdapter(chatAdapter);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    public void sendTextMessage(View v) {
        System.out.println("SEND MESSAGE");
        String message = msg_edittext.getEditableText().toString();
        if (!message.equalsIgnoreCase("")) {
            final ChatMessage chatMessage = new ChatMessage(user1, user2, message, "" + random.nextInt(1000), true);
            chatMessage.setMsgID();
            chatMessage.body = message;
            chatMessage.Date = CommonMethods.getCurrentDate();
            chatMessage.Time = CommonMethods.getCurrentTime();
            msg_edittext.setText("");
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this.getContext(), "HELLO", Toast.LENGTH_SHORT);
         sendTextMessage(v);
    }


}
