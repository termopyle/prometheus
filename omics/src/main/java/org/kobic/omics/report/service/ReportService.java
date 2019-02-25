package org.kobic.omics.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.kobic.omics.report.mapper.ReportMapper;
import org.kobic.omics.report.vo.AseemblyFilePathVo;
import org.kobic.omics.report.vo.AssemblyReportVo;
import org.springframework.stereotype.Service;

@Service(value = "reportService")
public class ReportService {
	
	@Resource(name = "reportMapper")
	private ReportMapper reportMapper;
	
	public List<AseemblyFilePathVo> getAssemblyResult(String newTaxId){
		return reportMapper.getAssemblyReport(newTaxId); 
	}

	public AssemblyReportVo getChangeAssemblyReport(String newTaxId, String refSeqAssemblyID){
		Map<String, String> map = new HashMap<String, String>();
		map.put("newTaxId", newTaxId);
		map.put("refSeqAssemblyID", refSeqAssemblyID);
		
		return reportMapper.getChangeAssemblyReport(map);
	};
	
	public List<AseemblyFilePathVo> getAssemblyReportExist(String newTaxId){
		return reportMapper.getAssemblyReportExist(newTaxId);
	};
	
	public AseemblyFilePathVo getRefseqAssemblyReport(String newTaxId){
		return reportMapper.getRefseqAssemblyReport(newTaxId);
	};
	public AseemblyFilePathVo getEnsemblAssemblyReport(String newTaxId){
		return reportMapper.getEnsemblAssemblyReport(newTaxId);
	};
	public AseemblyFilePathVo getRawdataAssemblyReport(String newTaxId){
		return reportMapper.getRawdataAssemblyReport(newTaxId);
	};
}
