package org.shakti;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ppoddar on 8/8/16.
 */
public class FileManager {
    private static final String TAG = "FileManager";
    private static final byte[] EMPTY_BYTES = new byte[0];

    private static Map<String,String> mLanguageContentRoots
            = new HashMap<>();

    private static String contentRoot = "";

    public static void initialize(Context ctx) {
        Log.d(TAG, "initialize()");
        String[] languages = ctx.getResources().getStringArray(R.array.languages);
        for (String language : languages) {
            String[] tokens = language.split(":");
            Log.d(TAG, "initialize() language=" + tokens[0] + " dir path=" + tokens[1]);
            mLanguageContentRoots.put(tokens[0].toUpperCase(), tokens[1]);
        }

    }
    public static byte[] read(InputStream in) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            int b = -1;
            while ((b = in.read()) != -1) {
                baos.write(b);
            }
            in.close();
            return baos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return EMPTY_BYTES;
    }


    public static String getAssetPath(String language, String relativePath) {
        String root = mLanguageContentRoots.get(language.toUpperCase());
        return root + "/" + relativePath;

    }
}


