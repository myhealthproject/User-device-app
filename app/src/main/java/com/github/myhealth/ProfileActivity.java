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

    // TODO: Use real implementation instead of this mocked object
    private ArrayList<Bill> mBillList;
    {
        ArrayList<Bill.Line> mLineList = new ArrayList<>();
        mLineList.add(new Bill.Line("test description", "TESTCODE", 12.3));
        mLineList.add(new Bill.Line("test description", "TESTCODE", 12.3));
        mLineList.add(new Bill.Line("test description", "TESTCODE", 12.3));

        mBillList = new ArrayList<>();
        mBillList.add(new Bill("57f4001464ae271261d85b49", userId, "paid", mLineList));

        ArrayList<Bill.Line> mLineList2 = new ArrayList<>();
        mLineList2.add(new Bill.Line("test description2", "TESTCODE2", 54.2));
        mLineList2.add(new Bill.Line("test description2", "TESTCODE2", 54.2));
        mLineList2.add(new Bill.Line("test description2", "TESTCODE2", 54.2));

        mBillList.add(new Bill("92t7001464a3245171242319", userId, "paid", mLineList2));

        ArrayList<Bill.Line> mLineList3 = new ArrayList<>();
        mLineList3.add(new Bill.Line("test description3", "TESTCODE3", 58.8));
        mLineList3.add(new Bill.Line("test description3", "TESTCODE3", 58.8));
        mLineList3.add(new Bill.Line("test description3", "TESTCODE3", 58.8));

        mBillList.add(new Bill("92t7001464a3245171242319", userId, "paid", mLineList3));
        mBillList.add(new Bill("92t7001464a3245171242319", userId, "paid", mLineList3));
        mBillList.add(new Bill("92t7001464a3245171242319", userId, "paid", mLineList3));
        mBillList.add(new Bill("92t7001464a3245171242319", userId, "paid", mLineList3));
        mBillList.add(new Bill("92t7001464a3245171242319", userId, "paid", mLineList3));
        mBillList.add(new Bill("92t7001464a3245171242319", userId, "paid", mLineList3));
    }

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

        initAdapter(); // TODO: Remove this from here, it will be done in GetBillsTask.
        // TODO: Use the real implementation instead of mocked mBillList
        //mTask = new GetBillsTask(userId, this);
        //mTask.execute((Void) null);

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


            if (response.isSuccess()) {
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

