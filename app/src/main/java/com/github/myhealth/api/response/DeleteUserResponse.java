package com.github.myhealth.api.response;

import android.util.Log;

import static com.github.myhealth.Const.LOG_TAG;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */
public class DeleteUserResponse extends APIResponse {

    public DeleteUserResponse(String raw) {
        super(raw);
    }

    @Override
    protected void parseRawResponse(String raw) {
        success = raw != null;
    }
}
