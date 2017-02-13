package fr.amcf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

        prepareMovieData();
    }

    private void prepareMovieData() {
        contactList.add(new Contact.Builder().setEmail("aa@gmail.com").setName("Damien Chesneau").setPrimaryPhoneNumber("+33638735700").build());
        contactList.add(new Contact.Builder().setEmail("aa@gmail.com").setName("Damien Chesneau").setPrimaryPhoneNumber("+33638735700").build());
        contactList.add(new Contact.Builder().setEmail("aa@gmail.com").setName("Damien Chesneau").setPrimaryPhoneNumber("+33638735700").build());
        contactList.add(new Contact.Builder().setEmail("aa@gmail.com").setName("Damien Chesneau").setPrimaryPhoneNumber("+33638735700").build());
        contactList.add(new Contact.Builder().setEmail("aa@gmail.com").setName("Damien Chesneau").setPrimaryPhoneNumber("+33638735700").build());
        contactList.add(new Contact.Builder().setEmail("aa@gmail.com").setName("Damien Chesneau").setPrimaryPhoneNumber("+33638735700").build());
        contactList.add(new Contact.Builder().setEmail("aa@gmail.com").setName("Damien Chesneau").setPrimaryPhoneNumber("+33638735700").build());
        adapter.notifyDataSetChanged();
    }

}
