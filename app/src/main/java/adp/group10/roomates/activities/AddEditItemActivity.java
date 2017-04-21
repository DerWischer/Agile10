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
import adp.group10.roomates.backend.model.ShoppingListEntry;

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
        Button addButton = (Button) findViewById(R.id.bAdd);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etAmount = (EditText) findViewById(R.id.etAmount);
        final EditText etPrice = (EditText) findViewById(R.id.etPrice);

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
                etPrice.setEnabled(false);
                break;
            case MODE_EDIT:
                // TODO if entry == null -> exception
                etName.setText(entry.getName());
                etAmount.setText("" + entry.getAmount());
                addButton.setText("Update");
                etPrice.setEnabled(false);
                break;
            case MODE_BUY:
                // TODO if entry == null -> exception
                etName.setText(entry.getName());
                etAmount.setText("" + entry.getAmount());
                etPrice.setEnabled(true);
                addButton.setText("Buy");
                break;
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                int amount = Integer.parseInt(etAmount.getText().toString());


                Intent returnIntent = new Intent(getApplicationContext(), ShoppingListEntry.class);
                if (entry != null) { // editing existing entry
                    entry.setName(name);
                    entry.setAmount(amount);
                    if (mode.equals(MODE_BUY)) {
                        try {
                            double price = Double.parseDouble(etPrice.getText().toString());
                            entry.setPrice(price);
                        } catch (NumberFormatException e) {
                            Toast.makeText(getApplicationContext(), "Please enter the price",
                                    Toast.LENGTH_SHORT).show();
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
