package adp.group10.roomates.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import adp.group10.roomates.backend.ShoppingListStorage;
import adp.group10.roomates.R;
import adp.group10.roomates.model.ShoppingListEntry;

public class AddEditItemActivity extends AppCompatActivity {

    /* The mode, when passed through the intent, will determine the activities appearance*/
    public final static String MODE = "mode";
    public final static String MODE_ADD = "add";
    public final static String MODE_EDIT = "edit";
    public final static String MODE_BUY = "buy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);
        Button addButton = (Button) findViewById(R.id.addButton2);
        final EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        final EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        final EditText priceEditText = (EditText) findViewById(R.id.priceEditText);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        final String mode;
        final ShoppingListEntry entry;
        if (extra == null) {
            mode = MODE_ADD;
            entry = null;
        } else {
            mode = extra.getString(MODE, MODE_ADD);
            entry = (ShoppingListEntry) extra.getSerializable(
                    ShoppingListStorage.class.getSimpleName());
        }

        switch (mode) {
            case MODE_ADD:
                priceEditText.setEnabled(false);
                break;
            case MODE_EDIT:
                // TODO if entry == null -> exception
                nameEditText.setText(entry.getName());
                amountEditText.setText("" + entry.getAmount());
                addButton.setText("Update");
                priceEditText.setEnabled(false);
                break;
            case MODE_BUY:
                // TODO if entry == null -> exception
                nameEditText.setText(entry.getName());
                amountEditText.setText("" + entry.getAmount());
                priceEditText.setEnabled(true);
                addButton.setText("Buy");
                break;
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                int amount = Integer.parseInt(amountEditText.getText().toString());


                Intent returnIntent = new Intent(getApplicationContext(), ShoppingListEntry.class);
                if (entry != null) { // editing existing entry
                    entry.setName(name);
                    entry.setAmount(amount);
                    if (mode.equals(MODE_BUY)) {
                        try {
                            double price = Double.parseDouble(priceEditText.getText().toString());
                            entry.setPrice(price);
                        } catch(NumberFormatException e){
                            Toast.makeText(getApplicationContext(), "Please enter the price", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }

                    returnIntent.putExtra(ShoppingListEntry.class.getSimpleName(), entry);
                } else { // creating new entry
                    ShoppingListEntry newEntry = new ShoppingListEntry(name, amount);
                    returnIntent.putExtra(ShoppingListEntry.class.getSimpleName(), newEntry);
                }

                returnIntent.putExtra(MODE, mode);
                setResult(RESULT_OK, returnIntent);

                finish();
            }
        });
    }
}
