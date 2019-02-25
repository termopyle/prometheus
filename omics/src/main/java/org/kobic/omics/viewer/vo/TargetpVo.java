package org.kobic.omics.viewer.vo;

import java.io.Serializable;

public class TargetpVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String tax_id;
	private String refseq_assembly_id;
	private String protein_accession_id;
	private String len;
	private String ctp;
	private String mtp;
	private String sp;
	private String other;
	private String loc;
	private String rc;
	private String tplen;
	
	
	
	public String getTax_id() {
		return tax_id;
	}
	public void setTax_id(String tax_id) {
		this.tax_id = tax_id;
	}
	public String getRefseq_assembly_id() {
		return refseq_assembly_id;
	}
	public void setRefseq_assembly_id(String refseq_assembly_id) {
		this.refseq_assembly_id = refseq_assembly_id;
	}
	public String getProtein_accession_id() {
		return protein_accession_id;
	}
	public void setProtein_accession_id(String protein_accession_id) {
		this.protein_accession_id = protein_accession_id;
	}
	public String getLen() {
		return len;
	}
	public void setLen(String len) {
		this.len = len;
	}
	public String getCtp() {
		return ctp;
	}
	public void setCtp(String ctp) {
		this.ctp = ctp;
	}
	public String getMtp() {
		return mtp;
	}
	public void setMtp(String mtp) {
		this.mtp = mtp;
	}
	public String getSp() {
		return sp;
	}
	public void setSp(String sp) {
		this.sp = sp;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getRc() {
		return rc;
	}
	public void setRc(String rc) {
		this.rc = rc;
	}
	public String getTplen() {
		return tplen;
	}
	public void setTplen(String tplen) {
		this.tplen = tplen;
	}
	
	
}
