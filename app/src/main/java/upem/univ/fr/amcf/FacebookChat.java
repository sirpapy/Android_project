package upem.univ.fr.amcf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.messenger.MessengerUtils;
import com.facebook.messenger.MessengerThreadParams;
import com.facebook.messenger.ShareToMessengerParams;
public class FacebookChat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_chat);
    }
}
