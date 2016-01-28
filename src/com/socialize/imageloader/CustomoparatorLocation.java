package com.socialize.imageloader;

import java.util.Comparator;

import com.socialize.restaurant.RestaurantLocationModel;

public class CustomoparatorLocation implements Comparator<RestaurantLocationModel> {

	@Override
	public int compare(RestaurantLocationModel lhs, RestaurantLocationModel rhs) {
		System.out.println("Distance----------------- "+lhs.getDistance());
		int x = (int)(lhs.getDistance());   
		int y = (int)(rhs.getDistance());   

		 return x-y; 
	}
}

