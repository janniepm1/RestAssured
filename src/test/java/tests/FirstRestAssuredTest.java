package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import static io.restassured.RestAssured.*;

public class FirstRestAssuredTest {
    /*
    when i send the request to hhtps then the status must be 200
     */
    @Test
    public void verifyStatusCode(){
        //response -->response that is sent by server as result of our request
        //get-->send the request to given url
        Response response=when().get("http://api.openrates.io/latest");
//print the response
        response.prettyPrint();
        //verify the status code
        //verifies that status code matches the provided option
        response.then().statusCode(202);

    }
    /*
    when i send request to
    the body should contain :base:"EUR"
     */
    @Test
    public void verifyBodyContains(){
        Response response=when().get("http://api.openrates.io/latest");
        //asString()--> returns the body as a single String
        String bodySTR=response.asString();
        System.out.println(bodySTR);
        assertTrue(bodySTR.contains("\"base\":\"EUR"));


    }
     /*
    when i send request to
    the body should contain header application/json
     */
    @Test
    public void verifyHeader1(){
        Response response=when().get("http://api.openrates.io/latest");
        //response.header()--> returns the value of the provided header
        String contentType=response.header("Content-type");
        String date=response.header("Date");

        System.out.println("contentType= "+contentType);
        System.out.println("date= "+date);

        assertEquals("application/json",contentType);
        assertTrue(date.contains("2020"));
    }
    @Test
    public void verifyContentType(){
        Response response=when().get("http://api.openrates.io/latest");
        //response.header()--> returns the value of the provided header
        String contentType=response.header("Content-type");
        System.out.println(contentType);
        //response get status code
        int statusCode=response.getStatusCode();
        System.out.println(statusCode);

        assertEquals("application/json",contentType);

        response.prettyPeek().then().statusCode(200);
    }
    /*
    when i send request to http://api.zippopotam.us/us/20031
     */

    @Test
    public void verifyZippo() {
        Response response = when().get("http://api.zippopotam.us/us/22031");
        response.prettyPrint();
        response.then().statusCode(200);
        assertTrue(response.asString().contains("Fairfax"));
    }
    /**
     *    when i send request to  http://api.zippopotam.us/us/22031111
     *    Then the status must 400
     */
    @Test
    public void verifyZippo2(){
        Response response = when().get("http://api.zippopotam.us/us/22031111");
        response.prettyPeek();
        response.then().statusCode(400);
    }


}
