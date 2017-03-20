package fr.amcf.contactdata;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by dchesnea on 21/02/2017.
 */

public class ContactProviders {

    private static ArrayList<Contact> contacts = new ArrayList<>();

    /**
     * Do not forget to use the dynamic permission asking.
     *
     * @param context
     */
    public static void initContacts(Context context) {
        if (!contacts.isEmpty()) {
            return;
        }
        ContentResolver cr = context.getContentResolver();
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
                    pCur.moveToNext();
                    //while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        builders.add(Contact.builder().setName(name)
                                .setEmail(email).setPrimaryPhoneNumber(phoneNo).build());
                    //}
                    pCur.close();
                }
            }
            contacts.addAll(builders);
        }
    }

    private static void checkState() {
        if (contacts.isEmpty()) {
            throw new IllegalStateException("Please use initContact method before.");
        }
    }

    /**
     * Return a contact if found, otherwise return null.
     *
     * @param phoneNumber
     * @return contact
     */
    public static Contact getByPhoneNumber(String phoneNumber) {
        checkState();
        for (Contact contact : contacts) {
            if (contact.isPhoneNumberEquals(phoneNumber)) {
                return contact;
            }
        }
        return null;
    }

    public static List<Contact> getAll() {
        checkState();
        return contacts;
    }

    public static List<Contact> getLastUseContacts(){
        List<Contact> list = contacts;
        for(Contact c : list){
            if(c.getMessages().size() == 0){
                list.remove(c);
            }
        }

        if(list.isEmpty()) {
            Collections.sort(list, new Comparator<Contact>() {
                @Override
                public int compare(Contact o1, Contact o2) {
                    if (o1.getMessages().get(o1.getMessages().size() - 1).getDate() >= o2.getMessages().get(o2.getMessages().size() - 1).getDate()) {
                        return -1;
                    }
                    return 1;
                }
            });
        }
        return list;
    }
}
