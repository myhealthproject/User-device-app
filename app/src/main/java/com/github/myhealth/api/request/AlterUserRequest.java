package com.github.myhealth.api.request;

import android.content.Context;
import android.util.Log;

import com.github.myhealth.Const;

import org.json.JSONException;
import org.json.JSONObject;

import static com.github.myhealth.Const.LOG_TAG;


/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class AlterUserRequest extends PutRequest {


    private final String username, password, firstName, lastName;

    public AlterUserRequest(String userId, String username, String password, String firstName, String lastName) {
        super("user/" + userId);
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    protected JSONObject buildPostData() throws JSONException {
            return new JSONObject().accumulate("fname", firstName).accumulate("lname", lastName);

    }
}
