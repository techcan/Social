package com.socilize.contact;

import java.util.Comparator;

public class customcoparator implements Comparator<ContactModel> {

	@Override
	public int compare(ContactModel lhs, ContactModel rhs) {
		// TODO Auto-generated method stub
		 return lhs.name.compareTo(rhs.name);
	}

}
