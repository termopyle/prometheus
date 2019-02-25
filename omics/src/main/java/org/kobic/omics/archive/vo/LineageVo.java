package org.kobic.omics.archive.vo;

import java.io.Serializable;

public class LineageVo extends TaxonomyVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String lineage;

	public LineageVo() {
		this(-1, -1, null, null);
	}

	public LineageVo(int tax_id, int parent_tax_id, String rank,
			String name_txt) {
		super(tax_id, parent_tax_id, rank, name_txt);
		this.lineage = null;
	}

	public String getLineage() {
		return lineage;
	}

	public void setLineage(String lineage) {
		this.lineage = lineage;
	}
}
