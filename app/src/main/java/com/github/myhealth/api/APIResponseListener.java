package com.github.myhealth.api;

/**
 * Created by henkd on 26-9-2016.
 */

public interface APIResponseListener <T> {
    void onResponse(T... response);
}
