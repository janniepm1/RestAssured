package tests.restAssuredewithDB;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

/**
 * Write test case to test in new class ORDSTests:
 *     get employee from employees table with id 100 (use path parameters)
 *     Verify response contains King
 *         response contains 100
 *         status code 200
 *         header application/json
 *
 *     get employee from countries table with id AR (use path parameters)
 *     Verify response contains Argentina
 *         response contains AR
 *         status code 200
 *         header application/json
 *
 *     get employee from departments table with id 2000 (use path parameters)
 *     Verify response contains Argentina
 *         response contains AR
 *         status code 404
 *         header text/html
 *
 */

public class restAssuredTestCase {
    @Test
    public void getEmployee(){
        Response response = given().pathParams("employee_id", 100).when().
                get("http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/employees/{id}");
        response.then().statusCode(200).and().contentType(ContentType.JSON);
        String responsestr=response.asString();
       assertTrue(responsestr.contains("King"));
        assertTrue(responsestr.contains("100"));

    }
    /**
     *     get employee from countries table with id AR (use path parameters)
     *     Verify response contains Argentina
     *         response contains AR
     *         status code 200
     *         header application/json
     *

     */
    @Test
    public void getCountry(){
        Response response=given().queryParams("id","AR")
                .when().get("http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/countries/{id}");
        response.prettyPeek().then().statusCode(200).and().contentType(ContentType.JSON);
        assertTrue(response.asString().contains("AR"));
        assertTrue(response.toString().contains("Argentina"));

    }
    /**
     *   get employee from departments table with id 2000 (use path parameters)
     *  *     Verify response contains Argentina
     *  *         response contains AR
     *  *         status code 404
     *  *         header text/html
     */
    @Test
    public void arg(){
        Response response = given().pathParam("id", "2000").
                when().get("http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr/departments/{id}");
        response.then().statusCode(404);
        String contantType=response.header("Content-Type");
        assertEquals(contantType,"text/html");

    }
}
