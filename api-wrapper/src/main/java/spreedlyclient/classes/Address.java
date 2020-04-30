package spreedlyclient.classes;

public class Address {
    String address1;
    String address2;
    String city;
    String state;
    String zip;
    String country;

    public Address(String address1, String address2, String city, String state, String zip, String country){
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }
    public Address(){};
}
