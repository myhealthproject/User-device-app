package com.github.myhealth.api.request;

import com.github.myhealth.api.request.RequestMethod;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

/**
 * Contains all information needed to make a request
 */
public abstract class Request {
    public final RequestMethod method;
    public final String path;

    protected Request(RequestMethod method, String path) {
        this.method = method;
        this.path = path;
    }


    //TODO add param for connection

    /**
     * Opens the connection, sends the request and returns the raw response
     * @return
     */
    public abstract String execute();
}
