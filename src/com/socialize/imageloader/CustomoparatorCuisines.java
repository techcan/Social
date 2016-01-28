package com.socialize.imageloader;

import java.util.Comparator;

import com.socialize.restaurant.RestaurantLocationModel;

public class CustomoparatorCuisines implements Comparator<RestaurantLocationModel> {

	@Override
	public int compare(RestaurantLocationModel lhs, RestaurantLocationModel rhs) {
		
		 return lhs.getCuisinesString().compareTo(rhs.getCuisinesString());
	}
}

