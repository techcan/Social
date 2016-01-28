package com.socialize.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.socialize.restaurant.RestaurantCityListing;

public class RestaurantDashbord extends Activity implements OnClickListener {

	private Button mRestaurantcityButton;
	private Button mRestaurantnearbyButton;

	/* GPSTracker class */
	GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.restaurant_dashbord);

		mRestaurantcityButton = (Button) findViewById(R.id.city);
		mRestaurantnearbyButton = (Button) findViewById(R.id.nearby);

		mRestaurantcityButton.setOnClickListener(this);
		mRestaurantnearbyButton.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {

		if (arg0 == mRestaurantcityButton) {
			Intent intent = new Intent(RestaurantDashbord.this,
					RestaurantCityListing.class);
			intent.putExtra("header", "CITY");
			startActivity(intent);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
		}
		if (arg0 == mRestaurantnearbyButton) {

			// create class object
			gps = new GPSTracker(RestaurantDashbord.this);

			// check if GPS enabled
			if (gps.canGetLocation()) {

				double latitude = gps.getLatitude();
				double longitude = gps.getLongitude();
				
				
				Intent intent = new Intent(RestaurantDashbord.this,
						RestaurantCityListing.class);
				intent.putExtra("header", "NEARBY");
				intent.putExtra("latitude", latitude);
				intent.putExtra("longitude", longitude);
				startActivity(intent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);

				// \n is for new line
				Toast.makeText(
						getApplicationContext(),
						"Your Location is - \nLat: " + latitude + "\nLong: "
								+ longitude, Toast.LENGTH_LONG).show();
			} else {
				// can't get location
				// GPS or Network is not enabled
				// Ask user to enable GPS/network in settings
				gps.showSettingsAlert();
			}

		}

	}

}
