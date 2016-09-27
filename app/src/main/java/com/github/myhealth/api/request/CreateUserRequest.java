package com.github.myhealth.api.request;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */
public class CreateUserRequest extends PostRequest {
    private final String username, password, firstName, lastName;

    public CreateUserRequest(String username, String password, String firstName, String lastName) {
        super("user/");
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
