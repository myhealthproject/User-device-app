package com.github.myhealth.api.request;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */

public class DeleteBillRequest extends DeleteRequest {
    public DeleteBillRequest(int billId) {
        super("bill/" + billId);
    }
}
