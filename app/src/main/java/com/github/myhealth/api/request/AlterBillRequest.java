package com.github.myhealth.api.request;

import android.content.Context;


import com.github.myhealth.api.Bill;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class AlterBillRequest extends PutRequest {
    private final String userId;
    private final String status;
    private final List<Bill.Line> lines;

    public AlterBillRequest(String billId, String userId, String status, List<Bill.Line> lines) {
        super("bill/"+billId);
        this.userId = userId;
        this.status = status;
        this.lines = lines;
    }

    @Override
    protected JSONObject buildPostData() {
        return null;
    }
}
