package adp.group10.roomates.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import adp.group10.roomates.R;
import adp.group10.roomates.backend.model.AvailableItem;

public class CreateGroupActivity extends AppCompatActivity {
    public static class Group {

        public String GroupDesc;
        public String GroupName;


        public Group(String GroupDesc) {
            this.GroupDesc = GroupDesc;
        }

        public Group(String GroupDesc, String GroupName) {
            this.GroupDesc = GroupDesc;
            this.GroupName = GroupName;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
    }




    public void onClick_CreateGroup(View view) {
        final EditText etGroupname = (EditText) findViewById(R.id.etGroupName);
        final EditText etGroupdesc = (EditText) findViewById(R.id.etGroupdesc);

        if (etGroupname.getText().toString().length() == 0) {
            etGroupname.setError("Group name is required");
            etGroupname.requestFocus();
        } else {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();

            final DatabaseReference groupsRef =
                    FirebaseDatabase.getInstance().getReference().child("GROUPS");


            groupsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if ( dataSnapshot.child(etGroupname.getText().toString()).getValue() != null) {

                        etGroupname.setError(
                                "Group already exists");
                    }
                    else if (etGroupname.getText().toString().length() > 8)

                    {
                        etGroupname.setError(
                                "Max length is 8 characters");
                    }

                    else {
                        // add the group


                        Map<String, CreateGroupActivity.Group> GROUPS =
                                new HashMap<String, CreateGroupActivity.Group>();
                        CreateGroupActivity.Group vgroup = new CreateGroupActivity.Group(
                                etGroupdesc.getText().toString());
                        groupsRef.child(etGroupname.getText().toString()).setValue(vgroup);

                        DatabaseReference availableItemsRef = FirebaseDatabase.getInstance().getReference(
                                "available-items" + "/" + etGroupname.getText());


                        for (AvailableItem item : standardItems) {
                            availableItemsRef.push().setValue(item);
                        }


                        // TODO This will override an existing group
                        //groupsRef.child(etGroupname.getText().toString()).getValue(vgroup);

                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });


        }
    }

    private final static AvailableItem[] standardItems = new AvailableItem[]
            {
                    new AvailableItem("Bread"),
                    new AvailableItem("Milk"),
                    new AvailableItem("Pasta"),
                    new AvailableItem("Apple"),
                    new AvailableItem("Cheese"),
                    new AvailableItem("Eggs"),
                    new AvailableItem("Corn Flakes"),
                    new AvailableItem("Chicken"),
                    new AvailableItem("Tomatoes")
            };





}
