package com.github.myhealth.api.request;

import android.util.Log;

import com.github.myhealth.Const;

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.github.myhealth.Const.LOG_TAG;


/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */
public abstract class PostRequest extends APIRequest {
    protected PostRequest(String path) {
        super(path);
    }

    /**
     * Builds post data
     * @return
     */
    protected abstract String buildPostData();

    @Override
    public String execute(String apiURL, String token) throws IOException {
        HttpURLConnection connection = null;
        String response = null;
        try {
            connection = setUpConnection(apiURL, token);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.connect();

            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(buildPostData());
            Log.d(LOG_TAG, "POST REQUEST TO " + connection.getURL().toString() + " DATA: " + buildPostData());
            wr.flush();
            wr.close();
            InputStream is = connection.getInputStream();
            response = IOUtils.toString(is);
            Log.d(LOG_TAG, "POST REQUEST TO " + connection.getURL().toString() + " RESPONSE: " + response);
        } catch (Exception e){
            Log.d(LOG_TAG, "POST REQUEST TO " + apiURL+path + " EXCEPTION: " + Log.getStackTraceString(e));
        } finally {
            connection.disconnect();
            return response;
        }
    }
}
