package com.socialize.restaurant;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.socialize.main.R;

public class CustomAdapterList extends BaseAdapter implements Filterable {

	private ArrayList<RestaurantCityModel> _nameList1;
	private ArrayList<RestaurantCityModel> stringArrayInitial1;
	public Activity context;
	public LayoutInflater inflater;
	private ValueFilter valueFilter;
	public static List<RestaurantCityModel> contentList = new ArrayList<RestaurantCityModel>();
	
	public CustomAdapterList(Activity context,ArrayList<RestaurantCityModel> _nameList1) {
		super();
		this.context = context;
		this._nameList1 = _nameList1;
		stringArrayInitial1 = _nameList1;
		contentList =  _nameList1;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return _nameList1.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public static class ViewHolder {
		/** used for name of the Spot */
		TextView nameTextView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.near_list_row, null);

			viewHolder.nameTextView = (TextView) convertView
					.findViewById(R.id.near_list_row_tv_spot_name);
			convertView.setTag(viewHolder);
		} else
			viewHolder = (ViewHolder) convertView.getTag();

		
		viewHolder.nameTextView.setText("" + _nameList1.get(position).getName());
		
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
	            ArrayList<RestaurantCityModel> filterList=new ArrayList<RestaurantCityModel>();
	            for(int i=0;i<stringArrayInitial1.size();i++){
	                if((stringArrayInitial1.get(i).getName().toUpperCase())
	                		.contains(constraint.toString().toUpperCase())) {
	                	RestaurantCityModel contacts = new RestaurantCityModel();
	                	contacts.setName(stringArrayInitial1.get(i).getName());
	                	contacts.setLatitude(stringArrayInitial1.get(i).getLatitude());
	                	contacts.setLongitude(stringArrayInitial1.get(i).getLongitude());
	                	contacts.setId(stringArrayInitial1.get(i).getId());
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
	    	_nameList1=(ArrayList<RestaurantCityModel>) results.values;
	    	contentList = _nameList1;
	        notifyDataSetChanged();
	    }
	}
}