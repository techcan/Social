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
import android.widget.ImageView;
import android.widget.TextView;

import com.socialize.imageloader.ImageLoader;
import com.socialize.main.R;

public class ResturentLocationAdapter extends BaseAdapter implements Filterable {

	private ArrayList<RestaurantLocationModel> _nameList1;
	private ArrayList<RestaurantLocationModel> stringArrayInitial1;
	public Activity context;
	public LayoutInflater inflater;
	private ValueFilter valueFilter;
	
	 ArrayList<RestaurantLocationModel> filterList1=new ArrayList<RestaurantLocationModel>();
	public static List<RestaurantLocationModel> contentList = new ArrayList<RestaurantLocationModel>();
	ImageLoader imgLoader ;
	
	public ResturentLocationAdapter(Activity context,ArrayList<RestaurantLocationModel> _nameList1) {
		super();
		this.context = context;
		this._nameList1 = _nameList1;
		stringArrayInitial1 = _nameList1;
		contentList =  _nameList1;
		imgLoader = new ImageLoader(context);
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
		/** used for name of the Restaurant */
		TextView nameTextView;
		/** used for Address of the Restaurant */
		TextView addressTextView;
		/** used for Image of the Restaurant */
		ImageView reastaurantImageView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
		   // int loader = R.drawable.socialyze_center_logo;
			convertView = inflater.inflate(
					R.layout.near_list_row_location_based, null);
			viewHolder.nameTextView = (TextView) convertView
					.findViewById(R.id.near_list_row_tv_spot_name);
			viewHolder.addressTextView = (TextView) convertView
					.findViewById(R.id.near_list_row_tv_spot_address);
			viewHolder.reastaurantImageView = (ImageView) convertView
					.findViewById(R.id.restaurantimg);
			convertView.setTag(viewHolder);
		} else
			viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.nameTextView.setText("" + _nameList1.get(position).getNameString());
		viewHolder.addressTextView.setText("" + _nameList1.get(position).getAddressString());
		imgLoader.DisplayImage(_nameList1.get(position).getImagesString(), R.drawable.socialyze_center_logo, viewHolder.reastaurantImageView);
		
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
	        ArrayList<RestaurantLocationModel> filterList=new ArrayList<RestaurantLocationModel>();
	        if(constraint!=null && constraint.length()>0){
	            
	            for(int i=0;i<stringArrayInitial1.size();i++){
	            	
	        
	            	if(!RestaurantLocationBasedListing.Location.equals("") && 
            				!RestaurantLocationBasedListing.Cuisine.equals("")&& 
            				!RestaurantLocationBasedListing.SearchText.equals("")) {
            			System.out.println("8-------------");
            			if((stringArrayInitial1.get(i).getLocality().toUpperCase())
		                		.contains(RestaurantLocationBasedListing.Location.toUpperCase()) &&
		                		(stringArrayInitial1.get(i).getCuisinesString().toUpperCase())
		                		.contains(RestaurantLocationBasedListing.Cuisine.toUpperCase()) &&
		                		(stringArrayInitial1.get(i).getNameString().toUpperCase())
		                		.contains(RestaurantLocationBasedListing.SearchText.toUpperCase()) &&
		                		(stringArrayInitial1.get(i).getBudgetString()<RestaurantLocationBasedListing.budget) 
	            				) 
	            		{
	            			System.out.println("if loop=============ddddddd==================");
		                	RestaurantLocationModel contacts = new RestaurantLocationModel();
		                	
		                	contacts.setAddressString(stringArrayInitial1.get(i).getAddressString());
		                	contacts.setCuisinesString(stringArrayInitial1.get(i).getCuisinesString());
		                	contacts.setImagesString(stringArrayInitial1.get(i).getImagesString());
		                	contacts.setLatitudeString(stringArrayInitial1.get(i).getLatitudeString());
		                	contacts.setLongitudeString(stringArrayInitial1.get(i).getLongitudeString());
		                	contacts.setNameString(stringArrayInitial1.get(i).getNameString());
		                	contacts.setRatingString(stringArrayInitial1.get(i).getRatingString());
		                	contacts.setRatingtextString(stringArrayInitial1.get(i).getRatingtextString());
		                	contacts.setLocality(stringArrayInitial1.get(i).getLocality());
		                	
		                    filterList.add(contacts);
		                }
            		}
	            	else if(!RestaurantLocationBasedListing.SearchText.equals("") && RestaurantLocationBasedListing.Location.equals("")
            				&& !RestaurantLocationBasedListing.Cuisine.equals("")) {
            			System.out.println("7-------------");
            			if((stringArrayInitial1.get(i).getCuisinesString().toUpperCase())
		                		.contains(RestaurantLocationBasedListing.Cuisine.toUpperCase()) &&
		                		(stringArrayInitial1.get(i).getNameString().toUpperCase())
		                		.contains(RestaurantLocationBasedListing.SearchText.toUpperCase()) &&
		                		
		                		(stringArrayInitial1.get(i).getBudgetString()<RestaurantLocationBasedListing.budget) 
	            				) 
	            		{
	            			System.out.println("if loop=============ddddddd==================");
		                	RestaurantLocationModel contacts = new RestaurantLocationModel();
		                	
		                	contacts.setAddressString(stringArrayInitial1.get(i).getAddressString());
		                	contacts.setCuisinesString(stringArrayInitial1.get(i).getCuisinesString());
		                	contacts.setImagesString(stringArrayInitial1.get(i).getImagesString());
		                	contacts.setLatitudeString(stringArrayInitial1.get(i).getLatitudeString());
		                	contacts.setLongitudeString(stringArrayInitial1.get(i).getLongitudeString());
		                	contacts.setNameString(stringArrayInitial1.get(i).getNameString());
		                	contacts.setRatingString(stringArrayInitial1.get(i).getRatingString());
		                	contacts.setRatingtextString(stringArrayInitial1.get(i).getRatingtextString());
		                	contacts.setLocality(stringArrayInitial1.get(i).getLocality());
		                	
		                    filterList.add(contacts);
		                }
            		}
	            	
	            	else if(RestaurantLocationBasedListing.Location.equals("") && 
            				RestaurantLocationBasedListing.Cuisine.equals("")&& 
            				!RestaurantLocationBasedListing.SearchText.equals("")) {
            			System.out.println("10-------------");
            			if(
		                		(stringArrayInitial1.get(i).getNameString().toUpperCase())
		                		.contains(RestaurantLocationBasedListing.SearchText.toUpperCase()) &&
		                		(stringArrayInitial1.get(i).getBudgetString()<RestaurantLocationBasedListing.budget) 
	            				) 
	            		{
	            			System.out.println("if loop=============ddddddd==================");
		                	RestaurantLocationModel contacts = new RestaurantLocationModel();
		                	
		                	contacts.setAddressString(stringArrayInitial1.get(i).getAddressString());
		                	contacts.setCuisinesString(stringArrayInitial1.get(i).getCuisinesString());
		                	contacts.setImagesString(stringArrayInitial1.get(i).getImagesString());
		                	contacts.setLatitudeString(stringArrayInitial1.get(i).getLatitudeString());
		                	contacts.setLongitudeString(stringArrayInitial1.get(i).getLongitudeString());
		                	contacts.setNameString(stringArrayInitial1.get(i).getNameString());
		                	contacts.setRatingString(stringArrayInitial1.get(i).getRatingString());
		                	contacts.setRatingtextString(stringArrayInitial1.get(i).getRatingtextString());
		                	contacts.setLocality(stringArrayInitial1.get(i).getLocality());
		                	
		                    filterList.add(contacts);
		                }
            		}
	            	
	            	
	            	else if(!RestaurantLocationBasedListing.Location.equals("") && 
	            			!RestaurantLocationBasedListing.Cuisine.equals("") && 
	            			RestaurantLocationBasedListing.SearchText.equals("")){
	            
	            		System.out.println("1----234---"+stringArrayInitial1.get(i).getLocality().toUpperCase()+"------"+RestaurantLocationBasedListing.Location.toUpperCase()+"----"+stringArrayInitial1.get(i).getCuisinesString().toUpperCase()+"----"+RestaurantLocationBasedListing.Cuisine);
            		if(
            				stringArrayInitial1.get(i).getLocality().toUpperCase().contains(RestaurantLocationBasedListing.Location.toUpperCase())
            				&&
            				stringArrayInitial1.get(i).getCuisinesString().toUpperCase().contains(RestaurantLocationBasedListing.Cuisine.toUpperCase())
            				
            				&&
            				
            				(stringArrayInitial1.get(i).getBudgetString()<RestaurantLocationBasedListing.budget) ) 
            		{
            			System.out.println("if loop==========dasfdsfdfdsf======================");
	                	RestaurantLocationModel contacts = new RestaurantLocationModel();
	                	
	                	contacts.setAddressString(stringArrayInitial1.get(i).getAddressString());
	                	contacts.setCuisinesString(stringArrayInitial1.get(i).getCuisinesString());
	                	contacts.setImagesString(stringArrayInitial1.get(i).getImagesString());
	                	contacts.setLatitudeString(stringArrayInitial1.get(i).getLatitudeString());
	                	contacts.setLongitudeString(stringArrayInitial1.get(i).getLongitudeString());
	                	contacts.setNameString(stringArrayInitial1.get(i).getNameString());
	                	contacts.setRatingString(stringArrayInitial1.get(i).getRatingString());
	                	contacts.setRatingtextString(stringArrayInitial1.get(i).getRatingtextString());
	                	contacts.setLocality(stringArrayInitial1.get(i).getLocality());
	                	
	                    filterList.add(contacts);
	                
	            	}
	            	
	            	}
	            	
	            	
	            	
	            	else if(RestaurantLocationBasedListing.Location.equals("") && 
	            			RestaurantLocationBasedListing.Cuisine.equals("") &&
	            			RestaurantLocationBasedListing.SearchText.equals("")){
	            
	            		System.out.println("1-------------");
            		if((stringArrayInitial1.get(i).getBudgetString()<RestaurantLocationBasedListing.budget) ) 
            		{
            			System.out.println("if loop==========1111======================");
	                	RestaurantLocationModel contacts = new RestaurantLocationModel();
	                	
	                	contacts.setAddressString(stringArrayInitial1.get(i).getAddressString());
	                	contacts.setCuisinesString(stringArrayInitial1.get(i).getCuisinesString());
	                	contacts.setImagesString(stringArrayInitial1.get(i).getImagesString());
	                	contacts.setLatitudeString(stringArrayInitial1.get(i).getLatitudeString());
	                	contacts.setLongitudeString(stringArrayInitial1.get(i).getLongitudeString());
	                	contacts.setNameString(stringArrayInitial1.get(i).getNameString());
	                	contacts.setRatingString(stringArrayInitial1.get(i).getRatingString());
	                	contacts.setRatingtextString(stringArrayInitial1.get(i).getRatingtextString());
	                	contacts.setLocality(stringArrayInitial1.get(i).getLocality());
	                	
	                    filterList.add(contacts);
	                
	            	}
	            	
	            	}
	            	
	            	else if(!RestaurantLocationBasedListing.Cuisine.equals("") && 
	            			!RestaurantLocationBasedListing.Location.equals("") && RestaurantLocationBasedListing.SearchText.equals("")){
	            		System.out.println("2-------------");
	            		if((stringArrayInitial1.get(i).getLocality().toUpperCase())
		                		.contains(RestaurantLocationBasedListing.Location.toUpperCase()) &&
		                		
		                		(stringArrayInitial1.get(i).getCuisinesString().toUpperCase())
		                		.contains(RestaurantLocationBasedListing.Cuisine.toUpperCase()) &&
		                		
		                		(stringArrayInitial1.get(i).getBudgetString()<RestaurantLocationBasedListing.budget) 
	            				) 
	            		{
	            			System.out.println("if loop============kjkjkjkjkj===================");
		                	RestaurantLocationModel contacts = new RestaurantLocationModel();
		                	
		                	contacts.setAddressString(stringArrayInitial1.get(i).getAddressString());
		                	contacts.setCuisinesString(stringArrayInitial1.get(i).getCuisinesString());
		                	contacts.setImagesString(stringArrayInitial1.get(i).getImagesString());
		                	contacts.setLatitudeString(stringArrayInitial1.get(i).getLatitudeString());
		                	contacts.setLongitudeString(stringArrayInitial1.get(i).getLongitudeString());
		                	contacts.setNameString(stringArrayInitial1.get(i).getNameString());
		                	contacts.setRatingString(stringArrayInitial1.get(i).getRatingString());
		                	contacts.setRatingtextString(stringArrayInitial1.get(i).getRatingtextString());
		                	contacts.setLocality(stringArrayInitial1.get(i).getLocality());
		                	
		                    filterList.add(contacts);
		                }
		            	
	            	}
	            	
	            	
	            	
	            	else if(RestaurantLocationBasedListing.Cuisine.equals("") && RestaurantLocationBasedListing.SearchText.equals("") &&
	            			!RestaurantLocationBasedListing.Location.equals("")){
	            		System.out.println("3-------------");
	            		if((stringArrayInitial1.get(i).getLocality().toUpperCase())
		                		.contains(RestaurantLocationBasedListing.Location.toUpperCase()) &&
		                		
		                		(stringArrayInitial1.get(i).getBudgetString()<RestaurantLocationBasedListing.budget) 
	            				) 
	            		{
	            			System.out.println("if loop=============222===================");
		                	RestaurantLocationModel contacts = new RestaurantLocationModel();
		                	
		                	contacts.setAddressString(stringArrayInitial1.get(i).getAddressString());
		                	contacts.setCuisinesString(stringArrayInitial1.get(i).getCuisinesString());
		                	contacts.setImagesString(stringArrayInitial1.get(i).getImagesString());
		                	contacts.setLatitudeString(stringArrayInitial1.get(i).getLatitudeString());
		                	contacts.setLongitudeString(stringArrayInitial1.get(i).getLongitudeString());
		                	contacts.setNameString(stringArrayInitial1.get(i).getNameString());
		                	contacts.setRatingString(stringArrayInitial1.get(i).getRatingString());
		                	contacts.setRatingtextString(stringArrayInitial1.get(i).getRatingtextString());
		                	contacts.setLocality(stringArrayInitial1.get(i).getLocality());
		                	
		                    filterList.add(contacts);
		                }
		            	
	            	}
	            	else if(RestaurantLocationBasedListing.Location.equals("") && RestaurantLocationBasedListing.SearchText.equals("") &&
	            			!RestaurantLocationBasedListing.Cuisine.equals("") ){
	            		System.out.println("4-------------");
	            		if((stringArrayInitial1.get(i).getCuisinesString().toUpperCase())
		                		.contains(RestaurantLocationBasedListing.Cuisine.toUpperCase()) &&
		                		
		                		(stringArrayInitial1.get(i).getBudgetString()<RestaurantLocationBasedListing.budget) 
	            				) 
	            		{
	            			System.out.println("if loop=============222===================");
		                	RestaurantLocationModel contacts = new RestaurantLocationModel();
		                	
		                	contacts.setAddressString(stringArrayInitial1.get(i).getAddressString());
		                	contacts.setCuisinesString(stringArrayInitial1.get(i).getCuisinesString());
		                	contacts.setImagesString(stringArrayInitial1.get(i).getImagesString());
		                	contacts.setLatitudeString(stringArrayInitial1.get(i).getLatitudeString());
		                	contacts.setLongitudeString(stringArrayInitial1.get(i).getLongitudeString());
		                	contacts.setNameString(stringArrayInitial1.get(i).getNameString());
		                	contacts.setRatingString(stringArrayInitial1.get(i).getRatingString());
		                	contacts.setRatingtextString(stringArrayInitial1.get(i).getRatingtextString());
		                	contacts.setLocality(stringArrayInitial1.get(i).getLocality());
		                	
		                    filterList.add(contacts);
		                }
		            	
	            	}
	            	else if (!RestaurantLocationBasedListing.Location.equals("") && RestaurantLocationBasedListing.Cuisine.equals("")
	            			&&  !RestaurantLocationBasedListing.SearchText.equals("")){
	            		System.out.println("5------------");
	            		if(
	            				(stringArrayInitial1.get(i).getLocality().toUpperCase())
		                		.contains(RestaurantLocationBasedListing.Location.toUpperCase()) &&
		                		(stringArrayInitial1.get(i).getNameString().toUpperCase())
		                		.contains(RestaurantLocationBasedListing.SearchText.toUpperCase()) &&
		                		(stringArrayInitial1.get(i).getBudgetString()<RestaurantLocationBasedListing.budget) 
	            				) 
	            		{
	            			System.out.println("if loop=============ddddddd==================");
		                	RestaurantLocationModel contacts = new RestaurantLocationModel();
		                	
		                	contacts.setAddressString(stringArrayInitial1.get(i).getAddressString());
		                	contacts.setCuisinesString(stringArrayInitial1.get(i).getCuisinesString());
		                	contacts.setImagesString(stringArrayInitial1.get(i).getImagesString());
		                	contacts.setLatitudeString(stringArrayInitial1.get(i).getLatitudeString());
		                	contacts.setLongitudeString(stringArrayInitial1.get(i).getLongitudeString());
		                	contacts.setNameString(stringArrayInitial1.get(i).getNameString());
		                	contacts.setRatingString(stringArrayInitial1.get(i).getRatingString());
		                	contacts.setRatingtextString(stringArrayInitial1.get(i).getRatingtextString());
		                	contacts.setLocality(stringArrayInitial1.get(i).getLocality());
		                	
		                    filterList.add(contacts);
		                }
	            		else if(!RestaurantLocationBasedListing.SearchText.equals("") && !RestaurantLocationBasedListing.Cuisine.equals("")
	            				&& RestaurantLocationBasedListing.Location.equals("")) {
	            			System.out.println("6-------------");
	            			if((stringArrayInitial1.get(i).getNameString().toUpperCase())
			                		.contains(RestaurantLocationBasedListing.SearchText.toUpperCase()) &&
			                		(stringArrayInitial1.get(i).getCuisinesString().toUpperCase())
			                		.contains(RestaurantLocationBasedListing.Cuisine.toUpperCase()) &&
			                		
			                		(stringArrayInitial1.get(i).getBudgetString()<RestaurantLocationBasedListing.budget) 
		            				) 
		            		{
		            			System.out.println("if loop=============ddddddd==================");
			                	RestaurantLocationModel contacts = new RestaurantLocationModel();
			                	
			                	contacts.setAddressString(stringArrayInitial1.get(i).getAddressString());
			                	contacts.setCuisinesString(stringArrayInitial1.get(i).getCuisinesString());
			                	contacts.setImagesString(stringArrayInitial1.get(i).getImagesString());
			                	contacts.setLatitudeString(stringArrayInitial1.get(i).getLatitudeString());
			                	contacts.setLongitudeString(stringArrayInitial1.get(i).getLongitudeString());
			                	contacts.setNameString(stringArrayInitial1.get(i).getNameString());
			                	contacts.setRatingString(stringArrayInitial1.get(i).getRatingString());
			                	contacts.setRatingtextString(stringArrayInitial1.get(i).getRatingtextString());
			                	contacts.setLocality(stringArrayInitial1.get(i).getLocality());
			                	
			                    filterList.add(contacts);
			                }
	            		}
	            		else if(RestaurantLocationBasedListing.SearchText.equals("") && !RestaurantLocationBasedListing.Location.equals("")
	            				&& !RestaurantLocationBasedListing.Cuisine.equals("")) {
	            			System.out.println("7-------------");
	            			if((stringArrayInitial1.get(i).getCuisinesString().toUpperCase())
			                		.contains(RestaurantLocationBasedListing.Cuisine.toUpperCase()) &&
			                		(stringArrayInitial1.get(i).getLocality().toUpperCase())
			                		.contains(RestaurantLocationBasedListing.Location.toUpperCase()) &&
			                		
			                		(stringArrayInitial1.get(i).getBudgetString()<RestaurantLocationBasedListing.budget) 
		            				) 
		            		{
		            			System.out.println("if loop=============ddddddd==================");
			                	RestaurantLocationModel contacts = new RestaurantLocationModel();
			                	
			                	contacts.setAddressString(stringArrayInitial1.get(i).getAddressString());
			                	contacts.setCuisinesString(stringArrayInitial1.get(i).getCuisinesString());
			                	contacts.setImagesString(stringArrayInitial1.get(i).getImagesString());
			                	contacts.setLatitudeString(stringArrayInitial1.get(i).getLatitudeString());
			                	contacts.setLongitudeString(stringArrayInitial1.get(i).getLongitudeString());
			                	contacts.setNameString(stringArrayInitial1.get(i).getNameString());
			                	contacts.setRatingString(stringArrayInitial1.get(i).getRatingString());
			                	contacts.setRatingtextString(stringArrayInitial1.get(i).getRatingtextString());
			                	contacts.setLocality(stringArrayInitial1.get(i).getLocality());
			                	
			                    filterList.add(contacts);
			                }
	            		}
	            		
	            		
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
	    	_nameList1=(ArrayList<RestaurantLocationModel>) results.values;
	    	contentList = _nameList1;
	        notifyDataSetChanged();
	    }
	}

}