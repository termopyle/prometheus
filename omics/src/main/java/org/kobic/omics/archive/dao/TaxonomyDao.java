package org.kobic.omics.archive.dao;

import java.util.List;

import org.kobic.omics.archive.vo.LineageVo;
import org.kobic.omics.archive.vo.NcbiNameVo;
import org.kobic.omics.archive.vo.TaxonomyVo;

public interface TaxonomyDao {
	
	List<TaxonomyVo> getTaxonNodes(int taxonType);
	List<String> getScientificName(String keyword);
	List<LineageVo> getQueryResultAsTaxonNode(String query);
	public NcbiNameVo getAssemblyId(String speciesName);
}