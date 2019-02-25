package org.kobic.omics.interpro.com;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Utility {
	public static List<String> uniqueList(Map<Integer, List<String>> map, Integer key) {
		return new ArrayList<String>(new HashSet<String>(map.get(key)));
	}
	
	public static String emptyToNull(String value) {
		if( value == null || value.equals("") )	return null;
		return value;
	}
}
