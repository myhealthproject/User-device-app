package com.github.myhealth.api.request;

import android.util.Log;

import com.github.myhealth.Const;

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


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
        URL url;
        HttpURLConnection connection = null;
        String response = null;
        try {
            url = new URL(apiURL + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.connect();

            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream ());
            wr.writeBytes(buildPostData());
            wr.flush();
            wr.close ();
            InputStream is = connection.getInputStream();
            response = IOUtils.toString(is);
        } finally {
            connection.disconnect();
            return response;
        }
    }
}
