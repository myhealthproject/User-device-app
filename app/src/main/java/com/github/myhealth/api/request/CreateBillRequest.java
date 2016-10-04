package com.github.myhealth.api.request;

import android.content.Context;
import android.util.Log;


import com.github.myhealth.api.Bill;

import java.util.List;

import static com.github.myhealth.Const.LOG_TAG;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class CreateBillRequest extends PostRequest {
    private final String userId;
    private final Bill.Status status;
    private final List<Bill.Line> lines;

    public CreateBillRequest(String userId, Bill.Status status, List<Bill.Line> lines) {
        super("bill/");
        this.userId = userId;
        this.status = status;
        this.lines = lines;
    }

    @Override
    protected String buildPostData() {
        StringBuilder builder = new StringBuilder("userid="+userId+"&status="+status.value);
        int count = 0;
        for(Bill.Line line : lines){
            builder.append("&line["+count+"][description]="+line.description);
            builder.append("&line["+ count + "][code]="+line.code);
            builder.append("&line["+ count++ + "][price]="+line.price);
        }
        Log.d(LOG_TAG, builder.toString());
        return builder.toString();
    }
}
