package org.shakti.util;

import android.content.Context;

import org.json.JSONArray;
import org.shakti.data.ContentIndex;
import org.shakti.R;

/**
 * Created by ppoddar on 8/9/16.
 */
public class IndexManager {

    private static ContentIndex mIndex;
    public static void initialize(Context ctx) {
        String indexFileName = ctx.getResources().getString(R.string.index_file);

        try {
            byte[] bytes = FileManager.read(ctx.getAssets().open(indexFileName));

            mIndex = new ContentIndex(new JSONArray(new String(bytes)));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ContentIndex getIndex() {
        return mIndex;
    }
}
