package com.nitiverma.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import static com.nitiverma.simpletodo.R.id.lvItems;

public class EditItemActivity extends AppCompatActivity {
    ArrayAdapter<String> itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String inputString = getOriginalItemString();
        EditText eiEditText = (EditText)findViewById(R.id.eiEditText);
        eiEditText.setText(inputString);
    }

    public void onSaveItem(View v){
        EditText eiEditText = (EditText)findViewById(R.id.eiEditText);
        String itemText = eiEditText.getText().toString();
        if (itemText.isEmpty()) {
            itemText = getOriginalItemString();
        }
        int itemPosition = getIntent().getIntExtra("itemPosition", 0);
        Intent data = new Intent(EditItemActivity.this, MainActivity.class);
        data.putExtra("itemValue", itemText);
        data.putExtra("code", itemPosition);
        setResult(RESULT_OK, data);
        finish();
    }

    private final String getOriginalItemString() {
        return getIntent().getExtras().getString("itemText");
    }
}
