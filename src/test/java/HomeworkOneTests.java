import DTOs.PassengerRequest;
import Models.Passenger;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import Utils.PassengerService;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeworkOneTests {
    /*
    2.2 Add tests for GET, POST, PUT, and DELETE methods without AssertJ validation.
    endpoint: https://api.instantwebtools.net/v1/passenger
    methods:
        POST - create a new passenger with passenger name, number of trips, and valid airline ID (for example, 5)
        GET - read passenger data by passenger ID from 2.2.1
        PUT - edit passenger name by passenger ID from 2.2.1
        DELETE - delete passenger data by passenger ID from 2.2.1
    For each test, check the expected Status Code with the standard Rest Assured method.
     */

    @Test
    public void airlinesDataCanBeRead() {
        given().log().uri().
                when().
                get("https://api.instantwebtools.net/v1/airlines").
                then().
                log().all().statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void newPassengerCanBeAdded() {
        PassengerRequest newPassenger = PassengerRequest.builder().name("Yury Dud").airline(128116).trips(200).build();

        given().log().uri().
                contentType(ContentType.JSON).
                body(newPassenger).
                when().
                post("https://api.instantwebtools.net/v1/passenger").
                then().
                log().all().statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void informationAboutPassengerCanBeReceivedById() throws IOException {

        //arrange:
        Passenger Yura = PassengerService.createNewPassengerInDatabase(PassengerRequest.builder()
                .name("Yury Dud")
                .airline(128116)
                .trips(200).build());

        //act
        Response response = given().log().uri().
                when().
                get("https://api.instantwebtools.net/v1/passenger/" + Yura.getId()).
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
    public void informationAboutPassengerCanBeChanged() throws IOException {

        //arrange:
        Passenger Yura = PassengerService.createNewPassengerInDatabase(PassengerRequest.builder()
                .name("Yury Dud")
                .airline(128116)
                .trips(200).build());

        PassengerRequest newPassenger = PassengerRequest.builder().name("Max Kats").build();

        //act
        given().log().uri().
                contentType(ContentType.JSON).
                body(newPassenger).
                when().
                put("https://api.instantwebtools.net/v1/passenger/" + Yura.getId()).
                then().
                log().all().statusCode(HttpStatus.SC_OK);

        //assert
        Response response = given().log().uri().
                when().
                get("https://api.instantwebtools.net/v1/passenger/" + Yura.getId()).
                then().
                log().all().statusCode(200).
                extract().response();
        JsonPath jsonPath = new JsonPath(response.asString());
        assertEquals("Max Kats", jsonPath.get("name"));
    }

    @Test
    public void passengerCanBeDeleted() throws IOException {

        //arrange:
        Passenger Yura = PassengerService.createNewPassengerInDatabase(PassengerRequest.builder()
                .name("Yury Dud")
                .airline(128116)
                .trips(200).build());

        //act
        given().log().uri().
                when().
                delete("https://api.instantwebtools.net/v1/passenger/" + Yura.getId()).
                then().
                log().all().statusCode(HttpStatus.SC_OK);

        //assert
        given().log().uri().
                when().
                get("https://api.instantwebtools.net/v1/passenger/" + Yura.getId()).
                then().
                log().all().statusCode(HttpStatus.SC_NO_CONTENT);
    }


}
