package fr.amcf.contactview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import fr.amcf.R;

/**
 * Created by dchesnea on 15/02/2017.
 */
class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.AmcfViewHolder> implements ItemTouchHelperAdapter {

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
        private final TextView name, phoneNumber, email;

        public AmcfViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
            phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);
        }
    }

    @Override
    public AmcfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("TEST", "VIEW TYPE=" + viewType);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_row, parent, false);
        return new AmcfViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AmcfViewHolder amcfViewHolder, int position) {
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
