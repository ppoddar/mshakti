package org.shakti.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.shakti.adapter.MyPageAdapter;
import org.shakti.R;

/**
 * Shows a fixed number of HTML pages. The pages are local and resides in assets folder.
 * Maintains a view pager and fixed set of pages
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
        PagerAdapter adapter = new MyPageAdapter(getSupportFragmentManager(), pages);
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
}
