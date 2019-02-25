package org.kobic.omics.jbrowse.vo;

import java.io.Serializable;

public class JBrowseVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int taxId;
	private String jbrowse;
	private String organism;
	private String refSeqAssemblyID;
	private String genomicfnaLocation;
	
	
	public int getTaxId() {
		return taxId;
	}
	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}
	
	public String getJbrowse() {
		return jbrowse;
	}
	public void setJbrowse(String jbrowse) {
		this.jbrowse = jbrowse;
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
	public String getGenomicfnaLocation() {
		return genomicfnaLocation;
	}
	public void setGenomicfnaLocation(String genomicfnaLocation) {
		this.genomicfnaLocation = genomicfnaLocation;
	}

}
