package com.darkbyt3.example.smarttrial_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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

import java.io.File;

/**
 * This Activity interacts with user so that they can flag some problems
 * along with a description and a photo of the problem(if possible).
 */

public class FlagActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;

    int pos;                                            // stores index of selected item in spinner
    String problem;                                     // problems stores the selected item from spinner

    Spinner spinner;
    EditText editText;
    public ProgressBar progressBar;

    Context context;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        editText = findViewById(R.id.editText2);

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);

        //create a list of items for the spinner.
        spinner = findViewById(R.id.spinner);
        final String[] items = new String[]{"Select Problem type.", "Garbage", "Floor mopping",
                                                    "Medical Emergency", "Robbery", "Not Mentioned",
                                                        "Food"};

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
                Toast.makeText(FlagActivity.this, "Please select a problem type", Toast.LENGTH_SHORT).show();
            }
        });

        context = this;
        new GetLocation(context, progressBar);
    }

    // OnClick() for 'send' button. Sends the data to server
    public void sendFlag(View view){
        progressBar.setVisibility(View.VISIBLE);

        String lat = String.valueOf(MainActivity.lat);
        String lng = String.valueOf(MainActivity.lng);
        String desc = String.valueOf(editText.getText());

        context = FlagActivity.this;
        SendToFirebase send = new SendToFirebase(lat, lng, desc, problem, context);
        send.sendDetails();
    }

    // Take photo
    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("not here---------------------------");
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);
                        Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
        }
    }
}
