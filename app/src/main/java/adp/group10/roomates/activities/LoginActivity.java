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
        setTitle("Sign In");

        Button bUserLogin = (Button) findViewById(R.id.bLogin);
        bUserLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText etUsername;
                EditText etPassword;
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
                    finish();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });

    }

    public void onClick_Register(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }


}
