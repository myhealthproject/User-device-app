package com.github.myhealth.api.request;

import com.github.myhealth.api.Bill;

import java.util.List;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class CreateBillRequest extends PostRequest {
    private final int userId;
    private final String status;
    private final List<Bill.Line> lines;

    public CreateBillRequest(int userId, String status, List<Bill.Line> lines) {
        super("bill/");
        this.userId = userId;
        this.status = status;
        this.lines = lines;
    }
}
