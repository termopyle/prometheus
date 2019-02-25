package org.kobic.omics.viewer.vo;

public class ParalogVO extends CommonOrthologParalogVO{
	private String paralog_id;
	private String paralog_tax_id;

	public String getParalog_id() {
		return paralog_id;
	}
	public void setParalog_id(String paralog_id) {
		this.paralog_id = paralog_id;
	}
	public String getParalog_tax_id() {
		return paralog_tax_id;
	}
	public void setParalog_tax_id(String paralog_tax_id) {
		this.paralog_tax_id = paralog_tax_id;
	}
}
