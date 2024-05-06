package com.example.myynotees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        EditText editText = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();

        noteID = intent.getIntExtra("noteID" , -1);

        if(noteID != -1){

            editText.setText(MainActivity.notesList.get(noteID) );

        }

        else {

            MainActivity.notesList.add("");

            noteID = MainActivity.notesList.size()-1;

        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                MainActivity.notesList.set(noteID , String.valueOf(s));

                MainActivity.arrayAdapter.notifyDataSetChanged();


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
