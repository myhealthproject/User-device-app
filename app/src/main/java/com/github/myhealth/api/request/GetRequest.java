package com.github.myhealth.api.request;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

public class GetRequest extends Request {
    protected GetRequest(String path) {
        super(RequestMethod.GET, path);
    }

    @Override
    public String execute() {
        return null;
    }
}
