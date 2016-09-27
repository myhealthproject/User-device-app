package com.github.myhealth.api.request;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

/**
 * Class which represents a login request
 */
public class LoginRequest extends PostRequest {

    private final String email, password;

    public LoginRequest(String email, String password){
        super("login/");
        this.email = email;
        this.password = password;
    }
}
