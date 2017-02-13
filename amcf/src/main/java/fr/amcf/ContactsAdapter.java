package fr.amcf;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dchesnea on 15/02/2017.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.AmcfViewHolder> {

    private List<Contact> contactsList;

    public ContactsAdapter(List<Contact> moviesList) {
        this.contactsList = moviesList;
    }

    public class AmcfViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phoneNumber, email;

        public AmcfViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
            phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);
        }
    }

    @Override
    public AmcfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_row, parent, false);
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
