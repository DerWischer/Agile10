package adp.group10.roomates;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import adp.group10.roomates.R;

/**
 * Created by Ninas Dator on 2017-03-31.
 */

public class ShoppingListAdapter extends ArrayAdapter<ShoppingListEntryData> implements
        View.OnClickListener {

    public ShoppingListAdapter(@NonNull Context context, @LayoutRes int resource,
            @NonNull List<ShoppingListEntryData> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.shopping_list_entry_data_item, parent, false);
        }

        TextView tvName = (TextView) view.findViewById(R.id.entryName);
        TextView tvAmount = (TextView) view.findViewById(R.id.entryAmount);
        ShoppingListEntryData item = getItem(position);
        tvName.setText(item.getName());
        tvAmount.setText(item.getAmount());


        Button editButton = (Button) view.findViewById(R.id.editButton);
        Button deleteButton = (Button) view.findViewById(R.id.deleteButton);
        Button blockButton = (Button) view.findViewById(R.id.blockButton);
        Button buyButton = (Button) view.findViewById(R.id.buyButton);
        editButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        blockButton.setOnClickListener(this);
        buyButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
