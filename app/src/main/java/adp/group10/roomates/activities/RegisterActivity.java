package adp.group10.roomates.activities;

import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.content.Context;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import adp.group10.roomates.R;

public class RegisterActivity extends AppCompatActivity {
    public static class User {

        public String username;
        public String email;
        public String phone;
        public String password;

        public User(String pemail, String pphone, String ppassword) {
            this.email = pemail;
            this.phone = pphone;
            this.password = ppassword;
        }

        public User(String pusername, String pemail, String pphone, String ppassword) {
            this.username = pusername;
            this.email = pemail;
            this.phone = pphone;
            this.password = ppassword;
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button bCreateUser = (Button) findViewById(R.id.bCreateUser);
        bCreateUser.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                EditText etUsername;
                EditText etEmail;
                EditText etPhone;
                EditText etPassword;
                EditText etPassconf;


                etUsername = (EditText) findViewById(R.id.etUsername);
                etEmail = (EditText) findViewById(R.id.etEmail);
                etPhone = (EditText) findViewById(R.id.etPhoneNumber);
                etPassword = (EditText) findViewById(R.id.etPassword);
                etPassconf = (EditText) findViewById(R.id.etPassConf);

                if (etUsername.getText().toString().length() == 0) {
                    etUsername.setError("User name is required");
                    etUsername.requestFocus();
                } else if (etPassword.getText().toString().length() == 0) {
                    etPassword.setError("Password is not entered");
                    etPassword.requestFocus();
                } else if (etPhone.getText().toString().length() == 0) {
                    etPhone.setError("Phone is required");
                    etPhone.requestFocus();
                } else if (!etPassword.getText().toString().equals(
                        etPassconf.getText().toString())) {
                    etPassconf.setError("Password Not matched");
                    etPassconf.requestFocus();
                } else if (etPassconf.getText().toString().length() == 0) {
                    etPassconf.setError("Please confirm password");
                    etPassconf.requestFocus();
                } else if (etEmail.getText().toString().length() == 0) {
                    etEmail.setError("Email is required");
                    etEmail.requestFocus();
                } else {
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference usersRef =
                            FirebaseDatabase.getInstance().getReference().child("users");

                    Map<String, User> users = new HashMap<String, User>();
                    User vuser = new User(etEmail.getText().toString(),
                            etPhone.getText().toString(), etPassword.getText().toString());
                    usersRef.child(etUsername.getText().toString()).setValue(vuser);

                    //startActivity(new Intent(RegisterActivity.this, ShoppingListActivity.class))

                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }

            }


        });
    }
}
