package models;

public class Order {
    private Customer customer;
    private Item item;
    private int quantity;
    private double total;
    private String date;
    private String mop;
    private String paymentStatus;
    private String status;

    public Order (Customer customer, Item item, int quantity, String date, String mop, String paymentStatus, String status) {
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        this.total = item.getPrice() * quantity;
        this.date = date;
        this.mop = mop;
        this.paymentStatus = paymentStatus;
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }
    
    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return total;
    }

    public String getTotalInString() {
        return String.format("%.2f", total) + " PHP";
    }

    public String getDate() {
        return date;
    }

    public String getMop() {
        return mop;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
