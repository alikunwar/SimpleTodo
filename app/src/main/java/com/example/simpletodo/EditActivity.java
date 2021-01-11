package com.example.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {


    EditText editText;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editText= findViewById(R.id.editText);
        btnSave = findViewById(R.id.btnSave);

        getSupportActionBar().setTitle("Edit item ");

       editText.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

       //to save the updated item
       btnSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                //create Intent for getting result
               //pass result after editing
               //set the result of the intent
               //finish this activity

               Intent i = new Intent();
               i.putExtra(MainActivity.KEY_ITEM_TEXT , editText.getText().toString());
               i.putExtra(MainActivity.KEY_ITEM_POSITION , getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
               setResult(RESULT_OK,i);
               finish();


           }
       });
    }
}