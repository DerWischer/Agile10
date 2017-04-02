package adp.group10.roomates.backend;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import adp.group10.roomates.model.ShoppingListEntry;

/**
 * Created by Joshua Jungen on 02.04.2017.
 */

public class ShoppingListStorage {

    private static final String SHOPPING_LIST_KEY = "shopping-list";
    private static ShoppingListStorage instance;

    public static ShoppingListStorage getInstance() {
        if (instance == null) {
            instance = new ShoppingListStorage();
        }
        return instance;
    }


    private final DatabaseReference storage;
    private final HashMap<String, ShoppingListEntry> displayedEntries;

    private ShoppingListStorage() {
        storage = FirebaseDatabase.getInstance().getReference(SHOPPING_LIST_KEY);
        displayedEntries = new HashMap<>();
    }

    public void setAdapter(final ArrayAdapter<ShoppingListEntry> adapter) {
        storage.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ShoppingListEntry entry = dataSnapshot.getValue(ShoppingListEntry.class);
                entry.setKey(dataSnapshot.getKey());

                displayedEntries.put(entry.getKey(), entry);
                adapter.add(entry);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ShoppingListEntry entry = dataSnapshot.getValue(ShoppingListEntry.class);
                entry.setKey(dataSnapshot.getKey());

                ShoppingListEntry displayEntry = displayedEntries.get(entry.getKey());
                int position = adapter.getPosition(displayEntry);

                adapter.remove(adapter.getItem(position));
                displayedEntries.put(entry.getKey(), entry);
                adapter.insert(entry, position);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ShoppingListEntry entry = displayedEntries.get(dataSnapshot.getKey());

                displayedEntries.remove(entry.getKey());
                adapter.remove(entry);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(SHOPPING_LIST_KEY, "onChildMoved");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(SHOPPING_LIST_KEY, "onCancelled");
            }
        });
    }

    public void addEntry(ShoppingListEntry entry) {
        DatabaseReference newEntryRef = storage.push();
        entry.setKey(newEntryRef.getKey());
        newEntryRef.setValue(entry);
    }

    public void updateEntry(ShoppingListEntry entry) {
        String key = entry.getKey();
        storage.child(key).setValue(entry);
    }

    public void deleteEntry(ShoppingListEntry entry) {
        String key = entry.getKey();
        storage.child(key).removeValue();
    }
}
