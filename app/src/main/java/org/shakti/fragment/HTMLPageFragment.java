package org.shakti.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.shakti.R;
import org.shakti.activity.PageActivity;

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
    private PagerAdapter mAdapter;


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

        showContent(view);

        return view;
    }

    public void setAdapter(PagerAdapter adapter) {
        mAdapter = adapter;
        showContent(getView());

    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        setArguments(savedState);
        showContent(getView());
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


        final WebView webView = (WebView) view.findViewById(R.id.htmlpage_webview);
        // Loading on a separate thread. Using main thread often does not load the page

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("HTMLPage", "loading " + mContentURL + " page adapter is " + mAdapter);

                webView.getSettings().setUseWideViewPort(true);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
                webView.getSettings().setMinimumFontSize(14);
                webView.loadUrl(mContentURL);

                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }
        });




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
