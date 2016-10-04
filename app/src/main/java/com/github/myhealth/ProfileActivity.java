package com.github.myhealth;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.myhealth.api.APIClient;
import com.github.myhealth.api.response.GetUserResponse;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        userId = intent.getStringExtra(LoginActivity.EXTRA_USER_ID);
        new GetProfileTask().execute(userId);
        //TODO show load icon
    }

    private void completeProfilePage(String firstName, String lastName){
        TextView firstNameView = (TextView) findViewById(R.id.f_name);
        TextView lastNameView = (TextView) findViewById(R.id.l_name);
        firstNameView.setText(firstName);
        lastNameView.setText(lastName);
    }

    private class GetProfileTask extends AsyncTask<String, Void, GetUserResponse> {
        @Override
        protected void onPostExecute(GetUserResponse getUserResponse) {
            super.onPostExecute(getUserResponse);
            if(getUserResponse != null && getUserResponse.isSuccess()) completeProfilePage(getUserResponse.getFirstName(), getUserResponse.getLastName());
            else Log.d(Const.LOG_TAG, "Get user not successful");
        }

        @Override
        protected GetUserResponse doInBackground(String... params) {
            APIClient apiClient = APIClient.getInstance();
            try {
                return apiClient.getUser(params[0]);
            } catch (IOException e) {
                return null;
            }
        }
    }
}
