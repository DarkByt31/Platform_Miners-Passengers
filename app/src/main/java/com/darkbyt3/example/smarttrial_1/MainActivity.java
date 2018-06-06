package com.darkbyt3.example.smarttrial_1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;

    Context context;

	public static String stationName = "Unknown Station"; 	// default is Unknown Station
															// station stores the name of the station
															// where passenger is standing
    public static String lat, lng;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText3);
        button = findViewById(R.id.button);

        // check the build version of the mobile.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }

        context = MainActivity.this;
    }

    // Ask for the required permissions
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermission(){

        int result = ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION");

        if (result != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.CAMERA"}, 200);
    }

    // Check if permissions have already been granted.
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){
            case 200:

                boolean locationAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                boolean cameraAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;

                if(locationAccepted && cameraAccepted)
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Please grant the required permissions", Toast.LENGTH_SHORT).show();

        }
    }

    // OnClick function for direction button
    public void direction(View view){

        // Check for required permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(MainActivity.this, "Please grant the required permissions",Toast.LENGTH_SHORT).show();
            return;
        }

        // Open google maps with coordinates of the place where the coach of passenger will arrive.
        try {
            String uri = String.format(Locale.ENGLISH, "geo:0,0?q=%f,%f(%s)?z=23", 24.567709, 73.699898, "Train");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // All the functions below are associated with the respective buttons.
    // These buttons start a new activity to entertain the request of user.

    public void flagFunction(View view) {

        Intent i = new Intent(getApplicationContext(), FlagActivity.class);
        startActivity(i);
    }

    public void servicesWomen(View view) {

        Intent i = new Intent(getApplicationContext(), ServiceWomen.class);
        startActivity(i);
    }

    public void sericesSeniorCitizen(View view) {

        Intent i = new Intent(getApplicationContext(), ServiceSeniorActivity.class);
        startActivity(i);
    }

    public void eventActivity(View view) {

        Intent i = new Intent(getApplicationContext(), EventActivity.class);
        startActivity(i);
    }

}
