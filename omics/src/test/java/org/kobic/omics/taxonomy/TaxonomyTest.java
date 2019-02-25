package org.kobic.omics.taxonomy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.kobic.omics.archive.mapper.TaxonomyMapper;
import org.kobic.omics.archive.service.TaxonomyService;
import org.kobic.omics.archive.vo.TaxonomyVo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:config/spring/context/context-datasource.xml", 
        "classpath:config/spring/context/context-mybatis.xml" })
public class TaxonomyTest {
	@Autowired private TaxonomyMapper taxonomyMapper;

	@Test
	public void test() {
		try {
			String tax_id = "7955";
			
			List<TaxonomyVo> lineage = new ArrayList<TaxonomyVo>();
			List<TaxonomyVo> reverse_lineage = new ArrayList<TaxonomyVo>();
			
			while( true ) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("tax_id", tax_id);

				TaxonomyVo vo = this.taxonomyMapper.getTaxonomyInformation( paramMap );

				if( Integer.valueOf( tax_id ) == vo.getParent_tax_id() )	break;
				lineage.add( vo );
	
				tax_id = Integer.toString( vo.getParent_tax_id() );
			}
			
			TaxonomyVo vo = lineage.get(lineage.size()-1);
			System.out.println(vo.getName_txt());

			for(int i=0; i<lineage.size(); i++){
			reverse_lineage.add(lineage.get(lineage.size()-1-i));
			}
			
			for(TaxonomyVo vo1 : reverse_lineage){
				System.out.println(vo1.getTax_id() + " " + vo1.getParent_tax_id() + "  " + vo1.getName_txt());
			}
			
//			for(TaxonomyVo vo : lineage) {
//				System.out.println( vo.getTax_id() + " " + vo.getParent_tax_id() + "  " + vo.getName_txt() );
//			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
