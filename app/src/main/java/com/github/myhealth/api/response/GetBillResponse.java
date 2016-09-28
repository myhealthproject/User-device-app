package com.github.myhealth.api.response;

import com.github.myhealth.api.Bill;

import java.text.ParseException;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */
public class GetBillResponse extends APIResponse {
    private String id, userId;
    private String status;
    private String[][] lines;

    public GetBillResponse(String raw) {
        super(raw);
    }

    public Bill toBill() throws ParseException {
        return new Bill(id, userId, status, lines);
    }

    @Override
    protected void parseRawResponse(String raw) {

    }

    public String getId() {
        return id;
    }

    public String getUserID() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public String[][] getLines() {
        return lines;
    }
}
