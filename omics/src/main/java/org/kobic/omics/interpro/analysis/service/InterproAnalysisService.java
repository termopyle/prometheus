package org.kobic.omics.interpro.analysis.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.kobic.omics.interpro.analysis.mapper.InterproAnalysisMapper;
import org.kobic.omics.interpro.vo.IprDomainTypeVo;
import org.kobic.omics.viewer.vo.DomainVo;
import org.kobic.omics.viewer.vo.ProteinWithDomainVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import turbo.cache.lite.thrift.service.client.CacheLiteClient;

@Service(value = "interproAnalysisService")
public class InterproAnalysisService {

	private static final Logger logger = LoggerFactory.getLogger(InterproAnalysisService.class);

	@Resource(name = "interproAnalysisMapper")
	private InterproAnalysisMapper interproAnalysisMapper;
	
	static class CompareSeqDesc implements Comparator<DomainVo> {
		// TODO Auto-generated method stub
		 @Override
	        public int compare(DomainVo arg0, DomainVo arg1) {
				return arg0.getStart() < arg1.getStart() ? -1 : arg0.getStart() > arg1.getStart() ? 1:0;
		 }
	}
	
	static class CompareDomainWeightDesc implements Comparator<DomainVo> {
		// TODO Auto-generated method stub
		 @Override
	        public int compare(DomainVo arg0, DomainVo arg1) {
				return arg0.getTypeWeight() < arg1.getTypeWeight() ? -1 : arg0.getTypeWeight() > arg1.getTypeWeight() ? 1:0;
		 }
	}

	public ProteinWithDomainVo getProteinDomains(String path) throws Exception {
//	public ProteinWithDomainVo getProteinDomains( String taxId, String refSeqId, String proteinId ) {	
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("tax_id", taxId);
//		map.put("refseq_assembly_id", refSeqId);
//		map.put("protein_accession_id", proteinId);
//		
//		List<DomainVo> domainList = this.interproAnalysisMapper.searchInterproDomains( map );
		
		ProteinWithDomainVo proteinVo = null;
		
		try{
		
			CacheLiteClient client = new CacheLiteClient();
			List<String> lines = client.read(path);
			
			List<DomainVo> domainList = new ArrayList<DomainVo>();
			
			for(String s : lines){
				logger.info( "lines : " + s );
				
				DomainVo vo = new DomainVo();
				
				String[] line = s.split("\t");
				
				vo.setProtein_accession_version(line[0]);
				vo.setProtein_length(line[2]);
				vo.setDb(line[3]);
				vo.setDb_id(line[4]);
				vo.setDb_desc(line[5]);
				vo.setStart(Long.parseLong(line[6]));
				vo.setEnd(Long.parseLong(line[7]));
				vo.setEvalue(line[8]);
				
				if(line.length > 11)
					vo.setIpr_id(line[11]);
				else
					vo.setIpr_id("");
				
				if(line.length > 12)
					vo.setIpr_desc(line[12]);
				else
					vo.setIpr_id("");
				
				if(line.length == 14)
					vo.setGo(line[13]);
				else
					vo.setGo("");
				
				domainList.add(vo);
			}
			
			if(!domainList.isEmpty()) {
				Collections.sort(domainList, new CompareSeqDesc());
				
				List<String> iprArray = new LinkedList<String>();
				for(DomainVo vo : domainList) {
					if(!iprArray.contains(vo.getIpr_id()))
						iprArray.add(vo.getIpr_id());
				}
				
				Map<String, List<String>> paramMap = new LinkedHashMap<String, List<String>>();
				paramMap.put( "domainList", iprArray );
				List<IprDomainTypeVo> iprDomainTypeList = this.interproAnalysisMapper.getIprDomainInfo( paramMap );
				
				for(DomainVo vo : domainList) {
					for(IprDomainTypeVo iprDomainTypeVo : iprDomainTypeList) {
						if(vo.getIpr_id() == null)
							continue;
						else if(vo.getIpr_id().equals(iprDomainTypeVo.getId()))
							vo.setType(iprDomainTypeVo.getType());
					}
				}
				
				for(int i=0; i<domainList.size(); i++) {
					if(domainList.get(i).getType() == null) {
						domainList.get(i).setTypeWeight(8);
						continue;
					}
					else if(domainList.get(i).getType().equals("Family"))
						domainList.get(i).setTypeWeight(1);
					else if(domainList.get(i).getType().equals("Domain"))
						domainList.get(i).setTypeWeight(2);
					else if(domainList.get(i).getType().equals("Active_site"))
						domainList.get(i).setTypeWeight(3);
					else if(domainList.get(i).getType().equals("Binding_site"))
						domainList.get(i).setTypeWeight(4);
					else if(domainList.get(i).getType().equals("Conserved_site"))
						domainList.get(i).setTypeWeight(5);
					else if(domainList.get(i).getType().equals("PTM"))
						domainList.get(i).setTypeWeight(6);
					else if(domainList.get(i).getType().equals("Repeat"))
						domainList.get(i).setTypeWeight(7);
				}
				
				Collections.sort(domainList, new CompareDomainWeightDesc());
			}

			proteinVo = new ProteinWithDomainVo( domainList );
		} catch(Exception e) {
			e.printStackTrace();
		} 
		logger.debug("get protein domains");

		// TODO Auto-generated method stub
		return proteinVo;
	}
}