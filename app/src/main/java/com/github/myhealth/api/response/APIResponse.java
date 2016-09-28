package com.github.myhealth.api.response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */
public abstract class APIResponse {
    protected boolean success;
    public APIResponse(String raw){
        parseRawResponse(raw);
    }

    public boolean isSuccess() {
        return success;
    }

    protected void parseRawResponse(String raw){
        try {
            success = new JSONObject(raw).getBoolean("success");
        } catch (JSONException e) {
            success = false;
        }
    }
}
