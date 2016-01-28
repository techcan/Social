package com.socialize.main;

import java.util.Calendar;
import java.util.List;

import com.socilize.contact.ContactListActivity;
import com.socilize.contact.ContactListActivity2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class InviteEvent extends Activity implements OnClickListener {

	private Button mSelectrestaurantButton;
	private TextView mEventnameEditText;
	private EditText mEventdateEditText;
	private EditText mEventstarttimeEditText;
	private EditText mEventendtimeEditText;
	private EditText mEventrestaurantnameEditText;
	private EditText mEventreataurantaddressEditText;

	private int year;
	private int month;
	private int day;

	private int hour;
	private int min;

	private boolean starttimepicker_status = false;
	private boolean endtimepicker_status = false;

	static final int DATE_PICKER_ID = 1111;
	static final int TIME_PICKER_ID = 2222;

	private String event_name, event_date, event_start_time, event_end_time,
			event_city, event_restaurant_name, event_reataurant_address;

	SharedPreferences Eventdetailes;
	SharedPreferences Eventcity;
	SharedPreferences Eventrestaurantdetailes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.invite_event);
		
		Calendar c = Calendar.getInstance();
		year= c.get(Calendar.YEAR);
		month= c.get(Calendar.MONTH);
		day= c.get(Calendar.DAY_OF_MONTH);

		/* Event Detailes Preference */
		Eventdetailes = this.getSharedPreferences("Event_detailes",
				MODE_WORLD_READABLE);
		event_name = Eventdetailes.getString("Event_name", "nothing");
		event_date = Eventdetailes.getString("Event_date", "nothing");
		event_start_time = Eventdetailes.getString("Event_start_time",
				"nothing");
		event_end_time = Eventdetailes.getString("Event_end_time", "nothing");

		/* Event City Detailes Preference */
		Eventcity = this.getSharedPreferences("Event_city_detailes",
				MODE_WORLD_READABLE);
		event_city = Eventcity.getString("Event_city_name", "nothing");

		/* Event Restaurant Detailes Preference */
		Eventrestaurantdetailes = this.getSharedPreferences(
				"Restaurant_detailes", MODE_WORLD_READABLE);
		event_restaurant_name = Eventrestaurantdetailes.getString(
				"Restaurant_name", "nothing");
		event_reataurant_address = Eventrestaurantdetailes.getString(
				"Restaurant_address", "nothing");

		mEventnameEditText = (TextView) findViewById(R.id.eventname);
		mEventdateEditText = (EditText) findViewById(R.id.eventdate);
		mEventstarttimeEditText = (EditText) findViewById(R.id.eventstarttime);
		mEventendtimeEditText = (EditText) findViewById(R.id.eventendtime);
		mEventrestaurantnameEditText = (EditText) findViewById(R.id.eventrestaurantname);
		mEventreataurantaddressEditText = (EditText) findViewById(R.id.eventrestaurantaddress);

		mEventnameEditText.setText(event_name);
		mEventdateEditText.setText("On " + event_date);
		mEventstarttimeEditText.setText("Start Time " + event_start_time);
		mEventendtimeEditText.setText("End Time " + event_end_time);
		mEventrestaurantnameEditText.setText("At " + event_restaurant_name);
		mEventreataurantaddressEditText.setText(event_reataurant_address);

		/* Hiding Softkey bord for Datepicker */
		mEventdateEditText.setInputType(InputType.TYPE_NULL);
		mEventdateEditText.requestFocus();
		InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.showSoftInput(mEventdateEditText, InputMethodManager.SHOW_FORCED);

		/* Hiding Softkey bord for start time picker */
		mEventstarttimeEditText.setInputType(InputType.TYPE_NULL);
		mEventstarttimeEditText.requestFocus();
		InputMethodManager mgr_mEventstarttimeEditText = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr_mEventstarttimeEditText.showSoftInput(mEventstarttimeEditText,
				InputMethodManager.SHOW_FORCED);

		/* Hiding Softkey bord for end time picker */
		mEventendtimeEditText.setInputType(InputType.TYPE_NULL);
		mEventendtimeEditText.requestFocus();
		InputMethodManager mgr_mEventendtimeEditText = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr_mEventendtimeEditText.showSoftInput(mEventendtimeEditText,
				InputMethodManager.SHOW_FORCED);

		mSelectrestaurantButton = (Button) findViewById(R.id.invite);
		mSelectrestaurantButton.setOnClickListener(this);

		mEventdateEditText.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					showDatePicker();
				}
				return false;
			}
		});

		mEventendtimeEditText.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					showEndTimePicker();
				}
				return false;
			}
		});

		mEventstarttimeEditText.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					showStartTimePicker();
				}
				return false;
			}
		});

	}

	private void showDatePicker() {
		this.showDialog(DATE_PICKER_ID);
	}

	private void showStartTimePicker() {
		starttimepicker_status = true;
		endtimepicker_status = false;
		this.showDialog(TIME_PICKER_ID);
	}

	private void showEndTimePicker() {
		starttimepicker_status = false;
		endtimepicker_status = true;
		this.showDialog(TIME_PICKER_ID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		if (arg0 == mSelectrestaurantButton) {

			/*Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			String shareBody = "You have an invitation for the event "
					+ event_name + " to be held at " + event_restaurant_name
					+ event_reataurant_address + " on " + event_date + " from "
					+ event_start_time + " to " + event_end_time;
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"socialize Invitation");
			sharingIntent
					.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			
			startActivityForResult(Intent.createChooser(sharingIntent, "Share via"),1);*/

			
//			Intent share = new Intent(Intent.ACTION_SEND);
//			share.setType("text/plain");       
			
			final String shareBody = "You have an invitation for the event "
					+ event_name + " to be held at " + event_restaurant_name
					+ event_reataurant_address + " on " + event_date + " from "
					+ event_start_time + " to " + event_end_time;
		//	share.putExtra(Intent.EXTRA_TEXT, shareBody);
//			Intent addIntent = share.setClassName("com.google.android.gm",
//					"com.google.android.gm.ComposeActivityGmail");//whatever you want

//			Intent chooser = new Intent(Intent.ACTION_CHOOSER);
//			chooser.putExtra(Intent.EXTRA_INTENT, share );      
//			chooser.putExtra(Intent.EXTRA_TITLE, "Share via");
//			Uri smsUri = Uri.parse("sms:100861");
//			Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
//			intent.putExtra("sms_body", shareBody);
//			//startActivity(intent);
//			Intent[] intentArray =  {intent, share.setPackage("com.whatsapp")}; 
//			chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
//			startActivity(chooser);
			
			
			
			final Dialog dialog = new Dialog(InviteEvent.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.intentchooser);
			ImageButton whatsapp = (ImageButton)dialog.findViewById(R.id.whatsapp_btn_share);
			ImageButton messaging = (ImageButton)dialog.findViewById(R.id.messaging_btn_share);
			whatsapp.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent gmailIntent = new Intent(Intent.ACTION_SEND);
					
					gmailIntent.setType("text/plain");
					gmailIntent.putExtra(android.content.Intent.EXTRA_TEXT,shareBody);
					gmailIntent.setPackage("com.whatsapp");
					try {
						startActivity(gmailIntent);
					} catch (ActivityNotFoundException ex) {
						Toast.makeText(getApplicationContext(), "Please Install whatsapp", Toast.LENGTH_SHORT).show();
					}
					
					
					
				//	shareWhatsApp(InviteEvent.this,shareBody);
					dialog.dismiss();
				}
			});
			messaging.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					Uri smsUri = Uri.parse("sms:");
