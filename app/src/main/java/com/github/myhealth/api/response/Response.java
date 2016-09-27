package com.github.myhealth.api.response;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */
public abstract class Response {
    private boolean success;
    public Response(String raw){
        parseRawResponse(raw);
    }

    public boolean isSuccess() {
        return success;
    }

    protected abstract void parseRawResponse(String raw);
}
