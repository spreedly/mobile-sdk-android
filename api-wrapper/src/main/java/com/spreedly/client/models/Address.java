package com.spreedly.client.models;

public class Address {
    public final String address1;
    public final String address2;
    public final String city;
    public final String state;
    public final String zip;
    public final String country;

    public Address(String address1, String address2, String city, String state, String zip, String country){
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }
}
