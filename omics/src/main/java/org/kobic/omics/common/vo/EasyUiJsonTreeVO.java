package org.kobic.omics.common.vo;

import java.io.Serializable;
import java.util.Comparator;

public class EasyUiJsonTreeVO implements Serializable, Comparator<EasyUiJsonTreeVO>{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String text;
	private String rank;
	private String end_yn;
	private String leaf_node;
	
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getEnd_yn() {
		return end_yn;
	}
	public void setEnd_yn(String end_yn) {
		this.end_yn = end_yn;
	}
	
	@Override
	public int compare(EasyUiJsonTreeVO o1, EasyUiJsonTreeVO o2) {
		// TODO Auto-generated method stub
		
		String fruitName1 = o1.getText().toUpperCase();
	    String fruitName2 = o2.getText().toUpperCase();

		
	      return fruitName1.compareTo(fruitName2);

	}
	public String getLeaf_node() {
		return leaf_node;
	}
	public void setLeaf_node(String leaf_node) {
		this.leaf_node = leaf_node;
	}
}
