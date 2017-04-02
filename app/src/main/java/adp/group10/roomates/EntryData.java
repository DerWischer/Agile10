package adp.group10.roomates;

import java.util.ArrayList;

/**
 * Created by fabian on 02.04.2017.
 */


public class EntryData {
    static ArrayList<ShoppingListEntryData> data;

    public static ArrayList<ShoppingListEntryData> getData() {
        if (data == null) {
            data = new ArrayList<ShoppingListEntryData>();
            data.add(new ShoppingListEntryData("cheese", 5));
            data.add(new ShoppingListEntryData("tomatoes", 5));
            data.add(new ShoppingListEntryData("toilet paper", 5));
            data.add(new ShoppingListEntryData("fish", 2));
        }
        return data;
    }

    public static void addEntry(String name, int amount) {
        data.add(new ShoppingListEntryData(name, amount));
    }

    public static void deleteEntry(String name, int amount) {
        for (int i = 0; i < data.size(); i++) {
            ShoppingListEntryData entry = data.get(i);
            if (entry.getName() == name && Integer.parseInt(entry.getAmount()) == amount) {
                data.remove(i);
            }
        }
    }

    public static void editEntry(String oldName, int oldAmount, String newName, int newAmount) {
        for (int i = 0; i < data.size(); i++) {
            ShoppingListEntryData entry = data.get(i);
            if (entry.getName() == oldName && Integer.parseInt(entry.getAmount()) == oldAmount) {
                entry.setName(newName);
                entry.setAmount(newAmount);
            }
        }
    }
}
