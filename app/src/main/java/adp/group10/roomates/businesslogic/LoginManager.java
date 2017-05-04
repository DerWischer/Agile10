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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Joshua Jungen on 20.04.2017.
 */

public class LoginManager   {

    public boolean isValidEmail(String pEmail)
    {
        if ((!pEmail.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) || pEmail.length() == 0)
         {
             return false;
         }
         else
         {
             return true;
         }
    }


    public  boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public int loginValidation(String pName, String Ppassword)
    {
        if (pName.length() == 0 ) {
            return 0;

        }
        else if ( Ppassword.length() == 0)
        { return 1;

        }
        else
        {
            return 2;
        }
    }




}
