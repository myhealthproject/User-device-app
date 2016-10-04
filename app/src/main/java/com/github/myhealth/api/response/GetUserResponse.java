package com.github.myhealth.api.response;

import android.util.Log;

import static com.github.myhealth.Const.LOG_TAG;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class GetUserResponse extends APIResponse {
    private String userId, username, password, firstName, lastName;

    public GetUserResponse(String raw) {
        super(raw);
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    protected void parseRawResponse(String raw) {
        Log.d(LOG_TAG, "GET USER: " + raw);
        try {
            JSONObject json = new JSONObject(raw);
            userId = json.getString("_id");
            username = json.getString("uname");
            password = json.getString("password");
            firstName = json.getString("fname");
            lastName = json.getString("lname");
            success = true;
        } catch (JSONException e) {
            success = false;
        }
    }
}
