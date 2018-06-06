package com.darkbyt3.example.smarttrial_1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

/**
 * This class gets the current location of the user
 * It updates the 'lat'and 'lng' strings in MainActivity.
 */

public class GetLocation {
	private ProgressBar progressBar;

	// required to get the current location of the user
	private static LocationRequest mLocationRequest;
	private static Location mLastLocation;
	private FusedLocationProviderClient mFusedLocationClient;

	GetLocation(Context c, ProgressBar pb){
		progressBar = pb;

		// To find current location
		mFusedLocationClient = LocationServices.getFusedLocationProviderClient(c);

		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(120000); // two minute interval
		mLocationRequest.setFastestInterval(120000);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

		// Show progress bar while the location info is being collected
		progressBar.setVisibility(View.VISIBLE);

		locationUpdate();
	}

	@SuppressLint("MissingPermission")
	private void locationUpdate(){
		mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
	}

	// It is triggered when we get the result for location services
	LocationCallback mLocationCallback = new LocationCallback(){
		@Override
		public void onLocationResult(LocationResult locationResult) {
			for (Location location : locationResult.getLocations()) {
				//Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
				mLastLocation = location;

				MainActivity.lat = String.valueOf(mLastLocation.getLatitude());
				MainActivity.lng = String.valueOf(mLastLocation.getLongitude());

				new GetStationName(new LatLng(mLastLocation.getLatitude(),
						mLastLocation.getLongitude()), progressBar).execute();
			}
		}
	};
}
