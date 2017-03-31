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

public class ShoppingListAdapter extends ArrayAdapter<ShoppingListEntryData> {

    ShoppingListEntryData[] entries;
    Context context;
/*
    public ShoppingListAdapter(@NonNull Context context, @LayoutRes int resource,
            @NonNull List<ShoppingListEntryData> entries) {
        super(context, resource, entries);
        this.context = context;
        this.entries = entries;
    }*/


    public ShoppingListAdapter(@NonNull Context context, @LayoutRes int resource,
            @NonNull ShoppingListEntryData[] entries) {
        super(context, resource, entries);
        this.context = context;
        this.entries = entries;

        System.out.println("constructer");
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.sample_shopping_list_entry, parent);
        TextView amountView = (TextView) rowView.findViewById(R.id.entryAmount);
        TextView nameView = (TextView) rowView.findViewById(R.id.entryName);

        Button editButton = (Button) rowView.findViewById(R.id.editButton);
        Button deleteButton = (Button) rowView.findViewById(R.id.deleteButton);

        nameView.setText(entries[position].name);
        amountView.setText(entries[position].amount);


        return rowView;
    }
}
