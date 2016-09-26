package com.github.myhealth.api.request;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

public class DeleteRequest extends Request {
    protected DeleteRequest(String path) {
        super(RequestMethod.DELETE, path);
    }

    @Override
    public String execute() {
        return null;
    }
}
