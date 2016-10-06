package com.github.myhealth;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.myhealth.api.APIClient;
import com.github.myhealth.api.Bill;
import com.github.myhealth.api.request.GetBillsByUserIdRequest;
import com.github.myhealth.api.response.GetBillsByUserIDResponse;
import com.github.myhealth.api.response.GetUserResponse;
import com.github.myhealth.api.response.LoginResponse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private AsyncTask<Void, Void, Void> mTask = null;

    private String userId;

    private String mFirstName;
    private String mLastName;
    private String mUserName;

    private ArrayList<Bill> mBillList;


    private void initValues() {
        ((TextView) findViewById(R.id.full_name)).setText(getString(R.string.profile_full_name) + mFirstName + " " + mLastName);
        ((TextView) findViewById(R.id.user_name)).setText(getString(R.string.profile_username) + mUserName);
    }

    private void initAdapter() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.bill_list);

        recyclerView.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        BillsAdapter billsAdapter = new BillsAdapter(this, mBillList);
        recyclerView.setAdapter(billsAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        userId = intent.getStringExtra(LoginActivity.EXTRA_USER_ID);

        mTask = new GetUserTask(userId, this);
        mTask.execute((Void) null);

        mTask = new GetBillsTask(userId, this);
        mTask.execute((Void) null);

    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class GetUserTask extends AsyncTask<Void, Void, Void> {

        private final String mId;
        private final Context mContext;
        private GetUserResponse response;

        GetUserTask(String id, Context context) {
            mId = id;
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            APIClient client = APIClient.getInstance();

            try {
                response = client.getUser(mId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void param) {
            mTask = null;

            if (response.isSuccess()) {
                mFirstName = response.getFirstName();
                mLastName = response.getLastName();
                mUserName = response.getUsername();
                initValues();

                // TODO: Remove these debug lines
                Log.d(TAG, "getFirstName: " + response.getFirstName());
                Log.d(TAG, "getLastName: " + response.getLastName());
                Log.d(TAG, "getPassword " + response.getPassword());
                Log.d(TAG, "getUserId: " + response.getUserId());
                Log.d(TAG, "getUsername: " + response.getUsername());
            } else {
                Toast.makeText(mContext, "Failed to load user data.", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mTask = null;
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class GetBillsTask extends AsyncTask<Void, Void, Void> {

        private final String mId;
        private final Context mContext;
        private GetBillsByUserIDResponse response;

        GetBillsTask(String id, Context context) {
            mId = id;
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            APIClient client = APIClient.getInstance();

            try {
                response = client.getBillsByUserID(mId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void param) {
            mTask = null;



            if ((response != null) && response.isSuccess()) {
                mBillList = (ArrayList) response.toBillList();
                initAdapter();

                // TODO: Remove this, its just here to debug the values atm
                Iterator it = mBillList.iterator();
                while (it.hasNext()) {
                    Bill bill = (Bill) it.next();
                    Log.d(TAG, "bill: " + bill.getId());
                    Log.d(TAG, "bill status: " + bill.getStatus());
                    Log.d(TAG, "Lines size: " + bill.getLines().size());
                }
            } else {
                Toast.makeText(mContext, "Failed to load bill data.", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mTask = null;
        }
    }
}

