package com.github.myhealth.api.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

public class LoginResponse extends APIResponse {
    private String token;

    public LoginResponse(String raw) {
        super(raw);
    }

    public String getToken(){
        return token;
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
