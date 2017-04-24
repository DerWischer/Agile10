package adp.group10.roomates.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import adp.group10.roomates.R;

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
        EditText etGroupname = (EditText) findViewById(R.id.etGroupName);
        EditText etGroupdesc = (EditText) findViewById(R.id.etGroupdesc);

        if (etGroupname.getText().toString().length() == 0) {
            etGroupname.setError("Group name is required");
            etGroupname.requestFocus();
        } else {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference groupsRef =
                    FirebaseDatabase.getInstance().getReference().child("GROUPS");

            Map<String, CreateGroupActivity.Group> GROUPS =
                    new HashMap<String, CreateGroupActivity.Group>();

            CreateGroupActivity.Group vgroup = new CreateGroupActivity.Group(
                    etGroupdesc.getText().toString());
            groupsRef.child(etGroupname.getText().toString()).setValue(vgroup);
            //groupsRef.child(etGroupname.getText().toString()).getValue(vgroup);

            finish();
        }
    }
}
