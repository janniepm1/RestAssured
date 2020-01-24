package tests.restAssuredewithDB;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class PathAndHeaderVerification {
    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://ec2-34-201-69-55.compute-1.amazonaws.com:1000/ords/hr";
    }
    @Test
    public void test1() {
        // request logging
        // log().all(). --> prints everything in the request
        given().log().everything().
                pathParam("id", "101").
                when().get("/employees/{id}").prettyPeek().
                then().
                statusCode(200).and().header("Content-Type",equalTo("application/json"))//header overloaded method
                //extract the value of the key first_name and verify
.and().body("first_name",equalTo("Neena"));
    }

    @Test
    public void test2(){
        JsonPath jsonPath=given().pathParams("id","101").when().get("/employees/{id}").jsonPath();
        //json path class uses to navigate through json body

    }
}
