package models;

import java.util.ArrayList;
import java.util.Collections;

public class ShoppingCart {
    private ArrayList<Item> shoppingCart;
    private ArrayList<Integer> quantities; 
    private ArrayList<Boolean> selected;

    public ShoppingCart (ArrayList<Item> shoppingCart, ArrayList<Integer> quantities) {
        this.shoppingCart = shoppingCart;
        this.quantities = quantities;
        this.selected = new ArrayList<Boolean>(quantities.size());
        Collections.fill(selected, Boolean.FALSE);
    }

    public ShoppingCart (ArrayList<Item> shoppingCart, ArrayList<Integer> quantities, ArrayList<Boolean> selected) {
        this.shoppingCart = shoppingCart;
        this.quantities = quantities;
        this.selected = selected;
    }

    public ArrayList<Item> getShoppingCart() {
        return shoppingCart;
    }

    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    public ArrayList<Boolean> getSelected() {
        return selected;
    }

    public void increaseQuantity(int index) {
        quantities.set(index, quantities.get(index) + 1);
    }

    public void decreaseQuantity(int index) {
        quantities.set(index, quantities.get(index) - 1);

        // remove from shopping cart if 0
        if (quantities.get(index) == 0) {
            shoppingCart.remove(index);
            quantities.remove(index);
            selected.remove(index);
        }
    }

    public void setSelected(int index, boolean b) {
        selected.set(index, b);
    }

    public ArrayList<Integer> getAllSelectedIndices() {
        ArrayList<Integer> allSelected = new ArrayList<>();

        for (int i = 0; i < selected.size(); i++) {
            if (selected.get(i) == true) {
                allSelected.add(i);
            }
        }

        return allSelected;
    }

    public void removeAllSelected() {
        ArrayList<Integer> indices = getAllSelectedIndices();

        for (int i = 0; i < indices.size(); i++) {
            // remove starting from bottom of list to top of the list
            int index = indices.get(indices.size()-1 - i);

            shoppingCart.remove(index);
            quantities.remove(index);
            selected.remove(index);
        }
    }

    public double getTotal() {
        double total = 0;

        for (int i = 0; i < shoppingCart.size(); i++) {
            if (selected.get(i)) {
                total += shoppingCart.get(i).getPrice() * quantities.get(i);
            }
        }

        return total;
    }

    public String getTotalInString() {
        double total = 0;

        for (int i = 0; i < shoppingCart.size(); i++) {
            if (selected.get(i)) {
                total += shoppingCart.get(i).getPrice() * quantities.get(i);
            }
        }

        return String.format("%.2f", total) + " PHP";
    }
}