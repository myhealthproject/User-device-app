package com.github.myhealth.api.request;

import android.content.Context;



/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class AlterUserRequest extends PostRequest {


    private final String username, password, firstName, lastName;

    public AlterUserRequest(String userId, String username, String password, String firstName, String lastName) {
        super("user/" + userId);
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    protected String buildPostData() {
        return null;
    }
}
