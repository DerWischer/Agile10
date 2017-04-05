package adp.group10.roomates;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import adp.group10.roomates.activities.AddEditItemActivity;
import adp.group10.roomates.activities.ShoppingListActivity;
import adp.group10.roomates.backend.ShoppingListStorage;
import adp.group10.roomates.model.ShoppingListEntry;

/**
 * Created by Ninas Dator on 2017-03-31.
 */

public class ShoppingListAdapter extends ArrayAdapter<ShoppingListEntry> {

    public  ShoppingListAdapter(@NonNull Context context, @LayoutRes int resource,
            @NonNull List<ShoppingListEntry> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.shopping_list_entry_data_item,
                    parent, false);
        }

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
                Intent intent = new Intent(getContext(), AddEditItemActivity.class);
                Bundle extra = new Bundle();
                extra.putSerializable(ShoppingListStorage.class.getSimpleName(), entry);
                extra.putString(AddEditItemActivity.MODE, AddEditItemActivity.MODE_EDIT);
                intent.putExtras(extra);

                ((Activity) getContext()).startActivityForResult(intent,
                        ShoppingListActivity.EDIT_SHOPPING_LIST_ENTRY);
                notifyDataSetChanged();
            }
        });

        Button deleteButton = (Button) view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingListStorage.getInstance().deleteEntry(entry);
                notifyDataSetChanged();
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
                Intent intent = new Intent(getContext(), AddEditItemActivity.class);
                Bundle extra = new Bundle();
                extra.putSerializable(ShoppingListStorage.class.getSimpleName(), entry);
                extra.putString(AddEditItemActivity.MODE, AddEditItemActivity.MODE_BUY);
                intent.putExtras(extra);

                ((Activity) getContext()).startActivityForResult(intent,
                        ShoppingListActivity.EDIT_SHOPPING_LIST_ENTRY);
                notifyDataSetChanged();
            }
        });

        return view;
    }

}
