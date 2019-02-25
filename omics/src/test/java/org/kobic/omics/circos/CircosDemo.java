package org.kobic.omics.circos;

import org.junit.Test;
import org.kobic.omics.circos.service.CircosService;
import org.kobic.omics.circos.vo.SGVVo;
import org.kobic.omics.engine.constants.Constants;

public class CircosDemo {
	
	@Test
	public void test(){
		CircosService service = new CircosService();
		service.getEntrezRcord("9606");
	}
	
	public void demo(){
		
		System.out.println(Constants.REFSEQ_FORMAT);
		
		CircosService service = new CircosService();
		SGVVo sgv = service.full("/Users/kogun82/Downloads", "3702", Constants.REFSEQ_FORMAT);
		System.out.println(sgv.getSgv());
	}
	
}