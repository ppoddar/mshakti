package org.shakti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.shakti.adapter.ListOfContentAdapter;
import org.shakti.data.Content;
import org.shakti.util.IndexManager;
import org.shakti.R;

import java.util.List;

/**
 * Displays table of contents. Each item links directly to a page.
 */
public class IndexPageActivity extends AppCompatActivity {
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.index_page_activity);

        ListView list = (ListView) findViewById(R.id.table_of_content);

        IndexManager.initialize(this);

        List<Content> indices = IndexManager.getIndex().getContentList();
        mAdapter = new ListOfContentAdapter(this, R.layout.index_item_view, indices);
        list.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_index_page_activity, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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


}
