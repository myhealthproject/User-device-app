package com.github.myhealth.api.response;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class GetUserResponse extends APIResponse {
    private int id;
    private String firstName, lastName;

    public GetUserResponse(String raw) {
        super(raw);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    protected void parseRawResponse(String raw) {

    }
}
