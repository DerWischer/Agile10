package adp.group10.roomates.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.view.View;

//import com.google.firebase.database.ValueEventListener;

import adp.group10.roomates.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button loginButton = (Button) findViewById(R.id.bLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText vusername;
                EditText vpassword;
                vusername   = (EditText)findViewById(R.id.etUserName);
                vpassword  = (EditText)findViewById(R.id.etPassword);
                if (vusername.getText().toString().length() ==0 )
                 {    vusername.setError("User name is required");
                     vusername.requestFocus(); }
                else if ( vpassword.getText().toString().length()==0 )
                 {   vpassword.setError("Password is required");
                     vpassword.requestFocus();  }
                else
                {
                    //DatabaseReference mTemplateRef= FirebaseDatabase.getInstance().getReference().child("users");
                    startActivity(new Intent(LoginActivity.this, ShoppingListActivity.class));

                }

                // Context context = getApplicationContext();
               // CharSequence text = "Hello toast!";
               // int duration = Toast.LENGTH_SHORT;

                //Toast toast = Toast.makeText(context, text, duration);
               // toast.show();
            }
        });


        Button registerButton = (Button) findViewById(R.id.bRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });
        }

    @Override
    protected void onStart() {

        super.onStart();
    }





}
