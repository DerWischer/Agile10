package adp.group10.roomates.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

/**
 * Created by Ninas Dator on 2017-03-31.
 */

public class ShoppingListEntry implements Serializable{

    private String key;
    private String name;
    private int amount;
    private double price;

    public ShoppingListEntry(){
        // Default constructor is required by Firebase
    }

    public ShoppingListEntry(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

