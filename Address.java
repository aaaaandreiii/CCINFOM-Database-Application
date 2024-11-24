public class Address {
    private String street;
    private String city;
    private String province;

    public Address(String street, String city, String province) {
        this.street = street;
        this.city = city;
        this.province = province;
    }

    public static Address fromString(String address) {
        String[] parts = address.split(", ");
        return new Address(parts[0].replaceAll("(Street|Avenue)", "").trim(), parts[1].trim(), parts[2].trim());
    }

    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getProvince() { return province; }

    @Override
    public String toString() {
        return street + ", " + city + ", " + province;
    }
}
