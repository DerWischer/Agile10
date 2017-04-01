package adp.group10.roomates;

/**
 * Created by Ninas Dator on 2017-03-31.
 */

public class ShoppingListEntryData {
    private String name;
    private int amount;
    private double price;
    private boolean blocked;
    private String blockerName = "";

    public ShoppingListEntryData(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return "" + amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return "" + price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isBlocked() { return blocked; }

    public void setBlocked(boolean blocked) { this.blocked = blocked; }

    public String getBlockerName() { return blockerName; }

    public void setBlockerName(String name) { this.blockerName = name; }
}
