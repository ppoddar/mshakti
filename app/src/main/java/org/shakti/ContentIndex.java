package org.shakti;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a list of file paths and for each path zero or more path for translated
 * version.
 * Created by ppoddar on 8/8/16.
 */
public class ContentIndex {
    private static final String TAG = "ContentIndex";

    private List<Content> mContents = new ArrayList<>();

    public ContentIndex(JSONArray index) {
        Log.d(TAG, "creating from " + index);
        for (int i = 0; i < index.length(); i++) {
            try {
                JSONObject descriptor = index.getJSONObject(i);
                addContent(new Content(descriptor));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void addContent(Content content) {
        //Log.d(TAG, "adding content " + content);
        mContents.add(content);

    }

    public List<Content> getContentList() {
        return mContents;
    }


    public int size() {
        return mContents.size();
    }

    /**
     * Gets content at specific index.
     *
     * @param position
     * @return
     */
    public Content getContent(int position) {
        return mContents.get(position);
    }
}
