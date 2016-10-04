package com.github.myhealth.api.response;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static com.github.myhealth.Const.LOG_TAG;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */
public class CreateBillResponse extends APIResponse {
    private String billId;
    public CreateBillResponse(String raw) {
        super(raw);
    }

    public String getBillId() {
        return billId;
    }

    @Override
    protected void parseRawResponse(String raw) {
        Log.d(LOG_TAG, "CREATE BILL: " + raw);
        try {
            JSONObject json = new JSONObject(raw);
            billId = json.getString("_id");
            success = true;
        } catch (JSONException e) {
            success = false;
        }
    }
}
