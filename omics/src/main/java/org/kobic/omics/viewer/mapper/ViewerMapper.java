package org.kobic.omics.viewer.mapper;

import java.util.List;
import java.util.Map;

import org.kobic.omics.interpro.vo.TaxIdWithDbVo;
import org.kobic.omics.viewer.vo.DomainVo;
import org.kobic.omics.viewer.vo.FeatureVo;
import org.kobic.omics.viewer.vo.MultilocVo;
import org.kobic.omics.viewer.vo.OrthoMclVo;
import org.kobic.omics.viewer.vo.OrthologVO;
import org.kobic.omics.viewer.vo.ParalogVO;
import org.kobic.omics.viewer.vo.ProteinWithDomainVo;
import org.kobic.omics.viewer.vo.TargetpVo;
import org.kobic.omics.viewer.vo.TmHmmVo;
import org.springframework.stereotype.Repository;

@Repository(value = "viewerMapper")
public interface ViewerMapper {
	
	public ProteinWithDomainVo getProteinDomains();
	public List<FeatureVo> getGeneStructureByRefSeq( Map<String, String> paramMap );
	public List<FeatureVo> getGeneStructure( Map<String, String> paramMap );
	public FeatureVo getGeneInfoByRefSeq( Map<String, String> paramMap );
	public FeatureVo getGeneInfo( Map<String, String> paramMap );
	public TmHmmVo getTMHMM(Map<String, String> map);
	public String getKingdomByTaxId( String taxId );
	public List<TaxIdWithDbVo> getKingdomTablesByTaxId( Map<String, String> map );

	public String getSequence(Map<String, String> map);
	public String getCDS(Map<String, String> map);
	
	MultilocVo getMultiloc(Map<String, String> map);
	TargetpVo getTargetp(Map<String, String> map);
	
	public List<FeatureVo> findGeneFromProtein( Map<String, String> paramMap );
	
	public List<OrthologVO> findOrtholog( Map<String, String> map );
	public List<ParalogVO> findParalog( Map<String, String> map );
	
	public List<OrthoMclVo> getOrthoMCLResults( Map<String, String> map );
	
	public List<DomainVo> searchInterproDomains( Map<String, String> map );
	
	public List<TaxIdWithDbVo> getKingdomAndDB( String tax_id );
	public String getProteinID(Map<String, String> map);
}
