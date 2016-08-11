package org.shakti;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

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


    private class ListOfContentAdapter extends ArrayAdapter {

        public ListOfContentAdapter(Context context, int resource, List objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView (final int position,
                      View convertView,
                      ViewGroup parent) {
           View view =  ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.index_item_view, parent, false);

            Content content = (Content)getItem(position);

            makeItemLink( (Button)view.findViewById(R.id.index_item_translated_button),
                    content.getTitle(), position);
            makeItemLink( (Button)view.findViewById(R.id.index_item_original_button),
                    content.getOriginal().getTitle(), position);


            return view;
        }

        void makeItemLink(Button button, String text, final int position) {
            button.setText(text);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showPageIntent = new Intent(IndexPageActivity.this, PageActivity.class);
                    Bundle bundle = new Bundle();
                    showPageIntent.putExtra(PageActivity.KEY_START_PAGE, position);
                    startActivity(showPageIntent);
                }
            });

        }

    }
}
