package com.github.myhealth.api.request;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class GetBillsByUserIdRequest extends GetRequest {
    public GetBillsByUserIdRequest(int userId) {
        super("billsbyuserid" + userId);
    }
}
