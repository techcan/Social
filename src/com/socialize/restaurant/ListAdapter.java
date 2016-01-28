package com.socialize.restaurant;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.socialize.main.R;
import com.socilize.contact.ContactListActivity2;
import com.socilize.contact.ContactModel;

public class ListAdapter extends BaseAdapter implements Filterable {
	 
	 ArrayList <ContactModel> _nameList1=new ArrayList<ContactModel>();
	 Activity context;
	 boolean[] itemChecked;
	 private ValueFilter valueFilter;
	 private ArrayList<ContactModel> stringArrayInitial1;
	 TextView SelectedContacts;
	 public static List<ContactModel> contentList = new ArrayList<ContactModel>();
	 public ListAdapter(Activity context, ArrayList <ContactModel> contact,TextView ContactText) {
	  super();
	  this.context = context;
	  this._nameList1 = contact;
	  stringArrayInitial1 = _nameList1;
	  contentList =  _nameList1;
	  this.SelectedContacts=ContactText;
	  itemChecked = new boolean[_nameList1.size()];
	 }
	 
	 private class ViewHolder {
	  TextView TxtName;
	  TextView TxtNumber;
	  CheckBox ck1;
	 }
	 
	 public int getCount() {
	  return _nameList1.size();
	 }
	 
	 public Object getItem(int position) {
	  return _nameList1.get(position);
	 }
	 
	 public long getItemId(int position) {
	  return 0;
	 }
	 
	 @Override
	 public View getView(final int position, View convertView, ViewGroup parent) {
	  final ViewHolder holder;
	   
	  LayoutInflater inflater = context.getLayoutInflater();
	 
	  if (convertView == null) {
	   convertView = inflater.inflate(R.layout.contactlist_adapter12, null);
	   holder = new ViewHolder();
	 
	   holder.TxtName = (TextView) convertView
	     .findViewById(R.id.contact_list_name);
	   holder.TxtNumber = (TextView) convertView
			     .findViewById(R.id.contact_list_number);
	   holder.ck1 = (CheckBox) convertView
	     .findViewById(R.id.add_contacts_checkbox_list);
	 
	   convertView.setTag(holder);
	 
	  } else {
	    
	   holder = (ViewHolder) convertView.getTag();
	  }
	
	  holder.TxtName.setText(_nameList1.get(position).getName());
	  holder.TxtNumber.setText(_nameList1.get(position).getNumber());
	  holder.ck1.setChecked(false);
	 
	  if (itemChecked[position])
	   holder.ck1.setChecked(true);
	  else
	   holder.ck1.setChecked(false);
	 
	  holder.ck1.setOnClickListener(new OnClickListener() {
	   @Override
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
	    if (holder.ck1.isChecked()){
	     itemChecked[position] = true;
	    
	     int i=0;
			for(i=0;i<ContactListActivity2.NumberToSendSms.size();i++){
				
				if(ContactListActivity2.NumberToSendSms.get(i).equals(_nameList1.get(position).getNumber()))
					ContactListActivity2.NumberToSendSms.remove(_nameList1.get(position).getNumber());
			}
			
			
			ContactListActivity2.NumberToSendSms.add(_nameList1.get(position).getNumber());
	    
			SelectedContacts.setText(""+ContactListActivity2.NumberToSendSms.size());
	    
	    }else{
	     itemChecked[position] = false;
	   
	     if(ContactListActivity2.NumberToSendSms.contains(_nameList1.get(position).getNumber()))
	     {	ContactListActivity2.NumberToSendSms.remove(_nameList1.get(position).getNumber());
	     SelectedContacts.setText(""+ContactListActivity2.NumberToSendSms.size());
	     }
	    }
	    
	   } 
	  });
	 
	  return convertView;
	 
	 }
	 
	 @Override
		public Filter getFilter() {
			if(valueFilter==null) {

		        valueFilter=new ValueFilter();
		    }

		    return valueFilter;
		}
		private class ValueFilter extends Filter {

			//Invoked in a worker thread to filter the data according to the constraint.
		    @Override
		    protected FilterResults performFiltering(CharSequence constraint) {
		        FilterResults results=new FilterResults();
		        if(constraint!=null && constraint.length()>0){
		            ArrayList<ContactModel> filterList=new ArrayList<ContactModel>();
		            for(int i=0;i<stringArrayInitial1.size();i++){
		                if((stringArrayInitial1.get(i).getName().toUpperCase())
		                		.contains(constraint.toString().toUpperCase())) {
		                	ContactModel contacts = new ContactModel();
		                	contacts.setName(stringArrayInitial1.get(i).getName());
		                	contacts.setNumber(stringArrayInitial1.get(i).getNumber());
		                	contacts.setImage(stringArrayInitial1.get(i).getImage());
		                    filterList.add(contacts);
		                }
		            }
		            results.count=filterList.size();
		            results.values=filterList;
		        }else{
		            results.count=stringArrayInitial1.size();
		            results.values=stringArrayInitial1;
		        }
		        return results;
		    }


		    //Invoked in the UI thread to publish the filtering results in the user interface.
		    @SuppressWarnings("unchecked")
		    @Override
		    protected void publishResults(CharSequence constraint,
		            FilterResults results) {
		    	_nameList1=(ArrayList<ContactModel>) results.values;
		    	contentList = _nameList1;
		        notifyDataSetChanged();
		    }
		}
		
	 
	}