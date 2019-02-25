package org.kobic.omics.viewer.vo;

import java.util.LinkedHashMap;

public class AttributesMap<K, V> extends LinkedHashMap<String,String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2038372024398601078L;
	
	public AttributesMap(String attributes) {
		String[] parts = attributes.split(";");
		for(String part:parts) {
			String[] kv = part.split("=");
			this.put(kv[0], kv[1]);
		}
	}
}
