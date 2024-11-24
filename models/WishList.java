package models;

import java.util.ArrayList;
import java.util.Collections;

public class WishList {
    private ArrayList<Item> wishList;
    private ArrayList<Boolean> selected;

    public WishList (ArrayList<Item> wishList) {
        this.wishList = wishList;
        this.selected = new ArrayList<Boolean>(wishList.size());
        Collections.fill(selected, Boolean.FALSE);
    }

    public WishList (ArrayList<Item> wishList, ArrayList<Boolean> selected) {
        this.wishList = wishList;
        this.selected = selected;
    }

    public ArrayList<Item> getWishList() {
        return wishList;
    }

    public ArrayList<Boolean> getSelected() {
        return selected;
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

            wishList.remove(index);
            selected.remove(index);
        }
    }
}