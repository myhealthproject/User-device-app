package com.github.myhealth.api.request;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static com.github.myhealth.Const.LOG_TAG;

/**
 * Class which represents a login request
 */
public class LoginRequest extends PostRequest {

    private final String username, password;

    public LoginRequest(String username, String password) {
        super("login/");
        this.username = username;
        this.password = password;
    }

    @Override
    protected JSONObject buildPostData() throws JSONException {
        return new JSONObject().accumulate("uname", username).accumulate("password", password);
    }
}
