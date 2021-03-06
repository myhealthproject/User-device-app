package com.github.myhealth.api.response;

import android.util.Log;

import com.github.myhealth.api.Bill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.List;

import static com.github.myhealth.Const.LOG_TAG;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */
public class GetBillResponse extends APIResponse {
    private Bill bill;

    public GetBillResponse(String raw) {
        super(raw);
    }

    public String getBillId() {
        return bill.getId();
    }

    public String getUserId(){
        return bill.getUserId();
    }

    public String getStatus(){
        return bill.getStatus();
    }

    public Bill toBill(){
        return bill;
    }

    public List<Bill.Line> getLines(){
        return bill.getLines();
    }

    @Override
    protected void parseRawResponse(String raw) {
        try {
            JSONObject json = new JSONObject(raw);
            String billId = json.getString("_id");
            String userId = json.getString("userid");
            String status = json.getString("status");
            JSONArray lines = json.getJSONArray("lines");
            bill = new Bill(billId,userId,status,lines);
            success = true;
        } catch (JSONException e) {
            success = false;
        }
    }
}
