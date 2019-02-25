package org.kobic.omics.circos.vo;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import ca.ualberta.stothard.cgview.Cgview;
import ca.ualberta.stothard.cgview.FeatureSlot;

public class CircosVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@SerializedName(value = "gffVOList")
	private List<GFFVo> gffVOList;

	@SerializedName(value = "legendMap")
	private Map<String, Color> legendMap;

	@SerializedName(value = "featureSlotMap")
	private Map<String, FeatureSlot> featureSlotMap;

	@SerializedName(value = "circos")
	private Cgview circos;

	@SerializedName(value = "length")
	private int length;

	@SerializedName(value = "chromosomeMap")
	private Map<String, ChrVo> chromosomeMap;

	public List<GFFVo> getGffVOList() {
		return gffVOList;
	}

	public void setGffVOList(List<GFFVo> gffVOList) {
		this.gffVOList = gffVOList;
	}

	public Map<String, Color> getLegendMap() {
		return legendMap;
	}

	public void setLegendMap(Map<String, Color> legendMap) {
		this.legendMap = legendMap;
	}

	public Map<String, FeatureSlot> getFeatureSlotMap() {
		return featureSlotMap;
	}

	public void setFeatureSlotMap(Map<String, FeatureSlot> featureSlotMap) {
		this.featureSlotMap = featureSlotMap;
	}

	public Cgview getCircos() {
		return circos;
	}

	public void setCircos(Cgview circos) {
		this.circos = circos;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Map<String, ChrVo> getChromosomeMap() {
		return chromosomeMap;
	}

	public void setChromosomeMap(Map<String, ChrVo> chromosomeMap) {
		this.chromosomeMap = chromosomeMap;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
