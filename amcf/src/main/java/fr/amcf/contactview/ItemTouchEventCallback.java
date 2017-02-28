package fr.amcf.contactview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import fr.amcf.R;

/**
 * Created by dchesnea on 14/02/2017.
 */
class ItemTouchEventCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter recyclerAdapter;

    public ItemTouchEventCallback(ItemTouchHelperAdapter adapter) {
        recyclerAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags =0;// ItemTouchHelper.END; //ItemTouchHelper.START |
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        recyclerAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        TextView name = (TextView) viewHolder.itemView.findViewById(R.id.name);
//        name.setText("View contact messages ?");
        recyclerAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

}
