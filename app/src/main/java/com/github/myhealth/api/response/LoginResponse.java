package com.github.myhealth.api.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

public class LoginResponse extends APIResponse {
    private String token;
    private String userId, username, password, firstName, lastName;
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
            JSONObject user = json.getJSONObject("user");
            userId = user.getString("_id");
            username = user.getString("uname");
            password = user.getString("password");
            firstName = user.getString("fname");
            lastName = user.getString("lname");
        } catch (JSONException e) {
            success = false;
        }

    }
}
