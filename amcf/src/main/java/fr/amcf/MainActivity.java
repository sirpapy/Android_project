package fr.amcf;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import fr.amcf.contactdata.ContactProviders;
import fr.amcf.contactview.ContactActivity;
import fr.amcf.integration.facebook.LoginFacebookActivity;


public class MainActivity extends Activity {

    private CallbackManager callbackManager;
    private PendingAction pendingAction = PendingAction.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppEventsLogger.activateApp(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
            }
        } else {
            ContactProviders.initContacts(this);
        }

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult result) {
            }

            @Override
            public void onCancel() {
                if (pendingAction != PendingAction.NONE) {
                    showAlert();
                    pendingAction = PendingAction.NONE;
                }
                updateUI();
            }

            @Override
            public void onError(FacebookException exception) {
                if (pendingAction != PendingAction.NONE
                        && exception instanceof FacebookAuthorizationException) {
                    showAlert();
                    pendingAction = PendingAction.NONE;
                }
                updateUI();
            }

            private void showAlert() {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.cancelled)
                        .setMessage(R.string.permission_not_granted)
                        .setPositiveButton(R.string.ok, null)
                        .show();
            }

        });
    }

    private void updateUI() {
        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
//
//        postStatusUpdateButton.setEnabled(enableButtons || canPresentShareDialog);
//        postPhotoButton.setEnabled(enableButtons || canPresentShareDialogWithPhotos);
//
//        Profile profile = Profile.getCurrentProfile();
//        if (enableButtons && profile != null) {
//            profilePictureView.setProfileId(profile.getId());
//            greeting.setText(getString(R.string.hello_user, profile.getFirstName()));
//        } else {
//            profilePictureView.setProfileId(null);
//            greeting.setText(null);
//        }
    }

    public void startSendSMSActivity(View v) {
        startActivity(new Intent(MainActivity.this, SMSSender.class));
    }

    public void displayConversation(View v) {

    }

    public void startListSMSActivity(View v) {
        startActivity(new Intent(MainActivity.this, SMSList.class));
    }

    public void openNavigation(View v) {
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
    }

    public void goBackToLoginScreen(View v) {
        Intent intent = new Intent(this, LoginFacebookActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void logoutFacebookButtonClick(View v) {
        LoginManager.getInstance().logOut();
    }

    public void startContact(View view) {
        startActivity(new Intent(this, ContactActivity.class));
    }

    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }
}
