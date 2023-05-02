import DTOs.Passenger;
import DTOs.PassengerRequest;
import config.ServiceConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.AuthServiceApi;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void informationAboutPassengerCanBeReceivedById() {

        //arrange:
        PassengerRequest Yura = PassengerRequest.builder()
                .name("Yury Dud")
                .airline(128116)
                .trips(200).build();

        Passenger newUser = authService.createPassenger(Yura).
                then().
                log().all().statusCode(HttpStatus.SC_OK).extract().response().as(Passenger.class);

        //act
        Response response = authService.getPassengerInfo(newUser.getId()).
                then().
                log().all().
                extract().response();

        //assert
        assertEquals(200, response.statusCode());
        JsonPath jsonPath = new JsonPath(response.asString());
        assertEquals("Yury Dud", jsonPath.get("name"));
        assertEquals(200, (Integer) jsonPath.get("trips"));
    }

    @Test
    public void informationAboutPassengerCanBeReceivedByIdTestWithModel() {

        //arrange:
        PassengerRequest Yura = PassengerRequest.builder()
                .name("Yury Dud")
                .airline(128116)
                .trips(200).build();
        Passenger newUser = authService.createPassenger(Yura).
                then().
                log().all().statusCode(HttpStatus.SC_OK).extract().response().as(Passenger.class);

        //act
        Passenger response = authService.getPassengerInfo(newUser.getId()).
                then().
                log().all().
                extract().response().as(Passenger.class);

        //assert
        assertEquals("Yury Dud", response.getName());
        assertEquals(200, response.getTrips());
        assertEquals(128116, response.getAirline().get(0).getId());
    }


    @Test
    public void informationAboutPassengerCanBeChanged() {

        //arrange:
        PassengerRequest Yura = PassengerRequest.builder()
                .name("Yury Dud")
                .airline(128116)
                .trips(200).build();
        Passenger newUser = authService.createPassenger(Yura).
                then().
                log().all().statusCode(HttpStatus.SC_OK).extract().response().as(Passenger.class);
        PassengerRequest newPassenger = PassengerRequest.builder().name("Max Kats").build();

        //act
        authService.changePassengerName(newUser.getId(), newPassenger).
                then().
                log().all().statusCode(HttpStatus.SC_OK);

        //assert
        Passenger response = authService.getPassengerInfo(newUser.getId()).
                then().
                log().all().
                extract().response().as(Passenger.class);
        assertEquals("Max Kats", response.getName());
    }

    @Test
    public void passengerCanBeDeleted() throws IOException {

        //arrange:
        PassengerRequest Yura = PassengerRequest.builder()
                .name("Yury Dud")
                .airline(128116)
                .trips(200).build();
        Passenger newUser = authService.createPassenger(Yura).
                then().
                log().all().statusCode(HttpStatus.SC_OK).extract().response().as(Passenger.class);

        //act
        authService.deletePassenger(newUser.getId()).
                then().
                log().all().statusCode(HttpStatus.SC_OK);

        //assert
        authService.getPassengerInfo(newUser.getId()).
                then().
                log().all().statusCode(HttpStatus.SC_NO_CONTENT);
    }

}
