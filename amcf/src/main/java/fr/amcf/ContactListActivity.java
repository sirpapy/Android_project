package fr.amcf;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactListActivity extends Activity {
    private ListView listcontact = (ListView) findViewById(R.id.listcontact);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
            }
        } else {
            initContacts();
        }
*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initContacts();
                } else {
                    //TODO
                }
                return;
            }
        }
    }

    private void initContacts() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
            ArrayList<Contact> builders = new ArrayList<>();
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String email = "TODO";// cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        builders.add(new Contact.Builder().setName(name)
                                .setEmail(email).setPrimaryPhoneNumber(phoneNo).build());
                    }
                    pCur.close();
                }
            }
            Toast.makeText(this, builders.get(0).toString(), Toast.LENGTH_SHORT).show();
            ArrayList<String> elementsToPrint = new ArrayList<>();
            for (Contact contact : builders) {
                elementsToPrint.add(contact.toString());
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, elementsToPrint);

            //listcontact.setAdapter(adapter);

            /*btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    arrayList.add(editTxt.getText().toString());
                    adapter.notifyDataSetChanged();
                }
            });*/
        }
    }


}
