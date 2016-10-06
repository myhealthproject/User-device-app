package com.github.myhealth.api.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

/**
 * Contains all information needed to make a request
 */
public abstract class APIRequest {

    public final String path;

    protected APIRequest(String path) {
        this.path = path;
    }
    /**
     * Opens the connection, sends the request and returns the raw response
     * @return
     */
    public abstract String execute(String apiURL, String token) throws IOException;

    public HttpURLConnection setUpConnection(String apiURL, String token) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(apiURL+path).openConnection();
        if(token != null) connection.setRequestProperty("token", token);
        return connection;
    }
}
