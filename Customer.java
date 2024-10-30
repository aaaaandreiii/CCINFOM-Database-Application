import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String deliveryAddress;
    private double customerRating;

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setCustomerRating(double customerRating) {
        this.customerRating = customerRating;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public double getCustomerRating() {
        return customerRating;
    }

    public Customer(int customerId, String firstName, String lastName, String email, String phoneNumber, String deliveryAddress, double customerRating) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.deliveryAddress = deliveryAddress;
        this.customerRating = customerRating;
    }

    // Getters and setters here

    

    public void addCustomerToDatabase() {
        String query = "INSERT INTO Customer (first_name, last_name, email, phone_number, delivery_address, customer_rating) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, this.firstName);
            stmt.setString(2, this.lastName);
            stmt.setString(3, this.email);
            stmt.setString(4, this.phoneNumber);
            stmt.setString(5, this.deliveryAddress);
            stmt.setDouble(6, this.customerRating);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
