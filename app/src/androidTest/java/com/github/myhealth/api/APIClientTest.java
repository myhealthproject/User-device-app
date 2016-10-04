package com.github.myhealth.api;
import android.util.Log;

import static com.github.myhealth.Const.LOG_TAG;

import com.github.myhealth.api.request.CreateBillRequest;
import com.github.myhealth.api.request.GetBillRequest;
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

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Henk Dieter Oordt on 27-9-2016.
 */
public class APIClientTest {
    private static final String URL = "http://henkdieter.com/api/";
    private static APIClient apiClient;

    private static final String TEST_USERNAME = "henk";
    private static final String TEST_PASSWORD = "Wachtwoord";

    private static final String TEST_FIRST_NAME = "Henk Dieter";
    private static final String TEST_LAST_NAME = "Oordt";

    private static final String TEST_USER_ID = "57ecca82549b665082c734a4";
    private static final String TEST_BILL_ID = "57f3c32164ae271261d85aea";
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

        apiClient = APIClient.getInstance();
        try {
//            apiClient.createUser(TEST_USERNAME, TEST_PASSWORD, TEST_FIRST_NAME, TEST_LAST_NAME);
            apiClient.logIn(TEST_USERNAME, TEST_PASSWORD);
        } catch (InvalidRequestException e) {
            Log.d(LOG_TAG, "InvalidRequestException: " +  e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(LOG_TAG, "IOException: " +  e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void getInstance() throws Exception {
        assertTrue(APIClient.getInstance() == APIClient.getInstance());
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
        String mockUname = randomString(10);
        String mockFname = randomString(10);
        String mockLname = randomString(10);
        String mockPassword = "pass";
        CreateUserResponse response = apiClient.createUser(mockUname, mockPassword, mockFname, mockLname);
        assertTrue(response.isSuccess());
        GetUserResponse checkResponse = apiClient.getUser(response.getUserId());
        assertTrue(response.isSuccess());
        assertEquals(response.getUsername(), checkResponse.getUsername());
        assertEquals(response.getPassword(), checkResponse.getPassword());
        assertEquals(response.getFirstName(), checkResponse.getFirstName());
        assertEquals(response.getLastName(), checkResponse.getLastName());

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

    @Test(expected = FileNotFoundException.class)
    public void deleteUser() throws Exception {
        String mockUname = randomString(10);
        String mockFname = randomString(10);
        String mockLname = randomString(10);
        String mockPassword = "pass";
        String mockUserId = apiClient.createUser(mockUname, mockPassword, mockFname, mockLname).getUserId();
        DeleteUserResponse response = apiClient.deleteUser(mockUserId);

        GetUserResponse checkResponse = apiClient.getUser(mockUserId);
        assertFalse(checkResponse.isSuccess());
    }

    @Test
    public void getBill() throws Exception {
        GetBillResponse response = apiClient.getBill(TEST_BILL_ID);
        assertTrue(response.isSuccess());
        assertEquals(response.getBillId(), TEST_BILL_ID);
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
        try {
            CreateBillResponse response = apiClient.createBill(TEST_USER_ID, TEST_BILL_STATUS, TEST_BILL_LINES);
            assertTrue(response.isSuccess());
            GetBillResponse checkResponse = apiClient.getBill(response.getBillId());
            assertTrue(checkResponse.isSuccess());
        } catch (IOException e){
            Log.d(LOG_TAG, "CREATE BILL IOEXCEPTION: " + e.getMessage());
        }
    }

    @Test
    public void alterBill() throws Exception {
        AlterBillResponse response = apiClient.alterBill(TEST_BILL_ID, TEST_USER_ID, "unpaid", TEST_BILL_LINES);
        assertTrue(response.isSuccess());
        GetBillResponse checkResponse = apiClient.getBill(TEST_BILL_ID);
        assertEquals(checkResponse.getStatus(), "unpaid");
        apiClient.alterBill(TEST_BILL_ID, TEST_USER_ID, TEST_BILL_STATUS, TEST_BILL_LINES);
    }

    @Test(expected = FileNotFoundException.class)
    public void deleteBill() throws Exception {
        String billId = apiClient.createBill(TEST_USER_ID, TEST_BILL_STATUS, TEST_BILL_LINES).getBillId();
        DeleteBillResponse response = apiClient.deleteBill(billId);
        assertTrue(response.isSuccess());
        GetBillResponse checkResponse = apiClient.getBill(billId);
        assertFalse(checkResponse.isSuccess());

    }

    private String randomString(int lenght){
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < lenght; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}