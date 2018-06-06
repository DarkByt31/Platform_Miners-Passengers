package com.darkbyt3.example.smarttrial_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * This Activity gets review from the passengers about whether
 * their request was solved or not.
 * It also asks to give ratings to the station.
 */

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

    }

    public void pressYes(View view) {

        Toast.makeText(this, "Thank you for helping us", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void pressNo(View view) {

        Toast.makeText(this, "We will surely look into the matter now.", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void pressDontKnow(View view) {

        Toast.makeText(this, "Dont worry our supervisor will take care of that.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
