package adp.group10.roomates.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SelectGroupActivity extends AppCompatActivity  {
    
    public static String currentgroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group);

        Button bSelectgroup = (Button) findViewById(R.id.bChooseGroup);
        final List<String> groups = new ArrayList<String>();
        groups.add("dummyGroup");
        DatabaseReference selectGroupRef = FirebaseDatabase.getInstance().getReference("users").child(LoginActivity.currentuser).child("groups");
        selectGroupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array

                for (DataSnapshot groupSnapShot: dataSnapshot.getChildren()) {
                    String groupName = groupSnapShot.child("usergroup").getValue(String.class);
                    groups.add(groupName.trim());
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, groups);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        Spinner groupSpinner = (Spinner) findViewById(R.id.spSelectGroup);
        groupSpinner.setAdapter(groupAdapter);


        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {

                currentgroup = parent.getItemAtPosition(position).toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });



        bSelectgroup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectGroupActivity.this, MainActivity.class);
                startActivity(intent);


            }


        });

    }




}
