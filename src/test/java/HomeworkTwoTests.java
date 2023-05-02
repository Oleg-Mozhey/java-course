import config.ServiceConfig;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.AuthServiceApi;

import static io.restassured.RestAssured.given;

public class HomeworkTwoTests {
    static Logger LOGGER = LogManager.getLogger();

    //Add authentication for API project  - https://www.instantwebtools.net/secured-fake-rest-api
    //Add Assertions to tests
    //Add Allure report to project
    //4*. Use data from properties (Owner library)

    static AuthServiceApi authService;

    @BeforeAll
    public static void beforeAll() {
        authService = new AuthServiceApi();
    }

    @Test
    public void airlinesDataCanBeRead() {
        authService.getAllAirlines().then().statusCode(200);
    }
}
