package adp.group10.roomates.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import adp.group10.roomates.R;
import adp.group10.roomates.backend.FirebaseHandler;
import adp.group10.roomates.backend.model.AvailableItem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddItemsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private GridView gvList;
    private FirebaseListAdapter<AvailableItem> fbAdaper;

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
        fbAdaper = new FirebaseListAdapter<AvailableItem>(getActivity(), AvailableItem.class, R.layout.available_list_item, ref) {
            @Override
            protected void populateView(View view, AvailableItem model, int position) {
                TextView tvItemName = (TextView) view.findViewById(R.id.tvItemName);
                tvItemName.setText(model.getName());
            }
        };
        gvList.setAdapter(fbAdaper);
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
}
