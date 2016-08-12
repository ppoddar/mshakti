package org.shakti.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Content implements Parcelable {
    String mPath;      // Path relative to a root directory supplied from outside
    String mTitle;     // title of the content
    String mLanguage;  // language of the content used to select a font
    Content mOriginal; // content in original language

    private static final String TAG = "Content";

    public Content() {
    }

    /**
     * Supply a JSON descriptor
     *
     * @param json
     */
    public Content(JSONObject json) {
        fromJSON(json);
    }

    public String getPath() {
        return mPath;
    }

    public String getTitle() {
        return mTitle;
    }


    public String getLanguage() {
        return mLanguage;
    }

    public boolean isOriginal() {
        return mOriginal == null;
    }

    public Content getOriginal() {
        return mOriginal;
    }

    // Parcelable

    protected Content(Parcel in) {
        try {
            JSONObject json = new JSONObject(in.readString());
            fromJSON(json);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toJSON().toString());
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Content> CREATOR = new Parcelable.Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };


    public void fromJSON(JSONObject json) {
        //Log.d(TAG, "fromJSON() " + json);
        try {
            mPath = json.getString("path");
            mTitle = json.getString("title");
            this.mLanguage = json.getString("language");
            if (!json.has("original")) {
                // this is original
                return;
            } else {
                JSONObject original = json.getJSONObject("original");
                if (!original.has("path")) original.put("path", mPath);
                mOriginal = new Content(original);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("path", mPath);
            json.put("title", mTitle);
            json.put("language", mLanguage);
            if (mOriginal != null) {
                json.put("original", mOriginal.toJSON());
            }
           // Log.d(TAG, "toJSON() " + json);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        //Log.d(TAG, "toJSON() " + json);
        return json;
    }
}
