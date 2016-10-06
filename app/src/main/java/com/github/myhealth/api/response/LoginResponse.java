package com.github.myhealth.api.response;

import android.util.Log;

import com.github.myhealth.Const;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

public class LoginResponse extends APIResponse {
    private String token;
    private String userId ;
    public LoginResponse(String raw) {
        super(raw);
    }

    public String getToken(){
        return token;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    protected void parseRawResponse(String raw) {
        try {
            JSONObject json = new JSONObject(raw);
            token = json.getString("key");
            success = json.getBoolean("success");
            userId = json.getJSONObject("user").getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("parserawresponse", "parseRawResponse: failed to parse response");
            success = false;
        }

    }
}
