package com.github.myhealth.api.request;

import android.content.Context;
import android.util.Log;


import com.github.myhealth.api.Bill;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.github.myhealth.Const.LOG_TAG;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class CreateBillRequest extends PostRequest {
    private final String userId;
    private final String status;
    private final List<Bill.Line> lines;

    public CreateBillRequest(String userId, String status, List<Bill.Line> lines) {
        super("bill/");
        this.userId = userId;
        this.status = status;
        this.lines = lines;
    }

    @Override
    protected JSONObject buildPostData() throws JSONException {
            return new JSONObject()
                .accumulate("userid", userId)
                .accumulate("status", status)
                .accumulate("lines", Bill.Line.toJsonArray(lines));
    }
}
