package adp.group10.roomates.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adp.group10.roomates.backend.ShoppingListStorage;
import adp.group10.roomates.R;
import adp.group10.roomates.ShoppingListAdapter;
import adp.group10.roomates.model.ShoppingListEntry;

public class ShoppingListActivity extends Activity {
    public final static int ADD_SHOPPING_LIST_ENTRY = 0;
    public final static int EDIT_SHOPPING_LIST_ENTRY = 1;

    ArrayList<ShoppingListEntry> data;
    ArrayAdapter<ShoppingListEntry> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopping_list);
        data = getShoppingListEntries();

        ListView listView = (ListView) findViewById(R.id.ShoppingListView);
        adapter = new ShoppingListAdapter(this, R.layout.shopping_list_entry_data_item, data);
        listView.setAdapter(adapter);

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addIntent = new Intent(ShoppingListActivity.this, AddEditItemActivity.class);
                startActivityForResult(addIntent, ADD_SHOPPING_LIST_ENTRY);
            }
        });

        super.onCreate(savedInstanceState);
    }

    private ArrayList<ShoppingListEntry> getShoppingListEntries() {
        return ShoppingListStorage.getData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_SHOPPING_LIST_ENTRY) {
                ShoppingListEntry entry =
                        (ShoppingListEntry) data.getSerializableExtra(
                                ShoppingListEntry.class.getSimpleName());
                // TODO Add entry
            } else if (requestCode == EDIT_SHOPPING_LIST_ENTRY) {
                ShoppingListEntry entry =
                        (ShoppingListEntry) data.getSerializableExtra(
                                ShoppingListEntry.class.getSimpleName());
                Toast.makeText(this, "Edit-> " + entry.getName() + ", " + entry.getAmount(),
                        Toast.LENGTH_SHORT).show();
                // TODO Update entry
            } else {
                Log.d("Request", "?");
            }
        } else {
            Log.d("Result", "Cancelled");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
