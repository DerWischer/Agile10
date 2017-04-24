package adp.group10.roomates.activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import adp.group10.roomates.R;
import adp.group10.roomates.backend.FirebaseHandler;
import adp.group10.roomates.backend.model.AvailableItem;
import adp.group10.roomates.backend.model.ShoppingListEntry;
import adp.group10.roomates.fragments.AddItemsFragment;
import adp.group10.roomates.fragments.ShoppingListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ShoppingListFragment.OnFragmentInteractionListener,
        AddItemsFragment.OnFragmentInteractionListener {

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
            case R.id.nav_settings:
                // TODO Open setting
                Snackbar.make(drawer, "Open Settings", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.nav_group_join:
                startActivity(new Intent(this, JoinGroupActivity.class));
                break;
            case R.id.nav_group_create:
                startActivity(new Intent(this, CreateGroupActivity.class));
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
        // TODO Open dialog
        //Snackbar.make(view, "Open AddItem Dialog", Snackbar.LENGTH_LONG)
        //        .setAction("Action", null).show();
        final Dialog dialogAddItem = new Dialog(MainActivity.this);
        dialogAddItem.setTitle("Custom Item");
        dialogAddItem.setContentView(R.layout.dialog_add_item);
        dialogAddItem.show();

        final EditText dialogResults = (EditText) dialogAddItem.findViewById(R.id.et_NewItem);
        Button dialogSubmitButton = (Button) dialogAddItem.findViewById(R.id.b_NewITem);

        dialogSubmitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AvailableItem item = new AvailableItem(dialogResults.getText().toString());
                ShoppingListEntry shoppingListItem = new ShoppingListEntry(dialogResults.getText().toString(), 1);

                DatabaseReference availableItemsRef = FirebaseDatabase.getInstance().getReference("available-items");
                availableItemsRef.push().setValue(item);

                DatabaseReference shoppingListRef = FirebaseDatabase.getInstance().getReference("shopping-list");
                shoppingListRef.push().setValue(shoppingListItem);

                dialogAddItem.cancel();
            }

        });


    }
}
