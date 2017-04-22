package adp.group10.roomates.activities;

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

public class JoinGroupActivity extends AppCompatActivity {
    public static class GROUPUSER {

        public String username;
        public String Groupname;
        public String balance;
        public String transactions;

        public GROUPUSER(String pGroupName, String pusername, String pbalance,
                String transactions) {
            this.Groupname = pGroupName;
            this.username = pusername;
            this.balance = pbalance;
            this.transactions = transactions;
        }

        public GROUPUSER(String pusername, String pbalance, String transactions) {
            this.username = pusername;
            this.balance = pbalance;
            this.transactions = transactions;
        }

        public GROUPUSER(String pusername) {
            this.username = pusername;

        }

        public GROUPUSER(String pbalance, String transactions) {
            this.balance = pbalance;
            this.transactions = transactions;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
    }

    public void onClick_AddUser(View view) {
        EditText etGroupname = (EditText) findViewById(R.id.etGroupName);
        EditText etUserName = (EditText) findViewById(R.id.etUserName);

        if (etUserName.getText().toString().length() == 0) {
            etUserName.setError("User name is required");
            etUserName.requestFocus();
        }
        if (etGroupname.getText().toString().length() == 0) {
            etGroupname.setError("Group name is required");
            etGroupname.requestFocus();
        } else {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference groupsRef = FirebaseDatabase.getInstance().getReference().child(
                    "GROUPUSER");

            Map<String, JoinGroupActivity.GROUPUSER> GROUPUSER =
                    new HashMap<String, JoinGroupActivity.GROUPUSER>();

            JoinGroupActivity.GROUPUSER vguser1 = new JoinGroupActivity.GROUPUSER("0", "0");
            groupsRef.child(etGroupname.getText().toString()).child(
                    etUserName.getText().toString()).setValue(vguser1);
        }
    }
}
