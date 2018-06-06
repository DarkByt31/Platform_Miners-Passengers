package com.darkbyt3.example.smarttrial_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * This Activity gives the live updates of events happening in Railway Station.
 * These events are meant to encourage passengers to keep stations clean.
 */

public class EventActivity extends AppCompatActivity {

    ListView listView;
    static ArrayList<String> notes = new ArrayList<>();         //stores all the notes
    static ArrayAdapter arrayAdapter;                           //adapter for listView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        listView = findViewById(R.id.listView);

        notes.clear();

        notes.add("DPS Udaipur is coming on 01/04/18");
        notes.add("Children from DPS Udaipur are coming to help staff to clean the railway station,");
        notes.add("and to motivate other people to get involved in the upkeepmant of railways.");
        notes.add("");
        notes.add("Please come and help the students and the country to keep railways clean");

        //initialise your adapter
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, notes);
        listView.setAdapter(arrayAdapter);



    }
}
