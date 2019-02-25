package org.kobic.omics.jbrowse.mapper;

import java.util.List;

import org.kobic.omics.jbrowse.vo.JBrowseVo;
import org.kobic.omics.jbrowse.dao.JBrowseDao;
import org.springframework.stereotype.Repository;

@Repository(value = "jbrowseMapper")
public interface JBrowseMapper extends JBrowseDao{
	
	List<JBrowseVo> getJBrowseUrl(String taxId);
}
