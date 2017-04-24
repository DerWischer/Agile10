package adp.group10.roomates.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.view.View;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


//import com.google.firebase.database.ValueEventListener;

import adp.group10.roomates.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Sign In");

        Button bUserLogin = (Button) findViewById(R.id.bLogin);
        bUserLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText etUsername;
                final EditText etPassword;
                etUsername = (EditText) findViewById(R.id.etUserName);
                etPassword = (EditText) findViewById(R.id.etPassword);
                if (etUsername.getText().toString().length() == 0) {
                    etUsername.setError("User name is required");
                    etUsername.requestFocus();
                } else if (etPassword.getText().toString().length() == 0) {
                    etPassword.setError("Password is required");
                    etPassword.requestFocus();
                } else {
                    //DatabaseReference mTemplateRef= FirebaseDatabase.getInstance().getReference
                    // ().child("users");
                    // TODO Authenticate
                    DatabaseReference mTemplateRef= FirebaseDatabase.getInstance().getReference("users").child(etUsername.getText().toString()).child("password");
                    mTemplateRef.addListenerForSingleValueEvent(new ValueEventListener(){
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() == null) {
                                etUsername.setError("Username / Password combination does not exist");
                            }
                            else {
                                String Password =  dataSnapshot.getValue(String.class).toString();

                                if ( !Password.equals(etPassword.getText().toString()))
                                {   etUsername.setError("Username / Password combination does not exist");
                                etUsername.requestFocus();}
                                else
                                {   finish();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class)); }
                                }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });


                }
            }
        });

    }

    public void onClick_Register(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }


}
