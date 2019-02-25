package org.kobic.omics.archive.vo;

import java.io.Serializable;

public class NcbiNameVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tax_id;
	private String name_txt;


	public String getTax_id() {
		return tax_id;
	}

	public void setTax_id(String tax_id) {
		this.tax_id = tax_id;
	}

	public String getName_txt() {
		return name_txt;
	}

	public void setName_txt(String name_txt) {
		this.name_txt = name_txt;
	}

}
