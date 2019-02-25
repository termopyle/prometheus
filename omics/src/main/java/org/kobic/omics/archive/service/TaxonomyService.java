package org.kobic.omics.archive.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.kobic.omics.archive.mapper.TaxonomyMapper;
import org.kobic.omics.archive.vo.LineageVo;
import org.kobic.omics.archive.vo.NcbiNameVo;
import org.kobic.omics.archive.vo.TaxonomyVo;
import org.kobic.omics.report.vo.AseemblyFilePathVo;
import org.springframework.stereotype.Service;

@Service(value = "taxonomyService")
public class TaxonomyService {
	
	@Resource(name = "taxonomyMapper")
	private TaxonomyMapper taxonomyMapper;
	
	public List<TaxonomyVo> getTaxonNodes(String id){
		
		List<TaxonomyVo> NCBITaxonomyData = this.taxonomyMapper.getTaxonNodes(id);
		
		return NCBITaxonomyData;
	}
	
	public List<LineageVo> getQueryResultAsTaxonNode(String query){
		
		List<LineageVo> NCBITaxonomyQuery = this.taxonomyMapper.getQueryResultAsTaxonNode(query);
		return NCBITaxonomyQuery;
	}
	
	public TaxonomyVo getTaxonomyInformation(String tax_id) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("tax_id", tax_id);
		return this.taxonomyMapper.getTaxonomyInformation( paramMap );
	}

	public List<TaxonomyVo> getFullLineagePathFrom( String tax_id ) {
		List<TaxonomyVo> lineage = new ArrayList<TaxonomyVo>();
		List<TaxonomyVo> reverse_lineage = new ArrayList<TaxonomyVo>();
		while( true ) {
			TaxonomyVo vo = this.getTaxonomyInformation(tax_id);
			if( Integer.valueOf( tax_id ) == vo.getParent_tax_id() )	break;
			lineage.add( vo );

			tax_id = Integer.toString( vo.getParent_tax_id() );
		}
		for(int i=0; i<lineage.size(); i++){
			reverse_lineage.add(lineage.get(lineage.size()-1-i));
		}
		
		return reverse_lineage;
	}
	
	public NcbiNameVo getAssemblyId(String speciesName){
		return taxonomyMapper.getAssemblyId(speciesName);
	}
}