package com.github.myhealth.api.request;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class GetUserRequest extends GetRequest {
    public GetUserRequest(int userId) {
        super("user/"+userId);
    }
}
