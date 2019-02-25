package org.kobic.omics.jbrowse.service;

import java.util.List;

import javax.annotation.Resource;

import org.kobic.omics.jbrowse.mapper.JBrowseMapper;
import org.kobic.omics.jbrowse.vo.JBrowseVo;
import org.springframework.stereotype.Service;

@Service(value = "jbrowseService")
public class JBrowseService {

	@Resource(name = "jbrowseMapper")
	private JBrowseMapper jbrowseMapper;
	
	public List<JBrowseVo> getJBrowseResult(String taxId){
		return jbrowseMapper.getJBrowseUrl(taxId); 
	}
}
