package org.kobic.omics.report.vo;

import java.io.Serializable;

public class GenomicGffVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int no;
	private int TaxId;
	private String organism;
	private String RefSeqAssemblyID;
	private String AssemblyName;
	private String LocusVersion;
	private String Source;
	private String Feature;
	private int Start;
	private int End;
	private String Score;
	private String Strand;
	private String Frame;
	private String Attributes;
	private String ProteinID;
	private String GeneName;
	
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
	public String getSource() {
		return Source;
	}
	public void setSource(String Source) {
		this.Source=Source;
	}
	public String getFeature() {
		return Feature;
	}
	public void setFeature(String Feature) {
		this.Feature=Feature;
	}
	public int getStart() {
		return Start;
	}
	public void setStart(int Start) {
		this.Start=Start;
	}
	public int getEnd() {
		return End;
	}
	public void setEnd(int End) {
		this.End=End;
	}
	public String getScore() {
		return Score;
	}
	public void setScore(String Score) {
		this.Score=Score;
	}
	public String getStrand() {
		return Strand;
	}
	public void setStrand(String Strand) {
		this.Strand=Strand;
	}
	public String getFrame() {
		return Frame;
	}
	public void setFrame(String Frame) {
		this.Frame=Frame;
	}
	public String getAttributes() {
		return Attributes;
	}
	public void setAttributes(String Attributes) {
		this.Attributes=Attributes;
	}
	public String getProteinID() {
		return ProteinID;
	}
	public void setProteinID (String ProteinID) {
		this.ProteinID=ProteinID;
	}
	public String getGeneName() {
		return GeneName;
	}
	public void setGeneName (String GeneName) {
		this.GeneName=GeneName;
	}
}
