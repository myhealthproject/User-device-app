package com.github.myhealth.api.request;

import com.github.myhealth.api.request.Request;
import com.github.myhealth.api.request.RequestMethod;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */
public class PostRequest extends Request {
    protected PostRequest( String path) {
        super(RequestMethod.POST, path);
    }

    @Override
    public String execute() {
        return null;
    }
}
