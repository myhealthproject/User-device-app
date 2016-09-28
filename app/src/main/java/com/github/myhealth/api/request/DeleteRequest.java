package com.github.myhealth.api.request;

import java.io.IOException;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

public class DeleteRequest extends APIRequest {
    protected DeleteRequest(String path) {
        super(path);
    }

    @Override
    public String execute(String apiURL) throws IOException {

        return null;
    }
}
