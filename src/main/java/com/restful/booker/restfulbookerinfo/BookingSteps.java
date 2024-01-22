package com.restful.booker.restfulbookerinfo;

import com.restful.booker.constants.EndPoints;
import com.restful.booker.model.AuthPojo;
import com.restful.booker.model.BookingPojo;
import com.restful.booker.model.PartialBookingPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class BookingSteps {

    @Step("Creating auth token with username : {0}, password : {1}")
    public HashMap<String, String> createAuthToken(String username, String password) {
        ValidatableResponse response = SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .when()
                .body(AuthPojo.getAuthPojo(username, password))
                .post(EndPoints.AUTH)
                .then().log().all();

        HashMap<String, String> responseBody = response.extract().path("$");
        return responseBody;
    }

    @Step("Creating new booking with first name : {0}, last name : {1}, total price : {2}, deposit paid : {3}, booking dates : {4}, additional needs : {5}")
    public ValidatableResponse createBooking(String firstname, String lastname, int totalprice, boolean depositpaid, HashMap<String, String> bookingdates, String additionaldetails) {
        return SerenityRest.given().log().all()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .header("Content-Type", "application/json")
                .when()
                .body(BookingPojo.getBookingPojo(firstname, lastname, totalprice, depositpaid, bookingdates, additionaldetails))
                .post(EndPoints.BOOKING)
                .then().log().all();

    }

    @Step("Getting booking by Id : {0}")
    public ValidatableResponse getBookingById(int id) {
        return SerenityRest.given().log().all()
                .pathParam("ID", id)
                .when()
                .get(EndPoints.BOOKING_BY_ID)
                .then().log().all();
    }

    @Step("updating complete booking with id : {0}, first name : {1}, last name : {2}, total price : {3}, deposit paid : {4}, booking dates : {5}, additional needs : {6}")
    public ValidatableResponse updateCompleteBooking(int id, String firstname, String lastname, int totalprice, boolean depositpaid, HashMap<String, String> bookingdates, String additionaldetails) {
        return SerenityRest.given().log().all()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .header("Content-Type", "application/json")
                .pathParam("ID", id)
                .when()
                .body(BookingPojo.getBookingPojo(firstname, lastname, totalprice, depositpaid, bookingdates, additionaldetails))
                .put(EndPoints.BOOKING_BY_ID)
                .then().log().all();

    }

    @Step("updating partial booking with id : {0}, first name : {1}, last name : {2}")
    public ValidatableResponse updatePartialBooking(int id, String firstname, String lastname) {
        return SerenityRest.given().log().all()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .header("Content-Type", "application/json")
                .pathParam("ID", id)
                .when()
                .body(PartialBookingPojo.getPartialBookingPojo(firstname, lastname))
                .patch(EndPoints.BOOKING_BY_ID)
                .then().log().all();

    }

    @Step("Deleting booking with id : {0}")
    public ValidatableResponse deleteBookingById(int bookingId) {
       return SerenityRest.given().log().all()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .header("Content-Type", "application/json")
                .pathParam("ID", bookingId)
                .when()
                .delete(EndPoints.BOOKING_BY_ID)
                .then().log().all();
    }
}
