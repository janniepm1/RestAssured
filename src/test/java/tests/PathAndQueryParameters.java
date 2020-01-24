package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class PathAndQueryParameters {

    @Test
    public void pathParamTest() {
        Response response = given().pathParam("date", "2020-01-02").when().get("http://api.openrates.io/{date}");

        response.prettyPeek().then().statusCode(200);
        assertTrue(response.asString().contains("2020-01-02"));

    }

    /**
     * 400 message test
     * given i create request with wrong parameter 2020-30-02
     * when i send my request to http://api.openrates.io/{date}
     * then the status code should be 400
     */

    @Test
    public void pathParamTestNeg() {
        Response response = given().pathParam("date", "2020-30-02").
                when().get("http://api.openrates.io/{date}");
        response.prettyPeek().then().assertThat().statusCode(400);

    }

    /**
     *
     */
    @Test
    public void queryParams() {
        Response response = given().queryParams("base", "USD").
                when().get("http://api.openrates.io/latest");
        response.prettyPeek();
        assertTrue(response.asString().contains("\"base\":\"USD\""));


    }

    /**
     * given i create request with query parameter base=USD and symbol=MYR
     * when i send my request to http://api.openrates.io/latest?base
     * then the response should contain "base": "USD"
     * and body should contain MYR
     * but should not contain EUR
     */
    public void test3queryparams() {
        Response response = given().queryParams("base", "USD").
                queryParams("symbols", "MYR").when().get("http://api.openrates.io/latest");
        response.prettyPeek();
        String respoderstr = response.asString();
        assertTrue(respoderstr.contains("USD") && respoderstr.contains("MYR"));
        assertFalse(respoderstr.contains("EUR"));
    }

    /**
     * given i create request with query parameter base=USD and symbol=MYR
     * and path parameter date = 2020-01-02
     * when i send my request to http://api.openrates.io/{date}
     * then the response should contain "base": "USD"
     * and body should contain MYR
     * but should not contain EUR
     */
    @Test
    public void testPathandQueryParams() {
        //this request uses both path and query parameters
        Response response = given().
                pathParam("date", "2020-01-02").queryParams("base", "USD")
                .queryParams("symbols", "MYR")
                .when().get("http://api.openrates.io/{date}");
        response.prettyPeek();

        String responsestr = response.asString();
        assertTrue(responsestr.contains("2020-01-02") && responsestr.contains("MYR"));
        // assertFalse();
    }
    //https://www.metaweather.com/api/location/search/?query=san

    /**
     * Based on this request, write tests cases.
     * Positive, negative. 2 with 2 different cities at least. In negative, test by passing wrong parameter.
     */


    @Test
    public void testCity() {
        Response response = given().queryParam("query", "Chicago").when().
                get("https://www.metaweather.com/api/location/search/?query=Chicago");
        response.prettyPeek().then().statusCode(200);
        String responseStr = response.asString();
        assertTrue(responseStr.contains("Chicago"));
    }

    @Test
    public void testNegative() {
        Response response = given().queryParam("title", "Karaganda").when().
                get("https://www.metaweather.com/api/location/search/?query=Chicago");
        response.prettyPeek().then().statusCode(400);
        String responseStr = response.asString();
        assertTrue(responseStr.contains("Karaganda"));
    }

}