package com.socialize.imageloader;

import java.util.Comparator;

import com.socialize.restaurant.RestaurantLocationModel;

public class CustomoparatorCostwise implements Comparator<RestaurantLocationModel> {

	@Override
	public int compare(RestaurantLocationModel lhs, RestaurantLocationModel rhs) {
		
		int x = (int)(lhs.getBudgetString());   
		int y = (int)(rhs.getBudgetString());   

		 return x-y; 
	}
}

