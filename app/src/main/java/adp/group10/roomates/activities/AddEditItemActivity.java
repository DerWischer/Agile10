package adp.group10.roomates.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import adp.group10.roomates.backend.ShoppingListStorage;
import adp.group10.roomates.R;
import adp.group10.roomates.model.ShoppingListEntry;

public class AddEditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);
        Button addButton = (Button) findViewById(R.id.addButton2);
        final EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        final EditText amountEditText = (EditText) findViewById(R.id.amountEditText);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        final ShoppingListEntry entry;
        if (extra != null) {
            entry = (ShoppingListEntry) extra.getSerializable(
                    ShoppingListStorage.class.getSimpleName());
            nameEditText.setText(entry.getName());
            amountEditText.setText("" + entry.getAmount());
            addButton.setText("Update");
        } else {
            entry = null;
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                int amount = Integer.parseInt(amountEditText.getText().toString());

                Intent returnIntent = new Intent(getApplicationContext(), ShoppingListEntry.class);
                if (entry != null) { // editing existing entry
                    entry.setName(name);
                    entry.setAmount(amount);
                    returnIntent.putExtra(ShoppingListEntry.class.getSimpleName(), entry);
                } else { // creating new entry
                    ShoppingListEntry newEntry = new ShoppingListEntry(name, amount);
                    returnIntent.putExtra(ShoppingListEntry.class.getSimpleName(), newEntry);
                }

                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
