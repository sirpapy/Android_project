package fr.amcf.contactview;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.amcf.R;

/**
 * Created by dchesnea on 15/02/2017.
 */
public class ContactActivity extends AppCompatActivity {
    private List<Contact> contactList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new ContactsAdapter(contactList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Contact contact = contactList.get(position);
                Toast.makeText(getApplicationContext(), contact.getName() + " selected :)", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                //TODO
            }
        }));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
            }
        } else {
            initContacts();
        }
        ItemTouchHelper.Callback callback = new ItemTouchEventCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
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
//                String email = "TODO";// cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                int columnIndex = cur.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DISPLAY_NAME);
                String email = columnIndex == -1 ? "Not set" : cur.getString(columnIndex);

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
            contactList.addAll(builders);
            adapter.notifyDataSetChanged();
        }
    }

}
