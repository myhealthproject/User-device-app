package com.github.myhealth.api.request;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static com.github.myhealth.Const.LOG_TAG;


/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */
public class CreateUserRequest extends PostRequest {
    private final String username, password, firstName, lastName;

    public CreateUserRequest(String username, String password, String firstName, String lastName) {
        super("user/");
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    protected JSONObject buildPostData() {
        try {
            return new JSONObject().accumulate("uname", username)
                    .accumulate("password", password)
                    .accumulate("fname", firstName)
                    .accumulate("lname", lastName);
        } catch (JSONException e) {
            Log.d(LOG_TAG, e.getMessage());
            return null;
        }
    }
}
