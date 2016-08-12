package org.shakti.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.shakti.R;
import org.shakti.activity.IndexPageActivity;
import org.shakti.activity.PageActivity;
import org.shakti.data.Content;

import java.util.List;

/**
 * Created by ppoddar on 8/10/16.
 */
public class ListOfContentAdapter extends ArrayAdapter {

    public ListOfContentAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(final int position,
                        View convertView,
                        ViewGroup parent) {

        View view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.index_item_view, parent, false);

        Content content = (Content) getItem(position);

        makeItemLink((Button) view.findViewById(R.id.index_item_translated_button),
                content.getTitle(), position);
        makeItemLink((Button) view.findViewById(R.id.index_item_original_button),
                content.getOriginal().getTitle(), position);


        return view;
    }

    void makeItemLink(Button button, String text, final int position) {
        button.setText(text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showPageIntent = new Intent(getContext(), PageActivity.class);
                Bundle bundle = new Bundle();
                showPageIntent.putExtra(PageActivity.KEY_START_PAGE, position);
                getContext().startActivity(showPageIntent);
            }
        });

    }

}
