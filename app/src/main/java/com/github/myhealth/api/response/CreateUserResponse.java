package com.github.myhealth.api.response;

import android.util.Log;

import static com.github.myhealth.Const.LOG_TAG;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */
public class CreateUserResponse extends APIResponse {
    private String username, password, firstName, lastName, userId;

    public CreateUserResponse(String raw) {
        super(raw);
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

    public String getUserId() {
        return userId;
    }

    @Override
    protected void parseRawResponse(String raw) {
        try {
            JSONObject json = new JSONObject(raw);
            username = json.getString("uname");
            password = json.getString("password");
            firstName = json.getString("fname");
            lastName = json.getString("lname");
            userId = json.getString("_id");
            success = true;
        } catch (JSONException e) {
            success = false;
        }
    }
}
