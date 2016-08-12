package org.shakti.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import org.shakti.activity.PageActivity;
import org.shakti.fragment.HTMLPageFragment;

/**
 * Created by ppoddar on 8/10/16.
 */
public class MyPageAdapter extends FragmentStatePagerAdapter {
    private String[] pages;

    public MyPageAdapter(FragmentManager fm, String[] pages) {
        super(fm);
        this.pages = pages;
    }



    @Override
    public Fragment getItem(int position) {
        HTMLPageFragment f = new HTMLPageFragment();
        Bundle args = new Bundle();
        args.putString(PageActivity.KEY_CONTENT, pages[position]);
        f.setArguments(args);
        f.setAdapter(this);
        return f;
    }

    @Override
    public int getCount() {
        return pages.length;
    }

    /**
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return WebView.class.isInstance(view)
                && HTMLPageFragment.class.isInstance(object)
                && WebView.class.cast(view).getUrl().equals(
                HTMLPageFragment.class.cast(object).getUrl());
    }
    */
}
