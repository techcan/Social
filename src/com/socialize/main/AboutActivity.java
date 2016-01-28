package com.socialize.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class AboutActivity extends Activity {

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		Intent intent = new Intent(AboutActivity.this,MainActivity.class);
		intent.putExtra("header", "Main");
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about_activity);

	}
	


}
