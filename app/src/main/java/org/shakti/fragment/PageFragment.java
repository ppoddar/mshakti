package org.shakti.fragment;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.shakti.data.Content;
import org.shakti.util.FileManager;
import org.shakti.util.FontManager;
import org.shakti.R;
import org.shakti.activity.PageActivity;

import java.io.InputStream;

/**
 * Shows a page with content. Also toggles between translated and original content.
 *
 * Maintains the content handle/descriptor as state. The actual content is stored as
 * an asset.
 *
 */
public class PageFragment extends Fragment {
    private static final String TAG = "PageFragment";
    private static final long ANIM_DURATION = 100;

    private  PagerAdapter mAdapter;

    // The content is always the translated content, never the original.
    // when original content is to be shown then it is obtained by {@link Content#getOriginal()}

    private Content mContent;

    private boolean mIsShowingOriginal = false;


    public PageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.page_fragment, container, false);

        setArguments(savedInstanceState);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        setArguments(savedState);

        showContent(getView(), mIsShowingOriginal);


        FloatingActionButton switchContentButton = (FloatingActionButton)getView()
                .findViewById(R.id.translate_button);
        switchContentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View fadeOut = getView().findViewById(R.id.layout_translation);
                View fadeIn = getView().findViewById(R.id.layout_original);
                if (mIsShowingOriginal) {
                    // switch the target view
                    fadeOut = getView().findViewById(R.id.layout_original);
                    fadeIn = getView().findViewById(R.id.layout_translation);

                }

                Log.d(TAG, "onClick() switch view currently showing " +  (mIsShowingOriginal ? "original" : "translation"));
                boolean showOriginal = !mIsShowingOriginal;
                dissolve(fadeIn, showOriginal, fadeOut, ANIM_DURATION);
                mIsShowingOriginal = !mIsShowingOriginal;
            }
        });

   }



    @Override
    public  void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        if (mContent == null) return;

        state.putString(PageActivity.KEY_CONTENT, mContent.toJSON().toString());
        // saved KEY_SHOWING_ORIGINAL state is always false because
        // saved state content is always the translated content
        state.putBoolean(PageActivity.KEY_SHOWING_ORIGINAL, false);
    }

    @Override
    public  void onViewStateRestored(Bundle state) {
        super.onViewStateRestored(state);
        setArguments(state);
        showContent(getView(), mIsShowingOriginal);
    }


    /**
     * Shows the content in the given view.
     * If mIsShowingOriginal then the original content is shown
     * @param view
     */
    void showContent(View view, boolean showOriginal) {
        if (view == null || mContent == null) {
            Log.d(TAG, "***WARN: not showing because view or content is null");
            return;
        }

        //Log.d(TAG, "showContent()  currently showing " +  (mIsShowingOriginal.get() ? "original" : "translation"));
        //Log.d(TAG, "showContent()  going to show " +  (showOriginal ? "original" : "translation"));

        Content content = showOriginal ?  mContent.getOriginal()  : mContent;
        if (content == null) {
            Log.d(TAG, "***WARN: not showing because content is null");
            return;
        }


        String language = content.getLanguage();

        Typeface font = FontManager.getFontForLanguage(language);
        String assetPath = FileManager.getAssetPath(content.getLanguage(),
                content.getPath());


        //Log.d(TAG, "showContent() language=" + language + " asset=" + assetPath + " font " + font);

        Log.d(TAG, "showContent() " + content.getTitle());

        TextView title = (TextView)view.findViewById(
                showOriginal ? R.id.page_title_original : R.id.page_title_translation);
        title.setText(content.getTitle());
        title.setTypeface(font);


        try {
            TextView body = (TextView) view.findViewById(
                    showOriginal ? R.id.page_body_original : R.id.page_body_translation);
            InputStream in = getActivity().getAssets().open(assetPath);
            byte[] bytes = FileManager.read(in);
            String text = new String(bytes);
            body.setText(text);
            body.setTypeface(font);
            body.setMovementMethod(new ScrollingMovementMethod());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
   }

    /**
     * Fades in one view and fades out another.
     *
     * @param fadeIn
     * @param fadeOut
     */
    void dissolve(final View fadeIn, final boolean showOriginal, final View fadeOut, long duration) {
        Log.d(TAG, "dissolve() to " + (showOriginal ? "original" : "translated"));

        final View mainView = getView();

        fadeIn.setAlpha(0f);
        fadeIn.setVisibility(View.VISIBLE);
        fadeIn.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null);



        fadeOut.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        showContent(mainView, showOriginal);
                        mAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        fadeOut.setVisibility(View.GONE);
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
        //Log.d(TAG, "setArguments() bundle=" + bundle);
        if (bundle == null) return;
        try {
            mIsShowingOriginal = bundle.getBoolean(PageActivity.KEY_SHOWING_ORIGINAL);
            String jsonString = bundle.getString(PageActivity.KEY_CONTENT);
            if (jsonString == null) return;
            JSONObject json = new JSONObject(jsonString);
            mContent = new Content(json);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void setAdapter(PagerAdapter adapter) {
        mAdapter = adapter;
    }
}
