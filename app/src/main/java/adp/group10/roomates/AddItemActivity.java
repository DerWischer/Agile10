package adp.group10.roomates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText amountEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        amountEditText = (EditText) findViewById(R.id.amountEditText);

        Button addButton = (Button) findViewById(R.id.addButton2);
        addButton.setOnClickListener(addButtonClickHandler);
    }

    View.OnClickListener addButtonClickHandler = new View.OnClickListener() {
        public void onClick(View v) {
            String name = nameEditText.getText().toString();
            int amount = Integer.parseInt(amountEditText.getText().toString());

            Intent intent = new Intent(AddItemActivity.this, ShoppingListActivity.class);
            Bundle b = new Bundle();
            b.putString("newItemName", name);
            b.putInt("newItemAmount", amount);
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
            finish();



//            startActivity(new Intent(AddItemActivity.this, ShoppingListActivity.class));
        }
    };
}