//					Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
//					intent.putExtra("sms_body", shareBody);
//					startActivity(intent);
//					dialog.dismiss();
					
					Intent intent= new Intent(InviteEvent.this,ContactListActivity2.class);
					intent.putExtra("Message", shareBody);
					startActivity(intent);
					dialog.dismiss();
				}
			});
			
			dialog.show();
		}
	}
	
	public static void shareWhatsApp(Activity appActivity, String texto) {

	    Intent sendIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);     
	    sendIntent.setType("text/plain");
	    sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, texto);

	    PackageManager pm = appActivity.getApplicationContext().getPackageManager();
	    final List<ResolveInfo> matches = pm.queryIntentActivities(sendIntent, 0);
	    boolean temWhatsApp = false;
	    for (final ResolveInfo info : matches) {
	      if (info.activityInfo.packageName.startsWith("com.whatsapp"))  {
	          final ComponentName name = new ComponentName(info.activityInfo.applicationInfo.packageName, info.activityInfo.name);
	          sendIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	          sendIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
	          sendIntent.setComponent(name);
	          temWhatsApp = true;
	          break;
	      }
	    }               

	    if(temWhatsApp) {
	        //abre whatsapp
	        appActivity.startActivity(sendIntent);
	    } else {
	        //alerta - você deve ter o whatsapp instalado
	        Toast.makeText(appActivity, "Please Install whatsapp", Toast.LENGTH_SHORT).show();
	    }
	}
	
	

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_ID:

			// open datepicker dialog.
			// set date picker for current date
			// add pickerListener listner to date picker
			return new DatePickerDialog(this, pickerListener, year, month, day);

		case TIME_PICKER_ID:

			// open timepicker dialog.
			// add pickerListener listner to time picker
			return new TimePickerDialog(this, timePickerListener, hour, min,
					false);
		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			int hour;
			String am_pm;
			if (hourOfDay > 12) {
				hour = hourOfDay - 12;
				am_pm = "PM";
			} else {
				hour = hourOfDay;
				am_pm = "AM";
			}
			if (starttimepicker_status)
				mEventstarttimeEditText.setText(hour + " : " + minute + " "
						+ am_pm);
			if (endtimepicker_status)
				mEventendtimeEditText.setText(hour + " : " + minute + " "
						+ am_pm);
		}
	};

	private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// Show selected date
			mEventdateEditText.setText(new StringBuilder().append(month + 1)
					.append("/").append(day).append("/").append(year)
					.append(" "));

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		/*Intent intent = new Intent(InviteEvent.this, MainActivity.class);
		intent.putExtra("header", "MainActivity");
		startActivity(intent);
		finish();*/
	}
	
	
}
