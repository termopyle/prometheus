package org.kobic.omics.circos.vo;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DrawVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int length;
	private List<GFFVo> gff;
	private Map<String, ChrVo> chromosomeMap;
	private Map<String, Color> legendMap;
	private boolean isRange;
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public List<GFFVo> getGff() {
		return gff;
	}
	public void setGff(List<GFFVo> gff) {
		this.gff = gff;
	}
	public Map<String, ChrVo> getChromosomeMap() {
		return chromosomeMap;
	}
	public void setChromosomeMap(Map<String, ChrVo> chromosomeMap) {
		this.chromosomeMap = chromosomeMap;
	}
	public Map<String, Color> getLegendMap() {
		return legendMap;
	}
	public void setLegendMap(Map<String, Color> legendMap) {
		this.legendMap = legendMap;
	}
	public boolean isRange() {
		return isRange;
	}
	public void setRange(boolean isRange) {
		this.isRange = isRange;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
