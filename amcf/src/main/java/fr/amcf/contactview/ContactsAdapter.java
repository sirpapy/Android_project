package fr.amcf.contactview;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import fr.amcf.R;

/**
 * Created by dchesnea on 15/02/2017.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.AmcfViewHolder> implements ItemTouchHelperAdapter {

    private final List<Contact> contactsList;

    public ContactsAdapter(List<Contact> moviesList) {
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

    public class AmcfViewHolder extends RecyclerView.ViewHolder {

        private final CustomPagerAdapter customPagerAdapter;

        public AmcfViewHolder(View view) {
            super(view);

            ViewPager viewPager = (ViewPager) itemView.findViewById(R.id.swipableRecyclerItem);
            customPagerAdapter = new CustomPagerAdapter(view.getContext());
            viewPager.setAdapter(customPagerAdapter);
        }
    }

    @Override
    public AmcfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_row, parent, false);
        return new AmcfViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AmcfViewHolder amcfViewHolder, int position) {
        Contact contact = contactsList.get(position);
        amcfViewHolder.customPagerAdapter.setContactValues(contact);
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }
}
