package com.github.myhealth.api.response;

import com.github.myhealth.api.response.Response;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

public class LoginResponse extends Response {
    private String token;

    public LoginResponse(String raw) {
        super(raw);
    }

    public String getToken(){
        return token;
    }

    @Override
    protected void parseRawResponse(String raw) {
        //TODO implement
        token = raw;
    }
}
