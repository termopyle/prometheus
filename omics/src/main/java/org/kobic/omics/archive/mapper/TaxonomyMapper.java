package org.kobic.omics.archive.mapper;

import java.util.List;
import java.util.Map;

import org.kobic.omics.archive.dao.TaxonomyDao;
import org.kobic.omics.archive.vo.LineageVo;
import org.kobic.omics.archive.vo.NcbiNameVo;
import org.kobic.omics.archive.vo.TaxonomyVo;
import org.springframework.stereotype.Repository;

@Repository(value = "taxonomyMapper")
public interface TaxonomyMapper extends TaxonomyDao{
	
	List<TaxonomyVo> getTaxonNodes(String id);
	List<String> getScientificName(String keyword);
	List<LineageVo> getQueryResultAsTaxonNode(String query);
	
	public TaxonomyVo getTaxonomyInformation(Map<String, String> param);
	public NcbiNameVo getAssemblyId(String speciesName);
}
