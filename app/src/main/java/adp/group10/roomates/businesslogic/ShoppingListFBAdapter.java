package adp.group10.roomates.businesslogic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import adp.group10.roomates.R;
import adp.group10.roomates.backend.model.ShoppingListEntry;

/**
 * Created by Joshua Jungen on 22.04.2017.
 */

public class ShoppingListFBAdapter extends FirebaseListAdapter<ShoppingListEntry> {

    /**
     * @param activity The activity containing the ListView
     * @param ref      The Firebase location to watch for data changes. Can also be a slice of a
     *                 location,
     *                 using some combination of {@code limit()}, {@code startAt()}, and {@code
     *                 endAt()}.
     */
    public ShoppingListFBAdapter(Activity activity, Query ref) {
        super(activity, ShoppingListEntry.class, R.layout.shopping_list_entry_data_item, ref);

    }

    @Override
    protected void populateView(View view, ShoppingListEntry model, final int position) {
        TextView tvName = (TextView) view.findViewById(R.id.entryName);
        TextView tvAmount = (TextView) view.findViewById(R.id.entryAmount);
        TextView tvPrice = (TextView) view.findViewById(R.id.entryPrice);

        ShoppingListEntry entry = getItem(position);
        tvName.setText(entry.getName());
        tvAmount.setText("" + entry.getAmount());
        tvPrice.setText("" + entry.getPrice());
    }
}