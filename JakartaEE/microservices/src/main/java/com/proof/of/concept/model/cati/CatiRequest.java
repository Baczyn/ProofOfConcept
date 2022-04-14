package com.proof.of.concept.model.cati;

import com.proof.of.concept.model.SystemRequest;

public class CatiRequest {

    private String country;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;
    private String flatNumber;

    public CatiRequest(String country, String city, String postalCode, String street, String streetNumber, String flatNumber) {
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.streetNumber = streetNumber;
        this.flatNumber = flatNumber;
    }

    public CatiRequest(SystemRequest address) {
        this.country = address.getCountry();
        this.city = address.getCity();
        this.postalCode = address.getPostalCode();
        this.street = address.getStreet();
        this.streetNumber = address.getStreetNumber();
        this.flatNumber = address.getFlatNumber();
    }



    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    @Override
    public String toString() {
        return "AddressRequest{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", flatNumber='" + flatNumber + '\'' +
                '}';
    }
}
