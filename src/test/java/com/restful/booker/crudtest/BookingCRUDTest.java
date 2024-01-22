package com.restful.booker.crudtest;

import com.restful.booker.restfulbookerinfo.BookingSteps;
import com.restful.booker.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.*;


@RunWith(SerenityRunner.class)
public class BookingCRUDTest extends TestBase {

    static String username = "admin";
    static String password = "password123";

    static String firstname = "Annu";
    static String lastname = "Som";
    static int totalprice = 123;
    static boolean depositpaid = true;
    static HashMap<String, String> bookingdates = new HashMap<>() {{
        put("checkin", "2018-01-01");
        put("checkout", "2019-01-01");
    }};  //This is double curly brackets initialization of hash map at the time of declaration.
    static String additionalneeds = "Breakfast";
    static ValidatableResponse response;
    static int bookingId;

    @Steps
    BookingSteps steps;
//    @Test
//    public void dryRun(){
//
//    }

    @Title("This will create authorization token")
    @Test
    public void test_001() {
        HashMap<String, String> responseBody = steps.createAuthToken(username, password);
        Assert.assertThat(responseBody, hasKey("token"));
        String tokenValue = responseBody.get("token");
        Assert.assertFalse(tokenValue.isEmpty()); //Verifying token is created
    }

    @Title("This will create new booking and verify that booking by id")
    @Test
    public void test_002() {
        response = steps.createBooking(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds);
        //Soft assertion below
        response.statusCode(200).header("Content-Type", "application/json; charset=utf-8").time(Matchers.lessThan(2000L));
        response.body("booking.firstname", equalTo(firstname)).body("booking.lastname", equalTo(lastname)).body("booking.bookingdates", equalTo(bookingdates));
        response.body("$", hasKey("bookingid"));
        bookingId = response.extract().path("bookingid");
        response = steps.getBookingById(bookingId);
        response.statusCode(200).body("$", not(empty())); //Verifying body is not empty

    }

    @Title("This will update complete booking and verify update by id ")
    @Test
    public void test_003() {
        firstname = firstname + "_updated";
        response = steps.updateCompleteBooking(bookingId, firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds);
        response.statusCode(200).header("Content-Type", "application/json; charset=utf-8").time(Matchers.lessThan(2000L));
        response.body("firstname", equalTo(firstname)).body("lastname", equalTo(lastname)).body("bookingdates", equalTo(bookingdates));
        response = steps.getBookingById(bookingId);
        response.statusCode(200).body("$", not(empty())); //Verifying body is not empty
    }

    @Title("This will update complete booking and verify update by id ")
    @Test
    public void test_004() {
        firstname = firstname + "_updated";
        response = steps.updatePartialBooking(bookingId, firstname, lastname);
        response.statusCode(200).header("Content-Type", "application/json; charset=utf-8").time(Matchers.lessThan(2000L));
        response.body("firstname", equalTo(firstname)).body("lastname", equalTo(lastname));
        response = steps.getBookingById(bookingId);
        response.statusCode(200).body("$", not(empty())); //Verifying body is not empty
        response.body("firstname", equalTo(firstname));
    }

    @Title("This will delete the booking and verify deletion by id")
    @Test
    public void test_005() {
        response = steps.deleteBookingById(bookingId).statusCode(201);
        response = steps.getBookingById(bookingId).statusCode(404);
    }

}
