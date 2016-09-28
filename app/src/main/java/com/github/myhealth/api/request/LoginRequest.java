package com.github.myhealth.api.request;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

import android.content.Context;



/**
 * Class which represents a login request
 */
public class LoginRequest extends PostRequest {

    private final String username, password;

    public LoginRequest(String username, String password){
        super("login/");
        this.username = username;
        this.password = password;
    }

    @Override
    protected String buildPostData() {
        return "uname="+username+"&password="+password;
    }
}
