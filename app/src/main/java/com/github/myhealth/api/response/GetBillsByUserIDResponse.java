package com.github.myhealth.api.response;

import android.util.Log;

import com.github.myhealth.api.Bill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.github.myhealth.Const.LOG_TAG;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */
public class GetBillsByUserIDResponse extends APIResponse {
    private List<Bill> bills;
    public GetBillsByUserIDResponse(String raw) {
        super(raw);
    }

    public List<Bill> toBillList(){
        return bills;
    }

    @Override
    protected void parseRawResponse(String raw) {
        try {
            JSONObject json = new JSONObject(raw);
            bills = new ArrayList<>();
            JSONArray jsonBills = json.getJSONArray("bills");
            for(int i = 0; i < jsonBills.length(); i++){
                JSONObject jsonBill = jsonBills.getJSONObject(i);
                bills.add(new Bill(jsonBill.getString("_id"),
                        jsonBill.getString("userid"),
                        jsonBill.getString("status"),
                        jsonBill.getJSONArray("lines")));
            }
            success = true;
        } catch (JSONException e) {
            e.printStackTrace();
            success = false;
        }
    }
}
