package com.darkbyt3.example.smarttrial_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * This class sends the problem details to the server.
 */

public class SendToFirebase {

	// create a hashmap and all the info which has to be send to the server
	private HashMap<String, String> hashMap = new HashMap<>();

	private Context context;

	SendToFirebase(String lat, String lng, String desc, String prob, Context c){
		// create a hashmap and all the info which has to be send to the server
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("Latitude", lat);
		hashMap.put("Longitude", lng);
		hashMap.put("Description", desc);
		hashMap.put("Problem Type", prob);
		hashMap.put("val", "0");

		context = c;
	}

	public void sendDetails(){

		// Create a directory based on the name of the station
		// to ensure that only the appropriate station receives the notification.
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference myRef = database.getReference("FlagLocation");
		myRef.child(MainActivity.stationName)
				.push()
				.setValue(hashMap)
				.addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						Toast.makeText(context, "Your " +
								"requested has been successfully sent. " +
								"Thank you for your help!", Toast.LENGTH_LONG).show();
						openReview();
						finishContextActivity();
					}
				})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						Toast.makeText(context, "Request failed to sent. " +
								"Please check your connections and try again.", Toast.LENGTH_LONG).show();
						finishContextActivity();
					}
				});
	}

	// Start ReviewActivity to get review from passenger
	public void openReview() {
		Intent i = new Intent(context, ReviewActivity.class);
		context.startActivity(i);
	}

	public void finishContextActivity(){
		if(context instanceof Activity){
			((Activity)context).finish(); }
	}
}
