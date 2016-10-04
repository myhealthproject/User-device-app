package com.github.myhealth.api.request;


/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class GetBillsByUserIdRequest extends GetRequest {
    public GetBillsByUserIdRequest(String userId) {
        super("billsbyuserid/" + userId);
    }
}
