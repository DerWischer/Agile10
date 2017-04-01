package adp.group10.roomates;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopping_list);

        ListView listView = (ListView) findViewById(R.id.ShoppingListView);
        ArrayList<ShoppingListEntryData> data = new ArrayList<ShoppingListEntryData>();
        data.add(new ShoppingListEntryData("cheese", 5));
        data.add(new ShoppingListEntryData("tomatoes", 5));
        data.add(new ShoppingListEntryData("toilet paper", 5));
        data.add(new ShoppingListEntryData("fish", 2));

        ArrayAdapter<ShoppingListEntryData> adapter = new ShoppingListAdapter(this, R.layout.shopping_list_entry_data_item, data);
        listView.setAdapter(adapter);

        super.onCreate(savedInstanceState);
    }
}
