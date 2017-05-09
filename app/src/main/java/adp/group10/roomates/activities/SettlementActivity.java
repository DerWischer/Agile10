package adp.group10.roomates.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import adp.group10.roomates.R;
import adp.group10.roomates.backend.FirebaseHandler;
import adp.group10.roomates.backend.model.AvailableItem;
import adp.group10.roomates.backend.model.ShoppingListEntry;
import adp.group10.roomates.backend.model.Transaction;

public class SettlementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);


    }




    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference ref0 = FirebaseDatabase.getInstance().getReference("updateTransactions");
        ref0.setValue(LoginActivity.currentGroup);

        final ListView lvSettlement = (ListView) findViewById(R.id.lvSettlements);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FirebaseHandler.KEY_TRANSACTIONS + "/" + LoginActivity.currentGroup);
        FirebaseListAdapter fbAdapter = new FirebaseListAdapter<Transaction>(this, Transaction.class, R.layout.settlement_list_item, ref) {
            @Override
            protected void populateView(View view, Transaction model, final int position) {
                TextView tvFromUser = (TextView) view.findViewById(R.id.tvFromUser);
                TextView tvToUser = (TextView) view.findViewById(R.id.tvToUser);
                TextView tvAmount = (TextView) view.findViewById(R.id.etAmount);
                final Button bSetlled = (Button) view.findViewById(R.id.settlement_button);

                final String fromUser = model.getFromUser();
                final String toUser = model.getToUser();
                final String amount = "" + model.getAmount();

                tvFromUser.setText(fromUser);
                tvToUser.setText(toUser);
                tvAmount.setText(amount);


                //bSetlled.setChecked(model.isSettled());


                bSetlled.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //
                        final AlertDialog.Builder builder = new AlertDialog.Builder(SettlementActivity.this);
                        builder.setMessage("Are you sure you want to settle?\n\n"
                                + "\nFrom: " + fromUser
                                + "\n\nCost: " + amount);


                        builder.setPositiveButton("Yes.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getRef(position).child("settled").setValue(true);
                            }
                        });

                        builder.setNegativeButton("No.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.setTitle("Warning");

                        builder.show();
                    }

                });
                //cbSetlled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                //    @Override
                //    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //        getRef(position).child("settled").setValue(isChecked);
                //    }
                //});
            }
        };
        lvSettlement.setAdapter(fbAdapter);
    }
}
