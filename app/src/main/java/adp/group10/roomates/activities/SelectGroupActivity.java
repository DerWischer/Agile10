package adp.group10.roomates.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adp.group10.roomates.R;
import adp.group10.roomates.backend.GroupNotificationSubscriber;
import adp.group10.roomates.businesslogic.LoginManager;

public class SelectGroupActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group);

        Button bSelectgroup = (Button) findViewById(R.id.bChooseGroup);
        final List<String> groups = new ArrayList<String>();
        groups.add("");
        DatabaseReference selectGroupRef = FirebaseDatabase.getInstance().getReference(
                "users").child(LoginActivity.currentuser).child("groups");

        final ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, groups);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        final Spinner groupSpinner = (Spinner) findViewById(R.id.spSelectGroup);
        groupSpinner.setAdapter(groupAdapter);


        selectGroupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array

                for (DataSnapshot groupSnapShot : dataSnapshot.getChildren()) {
                    String groupName = groupSnapShot.child("usergroup").getValue(String.class);
                    groups.add(groupName.trim());
                    groupAdapter.notifyDataSetChanged();
                    GroupNotificationSubscriber.Subscribe(groupSnapShot.child("usergroup").getValue(String.class));
                }

                if (LoginActivity.currentGroup != null) {
                    int pos = -1;
                    for (int i = 0; i < groups.size(); i++) {
                        String cgroup = groups.get(i);
                        if (cgroup.equals(LoginActivity.currentGroup)) {
                            pos = i ;
                            break;
                        }

                    }

                    groupSpinner.setSelection(pos);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        bSelectgroup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int pos = groupSpinner.getSelectedItemPosition();
                LoginActivity.currentGroup = groups.get(pos);
                //Intent intent = new Intent(SelectGroupActivity.this, MainActivity.class);
                //startActivity(intent);
                finish();

            }


        });

    }


}
