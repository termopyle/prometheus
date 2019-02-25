package org.kobic.omics.report.vo;

import java.io.Serializable;

public class AssemblyReportVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int no;
	private int taxId;
	private String organism;
	private String refSeqAssemblyID;
	private String assemblyName;
	private String description;
	private String submitter;
	private String depositDate;
	private String bioSample;
	private String assemblyType;
	private String releaseType;
	private String assemblyLevel;
	private String genomeRepresentation;
	private String genBankAssemblyID;
	private String identical;
	private String genomicfnaLocation;
	private String rMoutLocation;
	private String paralogLocation;
	private String fileSize;
	private String geneCount;
	private String jbrowse;
	private String source;
	private String releaseVersion;
	private String releaseDate;
	private int NewTaxId;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getTaxId() {
		return taxId;
	}
	public void setTaxId(int taxId) {
		this.taxId = taxId;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSubmitter() {
		return submitter;
	}
	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}
	public String getDepositDate() {
		return depositDate;
	}
	public void setDepositDate(String depositDate) {
		this.depositDate = depositDate;
	}
	public String getBioSample() {
		return bioSample;
	}
	public void setBioSample(String bioSample) {
		this.bioSample = bioSample;
	}
	public String getAssemblyType() {
		return assemblyType;
	}
	public void setAssemblyType(String assemblyType) {
		this.assemblyType = assemblyType;
	}
	public String getReleaseType() {
		return releaseType;
	}
	public void setReleaseType(String releaseType) {
		this.releaseType = releaseType;
	}
	public String getAssemblyLevel() {
		return assemblyLevel;
	}
	public void setAssemblyLevel(String assemblyLevel) {
		this.assemblyLevel = assemblyLevel;
	}
	public String getGenomeRepresentation() {
		return genomeRepresentation;
	}
	public void setGenomeRepresentation(String genomeRepresentation) {
		this.genomeRepresentation = genomeRepresentation;
	}
	public String getGenBankAssemblyID() {
		return genBankAssemblyID;
	}
	public void setGenBankAssemblyID(String genBankAssemblyID) {
		this.genBankAssemblyID = genBankAssemblyID;
	}
	public String getIdentical() {
		return identical;
	}
	public void setIdentical(String identical) {
		this.identical = identical;
	}
	public String getGenomicfnaLocation() {
		return genomicfnaLocation;
	}
	public void setGenomicfnaLocation(String genomicfnaLocation) {
		this.genomicfnaLocation = genomicfnaLocation;
	}
	public String getrMoutLocation() {
		return rMoutLocation;
	}
	public void setrMoutLocation(String rMoutLocation) {
		this.rMoutLocation = rMoutLocation;
	}
	public String getParalogLocation() {
		return paralogLocation;
	}
	public void setParalogLocation(String paralogLocation) {
		this.paralogLocation = paralogLocation;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getGeneCount() {
		return geneCount;
	}
	public void setGeneCount(String geneCount) {
		this.geneCount = geneCount;
	}
	public String getJbrowse() {
		return jbrowse;
	}
	public void setJbrowse(String jbrowse) {
		this.jbrowse = jbrowse;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getReleaseVersion() {
		return releaseVersion;
	}
	public void setReleaseVersion(String releaseVersion) {
		this.releaseVersion = releaseVersion;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getNewTaxId() {
		return NewTaxId;
	}
	public void setNewTaxId(int newTaxId) {
		NewTaxId = newTaxId;
	}
	
	
}
