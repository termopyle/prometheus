package org.kobic.omics.interpro.vo;

public class RangeObj {
	private int ord;
	private Long start;
	private Long end;
	
	public RangeObj() {
		this.ord = -1;
		this.start = 0l;
		this.end = 0l;
	}
	
	public RangeObj(int ord, Long start, Long end) {
		this.ord = ord;
		this.start = start;
		this.end = end;
	}
	
	public RangeObj(int ord, String start, String end) {
		this( ord, Long.valueOf(start), Long.valueOf(end) );
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}
}
