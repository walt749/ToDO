package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
//import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    ArrayList items;

    Button BTNAdd;
    EditText editText;
    RecyclerView rvItems;
    ItemAdapter itemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BTNAdd = findViewById(R.id.BTNAdd);
        editText = findViewById(R.id.editText);
        rvItems = findViewById(R.id.rvItems);

        loadItems();
        //items = new ArrayList<>();
      /**
        items.add("Buy milk");
        items.add("go to the Gym");
        items.add("study");
        **/
       ItemAdapter.OnLongClickListner onLongClickListner =  new ItemAdapter.OnLongClickListner(){

            @Override
            public void onItemLongClicked(int position) {

                items.remove(position);
                itemAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

       itemAdapter = new ItemAdapter(items, onLongClickListner);
        rvItems.setAdapter(itemAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        BTNAdd.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                String todoItem = editText.getText().toString();

                items.add(todoItem);

                itemAdapter.notifyItemChanged(items.size() - 1);
                editText.setText("");
                Toast.makeText(getApplicationContext(),"Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }

        });


    }
    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");

    }

    //This function will load items by reading every line of the data file.

    private void loadItems(){

        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items",e);

            items = new ArrayList<>();
        }
    }

    //This function saves items by writing them into data file.
    private void saveItems(){

        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }

    }
}