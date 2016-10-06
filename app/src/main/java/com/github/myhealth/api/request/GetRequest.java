package com.github.myhealth.api.request;

import android.util.Log;

import com.github.myhealth.Const;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

public class GetRequest extends APIRequest {
    protected GetRequest(String path) {
        super(path);
    }

    @Override
    public String execute(String apiURL, String token) throws IOException {
        HttpURLConnection connection = setUpConnection(apiURL, token);
        InputStream in = connection.getInputStream();
        String result = IOUtils.toString(in, "UTF-8");
        return result;
    }

}
