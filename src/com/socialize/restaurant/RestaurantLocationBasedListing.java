package com.socialize.restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.socialize.imageloader.CustomoparatorCostwise;
import com.socialize.imageloader.CustomoparatorCuisines;
import com.socialize.imageloader.CustomoparatorLocation;
import com.socialize.main.GPSTracker;
import com.socialize.main.InviteEvent;
import com.socialize.main.R;

public class RestaurantLocationBasedListing extends Activity implements
		SearchView.OnQueryTextListener {

	/* Restaurant Name */
	private ArrayList<RestaurantLocationModel> _mArrayList = new ArrayList<RestaurantLocationModel>();

	private ListView mListView;
	private ResturentLocationAdapter mCustomAdapter;
	private ProgressDialog dialog;
	SearchView searchview;
	SharedPreferences.Editor prefsEditor;
	SharedPreferences Prefs;
	Button Sortbtn, LocationSortbtn;
	GPSTracker mGPSTracker;
	ActionBar mActionBar;
	SeekBar seekbar_cost_restaurants;
	Spinner Cuisines_spinner, location_spinner;
	ArrayAdapter<String> CuisineAdapter, LocationAdapter;
	TextView seekbar_progress_textview;
	public static int budget = 0;
	public static String Cuisine = "";
	public static String Location = "";
	public static String SearchText = "";
	MenuItem Rate_wise, Location_Wise, Cusines_wise, SearchView;
	Button btn_rate_wise, btn_location_wise, btn_cusines_wise;
	private String ZoneId, Longitude;
	ArrayList<String> CuisineList = new ArrayList<String>();
	ArrayList<String> LocationList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.restaurant_locationbase);

		Prefs = this.getSharedPreferences("Restaurant_detailes",
				MODE_WORLD_READABLE);
		prefsEditor = Prefs.edit();
		mActionBar = getActionBar();
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#8e37d6")));
		mGPSTracker = new GPSTracker(getApplicationContext());
		dialog = new ProgressDialog(RestaurantLocationBasedListing.this);
		dialog.setMessage("Loading...\nPlease Wait....");
		mListView = (ListView) findViewById(R.id.listview_main);
		seekbar_cost_restaurants = (SeekBar) findViewById(R.id.seekbar_cost_restaurants);
		// seekbar_progress_textview = (TextView)
		// findViewById(R.id.seekbar_progress_textview);

		Intent intent = getIntent();
		ZoneId = intent.getStringExtra("subzone_id");
		//Longitude = intent.getStringExtra("longitude");
		Cuisines_spinner = (Spinner) findViewById(R.id.Cuisines_spinner);
		//location_spinner = (Spinner) findViewById(R.id.location_spinner);
		mCustomAdapter = new ResturentLocationAdapter(
				RestaurantLocationBasedListing.this, _mArrayList);
		mListView.setAdapter(mCustomAdapter);
		CuisineList.add("Select Cuisine");
		LocationList.add("Select Location");
		LocationAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, LocationList);
		LocationAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	//	location_spinner.setAdapter(LocationAdapter);

		CuisineAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, CuisineList);
		CuisineAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		System.out.println("cuisinelist----------------- " + CuisineList);
		Cuisines_spinner.setAdapter(CuisineAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// HideKeyboard();
				List<RestaurantLocationModel> checkList = ResturentLocationAdapter.contentList;

				prefsEditor.putString("Restaurant_name", checkList
						.get(position).getNameString());
				prefsEditor.putString("Restaurant_address",
						checkList.get(position).getAddressString());
				prefsEditor.commit();

				Intent intent = new Intent(RestaurantLocationBasedListing.this,
						InviteEvent.class);
				intent.putExtra("header", "Restaurants");
				intent.putExtra("name", checkList.get(position).getNameString());
				intent.putExtra("address", checkList.get(position)
						.getAddressString());
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});

		seekbar_cost_restaurants
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {

						if (progress == 0) {
							budget = 500;
						}
						if (progress == 1) {
							budget = 1500;
						}
						if (progress == 2) {
							budget = 2500;
						}
						if (progress == 3) {
							budget = 3500;
						}
						if (progress == 4) {
							budget = 4500;
						}
						mCustomAdapter.getFilter().filter("aaa");
					}
				});

		Cuisines_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// CuisineList.get(position);
						// Collections.sort(_mArrayList, new
						// CustomoparatorCostwise());

						// mCustomAdapter.notifyDataSetChanged();
						if (!CuisineList.get(position).equals("Select Cuisine")) {
							Cuisine = CuisineList.get(position);
							mCustomAdapter.getFilter().filter("aaaa");
							System.out
									.println("Cuisiness------------------------- "
											+ Cuisine);

						} else {
							Cuisine = "";
							mCustomAdapter.getFilter().filter("aaaa");
							System.out
									.println("Cuisiness------------------------- "
											+ Cuisine);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

//		location_spinner
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					@Override
//					public void onItemSelected(AdapterView<?> parent,
//							View view, int position, long id) {
//						// CuisineList.get(position);
//						// Collections.sort(_mArrayList, new
//						// CustomoparatorCostwise());
//
//						// mCustomAdapter.notifyDataSetChanged();
//						if (!LocationList.get(position).equals(
//								"Select Location")) {
//							Location = LocationList.get(position);
//							mCustomAdapter.getFilter().filter("aaaa");
//							System.out
//									.println("Location------------------------- "
//											+ Location);
//						}
//
//						else {
//							Location = "";
//							mCustomAdapter.getFilter().filter("aaaa");
//							System.out
//									.println("Location------------------------- "
//											+ Location);
//						}
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> parent) {
//						// TODO Auto-generated method stub
//
//					}
//				});

	//	CallingVolley_restorent_nearby(); // Restaurant name & address
		CallingVolley_restorent_nearby22();
	}

	private void HideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(searchview.getWindowToken(), 0);
	}

