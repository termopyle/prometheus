package org.kobic.omics.jbrowse.dao;

import java.util.List;

import org.kobic.omics.jbrowse.vo.JBrowseVo;

public interface JBrowseDao {

	List<JBrowseVo> getJBrowseUrl(String taxId);
}
