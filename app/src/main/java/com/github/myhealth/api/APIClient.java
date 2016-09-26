package com.github.myhealth.api;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */


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

    public synchronized void logIn(String email, String password, APIResponseListener listener){
        //TODO implement

        listener.onResponse(false);
    }

}
