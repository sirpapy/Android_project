package fr.amcf.message;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by frech on 25/02/2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    private Activity activity;
    private List<Message> messages;

    public MessageAdapter(Activity context,int resource,List<Message> messages ){
        super(context,resource,messages);
        this.activity = activity;
        this.messages = messages;
    }

    
}
