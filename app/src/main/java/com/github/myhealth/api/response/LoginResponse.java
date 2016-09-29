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
    private String userId = "57ec159a549b665082c73484";
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
        } catch (JSONException e) {
            success = false;
        }

    }
}
