package com.github.myhealth.api.response;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */
public abstract class Response {

    public Response(String raw){
        parseRawResponse(raw);
    }

    protected abstract void parseRawResponse(String raw);
}
