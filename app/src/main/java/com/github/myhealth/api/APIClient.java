package com.github.myhealth.api;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

import java.util.Date;

/**
 * This is a singleton class, because we want to talk to the api one request at a time
 */
public class APIClient {
    private static volatile APIClient instance;

    private APIClient(){}

    /**
     * @return a new instance of the APIClient
     */
    public static APIClient getInstance(){
        if(instance == null){
            synchronized (APIClient.class){
                if(instance == null) instance = new APIClient();
            }
        }
        return instance;
    }

    /**
     * Creates a new API call and executes it
     * @param email
     * @param password
     * @param listener
     */
    public void logIn(String email, String password, APIResponseListener listener){
        //TODO implement
        listener.onResponse(false);
    }


    //TODO implement methods for other calls

    /**
     * Registers a new user
     * @param email
     * @param password
     * @param name
     * @param dateOfBirth
     * @param listener
     */
    public void register(String email, String password, String name, Date dateOfBirth, APIResponseListener listener){
        //TODO implement
    }

    /**
     * Makes the actual request in background
     */
    private synchronized void executeRequest(Request request, Callback callback){
        //TODO set up the connection, create request, pass raw response to callback

        callback.onResponse("Raw response goes in here");
    }

    private interface Callback{
        void onResponse(String... response);
    }
}
