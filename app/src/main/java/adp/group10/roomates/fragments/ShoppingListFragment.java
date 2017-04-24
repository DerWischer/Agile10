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
import android.widget.EditText;
import android.widget.GridView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import adp.group10.roomates.R;
import adp.group10.roomates.backend.FirebaseHandler;
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
public class ShoppingListFragment extends Fragment implements AbsListView.MultiChoiceModeListener {

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
        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gvList = (GridView) getView().findViewById(R.id.gvList);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("shopping-list");
        fbAdapter = new ShoppingListFBAdapter(getActivity(), ref);
        gvList.setAdapter(fbAdapter);

        gvList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        gvList.setMultiChoiceModeListener(this);
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
        mode.setTitle(selectedPositions.size() + " selected");
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
        String snackMsg = "empty";
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
        String user = "dummy"; // TODO Get current user

        for (int position : selectedPositions) {
            ShoppingListEntry entry = fbAdapter.getItem(position);

            if (!entry.isBlocked()) {
                entry.setBlockedBy(user);
            } else {
                String blockedBy = entry.getBlockedBy();
                if (blockedBy.equals(user)) {
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
            final EditText etAmount = (EditText) dialogView.findViewById(R.id.etAmount);
            etName.setText(entry.getName());
            etAmount.setText("" + entry.getAmount());

            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(dialogView);
            builder.setTitle("Edit Item");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String name = etName.getText().toString();
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
        // TODO Get price from a dialog
        double price = 0;

        // TODO Get current user and group
        String user = "dummy";
        String group = "dummyGroup";

        if (isAllowedToBuy(user)) {
            Snackbar.make(getView(), "Open Buy Item(s) dialog", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();


            DatabaseReference transactionReference = FirebaseDatabase.getInstance().getReference(
                    FirebaseHandler.KEY_GROUPUSER + "/" + group + "/" + user + "/"
                            + FirebaseHandler.KEY_TRANSACTIONS);
            transactionReference.push().setValue(price);
            delete();
        } else {
            Snackbar.make(getView(), "You must block all selected items first",
                    Snackbar.LENGTH_LONG).show();
        }
        selectedPositions.clear();
    }

    /**
     * Checks if the specified user is allowed to buy the currently selected items.
     *
     * @param user Current user
     * @return True if user may buy, False otherwise
     */
    private boolean isAllowedToBuy(String user) {
        for (int position : selectedPositions) {
            ShoppingListEntry entry = fbAdapter.getItem(position);

            if (entry.isBlocked()) {
                String blockedBy = entry.getBlockedBy();
                if (!blockedBy.equals(user)) {
                    return false;
                }
            } else {
                return false; // Item not blocked
            }
        }

        return true;
    }


}
