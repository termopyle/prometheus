package org.kobic.omics.circos.vo;

import java.io.Serializable;

public class ChrVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String chr;
	private int start;
	private int end;
	private String seqName;
	
	public String getChr() {
		return chr;
	}
	public void setChr(String chr) {
		this.chr = chr;
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
	public String getSeqName() {
		return seqName;
	}
	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
