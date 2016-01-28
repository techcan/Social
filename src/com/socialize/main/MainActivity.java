package com.socialize.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.socilize.contact.CalendarView;
import com.socilize.contact.ContactListActivity;

public class MainActivity extends Activity implements OnClickListener{
	
	private ImageView mCreateevenImageView;
	private ImageView mCalanderImageView;
	private ImageView mContactImageView;
	private ImageView mAboutImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		mCreateevenImageView=(ImageView) findViewById(R.id.create_event);
		mCreateevenImageView.setOnClickListener(this);
		
		mContactImageView=(ImageView) findViewById(R.id.contact);
		mContactImageView.setOnClickListener(this);
		
		mCalanderImageView=(ImageView) findViewById(R.id.calander);
		mCalanderImageView.setOnClickListener(this);
		
		mAboutImageView =(ImageView) findViewById(R.id.about);
		mAboutImageView.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		if(arg0==mCreateevenImageView)
		{
			MainActivity.this.finish();
			Intent intent = new Intent(MainActivity.this,Createevent.class);
			intent.putExtra("header", "Create Event");
			startActivity(intent);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);	
		}
		if(arg0==mCalanderImageView)
		{
			if(android.os.Build.VERSION.SDK_INT>=11){
				MainActivity.this.finish();
				Intent intent = new Intent(MainActivity.this,CalanderActivity.class);
				intent.putExtra("header", "Calander");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
			}
			else{
				Toast.makeText(MainActivity.this, "Your device cannot Support this", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(MainActivity.this,CalendarView.class);
				intent.putExtra("header", "Calander");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
			}
		}		
		if(arg0==mContactImageView)
		{
			MainActivity.this.finish();
			Intent intent = new Intent(MainActivity.this,ContactListActivity.class);
			intent.putExtra("header", "Contact");
			startActivity(intent);
			overridePendingTransition(R.anim.left_in, R.anim.right_out);	
		}
		if(arg0==mAboutImageView)
		{
			MainActivity.this.finish();
			Intent intent = new Intent(MainActivity.this,AboutActivity.class);
			intent.putExtra("header", "About");
			startActivity(intent);
			overridePendingTransition(R.anim.slide_out_down, R.anim.slide_in_down);	
		}
		
	}

}
