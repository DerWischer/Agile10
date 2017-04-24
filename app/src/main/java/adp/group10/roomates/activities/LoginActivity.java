package adp.group10.roomates.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import android.content.Context;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import adp.group10.roomates.R;
import adp.group10.roomates.backend.model.ShoppingListEntry;

import adp.group10.roomates.businesslogic.LoginManager;
public class LoginActivity extends AppCompatActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button bUserLogin = (Button) findViewById(R.id.bLogin);
        bUserLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText etUsername;
                final EditText etPassword;
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


                    DatabaseReference mTemplateRef= FirebaseDatabase.getInstance().getReference("users");
                    mTemplateRef.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    String password = dataSnapshot.child(etUsername.getText().toString()).child("password").getValue(String.class);
                    //String user = dataSnapshot.child(etUsername.getText().toString()).getValue(String.class);



                        if ( !password.equals( etPassword.getText().toString() )   )
                         {   etUsername.setError("Wrong User name or Password");
                             etUsername.requestFocus(); }
                        else
                        { startActivity(new Intent(LoginActivity.this, OptionsActivity.class));}

                        /*LoginManager Loginmanager= new LoginManager();
                        Loginmanager.CheckUserPassword(etUsername.getText().toString(), etPassword.getText().toString());
                        Context context = getApplicationContext();
                        CharSequence text = Loginmanager.Pass;
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();*/

                        /**Context context = getApplicationContext();
                        CharSequence text = etPassword.getText().toString() ;
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();*/
                }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                  }
                });


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
