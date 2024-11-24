package models;

public class Item {
    private String pathToImg;
    private String name;
    private double price;
    private String supplier;
    private int available;

    public Item (String name, double price, String supplier, int available) {
        this.pathToImg = "./products/" + name + ".png";
        this.name = name;
        this.price = price;
        this.supplier = supplier;
        this.available = available;
    }

    public String getPathToImg() {
        return pathToImg;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceInString() {
        return String.format("%.2f", price) + " PHP";
    }

    public String getSupplier() {
        return supplier;
    }

    public int getAvailable() {
        return available;
    }

    public void decreaseAvailable() {
        available--;
        if (available < 0) {
            available = 0;
        }
    }

    public void increaseAvailable() {
        available++;
    }
}
