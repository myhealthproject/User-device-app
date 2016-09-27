package com.github.myhealth.api;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

import android.content.res.Resources;

import com.github.myhealth.R;
import com.github.myhealth.api.request.AlterBillRequest;
import com.github.myhealth.api.request.AlterUserRequest;
import com.github.myhealth.api.request.CreateBillRequest;
import com.github.myhealth.api.request.CreateUserRequest;
import com.github.myhealth.api.request.DeleteBillRequest;
import com.github.myhealth.api.request.DeleteUserRequest;
import com.github.myhealth.api.request.GetBillRequest;
import com.github.myhealth.api.request.GetBillsByUserIdRequest;
import com.github.myhealth.api.request.GetUserRequest;
import com.github.myhealth.api.request.LoginRequest;
import com.github.myhealth.api.response.AlterBillResponse;
import com.github.myhealth.api.response.AlterUserResponse;
import com.github.myhealth.api.response.CreateBillResponse;
import com.github.myhealth.api.response.DeleteBillResponse;
import com.github.myhealth.api.response.DeleteUserResponse;
import com.github.myhealth.api.response.GetBillResponse;
import com.github.myhealth.api.response.GetBillsByUserIDResponse;
import com.github.myhealth.api.response.GetUserResponse;
import com.github.myhealth.api.response.LoginResponse;
import com.github.myhealth.api.request.Request;
import com.github.myhealth.api.response.CreateUserResponse;

import java.io.InputStream;
import java.util.List;

/**
 * This is a singleton class, because we want to talk to the api one request at a time
 */
public class APIClient {
    protected static volatile APIClient instance;


    /**
     * The default API url is extracted from a string resource, so that it can be accessed easily
     */

    private static String apiURL;

    //Protected for test purposes
    protected APIClient(String apiURL) {
        this.apiURL = apiURL;
    }

    /**
     * @return a new instance of the APIClient
     */
    public static APIClient getInstance() {
        if (instance == null) {
            synchronized (APIClient.class) {
                if (instance == null) {
                    instance = new APIClient(Resources.getSystem().getString(R.string.api_url));
                }
            }
        }
        return instance;
    }

    /**
     * Creates a new API call, executes and parses its response, after which it returns the response
     *
     * @param email
     * @param password
     * @return The parsed response
     * @throws InvalidRequestException
     */
    public LoginResponse logIn(String email, String password) throws InvalidRequestException {
        if(email.isEmpty()) throw new InvalidRequestException("No email given");
        if(password.isEmpty()) throw new InvalidRequestException("No password given");
        return new LoginResponse(executeRequest(new LoginRequest(email, password)));
    }


    //TODO implement methods for other calls (They should all throw an InvalidRequestException if the parameters are incomplete or incorrect)

    /**
     * Registers a new user
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @return The parsed response
     * @throws InvalidRequestException
     */
    public CreateUserResponse createUser(String username, String password, String firstName, String lastName) throws InvalidRequestException {
        if(username.isEmpty()) throw new InvalidRequestException("No username given");
        if(password.isEmpty()) throw new InvalidRequestException("No password given");
        if(firstName.isEmpty()) throw new InvalidRequestException("No first name given");
        if(lastName.isEmpty()) throw new InvalidRequestException("No last name given");
        return new CreateUserResponse(executeRequest(new CreateUserRequest(username, password, firstName, lastName)));
    }

    /**
     * Gets user details
     * @param userId
     * @return
     */
    public GetUserResponse getUser(int userId) {
        return new GetUserResponse(executeRequest(new GetUserRequest(userId)));
    }

    /**
     * Alters a user
     * @param userId
     * @param firstName
     * @param lastName
     * @return
     * @throws InvalidRequestException
     */
    public AlterUserResponse alterUser(int userId, String username, String password, String firstName, String lastName) throws InvalidRequestException {
        if(username.isEmpty()) throw new InvalidRequestException("No username given");
        if(password.isEmpty()) throw new InvalidRequestException("No password given");
        if(firstName.isEmpty()) throw new InvalidRequestException("No first name given");
        if(lastName.isEmpty()) throw new InvalidRequestException("No last name given");
        return new AlterUserResponse(executeRequest(new AlterUserRequest(userId, username, password, firstName, lastName)));
    }

    /**
     * Deletes a user
     * @param userId
     * @return
     */
    public DeleteUserResponse deleteUser(int userId) {
        return new DeleteUserResponse(executeRequest(new DeleteUserRequest(userId)));
    }

    /**
     * Gets a bill
     * @param billId
     * @return
     * @throws InvalidRequestException
     */
    public GetBillResponse getBill(int billId) {
        return new GetBillResponse(executeRequest(new GetBillRequest(billId)));
    }

    /**
     * Gets bills by user ID
     * @param userId
     * @return
     */
    public GetBillsByUserIDResponse getBillsByUserID(int userId) {
        return new GetBillsByUserIDResponse(executeRequest(new GetBillsByUserIdRequest(userId)));
    }

    /**
     * Creates a bill
     * @param userId
     * @param status
     * @param lines
     * @return
     */
    public CreateBillResponse createBill(int userId, String status, List<Bill.Line> lines) throws InvalidRequestException {
        if(status.isEmpty()) throw new InvalidRequestException("No status given");
        if(lines == null) throw new InvalidRequestException("lines == null");
        if(lines.size() == 0) throw new InvalidRequestException("No lines given");
        return new CreateBillResponse(executeRequest(new CreateBillRequest(userId, status, lines)));
    }

    /**
     * Alters a bill
     * @param billId
     * @param userId
     * @param status
     * @param lines
     * @return
     * @throws InvalidRequestException
     */
    public AlterBillResponse alterBill(int billId, int userId, String status, List<Bill.Line> lines) throws InvalidRequestException{
        if(status.isEmpty()) throw new InvalidRequestException("No status given");
        if(lines == null) throw new InvalidRequestException("lines == null");
        if(lines.size() == 0) throw new InvalidRequestException("No lines given");
        return new AlterBillResponse(executeRequest(new AlterBillRequest(billId, userId, status, lines)));
    }

    /**
     * Deletes a bill
     * @param billId
     * @return
     */
    public DeleteBillResponse deleteBill(int billId) {
        return new DeleteBillResponse(executeRequest(new DeleteBillRequest(billId)));
    }

    /**
     * Sends the actual request
     *
     * @param request
     */
    private synchronized String executeRequest(Request request) {
        return request.execute();
    }

    private InputStream connect() {
        //TODO implement and have it return the connection's inputstream
        return null;
    }
}
