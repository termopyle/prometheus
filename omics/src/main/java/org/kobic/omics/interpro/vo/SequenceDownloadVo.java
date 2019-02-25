package org.kobic.omics.interpro.vo;

public class SequenceDownloadVo {
	
	private String taxID;
	private String refSeqAssemblyID;
	private String proteinID;
	private String organism;
	private String sequence;
	private String kingdom;
	
	public String getTaxID() {
		return taxID;
	}
	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}
	public String getRefSeqAssemblyID() {
		return refSeqAssemblyID;
	}
	public void setRefSeqAssemblyID(String refSeqAssemblyID) {
		this.refSeqAssemblyID = refSeqAssemblyID;
	}
	public String getProteinID() {
		return proteinID;
	}
	public void setProteinID(String proteinID) {
		this.proteinID = proteinID;
	}
	public String getOrganism() {
		return organism;
	}
	public void setOrganism(String organism) {
		this.organism = organism;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getKingdom() {
		return kingdom;
	}
	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}
	
}
