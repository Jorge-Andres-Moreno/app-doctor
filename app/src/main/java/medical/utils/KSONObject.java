package medical.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by camilo on 11/15/17.
 */

public class KSONObject extends JSONObject {

    public KSONObject(String json) throws JSONException {
        super(json);
    }

    @Override
    public int getInt(String name) throws JSONException {
        try {
            return super.getInt(name);
        } catch (JSONException e) {
            return 0;
        }
    }

    @Override
    public boolean getBoolean(String name) throws JSONException {
        try {
            return super.getBoolean(name);
        } catch (JSONException e) {
            return false;
        }
    }

    @Override
    public String getString(String name) throws JSONException {
        try {
            return super.getString(name);
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public JSONArray getJSONArray(String name) throws JSONException {
        try {
            return super.getJSONArray(name);
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    @Override
    public JSONObject getJSONObject(String name) throws JSONException {
        try {
            return super.getJSONObject(name);
        } catch (JSONException e) {
            return null;
        }
    }
}
