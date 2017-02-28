package fr.amcf.contactview;

import android.content.Context;

import fr.amcf.R;

public enum ModelObject {

    RED(R.string.facebook_app_id, R.layout.contact_default_side, new ConsumeContext(){

        @Override
        public void apply(Context context) {

        }
    }),
    BLUE(R.string.app_name, R.layout.contact_right_side,new ConsumeContext(){

        @Override
        public void apply(Context context) {

        }
    }),
    GREEN(R.string.action_new, R.layout.contact_left_side,new ConsumeContext(){

        @Override
        public void apply(Context context) {

        }
    });

    private int mTitleResId;
    private int mLayoutResId;
    private ConsumeContext init;

    ModelObject(int titleResId, int layoutResId, ConsumeContext run) {
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
