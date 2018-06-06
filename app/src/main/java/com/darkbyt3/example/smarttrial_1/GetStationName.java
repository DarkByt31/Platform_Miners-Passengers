package com.darkbyt3.example.smarttrial_1;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class obtains the name of the station where the passenger currently is,
 * Using google places API we download and parse the json data.
 * It sets 'stationName' in MainActivity.
 */

public class GetStationName extends AsyncTask<Void, Void, String> {

	private String url;
	private LatLng place;
	ProgressBar progressBar;

	GetStationName(LatLng pl, ProgressBar pb) {
		place = pl;
		progressBar = pb;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		url = makeURL();
	}

	@Override
	protected String doInBackground(Void... params) {
		JSONDownloader jsonDownloader = new JSONDownloader();
		return jsonDownloader.getJSONFromUrl(url);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result != null) {
			jParser(result);
		}
	}

	// Construct the URL for Places API
	public String makeURL() {
		String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?";

		String location = "location=" + String.valueOf(place.latitude) +
				"," + String.valueOf(place.longitude);

		String key = "&key=AIzaSyCTHNQoC5lUPeNe7Ts3gQyMOAb70cvfI60";

		url = url + location + "&type=train_station" + key;

		return url;
	}

	// Downloads json data
	public class JSONDownloader {
		String json = "";
		HttpURLConnection urlConnection = null;
		InputStream istream = null;

		// Constructor
		public JSONDownloader() {
		}

		public String getJSONFromUrl(String url1) {
			// Making HTTP request
			try {

				URL url = new URL(url1);
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.connect();
				istream = urlConnection.getInputStream();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(istream, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}

				json = sb.toString();
				istream.close();
			} catch (Exception e) {
				Log.e("Buffer Error", "Error converting result " + e.toString());
			}
			return json;

		}
	}

	// Parses json data and set name of station
	public void jParser(String result) {

		try {
			// Convert the string into a json object
			final JSONObject json = new JSONObject(result);
			JSONArray results = json.getJSONArray("results");
			System.out.println(results);
			JSONObject index_0 = results.getJSONObject(0);
			System.out.println(index_0);
			MainActivity.stationName = index_0.getString("name");

			progressBar.setVisibility(View.GONE);

			Log.d("Station name", MainActivity.stationName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
