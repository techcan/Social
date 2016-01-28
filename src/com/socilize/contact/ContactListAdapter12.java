package com.socilize.contact;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.socialize.main.R;


public class ContactListAdapter12 extends BaseAdapter implements Filterable {

	
	
	boolean[] itemChecked;
	private ArrayList<ContactModel> _nameList1=new ArrayList<ContactModel>();
	public Activity context;
	private ArrayList<ContactModel> stringArrayInitial1;
	
	int position1=0;
	private ValueFilter valueFilter;
	public static List<ContactModel> contentList = new ArrayList<ContactModel>();
	public ContactListAdapter12(Activity activity, ArrayList<ContactModel> contact) {
		this.context = activity;
		this._nameList1 = contact;
		stringArrayInitial1 = _nameList1;
		contentList =  _nameList1;
		itemChecked = new boolean[_nameList1.size()];
	}
	
	
	
	
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _nameList1.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	private class ViewHolder {
		TextView Name;
		TextView Number;
		CheckBox AddContact;
		
	}

	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		LayoutInflater inflater = context.getLayoutInflater();
		if (convertView == null) 
		{
			convertView = inflater.inflate(R.layout.contactlist_adapter12, null);
			holder = new ViewHolder();
			holder.Name = (TextView) convertView.findViewById(R.id.contact_list_name);
			holder.Number = (TextView) convertView.findViewById(R.id.contact_list_number);
			
			holder.AddContact=(CheckBox)convertView.findViewById(R.id.add_contacts_checkbox_list);
			convertView.setTag(holder);
	} 
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.Name.setText(_nameList1.get(position).getName());
		holder.Number.setText(_nameList1.get(position).getNumber());
		
		holder.AddContact.setChecked(false);
		 
		  if (itemChecked[position])
		   holder.AddContact.setChecked(true);
		  else
		   holder.AddContact.setChecked(false);
		 
		  holder.AddContact.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked==true)
				{
					int i=0;
					for(i=0;i<ContactListActivity2.NumberToSendSms.size();i++){
						
						if(ContactListActivity2.NumberToSendSms.get(i).equals(_nameList1.get(position1).getNumber()))
							ContactListActivity2.NumberToSendSms.remove(_nameList1.get(position1).getNumber());
					}
					
					
					ContactListActivity2.NumberToSendSms.add(_nameList1.get(position1).getNumber());
					
//					if(!ContactListActivity2.NumberToSendSms.contains(_nameList1.get(position1).getNumber()))
//					{		ContactListActivity2.NumberToSendSms.add(_nameList1.get(position1).getNumber());
//					System.out.println("Added----------------------- "+_nameList1.get(position1).getNumber());	
//					}else
//						System.out.println("Present");						
//				
				}
				else
				{
					if(ContactListActivity2.NumberToSendSms.contains(_nameList1.get(position1).getNumber()))
						ContactListActivity2.NumberToSendSms.remove(_nameList1.get(position1).getNumber());
					
				}
				
			}
		});
		  notifyDataSetChanged();
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
