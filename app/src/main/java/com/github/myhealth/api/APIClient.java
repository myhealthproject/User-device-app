package com.github.myhealth.api;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

import android.content.res.Resources;

import com.github.myhealth.R;
import com.github.myhealth.api.request.LoginRequest;
import com.github.myhealth.api.response.LoginResponse;
import com.github.myhealth.api.request.Request;
import com.github.myhealth.api.response.RegisterResponse;

import java.io.InputStream;
import java.util.Date;

/**
 * This is a singleton class, because we want to talk to the api one request at a time
 */
public class APIClient {
    protected static volatile APIClient instance;


    /**
     * The default API url is extracted from a string resource, so that it can be accessed easily
     */

    private static String apiURL;

    //Protected for test purposes
    protected APIClient(String apiURL) {
        this.apiURL = apiURL;
    }

    /**
     * @return a new instance of the APIClient
     */
    public static APIClient getInstance() {
        if (instance == null) {
            synchronized (APIClient.class) {
                if (instance == null) {
                    instance = new APIClient(Resources.getSystem().getString(R.string.api_url));
                }
            }
        }
        return instance;
    }

    /**
     * Creates a new API call, executes and parses its response, after which it returns the response
     *
     * @param email
     * @param password
     * @return The parsed response
     */
    public LoginResponse logIn(String email, String password) {
        return new LoginResponse(executeRequest(new LoginRequest(email, password)));
    }


    //TODO implement methods for other calls

    /**
     * Registers a new user
     *
     * @param email
     * @param password
     * @param name
     * @param dateOfBirth
     * @return The parsed response
     */
    public RegisterResponse register(String email, String password, String name, Date dateOfBirth) {
        //TODO implement
        return null;
    }

    public void getProfile() {
        //TODO add parameters and implement
    }

    /**
     * Makes the actual request in background
     */
    private synchronized String executeRequest(Request request) {
        //TODO set up the connection, create request, return raw response
        return "Raw response goes in here";
    }

    private InputStream connect() {
        //TODO implement and have it return the connection's inputstream
        return null;
    }
}
