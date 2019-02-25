package org.kobic.omics.common.vo;

import java.io.Serializable;
import java.util.Collection;

public class EasyUiStaticJsonTreeVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String state;
	private String text;
	
	private Collection<EasyUiStaticJsonTreeVO> children;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Collection<EasyUiStaticJsonTreeVO> getChildren() {
		return children;
	}
	public void setChildren(Collection<EasyUiStaticJsonTreeVO> collection) {
		this.children = collection;
	}
	
	
}
