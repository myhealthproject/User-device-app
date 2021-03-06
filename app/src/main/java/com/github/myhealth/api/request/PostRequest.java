package com.github.myhealth.api.request;

import android.util.Log;

import com.github.myhealth.Const;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

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
    protected abstract JSONObject buildPostData() throws JSONException;

    @Override
    public String execute(String apiURL, String token) throws IOException {
        HttpURLConnection connection = null;
        String response = null;
        try {
            connection = setUpConnection(apiURL, token);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(buildPostData().toString());
            wr.flush();
            wr.close();
            InputStream is = connection.getInputStream();
            response = IOUtils.toString(is);
        } finally {
            connection.disconnect();
            return response;
        }
    }
}
