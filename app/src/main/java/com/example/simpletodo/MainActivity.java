package com.example.simpletodo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 25;


    //define list of strings
    List<String> items;

    //add member variable for each
    Button btnAdd;
    EditText editItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        editItem = findViewById(R.id.editItem);
        rvItems= findViewById(R.id.rvItems);



        //instantiating the model
        readItems();



       ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                //delete the item and notify the adapter the position
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast toast = Toast.makeText(getApplicationContext(), " Item has been removed successfully" , Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER , 0, 5);
                toast.show();
                writeItems();
            }
        };

       ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
           @Override
           public void onItemClicked(int position) {

            //creating the new activity
               // passing the edited data
               //display the activity

               Intent i = new Intent(MainActivity.this, EditActivity.class);
               i.putExtra(KEY_ITEM_TEXT, items.get(position));
               i.putExtra(KEY_ITEM_POSITION, position);
               startActivityForResult(i, EDIT_TEXT_CODE);


           }
       };

       itemsAdapter = new ItemsAdapter(items,onLongClickListener, onClickListener  );
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(  this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addItem = editItem.getText().toString();


                //Adding items to the model and notifying adapter about the insertion of an item
                items.add(addItem);


                itemsAdapter.notifyItemInserted(items.size()-1);
                editItem.setText("");
                Toast toast = Toast.makeText(getApplicationContext(), "Item has been added successfully!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER , 0, 5);
                toast.show();
                writeItems();

            }
        });

    }

    //handling the edited result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(resultCode == RESULT_OK && requestCode== EDIT_TEXT_CODE){
           String itemText = data.getStringExtra(KEY_ITEM_TEXT);
           int position = data.getExtras().getInt(KEY_ITEM_POSITION);

           items.set(position,itemText);
           itemsAdapter.notifyItemChanged(position);
           writeItems();
            Toast toast = Toast.makeText(getApplicationContext(), " Item has been updated successfully", Toast.LENGTH_SHORT);
           toast.setGravity(Gravity.CENTER , 0, 5);
            toast.show();

       }
       else
       {
            Log.w("MainActivity" , "onActivityResult call is unknown");
       }
    }


    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    //load items by reading every line of a data file
    private void readItems(){
        try {
            items= new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading the items", e);
            items = new ArrayList<>();
        }
    }

    // save items by writing into data file
    private void writeItems(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing the items", e);
        }
    }
}