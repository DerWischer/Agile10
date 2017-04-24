package adp.group10.roomates.businesslogic;



import android.os.StrictMode;
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

/**
 * Created by Joshua Jungen on 20.04.2017.
 */

public class LoginManager   {

        public static  String Pass  ;
        public  void CheckUserPassword(String pUsername, String pPassword) {


            DatabaseReference mTemplateRef= FirebaseDatabase.getInstance().getReference("users").child(pUsername).child("password");

            mTemplateRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Passw =  dataSnapshot.getValue(String.class).toString();
                Pass = Passw ;
                System.out.print(Pass);
               }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });





    } // CheckUserPassword end



}
