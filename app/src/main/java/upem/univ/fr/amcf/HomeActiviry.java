package upem.univ.fr.amcf;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;


public class HomeActiviry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activiry);
        FacebookSdk.setApplicationId("1046614502109093");
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public void startSendSMSActivity(View v) {
        startActivity(new Intent(HomeActiviry.this, SMSSender.class));
    }

    public void startListSMSActivity(View v) {
        startActivity(new Intent(HomeActiviry.this, SMSList.class));
    }

    public void goBackToLoginScreen(View v) {
        Intent intent = new Intent(this, LoginFacebookActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void goToDirectChat(View v) {
        Intent intent = new Intent(this, DirectCommunicationAcitivty.class);
        startActivity(intent);
    }

    public void logoutFacebookButtonClick (View v){
        LoginManager.getInstance().logOut();
    }

    public void openFacebookChat(View v){
//        startActivity(new Intent(HomeActiviry.this, FacebookChat.class));
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/{conversation-id}/messages",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                        Toast.makeText(HomeActiviry.this, response.getJSONArray().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ).executeAsync();
    }
}
