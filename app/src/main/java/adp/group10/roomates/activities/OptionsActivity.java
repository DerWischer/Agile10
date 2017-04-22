package adp.group10.roomates.activities;

import android.content.Intent;
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

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button bCreateNewGroup = (Button) findViewById(R.id.bCreateNewGroup);
        bCreateNewGroup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    startActivity(new Intent(OptionsActivity.this, CreateGroupActivity.class));
            }

            });

        Button bAddUser = (Button) findViewById(R.id.bAddUser);
        bAddUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(OptionsActivity.this, JoinGroupActivity.class));
            }

        });


        Button bShoppingList = (Button) findViewById(R.id.bShoppingList);
        bShoppingList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(OptionsActivity.this, ShoppingListActivity.class));
            }

        });

    }





}
