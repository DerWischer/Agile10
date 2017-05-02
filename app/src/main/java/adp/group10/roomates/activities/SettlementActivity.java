package adp.group10.roomates.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import adp.group10.roomates.R;
import adp.group10.roomates.backend.FirebaseHandler;
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
                TextView tvAmount = (TextView) view.findViewById(R.id.bAmount);
                final CheckBox cbSetlled = (CheckBox) view.findViewById(R.id.cbSettled);

                tvFromUser.setText(model.getFromUser());
                tvToUser.setText(model.getToUser());
                tvAmount.setText("" + model.getAmount());
                cbSetlled.setChecked(model.isSettled());

                final String toUser = model.getToUser();
                cbSetlled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        getRef(position).child("settled").setValue(isChecked);
                    }
                });
            }
        };
        lvSettlement.setAdapter(fbAdapter);
    }
}
