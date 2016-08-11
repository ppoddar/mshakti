package org.shakti;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Shows a page with content. Also toggles between translated and original content.
 *
 * Maintains the content handle/descriptor as state. The actual content is stored as
 * an asset.
 *
 */
public class HTMLPageFragment extends Fragment {
    private static final String TAG = "HTMLPageFragment";


    private String mContentURL;
    PagerAdapter mAdapter;


    public String getUrl() {
        return mContentURL;
    }

    public HTMLPageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.htmlpage_fragment, container, false);

        setArguments(savedInstanceState);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        setArguments(savedState);
    }



    @Override
    public  void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        if (mContentURL == null) return;

        state.putString(PageActivity.KEY_CONTENT, mContentURL);
     }

    @Override
    public  void onViewStateRestored(Bundle state) {
        super.onViewStateRestored(state);
        setArguments(state);
        showContent(getView());
    }


    /**
     * Shows the content in the given view.
     * If mIsShowingOriginal then the original content is shown
     * @param view
     */
    void showContent(View view) {
        if (view == null || mContentURL == null) {
            Log.d(TAG, "***WARN: not showing because view or content is null");
            return;
        }
        Log.d("HTMLPage", "loading " + mContentURL);

      ((WebView)view.findViewById(R.id.htmlpage_webview)).loadUrl(mContentURL);
        mAdapter.notifyDataSetChanged();


   }


    /**
     * Sets the content from given bundle.
     * If the bundle is null then the inner state
     * @param bundle
     */
    @Override
    public void setArguments(Bundle bundle) {
        if (bundle == null) return;
        mContentURL = bundle.getString(PageActivity.KEY_CONTENT);

    }
}
