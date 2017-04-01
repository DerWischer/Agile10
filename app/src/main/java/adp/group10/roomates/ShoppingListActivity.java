package adp.group10.roomates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShoppingListActivity extends Activity {
    ArrayList<ShoppingListEntryData> data;
    ArrayAdapter<ShoppingListEntryData> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopping_list);

        data = getShoppingListEntries();

        Bundle b = getIntent().getExtras();
        if(b != null) {
            String name = b.getString("newItemName");
            int amount = b.getInt("newItemAmount");
            if (name != null && name != "" && amount > 0) {
                addEntryToList(new ShoppingListEntryData(name, amount));
            }
        }


        ListView listView = (ListView) findViewById(R.id.ShoppingListView);
        adapter = new ShoppingListAdapter(this, R.layout.shopping_list_entry_data_item, data);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(listViewClickHandler);

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(addButtonClickHandler);

        super.onCreate(savedInstanceState);
    }

    // DUMMY METHOD
    // TODO IMPLEMENT
    private void addEntryToList(ShoppingListEntryData newEntry) {
        data.add(newEntry);
    }

    // DUMMY METHOD
    // TODO IMPLEMENT
    private ArrayList<ShoppingListEntryData> getShoppingListEntries() {
        getSharedPreferences("shoppingListEntries", 0);

        ArrayList<ShoppingListEntryData> data = new ArrayList<ShoppingListEntryData>();
        data.add(new ShoppingListEntryData("cheese", 5));
        data.add(new ShoppingListEntryData("tomatoes", 5));
        data.add(new ShoppingListEntryData("toilet paper", 5));
        data.add(new ShoppingListEntryData("fish", 2));

        return data;
    }

 /*   AdapterView.OnItemClickListener listViewClickHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(ShoppingListActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            if (view.getId() == R.id.deleteButton) {
                data.remove(position);
                adapter.notifyDataSetChanged();
            }
        }
    };*/

    View.OnClickListener addButtonClickHandler = new View.OnClickListener() {
        public void onClick(View v) {
            startActivity(new Intent(ShoppingListActivity.this, AddItemActivity.class));
        }
    };
}
