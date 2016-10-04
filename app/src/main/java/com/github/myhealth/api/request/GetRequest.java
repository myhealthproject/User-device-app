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
        URL url = new URL(apiURL + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream in = new BufferedInputStream(connection.getInputStream());
        String result = IOUtils.toString(in, "UTF-8");
        Log.d(Const.LOG_TAG, result);
        return result;
    }

}
