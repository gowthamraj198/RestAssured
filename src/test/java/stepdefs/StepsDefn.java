package stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class StepsDefn {

    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;

    private String ENDPOINT = "http://restapi.demoqa.com/utilities/weather/city/";
    private String circuitEndpoint="http://ergast.com/api/f1/{season}/circuits.json";

    @Given("^a city exists with the name given$")
    public void a_city_exists_with_the_name_given() throws Throwable {
        request = given();
    }

    @When("^a user retrieves the detials of a city by its name$")
    public void a_user_retrieves_the_detials_of_a_city_by_its_name()  {
        response = request.when().get(ENDPOINT + "Chennai");
        System.out.println("response : " + response.getBody().prettyPrint());
    }

    @Then("^the status code is (\\d+)$")
    public void the_status_code_is(int statusCode) {
        json = response.then().statusCode(statusCode);
        response.then().assertThat().statusCode(statusCode);
    }

    @Then("^response includes the following$")
    public void response_includes_the_following(Map<String,String> responseFields)  {
        for (Map.Entry<String, String> field : responseFields.entrySet()) {
            System.out.println(field.getKey() + " " + field.getValue());
            json.body(field.getKey(),equalTo(field.getValue()));
        }
    }

    @Given("^a circuit exists for the given year$")
    public void a_circuit_exists_for_the_given_year() {
        request = given();
    }

    @When("^user hits the webservice with a \"([^\"]*)\"$")
    public void user_hits_the_webservice_with_a_year(String year) {
        response = request.
                pathParam("season", year).
                when().
                get(circuitEndpoint);
    }

    @And("^total number of ids in response \"([^\"]*)\"$")
    public void total_number_of_ids_in_response(String size) {
        response.then().assertThat().body("MRData.CircuitTable.Circuits.circuitId",hasSize(Integer.parseInt(size)));
    }

}





import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

class TestClass1 {

    public static void main(String[] args)
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            public void run()
            {
                String output = "abc";
                String output1 = "";
                final int PRETTY_PRINT_INDENT_FACTOR = 4;
                String jsonPrettyPrintString="";
                try {

                    URL url = new URL("https://timesofindia.indiatimes.com/rssfeedstopstories.cms");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/json");

                    if (conn.getResponseCode() != 200) {

                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));


                    //String output;
                    System.out.println("Output from Server .... \n");
                    while ((output = br.readLine()) != null) {
                        output1=output1+output;
                    }

                    conn.disconnect();

                    JSONObject xmlJSONObj = XML.toJSONObject(output1.toString());
                    jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                JSONObject json;
                try {
                    json = XML.toJSONObject(output1.toString());
                    int size=json.getJSONObject("rss").getJSONObject("channel").getJSONArray("item").length();
                    for (int i=0;i<size;i++) {
                        System.out.println("Title : " +json.getJSONObject("rss").getJSONObject("channel").getJSONArray("item").getJSONObject(i).getString("title"));
                        System.out.println("Description : " +json.getJSONObject("rss").getJSONObject("channel").getJSONArray("item").getJSONObject(i).getString("description"));
                        System.out.println("Published Date : " +json.getJSONObject("rss").getJSONObject("channel").getJSONArray("item").getJSONObject(i).getString("pubDate"));
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }, 0, 3000);
    }
}
