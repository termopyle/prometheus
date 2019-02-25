package org.kobic.omics.viewer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.kobic.omics.viewer.mapper.ViewerMapper;
import org.kobic.omics.viewer.vo.FeatureVo;
import org.kobic.omics.viewer.vo.GeneStructureVo;
import org.kobic.omics.viewer.vo.OrthologVO;
import org.kobic.omics.viewer.vo.ParalogVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:config/spring/context/context-datasource.xml", 
        "classpath:config/spring/context/context-mybatis.xml" })
public class ViewerTest {
	@Autowired private ViewerMapper viewerMapper;

//	@Test
	public void test() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("keyword", "XP_004333665.1");
		paramMap.put("tax_id", "1257118");
		paramMap.put("tableName", "NEW_pri_genomicgff_PROTOZOA");

		GeneStructureVo geneStr = (GeneStructureVo) this.viewerMapper.getGeneInfo(paramMap);

		List<FeatureVo> featureList = viewerMapper.getGeneStructure(paramMap);
		
		for(FeatureVo vo : featureList) {
			System.out.println( vo.getFeature() );
		}
	}
	
	@Test
	public void testOrtholog() {
		Map<String, String> paramMap = new HashMap<String, String>();
		String tax_id="10029";
		String protein_id = "XP_007649008.1";

		paramMap.put("protein_id", protein_id);
		paramMap.put("tax_id", tax_id);
		paramMap.put("tableName", this.viewerMapper.getKingdomByTaxId(tax_id) );
		
		List<OrthologVO> orthologList = this.viewerMapper.findOrtholog( paramMap );
		List<ParalogVO> paralogList = this.viewerMapper.findParalog( paramMap );

		for(OrthologVO vo : orthologList) {
			String ortholog_protein_id = vo.getOrtholog_id();
			String ortholog_tax_id = vo.getOrtholog_tax_id();
			
			String tableName = this.viewerMapper.getKingdomByTaxId(ortholog_tax_id);
			if( tableName != null ) {
				Map<String, String> newParamMap = new HashMap<String, String>();
				newParamMap.put("protein_id", ortholog_protein_id);
				newParamMap.put("tax_id", ortholog_tax_id);
				newParamMap.put("tableName", tableName );
	
				List<FeatureVo> featureList = this.viewerMapper.findGeneFromProtein(newParamMap);
				for(FeatureVo gene : featureList) {
					System.out.println( "(" + ortholog_tax_id + ") " + ortholog_protein_id + " Orthologo gene " + gene.getFeature() );
				}
			}
		}
		
//		List<FeatureVo> featureList = this.viewerMapper.findGeneFromProtein(paramMap);
//		
//		for(FeatureVo vo : featureList) {
//			System.out.println( vo.getFeature() );
//		}
	}
}
