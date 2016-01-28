package com.socialize.restaurant;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.socialize.imageloader.ImageLoader;
import com.socialize.main.R;

public class CustomAdapterLocationBased extends BaseAdapter {

	private Context _ctx;
	private ArrayList<String> _mArrayList = new ArrayList<String>();
	private ArrayList<String> _mAddressArrayList = new ArrayList<String>();
	private ArrayList<String> _mImageArrayList = new ArrayList<String>();
	private ArrayList<String> _mRatingArrayList = new ArrayList<String>();
	private ArrayList<String> _mRatingtextArrayList = new ArrayList<String>();
	private ArrayList<String> _mCusinesArrayList = new ArrayList<String>();
	/** Used for Inflating the Layout */
	private LayoutInflater inflater;
	
	ImageLoader imgLoader ;

	public CustomAdapterLocationBased(Context _ctx,
			ArrayList<String> _mArrayList,
			ArrayList<String> _mAddressArrayList,
			ArrayList<String> _mImageArrayList,
			ArrayList<String> _mRatingArrayList,
			ArrayList<String> _mRatingtextArrayList,
			ArrayList<String> _mCusinesArrayList) {
		super();
		this._ctx = _ctx;
		this._mArrayList = _mArrayList;
		this._mAddressArrayList = _mAddressArrayList;
		this._mImageArrayList = _mImageArrayList;
		this._mRatingArrayList = _mRatingArrayList;
		this._mImageArrayList = _mImageArrayList;
		this._mCusinesArrayList = _mCusinesArrayList;
		imgLoader = new ImageLoader(_ctx);
		this.inflater = (LayoutInflater) _ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _mArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
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
		viewHolder.nameTextView.setText("" + _mArrayList.get(arg0));
		viewHolder.addressTextView.setText("" + _mAddressArrayList.get(arg0));
		imgLoader.DisplayImage(_mImageArrayList.get(arg0), R.drawable.socialyze_center_logo, viewHolder.reastaurantImageView);
		
		return convertView;
	}

	private static class ViewHolder {
		/** used for name of the Restaurant */
		TextView nameTextView;
		/** used for Address of the Restaurant */
		TextView addressTextView;
		/** used for Image of the Restaurant */
		ImageView reastaurantImageView;
	}
}
