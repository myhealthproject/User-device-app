package com.github.myhealth.api.request;

import android.content.Context;


import com.github.myhealth.api.Bill;

import java.util.List;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class AlterBillRequest extends PostRequest {
    private final String userId;
    private final Bill.Status status;
    private final List<Bill.Line> lines;

    public AlterBillRequest(int billId, String userId, Bill.Status status, List<Bill.Line> lines) {
        super("bill/"+billId);
        this.userId = userId;
        this.status = status;
        this.lines = lines;
    }

    @Override
    protected String buildPostData() {
        return null;
    }
}
