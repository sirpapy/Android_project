package fr.amcf;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.List;

import fr.amcf.contactdata.ContactProviders;
import fr.amcf.contactview.Contact;
import fr.amcf.contactview.ContactActivity;
import fr.amcf.integration.facebook.LoginFacebookActivity;

import static fr.amcf.R.id.fab;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CallbackManager callbackManager;
    private PendingAction pendingAction = PendingAction.NONE;
    private FloatingActionButton actionButtonSendSMS;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initSendSMSActionButton();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
            }

            @Override
            public void onError(FacebookException exception) {
                if (pendingAction != PendingAction.NONE
                        && exception instanceof FacebookAuthorizationException) {
                    showAlert();
                    pendingAction = PendingAction.NONE;
                }
            }

            private void showAlert() {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.cancelled)
                        .setMessage(R.string.permission_not_granted)
                        .setPositiveButton(R.string.ok, null)
                        .show();
            }

        });

        /*List<Contact> contacts = ContactProviders.getLastUseContacts();
        ListView mListView = (ListView) findViewById(R.id.lastContactContainer);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.activity_main,contacts);
        mListView.setAdapter(adapter);*/


    }

    private void initSendSMSActionButton() {
        actionButtonSendSMS = (FloatingActionButton) findViewById(fab);
        actionButtonSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SMSSender.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_contact_list:
                startActivity(new Intent(this, ContactActivity.class));
                break;
            case R.id.nav_sms_list:
                startActivity(new Intent(MainActivity.this, SMSList.class));
                break;
            case R.id.nav_display_conversation:
                startActivity(new Intent(MainActivity.this, DisplayConversation.class));
                break;
            case R.id.nav_facebook_log:
                startActivity(new Intent(MainActivity.this, LoginFacebookActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }
}
