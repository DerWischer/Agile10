package adp.group10.roomates.backend.model;

/**
 * Created by Joshua Jungen on 25.04.2017.
 */

public class Transaction {

    private String fromUser;
    private String toUser;
    private double amount;
    private boolean isSettled;

    public Transaction(){ // Required for Firebase
    }

    public Transaction(String fromUser, String toUser, double amount) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
        this.isSettled = false;
    }

    public Transaction(String fromUser, String toUser, double amount, boolean isSettled) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
        this.isSettled = isSettled;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public void setSettled(boolean settled) {
        this.isSettled = settled;
    }
}