//	private void CallingVolley_restorent_nearby() {
//		dialog.show();
//		String url = "https://api.zomato.com/v1/search.json/near?lat="
//				+ Latitude + "&lon=" + Longitude + "&count=500";
//		RequestQueue queue = Volley.newRequestQueue(getBaseContext());
//		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
//				Request.Method.GET, url, null,
//				new Response.Listener<JSONObject>() {
//
//					@Override
//					public void onResponse(JSONObject response) {
//						System.out.println("RestaurantLocationBasedListing==>"
//								+ response.toString());
//						int leng = response.toString().length();
//						int maxLogSize = 500;
//						for (int i = 0; i <= leng / maxLogSize; i++) {
//							int start = i * maxLogSize;
//							int end = (i + 1) * maxLogSize;
//							end = end > leng ? leng : end;
//							Log.e("" + i,
//									response.toString().substring(start, end));
//						}
//
//						parsingMethod(response.toString());
//					}
//				}, new Response.ErrorListener() {
//
//					@Override
//					public void onErrorResponse(VolleyError error) {
//						System.out.println("Volley Error===>" + error);
//						dialog.dismiss();
//					}
//				}) {
//
//			@Override
//			public Map<String, String> getHeaders() throws AuthFailureError {
//				HashMap<String, String> headers = new HashMap<String, String>();
//				headers.put("X-Zomato-API-Key",
//						"7749b19667964b87a3efc739e254ada2");
//				return headers;
//			}
//		};
//		queue.add(jsObjRequest);
//	}

	protected void parsingMethod(String value) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(value);
			System.out.println("values-------tttttttttttttttttttt---------- "
					+ value);
			JSONArray jsonArray = jsonObject.getJSONArray("results");
			System.out.println("jsonobject---------------- " + jsonObject);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				JSONObject jsonObject3 = jsonObject2.getJSONObject("result");
				RestaurantLocationModel model = new RestaurantLocationModel();

				model.setAddressString(jsonObject3.getString("address"));
				model.setCuisinesString(jsonObject3.getString("cuisines"));
				for (int j = 0; j < CuisineList.size(); j++) {
					if (CuisineList.get(j).equals(
							jsonObject3.getString("cuisines"))) {
						CuisineList.remove(j);
					}

				}
				CuisineList.add(jsonObject3.getString("cuisines"));
				model.setImagesString(jsonObject3.getString("image_310_150"));
				model.setLatitudeString(jsonObject3.getString("latitude"));
				model.setLongitudeString(jsonObject3.getString("longitude"));
				model.setNameString(jsonObject3.getString("name"));
				model.setRatingString(jsonObject3.getString("rating_aggregate"));
				model.setRatingtextString(jsonObject3.getString("rating_text"));
				model.setBudgetString(Integer.valueOf(jsonObject3
						.getString("cost_for_two")));
				model.setLocality(jsonObject3.getString("locality"));
				for (int j = 0; j < LocationList.size(); j++) {
					if (LocationList.get(j).equals(
							jsonObject3.getString("locality"))) {
						LocationList.remove(j);
					}

				}
				LocationList.add(jsonObject3.getString("locality"));
				model.setDistance(distance(model.getLatitudeString(),
						model.getLongitudeString()));
				System.out.println("distance------------------------------- "
						+ distance(model.getLatitudeString(),
								model.getLongitudeString()));
				_mArrayList.add(model);
			}

			mCustomAdapter.notifyDataSetChanged();
			CuisineAdapter.notifyDataSetChanged();
			LocationAdapter.notifyDataSetChanged();
			seekbar_cost_restaurants.setProgress(4);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CallingVolley_restorent_nearby22();
	//	dialog.dismiss();

	}
	
	private void CallingVolley_restorent_nearby22() {
		dialog.show();
		
		String url = "https://api.zomato.com/v1/search.json?subzone_id="+ZoneId;
		System.out.println("link00000000-------------- "+url);
		RequestQueue queue = Volley.newRequestQueue(getBaseContext());
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("RestaurantLocationBasedListing==>"
								+ response.toString());
						int leng = response.toString().length();
						int maxLogSize = 1000;
						for (int i = 0; i <= leng / maxLogSize; i++) {
							int start = i * maxLogSize;
							int end = (i + 1) * maxLogSize;
							end = end > leng ? leng : end;
							Log.e("" + i,
									response.toString().substring(start, end));
						}

						parsingMethod2(response.toString());
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						System.out.println("Volley Error===>" + error);
						dialog.dismiss();
					}
				}) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("X-Zomato-API-Key",
						"7749b19667964b87a3efc739e254ada2");
				return headers;
			}
		};
		queue.add(jsObjRequest);
	}
	protected void parsingMethod2(String value) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(value);
			System.out.println("values-------tttttttttttttttttttt---------- "
					+ value);
			JSONArray jsonArray = jsonObject.getJSONArray("results");
			System.out.println("jsonobject---------------- " + jsonObject);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				JSONObject jsonObject3 = jsonObject2.getJSONObject("result");
				RestaurantLocationModel model = new RestaurantLocationModel();

				model.setAddressString(jsonObject3.getString("address"));
				model.setCuisinesString(jsonObject3.getString("cuisines"));
				for (int j = 0; j < CuisineList.size(); j++) {
					if (CuisineList.get(j).equals(
							jsonObject3.getString("cuisines"))) {
						CuisineList.remove(j);
					}

				}
				CuisineList.add(jsonObject3.getString("cuisines"));
				model.setImagesString(jsonObject3.getString("image_310_150"));
				model.setLatitudeString(jsonObject3.getString("latitude"));
				model.setLongitudeString(jsonObject3.getString("longitude"));
				model.setNameString(jsonObject3.getString("name"));
				model.setRatingString(jsonObject3.getString("rating_aggregate"));
				model.setRatingtextString(jsonObject3.getString("rating_text"));
				model.setBudgetString(Integer.valueOf(jsonObject3
						.getString("cost_for_two")));
				model.setLocality(jsonObject3.getString("locality"));
				for (int j = 0; j < LocationList.size(); j++) {
					if (LocationList.get(j).equals(
							jsonObject3.getString("locality"))) {
						LocationList.remove(j);
					}

				}
				LocationList.add(jsonObject3.getString("locality"));
				model.setDistance(distance(model.getLatitudeString(),
						model.getLongitudeString()));
				System.out.println("distance------------------------------- "
						+ distance(model.getLatitudeString(),
								model.getLongitudeString()));
				_mArrayList.add(model);
			}

			mCustomAdapter.notifyDataSetChanged();
			CuisineAdapter.notifyDataSetChanged();
			LocationAdapter.notifyDataSetChanged();
			seekbar_cost_restaurants.setProgress(4);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dialog.dismiss();

	}

	public int distance(String location_latitude, String location_longitude) {

		double distance = 0;
		Location mLocationA = new Location("point A");

		mLocationA.setLatitude(Double.valueOf(mGPSTracker.getLatitude()));

		mLocationA.setLongitude(Double.valueOf(mGPSTracker.getLongitude()));

		Location mLocationB = new Location("point B");

		mLocationB.setLatitude(Double.parseDouble(location_latitude));

		mLocationB.setLongitude(Double.parseDouble(location_longitude));

		distance = mLocationA.distanceTo(mLocationB);

		// System.out.println("distance=========="+distance);

		return (int) distance / 1000;

		// locationA.distanceTo(locationB);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, (android.view.Menu) menu);

		System.out
				.println("oncreateoption============================================");
		// Rate_wise = menu.findItem(R.id.Sort_via_Rate);
		SearchView = menu.findItem(R.id.u_Search_menu);
		// Location_Wise=menu.findItem(R.id.Sort_via_Location);
		// Cusines_wise=menu.findItem(R.id.Sort_via_Cusines);
		// btn_rate_wise=(Button)Rate_wise.getActionView();
		// btn_location_wise=(Button)Location_Wise.getActionView();
		// btn_cusines_wise=(Button)Cusines_wise.getActionView();
		searchview = (SearchView) SearchView.getActionView();
		setupSearchView(SearchView);
		return super.onCreateOptionsMenu(menu);

	}

	private void setupSearchView(MenuItem searchView2) {
		// TODO Auto-generated method stub
		if (isAlwaysExpanded()) {
			System.out.println("if");
			searchview.setIconifiedByDefault(false);
		} else {
			System.out.println("else");
			SearchView.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
					| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}
		searchview.setOnQueryTextListener(this);
	}

	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * item selection
	 * 
	 * System.out.println("item-------------------------------------- "+item);
	 * switch (item.getItemId()) { // case R.id.Sort_via_Rate:
	 * Collections.sort(_mArrayList, new CustomoparatorCostwise());
	 * 
	 * mCustomAdapter.notifyDataSetChanged();
	 * 
	 * return true; case R.id.Sort_via_Location: Collections.sort(_mArrayList,
	 * new CustomoparatorLocation());
	 * 
	 * mCustomAdapter.notifyDataSetChanged(); return true; case
	 * R.id.Sort_via_Cusines: Collections.sort(_mArrayList, new
	 * CustomoparatorCuisines());
	 * 
	 * mCustomAdapter.notifyDataSetChanged(); return true;
	 * 
	 * 
	 * default:
	 * 
	 * 
	 * return super.onOptionsItemSelected(item); } }
	 */

	protected boolean isAlwaysExpanded() {
		return false;
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		if (arg0.length() != 0) {
			SearchText = arg0;
			mCustomAdapter.getFilter().filter("aaa");

		} else
			SearchText = "";
		mCustomAdapter.getFilter().filter("aaa");
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
