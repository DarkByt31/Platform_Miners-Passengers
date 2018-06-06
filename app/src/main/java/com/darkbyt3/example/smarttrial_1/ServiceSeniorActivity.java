package com.darkbyt3.example.smarttrial_1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * This activity accepts requests exclusively for Senior Citizens.
 */

public class ServiceSeniorActivity extends AppCompatActivity {

    int pos;
    String problem;

    Spinner spinner;
    EditText editText;
    ProgressBar progressBar;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        editText = findViewById(R.id.editText2);

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);

        //create a list of items for the spinner.
        spinner = findViewById(R.id.spinner);
        final String[] items = new String[]{"Select Problem type.", "Emergency", "Need Help",
                "Medical Emergency", "Robbery", "Need a wheelchair",
                "Need Assistance"};

        //create an adapter to describe how the items are displayed
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        //set the spinners adapter to the previously created one.
        spinner.setAdapter(adapter);

        // set onclick for spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                pos = i;
                problem = items[i];
                Log.i("#spinner", problem + " selected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(ServiceSeniorActivity.this, "Please select a problem type", Toast.LENGTH_SHORT).show();
            }
        });

        context = this;
        new GetLocation(context, progressBar);
    }

    public void sendFlag(View view){
        progressBar.setVisibility(View.VISIBLE);

        String lat = String.valueOf(MainActivity.lat);
        String lng = String.valueOf(MainActivity.lng);
        String desc = String.valueOf(editText.getText());

        context = ServiceSeniorActivity.this;
        SendToFirebase send = new SendToFirebase(lat, lng, desc, problem, context);
        send.sendDetails();
    }
}
