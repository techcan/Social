package com.socialize.restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.socialize.main.LocalityList;
import com.socialize.main.R;

public class RestaurantCityListing extends Activity implements SearchView.OnQueryTextListener{

	private ArrayList<RestaurantCityModel> _mArrayList = new ArrayList<RestaurantCityModel>();

	private ListView mListView;
	private CustomAdapterList mCustomAdapterList;
	private ProgressDialog dialog;
	SearchView searchview;
	private String Searchtype, Latitude, Longitude;
	//private EditText mSearchEditText;
	ActionBar mActionBar;
	SharedPreferences.Editor prefsEditor;
	SharedPreferences Prefs;
	MenuItem SearchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.restaurant_city_listing);
		
		Prefs = this.getSharedPreferences("Event_city_detailes",
				MODE_WORLD_READABLE);
		prefsEditor = Prefs.edit();

		Intent intent = getIntent();
		Searchtype = intent.getStringExtra("header");
		Latitude = intent.getStringExtra("latitude");
		Longitude = intent.getStringExtra("longitude");
	//	mSearchEditText = (EditText) findViewById(R.id.contact_main_et_search);
		dialog = new ProgressDialog(RestaurantCityListing.this);
		dialog.setMessage("Loading...\nPlease Wait....");
		mListView = (ListView) findViewById(R.id.listview_main12);
		mCustomAdapterList = new CustomAdapterList(RestaurantCityListing.this,
				_mArrayList);
		
		mActionBar=getActionBar();
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8e37d6")));
	/*	mSearchEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				mCustomAdapterList.getFilter().filter(arg0);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});*/
		mListView.setAdapter(mCustomAdapterList);

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			//	HideKeyboard();
				List<RestaurantCityModel> checkList = CustomAdapterList.contentList;
				prefsEditor.putString("Event_city_name", checkList
						.get(position).getName());
				prefsEditor.commit();

				Intent intent = new Intent(RestaurantCityListing.this,
						LocalityList.class);
				intent.putExtra("header", "Restaurants");
				intent.putExtra("latitude", checkList.get(position)
						.getLatitude());
				intent.putExtra("longitude", checkList.get(position)
						.getLongitude());
				intent.putExtra("cityid", checkList.get(position)
						.getId());
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.right_in, R.anim.left_out);

			}
		});

		if (Searchtype.equals("CITY"))
			CallingVolley_city();
		if (Searchtype.equals("NEARBY"))
			CallingVolley_city_neearby(Latitude, Longitude);
	}

	private void CallingVolley_city_neearby(String Latitude, String Longitude) {
		System.out.println("Near Byyyy");
		dialog.show();
		// String url =
		// "https://api.zomato.com/v1/geocode.json?lat="+Latitude+"&lon="+Longitude;
		String url = "https://api.zomato.com/v1/geocode.json?lat=28.557706&lon=77.205879";
		RequestQueue queue = Volley.newRequestQueue(getBaseContext());
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("CallingVolley_city_neearby==>"
								+ response.toString());
						parsingMethod(response.toString());
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

	private void CallingVolley_city() {
		dialog.show();
		String url = "https://api.zomato.com/v1/cities.json";
		RequestQueue queue = Volley.newRequestQueue(getBaseContext());
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("CallingVolley_city_response==>"
								+ response.toString());
						parsingMethod(response.toString());
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

	private void CallingVolley() {
		dialog.show();
		String url = "https://api.zomato.com/v1/cuisines.json?city_id=1";
		RequestQueue queue = Volley.newRequestQueue(getBaseContext());
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("response==>" + response.toString());
						parsingMethod(response.toString());
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

	protected void parsingMethod(String value) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(value);
			JSONArray jsonArray = jsonObject.getJSONArray("cities");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				JSONObject jsonObject3 = jsonObject2.getJSONObject("city");
				RestaurantCityModel restaurantCityModel = new RestaurantCityModel();
				restaurantCityModel.setId(jsonObject3.getString("id"));
				restaurantCityModel.setLatitude(jsonObject3.getString("latitude"));
				restaurantCityModel.setLongitude(jsonObject3.getString("longitude"));
				restaurantCityModel.setName(jsonObject3.getString("name"));
				_mArrayList.add(restaurantCityModel);
			}
			mCustomAdapterList.notifyDataSetChanged();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialog.dismiss();
	}

	private void HideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		//imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.menubtns, (android.view.Menu) menu);
	 SearchView = menu.findItem(R.id.u_Search_menubtns);
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
	protected boolean isAlwaysExpanded() {
        return false;
    }

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		mCustomAdapterList.getFilter().filter(newText);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}
}
