package org.shakti;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by ppoddar on 8/8/16.
 */
public class MultilingualPageAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "PageAdapter";

    ContentIndex mIndex;

    MultilingualPageAdapter(FragmentManager fm, ContentIndex index) {
        super(fm);
        mIndex = index;
    }
    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem() " + position);
        Content content = mIndex.getContent(position);
        PageFragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PageActivity.KEY_CONTENT, content.toJSON().toString());
        bundle.putBoolean(PageActivity.KEY_SHOWING_ORIGINAL, false);
        fragment.mAdapter = this;
        //Log.d(TAG, "getItem() Fragment.setArguments(bundle=" + bundle + ")");
        fragment.setArguments(bundle);
        return fragment;
    }



    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mIndex.size();
    }
}
