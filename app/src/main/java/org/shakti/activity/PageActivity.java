package org.shakti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.shakti.adapter.MultilingualPageAdapter;
import org.shakti.util.FileManager;
import org.shakti.util.FontManager;
import org.shakti.util.IndexManager;
import org.shakti.R;

/**
 * Created by ppoddar on 8/8/16.
 */
public class PageActivity extends AppCompatActivity {
    public static final String KEY_CONTENT = "content";
    public static final String KEY_SHOWING_ORIGINAL = "original";
    public static final String KEY_START_PAGE  = "start_page";
    private static final String TAG = "MLPageViewActivity";
    private ViewPager mPager;

    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.page_activity);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setIcon(R.mipmap.shakti_profile);


        FileManager.initialize(getApplicationContext());
        FontManager.initialize(getApplicationContext());
        IndexManager.initialize(getApplicationContext());





        mPager = (ViewPager) findViewById(R.id.view_pager);
        PagerAdapter mAdapter = new MultilingualPageAdapter(getSupportFragmentManager(),
                IndexManager.getIndex());


        int startPage = 0;
        try {
            startPage = getIntent().getExtras().getInt(KEY_START_PAGE);
        } catch (Exception ex) {
            Log.d(TAG, "***WARN: onCreate() can not get view pager starting page from intent bundle due to  " + ex);

        }
        Log.d(TAG, "onCreate() view pager starting at " + startPage);
        // Order of following calls is important
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(startPage);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_page_activity, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.index_menu_item:
                Intent showIndex = new Intent(this, IndexPageActivity.class);
                startActivity(showIndex);
                break;
            case R.id.about_menu_item:
                Intent aboutPage = new Intent(this, AboutActivity.class);
                startActivity(aboutPage);
                break;

            case R.id.settings_menu_item:
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (mPager != null) {
            // before screen rotation it's better to detach pagerAdapter from the ViewPager, so
            // pagerAdapter can remove all old fragments, so they're not reused after rotation.
            mPager.setAdapter(null);
        }
        super.onSaveInstanceState(savedInstanceState);
    }


}
