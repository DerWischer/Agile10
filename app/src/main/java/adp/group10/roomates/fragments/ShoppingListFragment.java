package adp.group10.roomates.fragments;

import android.content.Context;
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
import android.widget.GridView;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.HashMap;

import adp.group10.roomates.R;
import adp.group10.roomates.backend.model.ShoppingListEntry;

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
    private ArrayAdapter<String> adapter;

    public ShoppingListFragment() { // Required empty public constructor
    }

    public static ShoppingListFragment newInstance() {
        ShoppingListFragment fragment = new ShoppingListFragment();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
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

        gvList = (GridView) getView().findViewById(R.id.gvTest);


        ArrayList<String> items = new ArrayList<String>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
        items.add("Item 5");

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.test_gv_simple_item, items);
        gvList.setAdapter(adapter);

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


    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        // TODO Keep track of which items are selected
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.shopping_list_item_selected, menu);
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
                // TODO
                snackMsg = "Delete";
                break;
            case R.id.menu_block:
                // TODO
                snackMsg = "Block";
                break;
            case R.id.menu_edit:
                // TODO
                snackMsg = "Edit";
                break;
            case R.id.menu_buy:
                // TODO
                snackMsg = "Buy";
                break;
            default:
                return false;
        }
        mode.finish();

        Snackbar.make(getView(), snackMsg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }


}
