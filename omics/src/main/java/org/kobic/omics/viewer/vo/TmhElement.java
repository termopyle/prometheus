package org.kobic.omics.viewer.vo;

import org.kobic.omics.viewer.com.Utilities;

public class TmhElement implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String location;
	private int start;
	private int end;
	
	public TmhElement(String location, String start, String end) {
		this.location = location;
		this.start = Utilities.isNumeric(start)==true?Integer.parseInt(start):0;
		this.end = Utilities.isNumeric(end)==true?Integer.parseInt(end):0;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
}
