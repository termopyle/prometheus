package org.kobic.omics.report.vo;

import java.io.Serializable;

public class ProteinfaaVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int no;
	private int TaxId;
	private String organism;
	private String RefSeqAssemblyID;
	private String AssemblyName;
	private String LocusVersion;
	private String Definition;
	private String Seq;
	private String ProteinID;

	public int getno() {
		return no;
	}
	public void setno(int no) {
		this.no=no;
	}
	
	public int getTaxId() {
		return TaxId;
	}
	public void setTaxId(int TaxId) {
		this.TaxId=TaxId;
	}
	
	public String getorganism() {
		return organism;
	}
	public void setorganism(String organism) {
		this.organism=organism;
	}
	public String getRefSeqAssemblyID() {
		return RefSeqAssemblyID;
	}
	public void setRefSeqAssemblyID(String RefSeqAssemblyID) {
		this.RefSeqAssemblyID=RefSeqAssemblyID;
	}
	public String getAssemblyName() {
		return AssemblyName;
	}
	public void setAssemblyName(String AssemblyName) {
		this.AssemblyName=AssemblyName;
	}
	public String getLocusVersion() {
		return LocusVersion;
	}
	public void setLocusVersion(String LocusVersion) {
		this.LocusVersion=LocusVersion;
	}
	public String getDefinition() {
		return Definition;
	}
	public void setDefinition(String Definition) {
		this.Definition=Definition;
	}
	public String getSeq() {
		return Seq;
	}
	public void setSeq(String Seq) {
		this.Seq=Seq;
	}
	public String getProteinID() {
		return ProteinID;
	}
	public void setProteinID(String ProteinID) {
		this.ProteinID=ProteinID;
	}
}
