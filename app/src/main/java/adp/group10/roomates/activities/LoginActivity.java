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


        Button bUserLogin = (Button) findViewById(R.id.bLogin);
        bUserLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText etUsername;
                EditText etPassword;
                etUsername   = (EditText)findViewById(R.id.etUserName);
                etPassword  = (EditText)findViewById(R.id.etPassword);
                if (etUsername.getText().toString().length() ==0 )
                 {    etUsername.setError("User name is required");
                     etUsername.requestFocus(); }
                else if ( etPassword.getText().toString().length()==0 )
                 {   etPassword.setError("Password is required");
                     etPassword.requestFocus();  }
                else
                {
                    //DatabaseReference mTemplateRef= FirebaseDatabase.getInstance().getReference().child("users");
                    startActivity(new Intent(LoginActivity.this, OptionsActivity.class));

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
