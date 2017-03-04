package fr.amcf;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.amcf.contactview.Contact;
import fr.amcf.contactview.ModelObject;

/**
 * Created by frech on 01/03/2017.
 */

public class CustomRecentContactsAdapter extends PagerAdapter {

    private TextView name, phoneNumber, email;
    private Context mContext;
    private Contact contact;

    public CustomRecentContactsAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        RecentContactsModelObject modelObject = RecentContactsModelObject.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);
        if (position == 0) {
            name = (TextView) layout.findViewById(R.id.name);
            email = (TextView) layout.findViewById(R.id.email);
            phoneNumber = (TextView) layout.findViewById(R.id.phoneNumber);
            this.name.setText(contact.getName());
            this.email.setText(contact.getEmail());
            this.phoneNumber.setText(contact.getPrimaryPhoneNumber());
        } else if (position == 1) {
            /*name = (TextView) layout.findViewById(R.id.contact_click_name);
            name.setText(contact.getName());*/
           /* Intent intent = new Intent(mContext, DisplayConversation.class);
            mContext.startActivity(intent);*/
        }
        collection.addView(layout);
        return layout;
    }

    public void setContactValues(Contact contact) {
        this.contact = contact;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return ModelObject.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        RecentContactsModelObject customPagerEnum = RecentContactsModelObject.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }
}
