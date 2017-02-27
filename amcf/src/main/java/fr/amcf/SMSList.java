package fr.amcf;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.amcf.contactdata.ContactProviders;
import fr.amcf.contactview.Contact;

public class SMSList extends Activity implements AdapterView.OnItemClickListener {

    List<HashMap<String, List<String>>> smsMessagesList = new ArrayList<>();

    ListView smsListView;
    ArrayAdapter arrayAdapter;

    public SMSList() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smslist);
        smsListView = (ListView) findViewById(R.id.SMSList);
        arrayAdapter = new ArrayAdapter<HashMap<String, List<String>>>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);
        smsListView.setOnItemClickListener(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 3);
            }
        } else {
            refreshSmsInbox();
        }


    }


    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        String number;
        List<Contact> contacts = ContactProviders.getAll();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        int maxToLoad = 10;
        int cpt = 0;
        HashMap<String, List<String>> userAndMessages = new HashMap<>();
        do {
            cpt++;
           /*
           //CE CODE NE MARCHE PAS ENCORE A CAUSE DU CONTENT PROVIDER DE DAMIEN ---Pape
           if (cpt == maxToLoad) break;
            if (ContactProviders.getByPhoneNumber(smsInboxCursor.getString(indexAddress)) != null) {
                number = ContactProviders.getByPhoneNumber(smsInboxCursor.getString(indexAddress)).getName();
            } else {*/
                number = smsInboxCursor.getString(indexAddress);
            /*}*/
            if (userAndMessages.get(number) == null) {
                userAndMessages.put(number, new ArrayList<String>());
            }
                userAndMessages.get(number).add(smsInboxCursor.getString(indexBody));

            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
            arrayAdapter.add(str);
        } while (smsInboxCursor.moveToNext());
    }

    public void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        try {
            String[] smsMessages = {"Hello","World"};//smsMessagesList.get(pos).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";
            for (int i = 1; i < smsMessages.length; ++i) {
                smsMessage += smsMessages[i];
            }

            String smsMessageStr = address + "\n";
            smsMessageStr += smsMessage;
            Toast.makeText(this, smsMessageStr, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goBack(View v) {
        this.finish();
    }
}
