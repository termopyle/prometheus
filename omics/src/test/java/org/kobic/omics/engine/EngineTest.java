package org.kobic.omics.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kobic.omics.engine.constants.Constants;
import org.kobic.omics.engine.thrift.client.OmicsThriftClient;
import org.kobic.omics.thrift.service.InterproScanModel;
import org.kobic.omics.thrift.service.InterproScanTransmitModel;

public class EngineTest {
	
	@Test
	public void search(){
		OmicsThriftClient client = new OmicsThriftClient();
		
		client.isAlive();
		
		Map<String, String> query = new HashMap<String, String>();
		query.put("0", "IPR006115");
		query.put("1", "IPR009161");
		query.put("2", "IPR021401");
		query.put("3", "IPR027091");
		
		query.put("0", "500485@GCF_000226395.1@XP_002557265.1");
		
		List<InterproScanTransmitModel> res = client.search(query, Constants.SEARCH_FILE, Constants.IDS_TYPE);
		
		for(InterproScanTransmitModel tm : res){
		
			for(InterproScanModel omics : tm.getData().getData()){
				System.out.println(omics.getProteinID());
			}
		}
		
		client.close();
	}

}
