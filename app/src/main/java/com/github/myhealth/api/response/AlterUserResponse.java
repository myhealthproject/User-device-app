package com.github.myhealth.api.response;

import android.util.Log;

import static com.github.myhealth.Const.LOG_TAG;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */
public class AlterUserResponse extends APIResponse {
    public AlterUserResponse(String raw) {
        super(raw);
    }

    @Override
    protected void parseRawResponse(String raw) {
        Log.d(LOG_TAG, "ALTER USER: " + raw);
        success = true;
    }
}
