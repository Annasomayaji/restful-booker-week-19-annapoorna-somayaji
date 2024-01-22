package com.restful.booker.model;

public class PartialBookingPojo {
    private String firstname;
    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public static PartialBookingPojo getPartialBookingPojo(String firstname, String lastname) {
        PartialBookingPojo partialBookingPojo = new PartialBookingPojo();
        partialBookingPojo.setFirstname(firstname);
        partialBookingPojo.setLastname(lastname);
        return partialBookingPojo;
    }

}
