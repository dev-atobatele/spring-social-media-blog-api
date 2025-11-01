package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.example.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateMessageTest {	
	ApplicationContext app;
    HttpClient webClient;
    ObjectMapper objectMapper;

    /**
     * Before every test, reset the database, restart the Javalin app, and create a new webClient and ObjectMapper
     * for interacting locally on the web.
     * @throws InterruptedException
     */
    @BeforeEach
    public void setUp() throws InterruptedException {
        webClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        String[] args = new String[] {};
        app = SpringApplication.run(SocialMediaApp.class, args);
        Thread.sleep(500);
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
    	Thread.sleep(500);
    	SpringApplication.exit(app);
    }
    

    /**
     * Sending an http request to POST localhost:8080/messages with valid message credentials
     * 
     * Expected Response:
     *  Status Code: 200
     *  Response Body: JSON representation of message object
     */
    @Test
public void createMessageSuccessful_serverSetsTimestamp() throws IOException, InterruptedException {
    // request WITHOUT time_posted_epoch
    String json = "{\"posted_by\":9999,\"message_text\":\"hello message\"}";

    HttpRequest postMessageRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/messages"))
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .build();

    HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
    int status = response.statusCode();
    Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);

    ObjectMapper om = new ObjectMapper();
    Message actualResult = om.readValue(response.body(), Message.class);

    // basic sanity checks
    Assertions.assertNotNull(actualResult, "Response body must be a Message");
    Assertions.assertEquals(9999, actualResult.getPosted_by(), "posted_by should match");
    Assertions.assertEquals("hello message", actualResult.getMessage_text(), "message_text should match");
    Assertions.assertNotNull(actualResult.getMessage_id(), "message_id must be set by server");

    // server-set timestamp should be present and recent (milliseconds)
    long timeFromServer = actualResult.getTime_posted_epoch();
    long now = System.currentTimeMillis();
    long toleranceMillis = 5_000L; // 5 seconds tolerance

    Assertions.assertTrue(timeFromServer > 0, "time_posted_epoch must be > 0");
    Assertions.assertTrue(Math.abs(now - timeFromServer) < toleranceMillis,
            "Server timestamp should be within " + toleranceMillis + "ms of now. Was: " + timeFromServer);

    // optional: if you want to verify persistence shape, fetch by id and compare fields (not shown)
}

    
    /**
     * Sending an http request to POST localhost:8080/messages with empty message
     * 
     * Expected Response:
     *  Status Code: 400
     */
    @Test
    public void createMessageMessageTextBlank() throws IOException, InterruptedException {
    	String json = "{\"posted_by\":9999,\"message_text\": \"\",\"time_posted_epoch\": 1669947792}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected Status Code 400 - Actual Code was: " + status);
    }


    /**
     * Sending an http request to POST localhost:8080/messages with message length greater than 254
     * 
     * Expected Response:
     *  Status Code: 400
     *  Response Body: 
     */
    @Test
    public void createMessageMessageGreaterThan254() throws IOException, InterruptedException {
    	String json = "{\"posted_by\":9999,"
    			+ "\"message_text\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\""
    			+ "\"time_posted_epoch\": 1669947792}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected Status Code 400 - Actual Code was: " + status);
    }

    /**
     * Sending an http request to POST localhost:8080/messages with a user id that doesnt exist in db
     * 
     * Expected Response:
     *  Status Code: 400
     */
    @Test
    public void createMessageUserNotInDb() throws IOException, InterruptedException {
    	String json = "{\"posted_by\":5050,\"message_text\": \"hello message\",\"time_posted_epoch\": 1669947792}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected Status Code 400 - Actual Code was: " + status);
    }
}
