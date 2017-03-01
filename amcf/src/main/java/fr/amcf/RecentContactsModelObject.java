package fr.amcf;

import android.content.Context;

import fr.amcf.contactview.ModelObject;

/**
 * Created by frech on 01/03/2017.
 */

public enum RecentContactsModelObject {

    RED(R.string.facebook_app_id, R.layout.recent_contact_default_side, new RecentContactsModelObject.ConsumeContext(){

        @Override
        public void apply(Context context) {

        }
    }),
    BLUE(R.string.app_name, R.layout.recent_contact_right_side,new RecentContactsModelObject.ConsumeContext(){

        @Override
        public void apply(Context context) {

        }
    }),
    GREEN(R.string.action_new, R.layout.recent_contact_left_side,new RecentContactsModelObject.ConsumeContext(){

        @Override
        public void apply(Context context) {

        }
    });

    private int mTitleResId;
    private int mLayoutResId;
    private RecentContactsModelObject.ConsumeContext init;

    RecentContactsModelObject(int titleResId, int layoutResId, RecentContactsModelObject.ConsumeContext run) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
        init = run;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

    public void init(Context context) {
        init.apply(context);
    }

    interface ConsumeContext {
        public void apply(Context context);
    }
}
