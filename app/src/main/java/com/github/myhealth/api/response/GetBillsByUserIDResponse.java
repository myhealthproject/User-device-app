package com.github.myhealth.api.response;

import com.github.myhealth.api.Bill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */
public class GetBillsByUserIDResponse extends APIResponse {
    public GetBillsByUserIDResponse(String raw) {
        super(raw);
    }

    public List<Bill> toBillList(){
        return new ArrayList<Bill>();
    }

    @Override
    protected void parseRawResponse(String raw) {

    }
}
