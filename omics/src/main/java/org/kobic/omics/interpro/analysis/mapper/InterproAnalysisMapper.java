package org.kobic.omics.interpro.analysis.mapper;

import java.util.List;
import java.util.Map;

import org.kobic.omics.interpro.vo.IprDomainTypeVo;
import org.kobic.omics.viewer.vo.DomainVo;
import org.springframework.stereotype.Repository;

@Repository(value = "interproAnalysisMapper")
public interface InterproAnalysisMapper {
	public List<DomainVo> searchInterproDomains( Map<String, String> map );
	public List<IprDomainTypeVo> getIprDomainInfo( Map<String, List<String>> map );
}