package services;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import org.apache.http.HttpStatus;

public class CircuitTest {

    private static String ENDPOINT = "http://ergast.com/api/f1/{season}/circuits";

    ResponseSpecification checkStatusCodeAndContentType =
            new ResponseSpecBuilder().
                    expectStatusCode(200).
                    expectContentType(ContentType.JSON).
                    build();

    @Test
    public void verifyCircuitSizeForYear2016(){
        given().
                pathParam("season","2016").
                when().
                get(ENDPOINT + ".json").
                then().
                spec(checkStatusCodeAndContentType).
                and().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId",hasSize(21));

    }

    @Test
    public void verifyCircuitSizeForYear2017(){
        given().
                pathParam("season","2017").
                when().
                get(ENDPOINT+ ".json").
                then().
                spec(checkStatusCodeAndContentType).
                and().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId",hasSize(20));
    }

    @Test
    public void verifyJSONElements(){
        given().
                pathParam("season","2017").
                when().
                get(ENDPOINT+ ".json").
                then().
                statusCode(HttpStatus.SC_OK).
                and().
                assertThat().
                body("MRData.CircuitTable.Circuits.Location.lat[0]",equalTo("-37.8497"),
                        "MRData.CircuitTable.Circuits.Location[0].long",equalTo("144.968"),
                        "MRData.CircuitTable.Circuits.circuitName[0]",equalTo("Albert Park Grand Prix Circuit")
                        );
    }

    // First, retrieve the circuit ID for the first circuit of the 2017 season,
    // Then, retrieve the information known for that circuit and verify it is located in Australia

    @Test
    public void getCircuitIdAndValidateLocationIsAustralia() {
        String circuitId =
                given().
                        pathParam("season",2017).
                        when().
                        get(ENDPOINT+ ".json").
                        then().
                        extract().
                        path("MRData.CircuitTable.Circuits.circuitId[0]");

                given().
                        pathParam("season",2017).
                        when().
                        get(ENDPOINT + "/" + circuitId + ".json").
                        then().
                        spec(checkStatusCodeAndContentType).
                        and().
                        assertThat().
                        body("MRData.CircuitTable.Circuits.Location.country",contains("Australia"));


    }

    //json & response validation
    @Test
    public void validateJSONWithResponse()
    {
        String circuitId =
                given().
                        pathParam("season",2017).
                        when().
                        get(ENDPOINT+ ".json").
                        then().
                        extract().
                        path("MRData.CircuitTable.Circuits.circuitId[0]");
        given().
                pathParam("season",2017).
                when().
                get(ENDPOINT + "/" + circuitId + ".json").
                then().
                assertThat().
                body(matchesJsonSchemaInClasspath("albert_park_Australia.json")).
                body("MRData.CircuitTable.Circuits.Location.country",contains("Australia"));
    }
}