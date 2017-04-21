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
import adp.group10.roomates.activities.AddEditItemActivity;
import adp.group10.roomates.activities.ShoppingListActivity;
import adp.group10.roomates.backend.ShoppingListStorage;
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

        final ShoppingListEntry entry = getItem(position);
        tvName.setText(entry.getName());
        tvAmount.setText("" + entry.getAmount());
        tvPrice.setText("" + entry.getPrice());

        Button editButton = (Button) view.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, AddEditItemActivity.class);
                Bundle extra = new Bundle();
                entry.setKey(getRef(position).getKey());
                extra.putSerializable(ShoppingListStorage.class.getSimpleName(), entry);
                extra.putString(AddEditItemActivity.MODE, AddEditItemActivity.MODE_EDIT);
                intent.putExtras(extra);

                mActivity.startActivityForResult(intent,
                        ShoppingListActivity.EDIT_SHOPPING_LIST_ENTRY);
                notifyDataSetChanged();
            }
        });

        Button deleteButton = (Button) view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ShoppingListStorage.getInstance().deleteEntry(entry);
                FirebaseDatabase.getInstance().getReference("shopping-list").child(getRef(position).getKey()).removeValue();
            }
        });

                /* TODO Implement Block listener
                Button blockButton = (Button) view.findViewById(R.id.blockButton);
                blockButton.setOnClickListener(blockButtonClickHandler);
                */

        Button buyButton = (Button) view.findViewById(R.id.buyButton);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, AddEditItemActivity.class);
                Bundle extra = new Bundle();
                entry.setKey(getRef(position).getKey());
                extra.putSerializable(ShoppingListStorage.class.getSimpleName(), entry);
                extra.putString(AddEditItemActivity.MODE, AddEditItemActivity.MODE_BUY);
                intent.putExtras(extra);

                mActivity.startActivityForResult(intent,
                        ShoppingListActivity.EDIT_SHOPPING_LIST_ENTRY);
                notifyDataSetChanged();
            }
        });
    }
}
