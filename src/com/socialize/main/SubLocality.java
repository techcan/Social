package com.socialize.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.socialize.restaurant.CustomAdapterList;
import com.socialize.restaurant.RestaurantCityModel;
import com.socialize.restaurant.RestaurantLocationBasedListing;
import com.socialize.restaurant.RestaurantLocationModel;

public class SubLocality extends Activity{
	private ListView mListView;
	private ProgressDialog dialog;
	String id;
	private CustomAdapterList mCustomAdapterList;
	private ArrayList<RestaurantCityModel> _mArrayList = new ArrayList<RestaurantCityModel>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.restaurant_city_listing);
		dialog = new ProgressDialog(SubLocality.this);
		mListView = (ListView) findViewById(R.id.listview_main12);
		Intent intent = getIntent();
		id = intent.getStringExtra("zone_id");
		dialog.setMessage("Loading...\nPlease Wait....");
		mCustomAdapterList = new CustomAdapterList(SubLocality.this,
				_mArrayList);
		mListView.setAdapter(mCustomAdapterList);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				List<RestaurantCityModel> checkList = CustomAdapterList.contentList;
				Intent intent = new Intent(SubLocality.this,
						RestaurantLocationBasedListing.class);
				intent.putExtra("header", "Restaurants");
			//	intent.putExtra("latitude", checkList.get(position)
					//	.getLatitude());
			//	intent.putExtra("longitude", checkList.get(position)
					//	.getLongitude());
				intent.putExtra("subzone_id", checkList.get(position)
						.getCity_id());
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				
			}
		});
		CallingVolley_city();
}
	
	
	private void CallingVolley_city() {
		dialog.show();
		
		String url = "https://api.zomato.com/v1/subzones.json?zone_id="+id;
		System.out.println("subzoe link--------------------- "+url);
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

	protected void parsingMethod(String value) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = new JSONObject(value);
			System.out.println("json object--------sss---------- "+jsonObject);
			JSONArray jsonArray = jsonObject.getJSONArray("subzones");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				JSONObject jsonObject3 = jsonObject2.getJSONObject("subzone");
				RestaurantCityModel restaurantCityModel = new RestaurantCityModel();
				restaurantCityModel.setCity_id(jsonObject3.getString("subzone_id"));
			//	restaurantCityModel.setName(jsonObject3.getString("real_city_name"));
				restaurantCityModel.setZone_id(jsonObject3.getString("zone_id"));
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
}
