package org.shakti.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import org.shakti.R;

import java.util.HashMap;
import java.util.Map;

public class FontManager {
    private static final String TAG = "FontManager";

    private static Map<String, Typeface> mFontLibrary = new HashMap<>();

    public static void initialize(Context ctx) {
        Log.d(TAG, "initialize()");
        String[] fonts = ctx.getResources().getStringArray(R.array.fonts);
        for (String font : fonts) {
            String[] tokens = font.split(":");
            Typeface ttf = Typeface.createFromAsset(ctx.getAssets(), tokens[1]);
            Log.d(TAG, "initialize() language=" + tokens[0] + " font path=" + tokens[1]);
            mFontLibrary.put(tokens[0].toUpperCase(), ttf);
        }

    }

    public static Typeface getFontForLanguage(String lang) {
        Typeface font = mFontLibrary.get(lang.toUpperCase());
        return font == null ? Typeface.DEFAULT : font;
    }
}
