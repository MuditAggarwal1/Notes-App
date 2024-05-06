package com.example.myynotees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notesList = new ArrayList<>();

    static ArrayAdapter arrayAdapter ;

    ListView listView ;

    SharedPreferences sharedPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.example.myynotees" , MODE_PRIVATE);

        load();

        //notesList.add("First Note");

        listView = (ListView) findViewById(R.id.listView);

         arrayAdapter = new ArrayAdapter(this , android.R.layout.simple_expandable_list_item_1 , notesList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext() , SecondActivity.class);

                intent.putExtra("noteID" , position);

                startActivity(intent);



            }
        });



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                new AlertDialog.Builder(MainActivity.this)

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete the note !")
                        .setMessage("Are you sure you want to delete this note ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                notesList.remove(position);

                                arrayAdapter.notifyDataSetChanged();

                            }
                        })

                        .setNegativeButton("No" , null)

                        .show();



                         return true;





            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.add_note_menu , menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

         super.onOptionsItemSelected(item);

         if(item.getItemId() == R.id.add_note) {

             Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

             startActivity(intent);

             return true;

         }

         return  false;



         }

         public  void save(){

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("notes list size" , notesList.size());

         for (int i =0 ; i<notesList.size() ; i++){

           editor.putString("notes"+i , notesList.get(i));

         }

         editor.apply();


         }

         public void load(){

         ArrayList<String> updatedList = new ArrayList<>();

         int listSize = sharedPreferences.getInt("notes list size" , 0);

         for(int i=0 ; i<listSize ; i++){

           String note = sharedPreferences.getString("notes"+i , null);

           updatedList.add(note);

         }

         notesList = updatedList;

    }

    @Override
    protected void onStop() {
        super.onStop();

        save();
    }
}

