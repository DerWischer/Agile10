package adp.group10.roomates.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import adp.group10.roomates.R;

public class JoinGroupActivity extends AppCompatActivity {

    public static class GROUPUSER {

        public String username;
        public String Groupname;
        public String BALANCE;
        public String TRANSACTIONS;

        public GROUPUSER(String pGroupName, String pusername, String pbalance,
                String transactions) {
            this.Groupname = pGroupName;
            this.username = pusername;
            this.BALANCE = pbalance;
            this.TRANSACTIONS = transactions;
        }

        public GROUPUSER(String pusername, String pbalance, String transactions) {
            this.username = pusername;
            this.BALANCE = pbalance;
            this.TRANSACTIONS = transactions;
        }

        public GROUPUSER(String pusername) {
            this.username = pusername;

        }

        public GROUPUSER(String pbalance, String transactions) {
            this.BALANCE = pbalance;
            this.TRANSACTIONS = transactions;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
    }

    public void onClick_AddUser(View view) {
        final EditText etGroupname = (EditText) findViewById(R.id.etGroupName);
        final EditText etUserName = (EditText) findViewById(R.id.etUserName);

        if (etUserName.getText().toString().length() == 0) {
            etUserName.setError("User name is required");
            etUserName.requestFocus();
        }
        else if (etGroupname.getText().toString().length() == 0) {
            etGroupname.setError("Group name is required");
            etGroupname.requestFocus();
        }
        else {


           final FirebaseDatabase database = FirebaseDatabase.getInstance();

            final  DatabaseReference userExt = FirebaseDatabase.getInstance().getReference().child("users");
            /// Check if the user exists in DB
               userExt.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if ( dataSnapshot.child(etUserName.getText().toString()).getValue() == null) {

                        etUserName.setError("Username is not valid");

                    }
                    else
                     {  final  DatabaseReference groupsExt = FirebaseDatabase.getInstance().getReference().child("GROUPS");
                        /// Check if the Group exists in DB
                        groupsExt.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if ( dataSnapshot.child(etGroupname.getText().toString()).getValue() == null) {
                                etGroupname.setError("Groupname is not valid");

                            }
                           else
                            {
                             final  DatabaseReference groupsRef = FirebaseDatabase.getInstance().getReference().child("GROUPUSER");
                            /// Check if the user exists in the GROUPUSER under the current Group
                            groupsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if ( dataSnapshot.child(etGroupname.getText().toString()).child(etUserName.getText().toString()).getValue() != null) {

                                        etGroupname.setError("User is already a member in this group");
                                    }

                                    else {

                                        /// set value to GROUPUSER and USERS
                                        Map<String, JoinGroupActivity.GROUPUSER> GROUPUSER =  new HashMap<String, JoinGroupActivity.GROUPUSER>();
                                        JoinGroupActivity.GROUPUSER vguser1 = new JoinGroupActivity.GROUPUSER("0", "0");
                                        groupsRef.child(etGroupname.getText().toString()).child(
                                                etUserName.getText().toString()).setValue(vguser1);

                                        /////
                                        //DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");

                                        //Map<String, RegisterActivity.User> User = new HashMap<String, RegisterActivity.User>();

                                        RegisterActivity.User user1 = new RegisterActivity.User(etGroupname.getText().toString());
                                        userExt.child(etUserName.getText().toString()).child("groups").push().setValue(user1);


                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }

                            });
                           }



                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}

                    });
                    }

                }
                   @Override
                public void onCancelled(DatabaseError databaseError) {}

              });









        }
    }
}

