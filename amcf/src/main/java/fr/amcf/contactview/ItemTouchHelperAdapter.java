package fr.amcf.contactview;

/**
 * Created by dchesnea on 14/02/2017.
 */
interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}