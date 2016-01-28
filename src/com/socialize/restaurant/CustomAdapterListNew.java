//package com.socialize.restaurant;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.TextView;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import com.socialize.main.R;
//import com.socilize.contact.ContactListActivity;
//import com.socilize.contact.ContactModel;
//
//
//public class CustomAdapterListNew extends BaseAdapter {
//
//	private ArrayList<ContactModel> _nameList1;
//	public Activity context;
//	
//	public static List<ContactModel> contentList = new ArrayList<ContactModel>();
//	public CustomAdapterListNew(Activity activity, ArrayList<ContactModel> contact) {
//		this.context = activity;
//		this._nameList1 = contact;
//		
//	}
//	
//	
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		System.out.println("size of list============"+_nameList1.size());
//		return _nameList1.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		
//		return position;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//	private class ViewHolder {
//		TextView nameTextView;
//		TextView numberTextView;
//		CheckBox add_contacts_checkbox;
//		
//	}
//	
//	@Override
//	public View getView( final int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		final ViewHolder viewHolder;
//		//final Season season = (Season) getGroup(groupPosition);
//		LayoutInflater inflater = context.getLayoutInflater();
//		if (convertView == null) 
//		{
//			convertView = inflater.inflate(R.layout.contact_list, null);
//			viewHolder = new ViewHolder();
//			viewHolder.nameTextView = (TextView) convertView
//					.findViewById(R.id.near_list_row_tv_spot_name);
//			viewHolder.numberTextView = (TextView) convertView
//					.findViewById(R.id.near_list_row_tv_spot_number);
//			viewHolder.add_contacts_checkbox = (CheckBox) convertView.findViewById(R.id.add_contacts_checkbox);
//			convertView.setTag(viewHolder);
//	} 
//		else {
//			viewHolder = (ViewHolder) convertView.getTag();
//		}
//		
//		
//
//		viewHolder.nameTextView.setText("" + _nameList1.get(position).getName());
//		viewHolder.numberTextView.setText("" + _nameList1.get(position).getNumber());
//		
//		viewHolder.add_contacts_checkbox.setOnCheckedChangeListener(null);
//		if(viewHolder.add_contacts_checkbox.isChecked()) viewHolder.add_contacts_checkbox.setChecked(true);
//		else viewHolder.add_contacts_checkbox.setChecked(false);
//		
//		viewHolder.add_contacts_checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				if(isChecked==true)
//				{
//					if(!ContactListActivity.NumberToSendSms.contains(_nameList1.get(position).getNumber()))
//					{		ContactListActivity.NumberToSendSms.add(_nameList1.get(position).getNumber());
//					System.out.println("Added----------------------- "+_nameList1.get(position).getNumber());	
//					}else
//						System.out.println("Present");						
//				}
//				else
//				{
//					if(ContactListActivity.NumberToSendSms.contains(_nameList1.get(position).getNumber()))
//						ContactListActivity.NumberToSendSms.remove(_nameList1.get(position).getNumber());
//					
//				}
//			}
//		});
//		
//		return convertView;
//		
//	}
//	
//	
//	
//	
//}
