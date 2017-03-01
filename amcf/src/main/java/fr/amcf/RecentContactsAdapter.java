package fr.amcf;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import fr.amcf.contactview.Contact;
import fr.amcf.contactview.ContactsAdapter;
import fr.amcf.contactview.CustomPagerAdapter;
import fr.amcf.contactview.ItemTouchHelperAdapter;

/**
 * Created by frech on 01/03/2017.
 */

public class RecentContactsAdapter extends RecyclerView.Adapter<RecentContactsAdapter.RecentViewHolder> implements ItemTouchHelperAdapter {

    private final List<Contact> contactsList;

    public RecentContactsAdapter(List<Contact> moviesList) {
        if (moviesList != null) {
            this.contactsList = moviesList;
        } else {
            throw new NullPointerException("Your contact list is null.");
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(contactsList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(contactsList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
//        notifyItemRemoved(position);
        notifyItemChanged(position);
    }

    public class RecentViewHolder extends RecyclerView.ViewHolder {

        private final CustomRecentContactsAdapter customRecentContactAdapter;

        public RecentViewHolder(View view) {
            super(view);

            ViewPager viewPager = (ViewPager) itemView.findViewById(R.id.swipableRecyclerItem);
            customRecentContactAdapter = new CustomRecentContactsAdapter(view.getContext());
            viewPager.setAdapter(customRecentContactAdapter);
        }
    }

    @Override
    public RecentContactsAdapter.RecentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_row, parent, false);
        return new RecentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecentContactsAdapter.RecentViewHolder recentViewHolder, int position) {
        Contact contact = contactsList.get(position);
        recentViewHolder.customRecentContactAdapter.setContactValues(contact);
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }
}
