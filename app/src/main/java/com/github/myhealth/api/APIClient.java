package com.github.myhealth.api;

/**
 * Created by Henk Dieter Oordt on 26-9-2016.
 */

import static com.github.myhealth.Const.API_URL;
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
import com.github.myhealth.api.request.APIRequest;
import com.github.myhealth.api.response.CreateUserResponse;

import java.io.IOException;
import java.util.List;

/**
 * Class used to communicate with the API.
 * This is a singleton class, because we want to talk to the api one request at a time.
 * Please note that all requests will block the requesting thread, so that they should be made
 * from another thread than the main thread, e.g. by an AsyncTask
 *
 * @author Henk Dieter Oordt
 */
public class APIClient {
    protected static volatile APIClient instance;

    private static String apiURL;

    //Protected for test purposes
    protected APIClient(String apiURL) {
        this.apiURL = apiURL;
    }

    private String token = null;

    /**
     * @return a new instance of the APIClient
     */
    public static APIClient getInstance() {
        if (instance == null) {
            synchronized (APIClient.class) {
                if (instance == null) {
                    //The default API url is extracted from a string resource, so that it can be accessed easily
                    instance = new APIClient(API_URL);
                }
            }
        }
        return instance;
    }

    /**
     * Creates a new API call, executes and parses its response, after which it returns the response
     * PLEASE NOTE: This method should be called before any other request methods, because the APIToken must be retrieved
     *
     * @param email
     * @param password
     * @return The parsed response
     * @throws IllegalStateException, InvalidRequestException
     */
    public synchronized LoginResponse logIn(String email, String password) throws InvalidRequestException, IOException {
        if (email.isEmpty()) throw new InvalidRequestException("No email given");
        if (password.isEmpty()) throw new InvalidRequestException("No password given");
        LoginResponse response = new LoginResponse(new LoginRequest(email, password).execute(apiURL, token));
        if (response.isSuccess()) token = response.getToken();
        return response;
    }

    /**
     * Registers a new user
     *
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @return The parsed response
     * @throws IllegalStateException, InvalidRequestException
     */
    public CreateUserResponse createUser(String username, String password, String firstName, String lastName) throws IllegalStateException, InvalidRequestException, IOException {
        if (username.isEmpty()) throw new InvalidRequestException("No username given");
        if (password.isEmpty()) throw new InvalidRequestException("No password given");
        if (firstName.isEmpty()) throw new InvalidRequestException("No first name given");
        if (lastName.isEmpty()) throw new InvalidRequestException("No last name given");
        return new CreateUserResponse(executeRequest(new CreateUserRequest(username, password, firstName, lastName)));
    }

    /**
     * Gets user details
     *
     * @param userId
     * @return
     */
    public GetUserResponse getUser(String userId) throws IllegalStateException, IOException {
        return new GetUserResponse(executeRequest(new GetUserRequest(userId)));
    }

    /**
     * Alters a user
     *
     * @param userId
     * @param firstName
     * @param lastName
     * @return
     * @throws IllegalStateException, InvalidRequestException
     */
    public AlterUserResponse alterUser(String userId, String username, String password, String firstName, String lastName) throws IllegalStateException, InvalidRequestException, IOException {
        if (username.isEmpty()) throw new InvalidRequestException("No username given");
        if (password.isEmpty()) throw new InvalidRequestException("No password given");
        if (firstName.isEmpty()) throw new InvalidRequestException("No first name given");
        if (lastName.isEmpty()) throw new InvalidRequestException("No last name given");
        return new AlterUserResponse(executeRequest(new AlterUserRequest(userId, username, password, firstName, lastName)));
    }

    /**
     * Deletes a user
     *
     * @param userId
     * @return
     */
    public DeleteUserResponse deleteUser(String userId) throws IllegalStateException, IOException {
        return new DeleteUserResponse(executeRequest(new DeleteUserRequest(userId)));
    }

    /**
     * Gets a bill
     *
     * @param billId
     * @return
     * @throws IllegalStateException, InvalidRequestException
     */
    public GetBillResponse getBill(String billId) throws IllegalStateException, IOException {
        return new GetBillResponse(executeRequest(new GetBillRequest(billId)));
    }

    /**
     * Gets bills by user ID
     *
     * @param userId
     * @return
     */
    public GetBillsByUserIDResponse getBillsByUserID(String userId) throws IllegalStateException, IOException {
        return new GetBillsByUserIDResponse(executeRequest(new GetBillsByUserIdRequest(userId)));
    }

    /**
     * Creates a bill
     *
     * @param userId
     * @param status
     * @param lines
     * @return
     */
    public CreateBillResponse createBill(String userId, String status, List<Bill.Line> lines) throws IllegalStateException, InvalidRequestException, IOException {
        if (lines == null) throw new InvalidRequestException("lines == null");
        if (lines.size() == 0) throw new InvalidRequestException("No lines given");
        return new CreateBillResponse(executeRequest(new CreateBillRequest(userId, status, lines)));
    }

    /**
     * Alters a bill
     *
     * @param billId
     * @param userId
     * @param status
     * @param lines
     * @return
     * @throws IllegalStateException, InvalidRequestException
     */
    public AlterBillResponse alterBill(String billId, String userId, String status, List<Bill.Line> lines) throws IllegalStateException, IOException, InvalidRequestException {
        if (lines == null) throw new InvalidRequestException("lines == null");
        if (lines.size() == 0) throw new InvalidRequestException("No lines given");
        return new AlterBillResponse(executeRequest(new AlterBillRequest(billId, userId, status, lines)));
    }

    /**
     * Deletes a bill
     *
     * @param billId
     * @return
     */
    public DeleteBillResponse deleteBill(String billId) throws IllegalStateException, IOException {
        return new DeleteBillResponse(executeRequest(new DeleteBillRequest(billId)));
    }

    /**
     * Sends the actual request
     *
     * @param request
     */
    private synchronized String executeRequest(APIRequest request) throws IllegalStateException, IOException {
        if (token == null) throw new IllegalStateException("Please log in first");
        return request.execute(apiURL, token);
    }
}
