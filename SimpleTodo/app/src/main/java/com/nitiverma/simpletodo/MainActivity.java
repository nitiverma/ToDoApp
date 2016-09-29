package com.nitiverma.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemAdapter;
    ListView lvItems;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemAdapter);
        setupListViewListener();
    }

    public void populateItems(){
        readItems();
        itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

    }

    private void setupListViewListener(){
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        i.putExtra("itemText", lvItems.getItemAtPosition(position).toString());
                        i.putExtra("itemPosition", position);
                        startActivityForResult(i, position);
                    }
                }
        );
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText)findViewById(R.id.etEditText);
        String itemText = etNewItem.getText().toString();
        if (itemText.isEmpty()) {
            return;
        }
        itemAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String itemValue = data.getExtras().getString("itemValue");
            int position = data.getExtras().getInt("code", 0);
            items.remove(position);
            items.add(position, itemValue);
            itemAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch(IOException io){
            items = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
