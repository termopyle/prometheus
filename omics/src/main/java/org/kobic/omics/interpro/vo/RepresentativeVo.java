package org.kobic.omics.interpro.vo;

public class RepresentativeVo {
	public String taxID;
	public String organism;
	public String refSeqAssemblyID;
	public String assemblyName;
	public String geneID;
	public String proteinID;
	
	
	public String getTaxID() {
		return taxID;
	}
	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}
	public String getOrganism() {
		return organism;
	}
	public void setOrganism(String organism) {
		this.organism = organism;
	}
	public String getRefSeqAssemblyID() {
		return refSeqAssemblyID;
	}
	public void setRefSeqAssemblyID(String refSeqAssemblyID) {
		this.refSeqAssemblyID = refSeqAssemblyID;
	}
	public String getAssemblyName() {
		return assemblyName;
	}
	public void setAssemblyName(String assemblyName) {
		this.assemblyName = assemblyName;
	}
	public String getGeneID() {
		return geneID;
	}
	public void setGeneID(String geneID) {
		this.geneID = geneID;
	}
	public String getProteinID() {
		return proteinID;
	}
	public void setProteinID(String proteinID) {
		this.proteinID = proteinID;
	}
}
