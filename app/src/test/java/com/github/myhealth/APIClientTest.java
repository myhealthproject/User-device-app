package com.github.myhealth;

import android.content.res.Resources;

import com.github.myhealth.api.APIClient;
import com.github.myhealth.api.APIResponseListener;
import com.github.myhealth.api.response.LoginResponse;

import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by henkd on 26-9-2016.
 */

public class APIClientTest extends TestCase {
    private static final int MOCK_SERVER_PORT = 1200;

    private APIClient apiClient;
    private MockAPIServer server;

    private final String username = "test@test.com", password = "test1234";
    private String token = "testtoken1234";

    @BeforeClass
    public void setUp() throws MalformedURLException {
        server = new MockAPIServer();
        String url = "http://localhost:"+MOCK_SERVER_PORT;
        apiClient = TestAPIClient.getInstance(url);

    }

    @Test
    public void testLogin(){
        LoginResponse response = apiClient.logIn(username, password);
        assertTrue(response.getToken() != null);
        assertFalse(response.getToken().isEmpty());
        assertEquals(response.getToken(), this.token);
    }

    @Test
    public void testGetProfile(){
        // TODO implement
    }

    @Test
    public void testRegister(){
        //TODO implement
    }

    @AfterClass
    public void shutDownServer(){
        server.stop();
    }

    private class MockAPIServer extends NanoHTTPD{
        private NanoHTTPD server;
        private boolean running = true;

        public MockAPIServer(){
            super(MOCK_SERVER_PORT);
        }

        @Override
        public Response serve(IHTTPSession session) {
            System.out.println(session.getUri());
            return new Response("TEST");
        }
    }

    private static class TestAPIClient extends APIClient {
        private TestAPIClient(String apiURL) {
            super(apiURL);
        }

        public static APIClient getInstance(String apiURL){
            if(instance == null || !(instance instanceof TestAPIClient)){
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
