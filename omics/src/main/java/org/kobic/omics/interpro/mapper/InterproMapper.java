package org.kobic.omics.interpro.mapper;

import java.util.List;
import java.util.Map;

import org.kobic.omics.interpro.vo.GeneSearchVo;
import org.kobic.omics.interpro.vo.InterproVo;
import org.kobic.omics.interpro.vo.IprDomainTypeVo;
import org.kobic.omics.interpro.vo.RepresentativeVo;
import org.kobic.omics.interpro.vo.SequenceDownloadVo;
import org.kobic.omics.interpro.vo.TaxIdWithDbVo;
import org.kobic.omics.viewer.vo.DomainVo;
import org.kobic.omics.viewer.vo.ProteinWithDomainVo;
import org.kobic.omics.viewer.vo.TaxIdVo;
import org.springframework.stereotype.Repository;

@Repository(value = "interproMapper")
public interface InterproMapper {
	public List<InterproVo> getInterpro( Map<String, String> map );
	public List<ProteinWithDomainVo> compareInterpro(List<InterproVo> interpro);
	public List<DomainVo> getDomain(Map<String, String> map);
	
	public String getProteinKingdomByTaxId(String tax_id);
	public String getRnafnaKingdomByTaxId(String tax_id);
	public String getSequence(Map<String, String> map);
	public List<SequenceDownloadVo> getSequence3(Map<String, List<Map<String, String>>> map);
	public String getCDS(Map<String, String> map);
	public List<SequenceDownloadVo> getCDS3(Map<String, List<Map<String, String>>> map);
	
	public String getLength(Map<String, String> map);
	public String getSpecies(Map<String, String> map);
	
	public List<GeneSearchVo> getGeneSearch(Map<String, String> map);
	public List<GeneSearchVo> getUniqueProtein(Map<String, String> map) throws Exception;
	public List<GeneSearchVo> getDomainByProtein(Map<String, String> map);
	
	
	public Integer getHitProteinTotalCounts( Map<String, String> map );
	public List<GeneSearchVo> getGeneSearchModified( Map<String, String> paramMap );
	
	
	public List<TaxIdWithDbVo> getKingdomTablesByTaxId( Map<String, String> map );
	
	
	public List<GeneSearchVo> getDomainByProteinWithList(Map<String, List<GeneSearchVo>> map) throws Exception;
	
	public List<TaxIdVo> getKingdomInfoByTaxId( Map<String, List<String>> map );
	public List<TaxIdVo> getKingdomInfoByTaxIdCount( Map<String, List<String>> map );
	public List<TaxIdVo> getKingdomByTaxIdTotalCount();	
	
	public List<IprDomainTypeVo> getIprDomainInfo( Map<String, List<String>> map );
	
	public List<String> getGeneIDFromRepAll( Map<String, String> map );
	public RepresentativeVo getProteinFromRep( Map<String, String> map );
	
	
	public List<TaxIdWithDbVo> getKingdomAndDB( String tax_id );
}
