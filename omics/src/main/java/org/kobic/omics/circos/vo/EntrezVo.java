package org.kobic.omics.circos.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class EntrezVo {

	private String databaseNm;
	private String databaseLinks;
	private String linksCount;

	public String getDatabaseNm() {
		return databaseNm;
	}

	public void setDatabaseNm(String databaseNm) {
		this.databaseNm = databaseNm;
	}

	public String getDatabaseLinks() {
		return databaseLinks;
	}

	public void setDatabaseLinks(String databaseLinks) {
		this.databaseLinks = databaseLinks;
	}

	public String getLinksCount() {
		return linksCount;
	}

	public void setLinksCount(String linksCount) {
		this.linksCount = linksCount;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
