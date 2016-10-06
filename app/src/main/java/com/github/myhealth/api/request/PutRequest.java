package com.github.myhealth.api.request;

import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static com.github.myhealth.Const.LOG_TAG;

/**
 * Created by Henk Dieter Oordt on 6-10-2016.
 */

public abstract class PutRequest extends APIRequest {
    protected PutRequest(String path) {
        super(path);
    }

    @Override
    public String execute(String apiURL, String token) throws IOException {
        HttpURLConnection connection = null;
        String response = null;
        try {
            connection = setUpConnection(apiURL, token);
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(buildPostData().toString());
            Log.d(LOG_TAG, "POST REQUEST TO " + connection.getURL().toString() + " DATA: " + buildPostData());
            wr.flush();
            wr.close();
            InputStream is = connection.getInputStream();
            response = IOUtils.toString(is);
            Log.d(LOG_TAG, "POST REQUEST TO " + connection.getURL().toString() + " RESPONSE: " + response);
        } finally {
            connection.disconnect();
            return response;
        }
    }

    protected abstract JSONObject buildPostData() throws JSONException;
}
