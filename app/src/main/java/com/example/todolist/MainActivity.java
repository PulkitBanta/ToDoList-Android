package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText itemET;
    private Button btn;
    private ListView itemList;

    private ArrayList <String> items;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemET = findViewById(R.id.item_edit_text);
        btn = findViewById(R.id.add_btn);
        itemList = findViewById(R.id.item_list);

        items = FileHelper.readData(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        itemList.setAdapter(adapter);

        btn.setOnClickListener(this);
        itemList.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_btn:
                String itemEntered = itemET.getText().toString();

                if(itemEntered.equalsIgnoreCase("")) {
                    Toast.makeText(this, "Item cannot be blank", Toast.LENGTH_SHORT).show();
                    break;
                }

                adapter.add(itemEntered);
                itemET.setText("");

                FileHelper.writeData(items, this);

                Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        items.remove(position);
        adapter.notifyDataSetChanged();
        FileHelper.writeData(items, this);
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
    }
}
