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


import adp.group10.roomates.R;
import adp.group10.roomates.businesslogic.LoginManager;

public class UpdateUserAccountActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_account);



        final EditText etEmail;
        final EditText etPhone;
        final EditText etPassword;

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);


        Button bSelectUserInfo = (Button) findViewById(R.id.bUpdateUserValues);
        final DatabaseReference selectUserRef = FirebaseDatabase.getInstance().getReference("users").child(LoginActivity.currentuser);

        selectUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String UserEmail = dataSnapshot.child("email").getValue(String.class);
                etEmail.setText(UserEmail);

                String UserPhone = dataSnapshot.child("phone").getValue(String.class);
                etPhone.setText(UserPhone);

                String UserPassword = dataSnapshot.child("password").getValue(String.class);
                etPassword.setText(UserPassword);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        bSelectUserInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LoginManager lgmngr = new LoginManager();
                if (lgmngr.isValidEmail(etEmail.getText().toString()) == false) {
                    etEmail.setError("Invalid Email Address");
                    etEmail.requestFocus();
                }
                else if (lgmngr.isValidPassword(etPassword.getText().toString()) == false) {
                    etPassword.setError("Password is not valid");
                    etPassword.requestFocus();
                } else
                {
                    selectUserRef.getRef().child("email").setValue(etEmail.getText().toString());
                    selectUserRef.getRef().child("password").setValue(etPassword.getText().toString());
                    selectUserRef.getRef().child("phone").setValue(etPhone.getText().toString());

                    // selectUserRef.setValue(vuser);

                    finish();
                }
            }


        });

    }
}
