package adp.group10.roomates.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import adp.group10.roomates.R;
import adp.group10.roomates.backend.model.ShoppingListEntry;
import adp.group10.roomates.businesslogic.ShoppingListFBAdapter;

public class ShoppingListActivity extends Activity {
    public final static int ADD_SHOPPING_LIST_ENTRY = 0;
    public final static int EDIT_SHOPPING_LIST_ENTRY = 1;

    ArrayAdapter<ShoppingListEntry> shoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        ArrayList<ShoppingListEntry> data = new ArrayList<>();
        GridView gvShoppingList = (GridView) findViewById(R.id.gvShoppingList);
        // TODO Refactor: Use constant for 'shopping-list'
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("shopping-list");
        FirebaseListAdapter fbAdapter = new ShoppingListFBAdapter(this, ref);
        gvShoppingList.setAdapter(fbAdapter);

        final Button bAddItem = (Button) findViewById(R.id.bAddItem);
        bAddItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addIntent = new Intent(ShoppingListActivity.this,
                        AddEditItemActivity.class);
                addIntent.putExtra(AddEditItemActivity.MODE, AddEditItemActivity.MODE_ADD);
                startActivityForResult(addIntent, ADD_SHOPPING_LIST_ENTRY);
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_SHOPPING_LIST_ENTRY) {
                ShoppingListEntry entry =
                        (ShoppingListEntry) data.getSerializableExtra(
                                ShoppingListEntry.class.getSimpleName());
                //ShoppingListStorage.getInstance().addEntry(entry);
                FirebaseDatabase.getInstance().getReference("shopping-list").push().setValue(entry);
            } else if (requestCode == EDIT_SHOPPING_LIST_ENTRY) {
                ShoppingListEntry entry =
                        (ShoppingListEntry) data.getSerializableExtra(
                                ShoppingListEntry.class.getSimpleName());
                //ShoppingListStorage.getInstance().updateEntry(entry);
                FirebaseDatabase.getInstance().getReference("shopping-list").child(entry.getKey()).setValue(entry);
            } else {
                Log.d("Request", "?");
            }
        } else {
            Log.d("Result", "Cancelled");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
