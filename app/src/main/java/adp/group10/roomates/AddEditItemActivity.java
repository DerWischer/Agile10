package adp.group10.roomates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditItemActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText amountEditText;

    boolean edit = false;
    String oldName;
    int oldAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit_item);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        amountEditText = (EditText) findViewById(R.id.amountEditText);

        Button addButton = (Button) findViewById(R.id.addButton2);
        addButton.setOnClickListener(addButtonClickHandler);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            oldName = b.getString("oldName");
            oldAmount = b.getInt("oldAmount");
        }

        if (oldName != null && oldName != "" && oldAmount >= 0) {
            edit = true;
            nameEditText.setText(oldName);
            amountEditText.setText("" + oldAmount);
            addButton.setText("Update");
        }
    }

    View.OnClickListener addButtonClickHandler = new View.OnClickListener() {
        public void onClick(View v) {
            String name = nameEditText.getText().toString();
            int amount = Integer.parseInt(amountEditText.getText().toString());
            if (edit) {
                EntryData.editEntry(oldName, oldAmount, name, amount);
            } else {
                EntryData.addEntry(name, amount);
            }

            startActivity(new Intent(AddEditItemActivity.this, ShoppingListActivity.class));
        }
    };
}
