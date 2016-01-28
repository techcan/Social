package com.socilize.contact;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.socialize.main.MainActivity;
import com.socialize.main.R;

public class ContactListActivity extends Activity {
	static JSONArray arrayCallHistory;
	static JSONArray arrayContact;

	ArrayList<ContactModel> _mNameArrayList = new ArrayList<ContactModel>();
	
	private CustomAdapterList mCustomAdapter;

	JSONArray emailArray = new JSONArray();
	JSONArray numberArray = new JSONArray();
	ImageView image;
	String emailType;

	private ListView mListView;
	private EditText mSearchEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	
		setContentView(R.layout.contact_main);
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mListView = (ListView) findViewById(R.id.listview_main);
		mSearchEditText = (EditText) findViewById(R.id.contact_main_et_search);
		mCustomAdapter = new CustomAdapterList(ContactListActivity.this,_mNameArrayList);		
		mSearchEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				mCustomAdapter.getFilter().filter(arg0);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
		mListView.setAdapter(mCustomAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				HideKeyboard();
				List<ContactModel> checkList = CustomAdapterList.contentList;								
				// openWhatsApp(_mNumberArrayList.get(position));
				sendSMS(checkList.get(position).getNumber());

			}
		});
		
//		try {
//			readContacts_array();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			/* Converting to JSON array object */
			new ContactFetching().execute("");
			// readContacts_json();
			//readContacts_array();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}
	private class ContactFetching extends AsyncTask<String, Void, String> {

		//private ProgressDialog pdia;

		@Override
		protected String doInBackground(String... params) {
//				try {
//					readContacts_array();
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}


			try {
				ContentResolver cr = getContentResolver();
				Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
						null, null, null);
				String phone = null;
				String image_uri = "";
				if (cur.getCount() > 0) {
					while (cur.moveToNext()) {
						String id = cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts._ID));
						String name = cur
								.getString(cur
										.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

						try {
							image_uri = cur
									.getString(cur
											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (image_uri != null) {
							System.out.println(Uri.parse(image_uri));
							try {
								Bitmap bitmap = MediaStore.Images.Media
										.getBitmap(ContactListActivity.this.getContentResolver(),
												Uri.parse(image_uri));
								// image.setImageBitmap(bitmap);
								/**
								 * encode image to base64
								 */

								// imageBase64String = Helper.encodeTobase64(bitmap);
								// ImageList.add(imageBase64String);

							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

						if (Integer
								.parseInt(cur.getString(cur
										.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
							System.out.println("name : " + name + ", ID : " + id);
							// get the phone number
							numberArray = new JSONArray();
							phone = "";
							Cursor pCur = cr.query(
									ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
									null,
									ContactsContract.CommonDataKinds.Phone.CONTACT_ID
											+ " = ?", new String[] { id }, null);
							while (pCur.moveToNext()) {
								phone = pCur
										.getString(pCur
												.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
								System.out.println("phone" + phone);
								numberArray.put(phone);
							}
							pCur.close();
							final ContactModel contactModel = new ContactModel();
							contactModel.setName(name);
							contactModel.setNumber(phone);
							runOnUiThread(new Runnable() {
								  public void run() {
									  _mNameArrayList.add(contactModel);
								  }
							});
							publishProgress(null);
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			return "Executed";
		}
		
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			try {
				runOnUiThread(new Runnable() {
					  public void run() {
						  mCustomAdapter.notifyDataSetChanged();
						  mListView.invalidateViews();
					  }
					}); 
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sendSMS(String num) {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", num,
				null)));
	}

	private void openWhatsApp(String id) {

		Intent i = new Intent(Intent.ACTION_SENDTO,
				Uri.parse("content://com.android.contacts/data/" + id));
		i.setType("text/plain");
		i.setPackage("com.whatsapp"); // so that only Whatsapp reacts and not
										// the chooser
		i.putExtra(Intent.EXTRA_SUBJECT, "Subject");
		i.putExtra(Intent.EXTRA_TEXT, "I'm the body.");
		startActivity(i);

	}

	public void readContacts_json() throws JSONException {
		arrayContact = new JSONArray();
		JSONObject objContact = new JSONObject();
		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		String phone = null;
		String emailContact = null;
		String image_uri = "";
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				image_uri = cur
						.getString(cur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
				if (image_uri != null) {
					System.out.println(Uri.parse(image_uri));
					try {
						Bitmap bitmap = MediaStore.Images.Media
								.getBitmap(this.getContentResolver(),
										Uri.parse(image_uri));
						System.out.println(bitmap);
						image.setImageBitmap(bitmap);
						/**
						 * encode image to base64
						 */

						// imageBase64String = Helper.encodeTobase64(bitmap);
						// ImageList.add(imageBase64String);

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					System.out.println("name : " + name + ", ID : " + id);
					// get the phone number
					numberArray = new JSONArray();
					phone = "";
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { id }, null);
					while (pCur.moveToNext()) {
						phone = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						System.out.println("phone" + phone);
						numberArray.put(phone);
					}
					pCur.close();

					// get email and type
					emailContact = "";
					emailArray = new JSONArray();
					Cursor emailCur = cr.query(
							ContactsContract.CommonDataKinds.Email.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Email.CONTACT_ID
									+ " = ?", new String[] { id }, null);
					while (emailCur.moveToNext()) {
						// This would allow you get several email addresses
						// if the email addresses were stored in an array
						emailContact = emailCur
								.getString(emailCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
						emailType = emailCur
								.getString(emailCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
						System.out.println("Email " + emailContact
								+ " Email Type : " + emailType);

						emailArray.put(emailContact);
					}

					objContact.put("contact_name", name);
					objContact.put("phone_number_array", numberArray);
					objContact.put("email_array", emailArray);
					objContact.put("profile_img", image_uri);
					arrayContact.put(objContact);

					objContact = new JSONObject();
					emailCur.close();
				}

			}
			System.out.println("out of array*************" + arrayContact);

		}
	}

	public void readContacts_array() throws JSONException {

		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		String phone = null;
		String image_uri = "";
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				try {
					image_uri = cur
							.getString(cur
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (image_uri != null) {
					System.out.println(Uri.parse(image_uri));
					try {
						Bitmap bitmap = MediaStore.Images.Media
								.getBitmap(this.getContentResolver(),
										Uri.parse(image_uri));
						// image.setImageBitmap(bitmap);
						/**
						 * encode image to base64
						 */

						// imageBase64String = Helper.encodeTobase64(bitmap);
						// ImageList.add(imageBase64String);

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					System.out.println("name : " + name + ", ID : " + id);
					// get the phone number
					numberArray = new JSONArray();
					phone = "";
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { id }, null);
					while (pCur.moveToNext()) {
						phone = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						System.out.println("phone" + phone);
						numberArray.put(phone);
					}
					pCur.close();
					ContactModel contactModel = new ContactModel();
					contactModel.setName(name);
					contactModel.setNumber(phone);
					_mNameArrayList.add(contactModel);
					ContactListActivity.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							mCustomAdapter.notifyDataSetChanged();
						}
					});
				}
			}
		}
	}
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	//	super.onBackPressed();
		ContactListActivity.this.finish();
		Intent intent = new Intent(ContactListActivity.this,MainActivity.class);
		//intent.putExtra("header", "Contact");
		startActivity(intent);
		overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}
	private void HideKeyboard(){
		InputMethodManager imm = (InputMethodManager)getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);
	}
}
