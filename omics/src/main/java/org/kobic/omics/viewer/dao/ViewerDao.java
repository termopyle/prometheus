package org.kobic.omics.viewer.dao;

import java.util.List;
import java.util.Map;

import org.kobic.omics.viewer.vo.FeatureVo;
import org.kobic.omics.viewer.vo.GeneStructureVo;
import org.kobic.omics.viewer.vo.MultilocVo;
import org.kobic.omics.viewer.vo.OrthologVO;
import org.kobic.omics.viewer.vo.ProteinWithDomainVo;
import org.kobic.omics.viewer.vo.TargetpVo;
import org.kobic.omics.viewer.vo.TmHmmVo;

public interface ViewerDao {
	public ProteinWithDomainVo getProteinDomains();
	public GeneStructureVo getGeneStructure();
	public TmHmmVo getTMHMM(Map<String, String> map);
	MultilocVo getMultiloc(Map<String, String> map);
	TargetpVo getTargetp(Map<String, String> map);

	public List<FeatureVo> findGeneFromProtein( Map<String, String> paramMap );	
	public List<OrthologVO> findOrtholog( Map<String, String> map );
	public List<OrthologVO> findParalog( Map<String, String> map );
}