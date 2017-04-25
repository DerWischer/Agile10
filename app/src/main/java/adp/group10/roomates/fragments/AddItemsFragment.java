package adp.group10.roomates.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import adp.group10.roomates.R;
import adp.group10.roomates.backend.FirebaseHandler;
import adp.group10.roomates.backend.model.AvailableItem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddItemsFragment.OnFragmentInterActionListener} interface
 * to handle interaction events.
 * Use the {@link AddItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemsFragment extends Fragment implements AbsListView.MultiChoiceModeListener,
        AdapterView.OnItemClickListener {
    private OnFragmentInterActionListener mListener;

    private GridView gvList;
    private FirebaseListAdapter<AvailableItem> fbAdapter;

    public AddItemsFragment() { // Required empty public constructor
    }

    public static AddItemsFragment newInstance() {
        AddItemsFragment fragment = new AddItemsFragment();
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

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FirebaseHandler.KEY_AVAILABLE_LIST);
        fbAdapter = new FirebaseListAdapter<AvailableItem>(getActivity(), AvailableItem.class, R.layout.available_list_item, ref) {
            @Override
            protected void populateView(View view, AvailableItem model, int position) {
                TextView tvItemName = (TextView) view.findViewById(R.id.tvItemName);
                tvItemName.setText(model.getName());
            }
        };
        gvList.setAdapter(fbAdapter);
        gvList.setOnItemClickListener(this);
        gvList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        gvList.setMultiChoiceModeListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInterActionListener) {
            mListener = (OnFragmentInterActionListener) context;
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
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        if (checked) {
            selectedPositions.add(position);
        } else {
            selectedPositions.remove(selectedPositions.indexOf(position));
        }
        mode.setTitle(selectedPositions.size() + " selected");
    }

    private ArrayList<Integer> selectedPositions = new ArrayList<>();


    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.available_list_item_selected, menu);
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
            case R.id.menu_edit:
                edit();
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
     * Deletes all selected items in the available list
     */
    private void delete() {
        for (int position : selectedPositions) {
            fbAdapter.getRef(position).removeValue();
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
            final AvailableItem item = fbAdapter.getItem(position);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_add_item, null);
            final EditText etName = (EditText) dialogView.findViewById(R.id.etNewItem);
            etName.setText(item.getName());

            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(dialogView);
            builder.setTitle("Edit Item");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = etName.getText().toString().trim();
                    item.setName(name);
                    fbAdapter.getRef(position).setValue(item);
                }
            });
            builder.show();

        }
        selectedPositions.clear();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AvailableItem item = fbAdapter.getItem(position);
        mListener.onClickAvailableItem(item);
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
    public interface OnFragmentInterActionListener {
        void onClickAvailableItem(AvailableItem item);
    }
}
