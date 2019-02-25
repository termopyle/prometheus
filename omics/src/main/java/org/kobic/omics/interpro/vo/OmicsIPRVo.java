package org.kobic.omics.interpro.vo;

public class OmicsIPRVo {

	private String taxonomyID;
	private String assemblyID;
	private String proteinID;
	private String annotationAccession;
	private String domainStart;
	private String domainEnd;
	private String pfam;
	private String orgName;
	private String seqLength;
	
	public OmicsIPRVo() {
		
	}
	
	public OmicsIPRVo( String taxonomyID, String assemblyID, String proteinID, String annotationAccession, String domainStart,
			String domainEnd, String pfam, String orgName, String seqLength) {
		this.taxonomyID=taxonomyID;
		this.assemblyID=assemblyID;
		this.proteinID=proteinID;
		this.annotationAccession=annotationAccession;
		this.domainStart=domainStart;
		this.domainEnd=domainEnd;
		this.pfam=pfam;
		this.orgName=orgName;
		this.seqLength=seqLength;
	}
	
	
	public String getPfam() {
		return pfam;
	}
	public void setPfam(String pfam) {
		this.pfam = pfam;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getSeqLength() {
		return seqLength;
	}
	public void setSeqLength(String seqLength) {
		this.seqLength = seqLength;
	}
	public String getTaxonomyID() {
		return taxonomyID;
	}
	public void setTaxonomyID(String taxonomyID) {
		this.taxonomyID = taxonomyID;
	}
	public String getAssemblyID() {
		return assemblyID;
	}
	public void setAssemblyID(String assemblyID) {
		this.assemblyID = assemblyID;
	}
	public String getProteinID() {
		return proteinID;
	}
	public void setProteinID(String proteinID) {
		this.proteinID = proteinID;
	}
	public String getAnnotationAccession() {
		return annotationAccession;
	}
	public void setAnnotationAccession(String annotationAccession) {
		this.annotationAccession = annotationAccession;
	}
	public String getDomainStart() {
		return domainStart;
	}
	public void setDomainStart(String domainStart) {
		this.domainStart = domainStart;
	}
	public String getDomainEnd() {
		return domainEnd;
	}
	public void setDomainEnd(String domainEnd) {
		this.domainEnd = domainEnd;
	}
}
