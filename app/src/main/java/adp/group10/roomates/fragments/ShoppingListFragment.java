package adp.group10.roomates.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adp.group10.roomates.R;
import adp.group10.roomates.activities.LoginActivity;
import adp.group10.roomates.activities.MainActivity;
import adp.group10.roomates.backend.FirebaseHandler;
import adp.group10.roomates.backend.model.AvailableItem;
import adp.group10.roomates.backend.model.ShoppingListEntry;
import adp.group10.roomates.businesslogic.ShoppingListFBAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShoppingListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShoppingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingListFragment extends Fragment implements AbsListView.MultiChoiceModeListener,
        AddItemsFragment.OnFragmentInterActionListener,
        AdapterView.OnItemClickListener, View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private GridView gvList;
    private FirebaseListAdapter<ShoppingListEntry> fbAdapter;

    public ShoppingListFragment() { // Required empty public constructor
    }

    public static ShoppingListFragment newInstance() {
        ShoppingListFragment fragment = new ShoppingListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        ((TextView) view.findViewById(R.id.tvTitle)).setText("Shopping list");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateUI();
    }

    public void updateUI()
    {
        gvList = (GridView) getView().findViewById(R.id.gvList);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(
                FirebaseHandler.KEY_SHOPPING_LIST + "/" + LoginActivity.currentGroup);
        fbAdapter = new ShoppingListFBAdapter(getActivity(), ref, this);
        gvList.setAdapter(fbAdapter);
        gvList.setOnItemClickListener(this);
        gvList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        gvList.setMultiChoiceModeListener(this);


//        fbAdapter.setItemClickListener(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        final int position = (int) v.getTag();
        MainActivity currentActivity = (MainActivity) getActivity();
        if (v.getId() == R.id.etAmountPlus) {
            currentActivity.incrementShoppingCartItem(fbAdapter.getItem(position).getName(), +1);
        } else if (v.getId() == R.id.etAmountMinus) {
            currentActivity.incrementShoppingCartItem(fbAdapter.getItem(position).getName(), -1);
        } else if (v.getId() == R.id.etAmount) {
            final ShoppingListEntry entry = fbAdapter.getItem(position);
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_edit_item_amount, null);
            final EditText etNewAmount= (EditText) dialogView.findViewById(R.id.etNewAmount);
            int amount = entry.getAmount();
            etNewAmount.setText("" + amount);

            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(dialogView);
            builder.setTitle("Change amount of " + entry.getName());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    int amount = Integer.parseInt(etNewAmount.getText().toString());
                    entry.setAmount(amount);
                    fbAdapter.getRef(position).setValue(entry);
                }
            });
            builder.show();

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private ArrayList<Integer> selectedPositions = new ArrayList<>();

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        if (checked) {
            selectedPositions.add(position);
        } else {
            selectedPositions.remove(selectedPositions.indexOf(position));
        }
        //mode.setTitle("" + selectedPositions.size());
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.shopping_list_item_selected, menu);
        selectedPositions.clear();
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                delete();
                break;
            case R.id.menu_block:
                block();
                break;
            case R.id.menu_edit:
                edit();
                break;
            case R.id.menu_buy:
                buy();
                break;
            default:
                return false;
        }
        mode.finish();
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

    /**
     * Deletes all selected items in the shopping list
     */
    private void delete() {
        for (int position : selectedPositions) {
            fbAdapter.getRef(position).removeValue();
        }
        selectedPositions.clear();
    }

    /**
     * Blocks all selected items in the shopping list
     */
    private void block() {
        for (int position : selectedPositions) {
            ShoppingListEntry entry = fbAdapter.getItem(position);

            if (!entry.isBlocked()) {
                entry.setBlockedBy(LoginActivity.currentuser);
            } else {
                String blockedBy = entry.getBlockedBy();
                if (blockedBy.equals(LoginActivity.currentuser)) {
                    entry.setBlockedBy(null); // Unblock item
                }
            }

            fbAdapter.getRef(position).setValue(entry);
        }
        selectedPositions.clear();
    }

    /**
     * Opens a dialog to edit one selected item in the shopping list
     */
    private void edit() {
        if (selectedPositions.size() != 1) {
            Snackbar.make(getView(), "Too many items selected.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            final int position = selectedPositions.get(0);
            final ShoppingListEntry entry = fbAdapter.getItem(position);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_edit_item, null);
            final EditText etName = (EditText) dialogView.findViewById(R.id.etName);
            final TextView etAmount = (TextView) dialogView.findViewById(R.id.etAmount);
            etName.setText(entry.getName());
            etAmount.setText("" + entry.getAmount());

            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(dialogView);
            builder.setTitle("Edit Item");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String name = etName.getText().toString().trim();
                    int amount = Integer.parseInt(etAmount.getText().toString());

                    entry.setName(name);
                    entry.setAmount(amount);
                    fbAdapter.getRef(position).setValue(entry);
                }
            });
            builder.show();

        }
        selectedPositions.clear();
    }

    /**
     * Opens a dialog to buy all selected items in the shpopping list
     */
    private void buy() {
        if (!isAllowedToBuy()) {
            Snackbar.make(getView(), "You must block all selected items first",
                    Snackbar.LENGTH_LONG).show();
            selectedPositions.clear();
            return;
        }

        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_buy_items,
                null);
        ListView lvSelectedItems = (ListView) dialogView.findViewById(R.id.lvSelectedItems);
        ArrayList<String> items = new ArrayList<>();
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, items);
        for (int i = 0; i < selectedPositions.size(); i++) {
            ShoppingListEntry item = fbAdapter.getItem(selectedPositions.get(i));
            items.add(item.getAmount() + "x " + item.getName());
        }
        lvSelectedItems.setAdapter(itemAdapter);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        builder.setTitle("Specify Price");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView etAmount = (TextView) dialogView.findViewById(R.id.etAmount);
                String strPrice = etAmount.getText().toString().trim();

                DatabaseReference transactionReference =
                        FirebaseDatabase.getInstance().getReference(
                                FirebaseHandler.KEY_GROUPUSER + "/" + LoginActivity.currentGroup
                                        + "/" + LoginActivity.currentuser + "/"
                                        + FirebaseHandler.KEY_GROUPUSER_TRANSACTIONS);
                try {
                    double price = Double.parseDouble(strPrice);
                    transactionReference.push().setValue(price);
                    delete();
                } catch (NullPointerException | NumberFormatException e){
                       Snackbar.make(getView(), "No price specified. Could not buy items", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        builder.show();
    }

    /**
     * Checks if the specified user is allowed to buy the currently selected items.
     *
     * @return True if user may buy, False otherwise
     */
    private boolean isAllowedToBuy() {
        for (int position : selectedPositions) {
            ShoppingListEntry entry = fbAdapter.getItem(position);

            if (entry.isBlocked()) {
                String blockedBy = entry.getBlockedBy();
                if (!blockedBy.equals(LoginActivity.currentuser)) {
                    return false;

                }
            } else {
                return false; // Item not blocked
            }
        }

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast toast = Toast.makeText(getActivity(), ""+view.getId(), Toast.LENGTH_SHORT);
//        toast.show();
        MainActivity currentActivity = (MainActivity) getActivity();
//        currentActivity.incrementShoppingCartItem(fbAdapter.getItem(position).getName(), -1);
        // TODO Decrease amount, if 0 remove item
    }

    @Override
    public void onClickAvailableItem(AvailableItem item) {

        MainActivity currentActivity = (MainActivity) getActivity();

        if (!currentActivity.isDuplicateName(item.getName(), currentActivity.latestShoppingListSnapshot))
            FirebaseDatabase.getInstance().getReference(
            FirebaseHandler.KEY_SHOPPING_LIST
            + "/"
            + LoginActivity.currentGroup).push().setValue(new ShoppingListEntry(item.getName(), 1));
        else
            currentActivity.incrementShoppingCartItem(item.getName(), 1);

    }
}
