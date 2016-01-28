package com.socialize.main;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.socialize.restaurant.RestaurantCityListing;

public class Createevent extends Activity implements OnClickListener {

	private Button mSelectrestaurantButton;
	private EditText mEvennameEditText;
	private EditText mEventdateEditText;
	private EditText mEventstarttimeEditText;
	private EditText mEventendtimeEditText;

	private int year;
	private int month;
	private int day;

	private int hour;
	private int min;

	private boolean starttimepicker_status = false;
	private boolean endtimepicker_status = false;

	static final int DATE_PICKER_ID = 1111;
	static final int TIME_PICKER_ID = 2222;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.create_event);
		
		
		Calendar c = Calendar.getInstance();
		year= c.get(Calendar.YEAR);
		month= c.get(Calendar.MONTH);
		day= c.get(Calendar.DAY_OF_MONTH);

		mEvennameEditText = (EditText) findViewById(R.id.eventname);
		mEventdateEditText = (EditText) findViewById(R.id.eventdate);
		mEventstarttimeEditText = (EditText) findViewById(R.id.eventstarttime);
		mEventendtimeEditText = (EditText) findViewById(R.id.eventendtime);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		if (arg0 == mSelectrestaurantButton) {

			if (mEvennameEditText.getText().toString().length() > 0
					& mEventdateEditText.getText().toString().length() > 0
					& mEventstarttimeEditText.getText().toString().length() > 0
					& mEventendtimeEditText.getText().toString().length() > 0) {

				/* Toast.makeText(this, mEventdateEditText.getText().toString(),
				 Toast.LENGTH_LONG).show();*/

				SharedPreferences Prefs = this.getSharedPreferences("Event_detailes",
						MODE_WORLD_READABLE);
				SharedPreferences.Editor prefsEditor = Prefs.edit();
				prefsEditor.putString("Event_name",mEvennameEditText.getText().toString().toUpperCase());
				prefsEditor.putString( "Event_date",mEventdateEditText.getText().toString());
				prefsEditor.putString( "Event_start_time",mEventstarttimeEditText.getText().toString());
				prefsEditor.putString("Event_end_time",mEventendtimeEditText.getText().toString());
				prefsEditor.commit();

				Intent intent = new Intent(Createevent.this,
						RestaurantCityListing.class);
				intent.putExtra("header", "CITY");
				finish();
				startActivity(intent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			} else
				Toast.makeText(this, "Please enter all field",
						Toast.LENGTH_LONG).show();

		}
	}
	
	private void showDatePicker()
	{
		this.showDialog(DATE_PICKER_ID);	
	}
	private void showStartTimePicker()
	{
		starttimepicker_status = true;
		endtimepicker_status = false;
		this.showDialog(TIME_PICKER_ID);
	}
	private void showEndTimePicker()
	{
		starttimepicker_status = false;
		endtimepicker_status = true;
		this.showDialog(TIME_PICKER_ID);
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
			String min = null;
			String hr = null;
			if(minute<10)
				min="0"+minute;
			else
				min=""+minute;
			if (hourOfDay > 12) {
				hour = hourOfDay - 12;
				hr="0"+hour;
				am_pm = "PM";
			} else {
				hour = hourOfDay;
				if(hourOfDay<10)
					hr="0"+hour;
				am_pm = "AM";
			}
			if (starttimepicker_status)
				mEventstarttimeEditText.setText(hr + " : " + min + " "
						+ am_pm);
			if (endtimepicker_status)
				mEventendtimeEditText.setText(hr + " : " + min + " "
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
	//	super.onBackPressed();
		Createevent.this.finish();
		Intent intent = new Intent(Createevent.this, MainActivity.class);
		intent.putExtra("header", "MainActivity");
		startActivity(intent);
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}

}
