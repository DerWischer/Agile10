package adp.group10.roomates.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import adp.group10.roomates.backend.EntryData;
import adp.group10.roomates.R;
import adp.group10.roomates.ShoppingListAdapter;
import adp.group10.roomates.ShoppingListEntryData;

public class ShoppingListActivity extends Activity {
    ArrayList<ShoppingListEntryData> data;
    ArrayAdapter<ShoppingListEntryData> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopping_list);
        data = getShoppingListEntries();

        ListView listView = (ListView) findViewById(R.id.ShoppingListView);
        adapter = new ShoppingListAdapter(this, R.layout.shopping_list_entry_data_item, data);
        listView.setAdapter(adapter);

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(addButtonClickHandler);

        super.onCreate(savedInstanceState);
    }

    private ArrayList<ShoppingListEntryData> getShoppingListEntries() {
        return EntryData.getData();
    }

    View.OnClickListener addButtonClickHandler = new View.OnClickListener() {
        public void onClick(View v) {
            startActivity(new Intent(ShoppingListActivity.this, AddEditItemActivity.class));
        }
    };
}
