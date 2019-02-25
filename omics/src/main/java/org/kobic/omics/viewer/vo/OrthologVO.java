package org.kobic.omics.viewer.vo;

public class OrthologVO extends CommonOrthologParalogVO {
	private String ortholog_species;
	private String ortholog_id;
	private String ortholog_tax_id;

	public String getOrtholog_species() {
		return ortholog_species;
	}
	public void setOrtholog_species(String ortholog_species) {
		this.ortholog_species = ortholog_species;
	}
	public String getOrtholog_id() {
		return ortholog_id;
	}
	public void setOrtholog_id(String ortholog_id) {
		this.ortholog_id = ortholog_id;
	}
	public String getOrtholog_tax_id() {
		return ortholog_tax_id;
	}
	public void setOrtholog_tax_id(String ortholog_tax_id) {
		this.ortholog_tax_id = ortholog_tax_id;
	}
}
