package com.github.myhealth.api.request;

import android.content.Context;



/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class GetBillRequest extends GetRequest {
    public GetBillRequest(String billId) {
        super("bill/" + billId);
    }
}
