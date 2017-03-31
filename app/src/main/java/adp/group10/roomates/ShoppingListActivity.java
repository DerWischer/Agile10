package adp.group10.roomates;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopping_list);

        ListView listView = (ListView) findViewById(R.id.ShoppingListView);
        ArrayList<ShoppingListEntryData> arrayList = new ArrayList<ShoppingListEntryData>();
        ShoppingListEntryData data1 = new ShoppingListEntryData("cheese", 5);
        ShoppingListEntryData data2 = new ShoppingListEntryData("tomatoes", 5);
        ShoppingListEntryData data3 = new ShoppingListEntryData("toilet paper", 5);
        ShoppingListEntryData data4 = new ShoppingListEntryData("fish", 2);
        ShoppingListEntryData[] data = {data1, data2, data3, data4};
//        ShoppingListEntryData[] dataArray = (ShoppingListEntryData[]) arrayList.toArray();
        ShoppingListAdapter adapter = new ShoppingListAdapter(this, R.layout.sample_shopping_list_entry, data);
        listView.setAdapter(adapter);


        super.onCreate(savedInstanceState);
    }
}
