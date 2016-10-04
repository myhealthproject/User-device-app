package com.github.myhealth.api;

import android.content.Context;
import android.util.Log;

import com.github.myhealth.Const;
import com.github.myhealth.api.response.AlterBillResponse;
import com.github.myhealth.api.response.AlterUserResponse;
import com.github.myhealth.api.response.CreateBillResponse;
import com.github.myhealth.api.response.DeleteBillResponse;
import com.github.myhealth.api.response.DeleteUserResponse;
import com.github.myhealth.api.response.GetBillResponse;
import com.github.myhealth.api.response.GetBillsByUserIDResponse;
import com.github.myhealth.api.response.GetUserResponse;
import com.github.myhealth.api.response.LoginResponse;
import com.github.myhealth.api.response.CreateUserResponse;

import org.mockito.Mockito;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */
public class APIClientTest {
    private static final String URL = "http://henkdieter.com/api/";
    private static APIClient apiClient;

    private static final String TEST_USERNAME = "henk";
    private static final String TEST_PASSWORD = "Wachtwoord";

    private static final String TEST_FIRST_NAME = "test";
    private static final String TEST_LAST_NAME = "test";

    private static final String TEST_USER_ID = "57ecca82549b665082c734a4";
    private static final int TEST_BILL_ID = 1;
    private static final String TEST_BILL_STATUS = "paid";
    private static final List<Bill.Line> TEST_BILL_LINES = new ArrayList<Bill.Line>();

    static {
        try {
            TEST_BILL_LINES.add(new Bill.Line("test description", "TESTCODE", 12.3));
        } catch (Exception e){
            e.getMessage();
        }
    }

    @BeforeClass
    public static void setUp() {
        apiClient = TestAPIClient.getInstance(URL);
        try {
//            apiClient.createUser(TEST_USERNAME, TEST_PASSWORD, TEST_FIRST_NAME, TEST_LAST_NAME);
            apiClient.logIn(TEST_USERNAME, TEST_PASSWORD);
        } catch (InvalidRequestException e) {
            Log.d(Const.LOG_TAG, "InvalidRequestException: " +  e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(Const.LOG_TAG, "IOException: " +  e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void getInstance() throws Exception {
        assertTrue(TestAPIClient.getInstance(URL) == TestAPIClient.getInstance(URL));
    }

    @Test
    public void logIn() throws Exception {
        LoginResponse response = apiClient.logIn(TEST_USERNAME, TEST_PASSWORD);
        assertTrue(response.isSuccess());
        assertFalse(response.getToken().isEmpty());
        assertFalse(response.getUserId().isEmpty());
    }

    @Test
    public void createUser() throws Exception {
        CreateUserResponse response = apiClient.createUser("meneeraart", "sesamstraat", "aart", "test");
        assertTrue(response.isSuccess());
        //TODO check that user is actually inserted
    }

    @Test
    public void getUser() throws Exception {
        GetUserResponse response = apiClient.getUser(TEST_USER_ID);
        assertTrue(response.isSuccess());
        assertFalse(response.getFirstName().isEmpty());
        assertFalse(response.getLastName().isEmpty());
        assertNotEquals(null, response.getUserId());

        assertEquals(response.getFirstName(), TEST_FIRST_NAME);
        assertEquals(response.getLastName(), TEST_LAST_NAME);
    }
    
    @Test
    public void alterUser() throws Exception {
        AlterUserResponse response = apiClient.alterUser(TEST_USER_ID, TEST_USERNAME, TEST_PASSWORD, "John", "Doe");
        assertTrue(response.isSuccess());
        GetUserResponse checkResponse = apiClient.getUser(TEST_USER_ID);
        assertEquals(checkResponse.getUserId(), TEST_USER_ID);
        assertEquals(checkResponse.getFirstName(), "John");
        assertEquals(checkResponse.getLastName(), "Doe");
        apiClient.alterUser(TEST_USER_ID, TEST_USERNAME, TEST_PASSWORD, TEST_FIRST_NAME, TEST_LAST_NAME);
    }

    @Test
    public void deleteUser() throws Exception {
        DeleteUserResponse response = apiClient.deleteUser(TEST_USER_ID);
        GetUserResponse checkResponse = apiClient.getUser(TEST_USER_ID);
        assertFalse(checkResponse.isSuccess());
        apiClient.createUser(TEST_USERNAME, TEST_PASSWORD, TEST_FIRST_NAME, TEST_LAST_NAME);
    }

    @Test
    public void getBill() throws Exception {
        GetBillResponse response = apiClient.getBill(TEST_BILL_ID);
        assertTrue(response.isSuccess());
        assertEquals(response.getId(), TEST_BILL_ID);
    }

    @Test
    public void getBillsByUserID() throws Exception {
        GetBillsByUserIDResponse response = apiClient.getBillsByUserID(TEST_USER_ID);
        assertTrue(response.isSuccess());
        for(Bill bill : response.toBillList()){
            assertEquals(bill.getUserId(), TEST_USER_ID);
        }
    }

    @Test
    public void createBill() throws Exception {
        CreateBillResponse response = apiClient.createBill(TEST_USER_ID, TEST_BILL_STATUS, TEST_BILL_LINES);
        assertTrue(response.isSuccess());
        //TODO check that the bill has actually been created
    }

    @Test
    public void alterBill() throws Exception {
        AlterBillResponse response = apiClient.alterBill(TEST_BILL_ID, TEST_USER_ID, "unpaid", TEST_BILL_LINES);
        assertTrue(response.isSuccess());
        GetBillResponse checkResponse = apiClient.getBill(TEST_BILL_ID);
        assertEquals(checkResponse.getStatus(), "unpaid");
        apiClient.alterBill(TEST_BILL_ID, TEST_USER_ID, TEST_BILL_STATUS, TEST_BILL_LINES);
    }

    @Test
    public void deleteBill() throws Exception {
        DeleteBillResponse response = apiClient.deleteBill(TEST_BILL_ID);
        assertTrue(response.isSuccess());
        GetBillResponse checkResponse = apiClient.getBill(TEST_BILL_ID);
        assertFalse(checkResponse.isSuccess());
        apiClient.createBill(TEST_USER_ID, TEST_BILL_STATUS, TEST_BILL_LINES);
    }

    private static class TestAPIClient extends APIClient {
        private  static TestAPIClient instance;
        protected TestAPIClient(String apiURL) {
            super(apiURL);
        }

        public static TestAPIClient getInstance(String apiURL){
            if (instance == null) {
                synchronized (APIClient.class) {
                    if (instance == null) {
                        instance = new TestAPIClient(apiURL);
                    }
                }
            }
            return instance;
        }
    }

}