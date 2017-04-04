package adp.group10.roomates.activities;

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

        public User(String pemail,String pphone, String ppassword) {
            this.email = pemail;
            this.phone = pphone;
            this.password = ppassword;
        }

        public User(String pusername, String pemail,String pphone, String ppassword  ) {
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

        Button createUser = (Button) findViewById(R.id.bCreateUser);
        createUser.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                EditText vusername;
                EditText vemail;
                EditText vphone;
                EditText vpassword;
                EditText vpassconf;


                vusername   = (EditText)findViewById(R.id.etUsername);
                vemail   = (EditText)findViewById(R.id.etEmail);
                vphone  = (EditText)findViewById(R.id.etPhoneNumber);
                vpassword   = (EditText)findViewById(R.id.etPassword);
                vpassconf  = (EditText)findViewById(R.id.etPassConf);

                if(vusername.getText().toString().length()==0){
                    vusername.setError("User name is required");
                    vusername.requestFocus();
                }
                else if (vpassword.getText().toString().length()==0){
                    vpassword.setError("Password is not entered");
                    vpassword.requestFocus();
                }
                else if(vphone.getText().toString().length()==0){
                    vphone.setError("Phone is required");
                    vphone.requestFocus();
                }

                else if (!vpassword.getText().toString().equals(vpassconf.getText().toString())){
                    vpassconf.setError("Password Not matched");
                    vpassconf.requestFocus();
                }
                else if (vpassconf.getText().toString().length()==0){
                    vpassconf.setError("Please confirm password");
                    vpassconf.requestFocus();
                }
                else if(vemail.getText().toString().length()==0){
                    vemail.setError("Email is required");
                    vemail.requestFocus();
                }

                else
                    {final FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

                Map<String, User> users = new HashMap<String, User>();
                User vuser = new User(vemail.getText().toString(),vphone.getText().toString(),vpassword.getText().toString());
                    usersRef.child(vusername.getText().toString()).setValue(vuser);

                startActivity(new Intent(RegisterActivity.this, ShoppingListActivity.class));}

            }


        });}
}
