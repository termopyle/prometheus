package org.kobic.omics.common.vo;

import java.io.Serializable;
import java.util.Collection;

public class EasyUiRootJsonTreeVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String text;
	private Collection<EasyUiJsonTreeVO> children;
	
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
	public Collection<EasyUiJsonTreeVO> getChildren() {
		return children;
	}
	public void setChildren(Collection<EasyUiJsonTreeVO> collection) {
		this.children = collection;
	}
	
	
}
