package adp.group10.roomates.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.internal.ForegroundLinearLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import adp.group10.roomates.R;
import adp.group10.roomates.backend.FirebaseHandler;
import adp.group10.roomates.backend.model.AvailableItem;
import adp.group10.roomates.backend.model.ShoppingListEntry;
import adp.group10.roomates.fragments.AddItemsFragment;
import adp.group10.roomates.fragments.ShoppingListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ShoppingListFragment.OnFragmentInteractionListener,
        AddItemsFragment.OnFragmentInterActionListener {


    public DataSnapshot latestAvailableItemSnapshot;
    public DataSnapshot latestShoppingListSnapshot;

    @Override
    protected void onStart() {
        super.onStart();
        if (LoginActivity.currentGroup == null )
        {

            startActivity(new Intent(this, SelectGroupActivity.class));

        }
        else
        {
            // Navigation View
             NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
             View header = navigationView.getHeaderView(0);

            TextView tvUserName = (TextView) header.findViewById(R.id.tvUserName);
            TextView tvGroupName = (TextView) header.findViewById(R.id.tvGroupName);
            final TextView tvUserBalance = (TextView) header.findViewById(R.id.tvUserBalance);
            tvUserName.setText(LoginActivity.currentuser);
            tvGroupName.setText("Group: "  + LoginActivity.currentGroup);
            DatabaseReference balanceRef = FirebaseDatabase.getInstance().getReference(
                    FirebaseHandler.KEY_GROUPUSER + "/" + LoginActivity.currentGroup + "/"
                            + LoginActivity.currentuser + "/" + "BALANCE");
            balanceRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    double balance = Double.parseDouble(dataSnapshot.getValue().toString());
                    String result = String.format("%.2f", balance);
                    tvUserBalance.setText("Balance:" + result + "~");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference availableItemRef = database.getReference(
                    FirebaseHandler.KEY_AVAILABLE_LIST + "/" + LoginActivity.currentGroup);
            DatabaseReference shoppingListRef = database.getReference(
                    FirebaseHandler.KEY_SHOPPING_LIST + "/" + LoginActivity.currentGroup);

            availableItemRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    latestAvailableItemSnapshot = dataSnapshot;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

            shoppingListRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    latestShoppingListSnapshot = dataSnapshot;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

            ShoppingListFragment fragment =
                    (ShoppingListFragment) getSupportFragmentManager().findFragmentById(
                            R.id.fShoppingList);
            fragment.updateUI();


            AddItemsFragment fragment1 =
                    (AddItemsFragment) getSupportFragmentManager().findFragmentById(
                            R.id.fAddItemsFragment);
            fragment1.updateUI();

        }

    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // App Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Navigation View
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.nav_group_join:
                startActivity(new Intent(this, JoinGroupActivity.class));
                break;
            case R.id.nav_group_choose:
                startActivity(new Intent(this, SelectGroupActivity.class));
                break;
            case R.id.nav_group_create:
                startActivity(new Intent(this, CreateGroupActivity.class));
                break;
            case R.id.nav_account_update:
                startActivity(new Intent(this, UpdateUserAccountActivity.class));
                break;
            case R.id.nav_settlement_request:
                startActivity(new Intent(this, SettlementActivity.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // TODO Implement interaction between ShoppingListFragment and AddItemsFragment
    }

    /**
     * Opens a dialog to add a new item to the shopping list
     */
    public void onClick_fabAddCustomItem(View view) {
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add_item, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Add your own item");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText etNewItem = (EditText) dialogView.findViewById(R.id.etNewItem);
                String name = etNewItem.getText().toString().trim(); // TODO Check for length==0
                AvailableItem item = new AvailableItem(name);
                ShoppingListEntry shoppingListItem = new ShoppingListEntry(name,
                        1); // TODO check for duplicate

                DatabaseReference shoppingListRef = FirebaseDatabase.getInstance().getReference(
                        FirebaseHandler.KEY_SHOPPING_LIST + "/" + LoginActivity.currentGroup);

                DatabaseReference availableItemsRef = FirebaseDatabase.getInstance().getReference(
                        FirebaseHandler.KEY_AVAILABLE_LIST + "/" + LoginActivity.currentGroup);


                if(!isDuplicateName(item.getName(), latestShoppingListSnapshot))
                    shoppingListRef.push().setValue(shoppingListItem);
                else
                    incrementShoppingCartItem(item.getName(), 1);

                if(!isDuplicateName(item.getName(), latestAvailableItemSnapshot))
                    availableItemsRef.push().setValue(item);


            }
        });

        builder.show();


    }

    //For AvailableItems only
    public boolean isDuplicateName(String item, DataSnapshot snapShot) {


        for (DataSnapshot snap : snapShot.getChildren()) {
            String currentIteratingItem = snap.getValue(AvailableItem.class).getName();
            if (currentIteratingItem.equals(item)) {
                return true;
            }

        }

        return false;
    }

    public void incrementShoppingCartItem(String Name, int increment) {
        for (DataSnapshot snap : latestShoppingListSnapshot.getChildren()) {
            ShoppingListEntry currentIteratingItem = snap.getValue(ShoppingListEntry.class);
            Log.v("Iteration", currentIteratingItem.getName());
            if (currentIteratingItem.getName().equals(Name)) {
                if (currentIteratingItem.getAmount() + increment <= 0){
                    snap.getRef().removeValue();
                }
                else{
                    currentIteratingItem.setAmount(currentIteratingItem.getAmount() + increment);
                    snap.getRef().setValue(currentIteratingItem);
                }


                return;
            }

        }
        return;
    }


    @Override
    public void onClickAvailableItem(AvailableItem item) {
        // TODO Increment in ShoppingListFragment
        ShoppingListFragment fragment =
                (ShoppingListFragment) getSupportFragmentManager().findFragmentById(
                        R.id.fShoppingList);
        fragment.onClickAvailableItem(item);
    }
}
