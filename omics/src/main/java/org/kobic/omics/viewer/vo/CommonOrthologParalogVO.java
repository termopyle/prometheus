package org.kobic.omics.viewer.vo;

public class CommonOrthologParalogVO {
	private String no;
	private String subject_species;
	private String subject_id;
	private String subject_tax_id;

	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getSubject_species() {
		return subject_species;
	}
	public void setSubject_species(String subject_species) {
		this.subject_species = subject_species;
	}
	public String getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}
	public String getSubject_tax_id() {
		return subject_tax_id;
	}
	public void setSubject_tax_id(String subject_tax_id) {
		this.subject_tax_id = subject_tax_id;
	}
}
