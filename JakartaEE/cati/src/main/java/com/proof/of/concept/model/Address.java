package com.proof.of.concept.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Address")
@NamedQuery(name = "Address.findAll", query = "SELECT e FROM Address e")
@NamedQuery(name ="Address.getAddressByParams", query = "select a from Address a WHERE " +
        "a.country = :country AND " +
        "a.city = :city AND " +
        "a.postalCode = :postalcode AND " +
        "a.street = :street AND " +
        "a.streetNumber = :streetnumber " +
        "AND a.flatNumber=:flatnumber")
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Id
    private int id;
    private String country;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;
    private String flatNumber;

    public Address(String country, String city, String postalCode, String street, String streetNumber, String flatNumber) {
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.streetNumber = streetNumber;
        this.flatNumber = flatNumber;
    }

    public Address() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Address{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", flatNumber='" + flatNumber + '\'' +
                '}';
    }
}
