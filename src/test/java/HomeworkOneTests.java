import DTOs.Passenger;
import DTOs.PassengerRequest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

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
    public void informationAboutPassengerCanBeReceivedById() {

        //arrange:
        PassengerRequest Yura = PassengerRequest.builder()
                .name("Yury Dud")
                .airline(128116)
                .trips(200).build();
        Passenger newUser = given().log().uri().
                contentType(ContentType.JSON).
                body(Yura).
                when().
                post("https://api.instantwebtools.net/v1/passenger").
                then().
                log().all().statusCode(HttpStatus.SC_OK).extract().response().as(Passenger.class);

        //act
        Response response = given().log().uri().
                when().
                get("https://api.instantwebtools.net/v1/passenger/" + newUser.getId()).
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
        Passenger newUser = given().log().uri().
                contentType(ContentType.JSON).
                body(Yura).
                when().
                post("https://api.instantwebtools.net/v1/passenger").
                then().
                log().all().statusCode(HttpStatus.SC_OK).extract().response().as(Passenger.class);

        //act
        Passenger response = given().log().uri().
                when().
                get("https://api.instantwebtools.net/v1/passenger/" + newUser.getId()).
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
        Passenger newUser = given().log().uri().
                contentType(ContentType.JSON).
                body(Yura).
                when().
                post("https://api.instantwebtools.net/v1/passenger").
                then().
                log().all().statusCode(HttpStatus.SC_OK).extract().response().as(Passenger.class);
        PassengerRequest newPassenger = PassengerRequest.builder().name("Max Kats").build();

        //act
        given().log().uri().
                contentType(ContentType.JSON).
                body(newPassenger).
                when().
                put("https://api.instantwebtools.net/v1/passenger/" + newUser.getId()).
                then().
                log().all().statusCode(HttpStatus.SC_OK);

        //assert
        Response response = given().log().uri().
                when().
                get("https://api.instantwebtools.net/v1/passenger/" + newUser.getId()).
                then().
                log().all().statusCode(200).
                extract().response();
        JsonPath jsonPath = new JsonPath(response.asString());
        assertEquals("Max Kats", jsonPath.get("name"));
    }

    @Test
    public void passengerCanBeDeleted() throws IOException {

        //arrange:
        PassengerRequest Yura = PassengerRequest.builder()
                .name("Yury Dud")
                .airline(128116)
                .trips(200).build();
        Passenger newUser = given().log().uri().
                contentType(ContentType.JSON).
                body(Yura).
                when().
                post("https://api.instantwebtools.net/v1/passenger").
                then().
                log().all().statusCode(HttpStatus.SC_OK).extract().response().as(Passenger.class);

        //act
        given().log().uri().
                when().
                delete("https://api.instantwebtools.net/v1/passenger/" + newUser.getId()).
                then().
                log().all().statusCode(HttpStatus.SC_OK);

        //assert
        given().log().uri().
                when().
                get("https://api.instantwebtools.net/v1/passenger/" + newUser.getId()).
                then().
                log().all().statusCode(HttpStatus.SC_NO_CONTENT);
    }


}
