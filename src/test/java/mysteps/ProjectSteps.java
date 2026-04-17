package mysteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Utils.ApiClient;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProjectSteps {

    private Map<String, Object> projectData = new HashMap<>(); //input data for project creation
    private HttpResponse<String> response; // output data from API response
    String url = "http://localhost:8080/api/v1/projects";
    String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjMsInN1YiI6ImFkZW0uYWRlbUBwbXMuY29tIiwicm9sZSI6IkRFUEFSVE1FTlRfTEVBREVSIiwiaXNBY3RpdmUiOnRydWUsImlhdCI6MTc3NTA2NDU4NCwiZXhwIjoxNzc2MzYwNTg0fQ.iKtnw8AcgxoRlycUn27f1vxA67Q-QHdYYvnwJhMKYUc";

    public static final Logger logger = LoggerFactory.getLogger(ProjectSteps.class);
    @Given("the project manager is on the project creation page")
    public void theProjectManagerIsOnTheProjectCreationPage() {
        System.out.println("Project manager is on the project creation page");
        logger.info("Project manager is on the project creation page");
    }

    @When("he enters valid project title {string}")
    public void heEntersValidProjectTitle(String arg0) {
        projectData.put("title", arg0);
    }

    @And("sets project description {string}")
    public void setsProjectDescription(String arg0) {
        projectData.put("description", arg0);
    }

    @And("selects a start date {string}")
    public void selectsAStartDate(String arg0) {
        projectData.put("startDate", arg0);
    }

    @And("selects an estimated end date {string}")
    public void selectsAnEndDate(String arg0) {
        projectData.put("estimatedEndDate", arg0);
    }

    @And("clicks the {string} button")
    public void clicksTheButton(String arg0) throws Exception {
        logger.info("Submitting project form with data: " + projectData);


        // text block
        /*
        String body = """
                {
                    "title": "%s",
                    "description": "%s",
                    "managerId": 5,
                    "productOwnerId": 12,
                    "departmentId": 2,
                    "methodologyId": 1,
                    "status": "PENDING",
                    "startDate": "%s",
                    "estimatedEndDate": "%s"
                }
                """.formatted(
                    projectData.get("title"),
                    projectData.get("description"),
                    projectData.get("startDate"),
                    projectData.get("endDate")
                );
        */

        JSONObject json = new JSONObject();
        json.put("title", projectData.get("title"));
        json.put("description", projectData.get("description"));
        json.put("managerId", 5);
        json.put("productOwnerId", 12);
        json.put("departmentId", 2);
        json.put("methodologyId", 1);
        json.put("status", "PENDING");
        json.put("startDate", projectData.get("startDate"));
        json.put("estimatedEndDate", projectData.get("estimatedEndDate"));
        String body = json.toString();
        logger.info("Request body: " + body);
        this.response = ApiClient.post(url, token, body);
    }

    @Then("the project should be created successfully")
    public void theProjectShouldBeCreatedSuccessfully() {
        // validate project creation by checking response status code or response body
        logger.info("Response status code: " + response.statusCode());
        String responseBody = response.body();
        logger.info("Response body: " + responseBody);
        JSONObject jsonResponse = new JSONObject(responseBody);
        logger.info("Response body: " + jsonResponse.toString(4));

                // validation 1 : check status code
                assertEquals(201, this.response.statusCode(), "Expected status code 201 but got " + this.response.statusCode());

        // validation 2 : check fields in response body
        assertEquals(projectData.get("title"),       jsonResponse.getString("title"));
        assertEquals(projectData.get("description"), jsonResponse.getString("description"));
        assertEquals(projectData.get("startDate"),   jsonResponse.getString("startDate"));
        assertEquals(projectData.get("estimatedEndDate"), jsonResponse.getString("estimatedEndDate"));

    }


    @Then("the project should not  be created")
    public void theProjectShouldNotBeCreated() {
        logger.info("Response status code: " + response.statusCode());
        String responseBody = response.body();
        logger.info("Response body: " +responseBody );

        // validation 1 : check status code
        assertEquals(400, this.response.statusCode(), "Expected status code 400 but got " + this.response.statusCode());

    }

    @When("he enters the following details : title {string} ,descreption {string} ,start date {string} ,estimate date {string}")
    public void heEntersTheFollowingDetailsTitleDescreptionStartDateEstimateDate(
            String title, String descreption, String startDate, String estimateDate) throws Exception {
        logger.info("title:{}, descreption: {}", title , descreption);
        JSONObject json = new JSONObject();
        json.put("title",title);
        json.put("description",descreption);
        json.put("managerId", 5);
        json.put("productOwnerId", 12);
        json.put("departmentId", 2);
        json.put("methodologyId", 1);
        json.put("status", "PENDING");
        json.put("startDate", startDate);
        json.put("estimatedEndDate", estimateDate);
        String body = json.toString();
        logger.info("Request body: " + body);
        this.response = ApiClient.post(url, token, body);



    }

    @Then("the response message should be {string}")
    public void theResponseMessageShouldBe(String expectedResponse) {
        logger.info("Response: " + response);
        logger.info("Response status code: " +  this.response.statusCode());
        String responseBody = this.response.body();
        logger.info("Response body: " +responseBody );
        String foundresponse = "";
        if (this.response.statusCode() == 201) {
            foundresponse = "success";
        }
        else if (this.response.statusCode() == 400) {
            foundresponse = "failure";
        }
        assertEquals(expectedResponse, foundresponse);



//    assertEquals(201, this.response.statusCode(), "Expected status code 201 but got " + this.response.statusCode());
//    assertEquals(400, this.response.statusCode(), "Expected status code 400 but got " + this.response.statusCode());

}}