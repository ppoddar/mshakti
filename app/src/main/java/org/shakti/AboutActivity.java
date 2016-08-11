package org.shakti;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

/**
 * Maintains a view page and fixed set of pages
 */
public class AboutActivity extends AppCompatActivity {
    private static final String TAG = "AboutActivity";

    private String[] pages = {
            "file:///android_asset/biography-1.html",
            "file:///android_asset/biography-2.html",
            "file:///android_asset/biography-3.html",
            "file:///android_asset/biography-4.html",
            "file:///android_asset/biography-5.html",
            "file:///android_asset/biography-6.html"
    };

    private ViewPager mPager;

    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.about_page_activity);

        mPager = (ViewPager) findViewById(R.id.html_view_pager);
        PagerAdapter adapter = new MyPageAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);
    }


    protected Object[] getPages() {
        return pages;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    private class MyPageAdapter extends FragmentStatePagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            HTMLPageFragment f = new HTMLPageFragment();
            Bundle args = new Bundle();
            args.putString(PageActivity.KEY_CONTENT, pages[position]);
            f.setArguments(args);
            f.mAdapter = this;
            return f;
        }

        @Override
        public int getCount() {
            return pages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return WebView.class.isInstance(view)
                    && HTMLPageFragment.class.isInstance(object)
                    && WebView.class.cast(view).getUrl().equals(
                       HTMLPageFragment.class.cast(object).getUrl());
        }
    }
}
