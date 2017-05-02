package adp.group10.roomates.businesslogic;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;

import adp.group10.roomates.R;
import adp.group10.roomates.activities.MainActivity;
import adp.group10.roomates.backend.model.ShoppingListEntry;
import adp.group10.roomates.fragments.ShoppingListFragment;

/**
 * Created by Joshua Jungen on 22.04.2017.
 */

public class ShoppingListFBAdapter extends FirebaseListAdapter<ShoppingListEntry>  {

    private MainActivity mainActivity;
    private ShoppingListFragment shoppingListFragment;

    /**
     * @param activity The activity containing the ListView
     * @param ref      The Firebase location to watch for data changes. Can also be a slice of a
     *                 location,
     *                 using some combination of {@code limit()}, {@code startAt()}, and {@code
     *                 endAt()}.
     */
    public ShoppingListFBAdapter(Activity activity, Query ref, ShoppingListFragment fragment) {
        super(activity, ShoppingListEntry.class, R.layout.shopping_list_entry_data_item, ref);
//        this.mainActivity = (MainActivity) activity;
        this.shoppingListFragment = fragment;
    }

    @Override
    protected void populateView(View view, ShoppingListEntry model, final int position) {
        TextView tvName = (TextView) view.findViewById(R.id.etName);
        TextView etAmount = (TextView) view.findViewById(R.id.bAmount);
        TextView tvBlockedBy = (TextView) view.findViewById(R.id.etBlockedBy);

        ShoppingListEntry entry = getItem(position);
        tvName.setText(entry.getName());
        etAmount.setText("" + entry.getAmount());
        if (entry.isBlocked()){
            tvBlockedBy.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_outline_black_24dp, 0, 0, 0);
            tvBlockedBy.setText(entry.getBlockedBy());
        } else {
            tvBlockedBy.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            tvBlockedBy.setText("");

        }

    }

/*    public void setItemClickListener(View.OnClickListener ocl) {
        int count = getCount();
        for (int i = 0; i < count; i++) {
            ViewGroup vg = (ViewGroup) getView(i, null, null);
            vg.findViewById(R.id.etAmount).setOnClickListener(ocl);
            vg.findViewById(R.id.etAmountPlus).setOnClickListener(ocl);
            vg.findViewById(R.id.etAmountMinus).setOnClickListener(ocl);
        }
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup viewGroup = (ViewGroup) super.getView(position, convertView, parent);
        View etAmount = viewGroup.findViewById(R.id.bAmount);
        View etPlus = viewGroup.findViewById(R.id.etAmountPlus);
        View etMinus = viewGroup.findViewById(R.id.etAmountMinus);

        etAmount.setTag(position);
        etPlus.setTag(position);
        etMinus.setTag(position);

        etAmount.setOnClickListener(shoppingListFragment);
        etPlus.setOnClickListener(shoppingListFragment);
        etMinus.setOnClickListener(shoppingListFragment);

        return viewGroup;
    }
}
