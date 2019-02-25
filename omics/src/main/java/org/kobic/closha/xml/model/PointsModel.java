package org.kobic.closha.xml.model;

import java.io.Serializable;

public class PointsModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private int left;
	private int top;

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

}
