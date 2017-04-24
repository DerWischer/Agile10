package adp.group10.roomates.backend.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

/**
 * Created by Ninas Dator on 2017-03-31.
 */

public class ShoppingListEntry implements Serializable {

    private String name;
    private int amount;
    private String blockedBy;

    public ShoppingListEntry() {
        // Default constructor is required by Firebase
    }

    public ShoppingListEntry(String name, int amount) {
        this.name = name;
        this.amount = amount;
        this.blockedBy = "";
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

    public String getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(String blockedBy) {
        this.blockedBy = blockedBy;
    }

    @Exclude
    public boolean isBlocked() {
        if (blockedBy == null){
            return false;
        } else {
            return blockedBy.length() > 0;
        }
    }
}

