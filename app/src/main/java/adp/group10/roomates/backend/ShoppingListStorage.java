package adp.group10.roomates.backend;

import android.database.DataSetObserver;
import android.widget.ListAdapter;

import java.util.ArrayList;

import adp.group10.roomates.model.ShoppingListEntry;

/**
 * Created by fabian on 02.04.2017.
 */


public class ShoppingListStorage {

    static ArrayList<ShoppingListEntry> data;

    public static ArrayList<ShoppingListEntry> getData() {
        if (data == null) {
            data = new ArrayList<ShoppingListEntry>();
            data.add(new ShoppingListEntry("cheese", 5));
            data.add(new ShoppingListEntry("tomatoes", 5));
            data.add(new ShoppingListEntry("toilet paper", 5));
            data.add(new ShoppingListEntry("fish", 2));
        }

        return data;
    }

    public static void addEntry(String name, int amount) {
        data.add(new ShoppingListEntry(name, amount));
    }

    public static void deleteEntry(String name, int amount) {
        for (int i = 0; i < data.size(); i++) {
            ShoppingListEntry entry = data.get(i);
            if (entry.getName().equals(name) && entry.getAmount() == amount) {
                data.remove(i);
            }
        }
    }

    public static void editEntry(String oldName, int oldAmount, String newName, int newAmount) {
        for (int i = 0; i < data.size(); i++) {
            ShoppingListEntry entry = data.get(i);
            if (entry.getName().equals(oldName) && entry.getAmount() == oldAmount) {
                entry.setName(newName);
                entry.setAmount(newAmount);
            }
        }
    }
}
