package adp.group10.roomates.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import adp.group10.roomates.backend.ShoppingListStorage;
import adp.group10.roomates.R;
import adp.group10.roomates.ShoppingListAdapter;
import adp.group10.roomates.backend.model.ShoppingListEntry;

public class ShoppingListActivity extends Activity {
    public final static int ADD_SHOPPING_LIST_ENTRY = 0;
    public final static int EDIT_SHOPPING_LIST_ENTRY = 1;

    ArrayAdapter<ShoppingListEntry> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        ArrayList<ShoppingListEntry> data = new ArrayList<>();
        ListView listView = (ListView) findViewById(R.id.ShoppingListView);
        adapter = new ShoppingListAdapter(this, R.layout.shopping_list_entry_data_item, data);

        listView.setAdapter(adapter);
        ShoppingListStorage.getInstance().setAdapter(adapter);

        final Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addIntent = new Intent(ShoppingListActivity.this, AddEditItemActivity.class);
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
                ShoppingListStorage.getInstance().addEntry(entry);
            } else if (requestCode == EDIT_SHOPPING_LIST_ENTRY) {
                ShoppingListEntry entry =
                        (ShoppingListEntry) data.getSerializableExtra(
                                ShoppingListEntry.class.getSimpleName());
                ShoppingListStorage.getInstance().updateEntry(entry);
            } else {
                Log.d("Request", "?");
            }
        } else {
            Log.d("Result", "Cancelled");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
