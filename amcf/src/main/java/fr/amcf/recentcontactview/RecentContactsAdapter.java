package fr.amcf.recentcontactview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import fr.amcf.DisplayConversation;
import fr.amcf.R;
import fr.amcf.contactdata.Contact;
import fr.amcf.contactview.ItemTouchHelperAdapter;

/**
 * Created by frech on 01/03/2017.
 */

public class RecentContactsAdapter extends RecyclerView.Adapter<RecentContactsAdapter.RecentViewHolder> implements ItemTouchHelperAdapter {

    private final List<Contact> contactsList;
    private final Context context;

    public RecentContactsAdapter(List<Contact> moviesList, Context context) {
        this.context = context;
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
        Contact contact = contactsList.get(position);
        Intent intent = new Intent(context, DisplayConversation.class);
        intent.putExtra("contact", contact);
        context.startActivity(intent);
        notifyItemChanged(position);
    }

    public class RecentViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, phoneNumber, email;

        public RecentViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
            phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);
        }
    }

    @Override
    public RecentContactsAdapter.RecentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("TEST", "VIEW TYPE=" + viewType);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_default_side, parent, false);
        return new RecentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecentContactsAdapter.RecentViewHolder amcfViewHolder, int position) {
        Contact movie = contactsList.get(position);
        amcfViewHolder.name.setText(movie.getName());
        amcfViewHolder.email.setText(movie.getEmail());
        amcfViewHolder.phoneNumber.setText(movie.getPrimaryPhoneNumber());
    }


    @Override
    public int getItemCount() {
        return contactsList.size();
    }
}
