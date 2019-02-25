package org.kobic.omics.viewer.vo;

import java.io.Serializable;

public class MultilocVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String cytoplasmic;
	private String mitochondrial;
	private String peroxisomal;
	private String nuclear;
	private String er;
	private String plasma_membrane;
	private String golgi_apparatus;
	private String lysosomal;
	private String vacuolar;
	private String chloroplast;
	private String extracellular;
	private String refseq_assembly_id;
	private String protein_accession_id;
	private String tax_id;
	
	public String getCytoplasmic(){
		return cytoplasmic;
	}
	
	public void setCytoplasmic(String cytoplasmic){
		this.cytoplasmic = cytoplasmic;
	}

	public String getMitochondrial() {
		return mitochondrial;
	}

	public void setMitochondrial(String mitochondrial) {
		this.mitochondrial = mitochondrial;
	}

	public String getPeroxisomal() {
		return peroxisomal;
	}

	public void setPeroxisomal(String peroxisomal) {
		this.peroxisomal = peroxisomal;
	}

	public String getEr() {
		return er;
	}

	public void setEr(String er) {
		this.er = er;
	}

	public String getNuclear() {
		return nuclear;
	}

	public void setNuclear(String nuclear) {
		this.nuclear = nuclear;
	}

	public String getGolgi_apparatus() {
		return golgi_apparatus;
	}

	public void setGolgi_apparatus(String golgi_apparatus) {
		this.golgi_apparatus = golgi_apparatus;
	}

	public String getPlasma_membrane() {
		return plasma_membrane;
	}

	public void setPlasma_membrane(String plasma_membrane) {
		this.plasma_membrane = plasma_membrane;
	}

	public String getChloroplast() {
		return chloroplast;
	}

	public void setChloroplast(String chloroplast) {
		this.chloroplast = chloroplast;
	}

	public String getVacuolar() {
		return vacuolar;
	}

	public void setVacuolar(String vacuolar) {
		this.vacuolar = vacuolar;
	}

	public String getLysosomal() {
		return lysosomal;
	}

	public void setLysosomal(String lysosomal) {
		this.lysosomal = lysosomal;
	}

	public String getExtracellular() {
		return extracellular;
	}

	public void setExtracellular(String extracellular) {
		this.extracellular = extracellular;
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

	public String getTax_id() {
		return tax_id;
	}

	public void setTax_id(String tax_id) {
		this.tax_id = tax_id;
	}

}
