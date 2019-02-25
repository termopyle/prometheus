package org.kobic.omics.archive.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaxonomyVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int tax_id;
	private int parent_tax_id;
	private String rank;
	private String name_txt;
	private String size;
	private String end_yn;
	private String leaf_node;
	private String lineage;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public TaxonomyVo() {
		this(0, 0, null, null);
	}

	public TaxonomyVo(int tax_id, int parent_tax_id, String rank,
			String name_txt) {
		this.tax_id = tax_id;
		this.parent_tax_id = parent_tax_id;
		this.rank = rank;
		this.name_txt = name_txt;
	}

	public int getTax_id() {
		return tax_id;
	}

	public void setTax_id(int tax_id) {
		this.tax_id = tax_id;
	}

	public int getParent_tax_id() {
		return parent_tax_id;
	}

	public void setParent_tax_id(int parent_tax_id) {
		this.parent_tax_id = parent_tax_id;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getName_txt() {
		return name_txt;
	}

	public void setName_txt(String name_txt) {
		this.name_txt = name_txt;
	}

	public static List<TaxonomyVo> getTaxonLineage(String line) {
		String[] div = line.split("\\&", -1);

		List<TaxonomyVo> lineage = new ArrayList<TaxonomyVo>();
		for (String part : div) {
			String[] items = part.split("\\|");
			if (items.length == 4) {
				lineage.add(new TaxonomyVo(Integer.parseInt(items[0]), Integer
						.parseInt(items[1]), items[2], items[3]));
			}
		}

		return lineage;
	}

	public String getEnd_yn() {
		return end_yn;
	}

	public void setEnd_yn(String end_yn) {
		this.end_yn = end_yn;
	}

	public String getLeaf_node() {
		return leaf_node;
	}

	public void setLeaf_node(String leaf_node) {
		this.leaf_node = leaf_node;
	}

	public String getLineage() {
		return lineage;
	}

	public void setLineage(String lineage) {
		this.lineage = lineage;
	}
}
