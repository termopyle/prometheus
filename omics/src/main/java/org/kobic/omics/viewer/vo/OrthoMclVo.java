package org.kobic.omics.viewer.vo;

public class OrthoMclVo {
	private String no;
	private String src_organism;
	private String src_assembly_id;
	private String src_protein_id;
	private String tar_organism;
	private String tar_assembly_id;
	private String tar_protein_id;
	private String desc;

	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getSrc_organism() {
		return src_organism;
	}
	public void setSrc_organism(String src_organism) {
		this.src_organism = src_organism;
	}
	public String getSrc_assembly_id() {
		return src_assembly_id;
	}
	public void setSrc_assembly_id(String src_assembly_id) {
		this.src_assembly_id = src_assembly_id;
	}
	public String getSrc_protein_id() {
		return src_protein_id;
	}
	public void setSrc_protein_id(String src_protein_id) {
		this.src_protein_id = src_protein_id;
	}
	public String getTar_organism() {
		return tar_organism;
	}
	public void setTar_organism(String tar_organism) {
		this.tar_organism = tar_organism;
	}
	public String getTar_assembly_id() {
		return tar_assembly_id;
	}
	public void setTar_assembly_id(String tar_assembly_id) {
		this.tar_assembly_id = tar_assembly_id;
	}
	public String getTar_protein_id() {
		return tar_protein_id;
	}
	public void setTar_protein_id(String tar_protein_id) {
		this.tar_protein_id = tar_protein_id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}