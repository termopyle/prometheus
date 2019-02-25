package org.kobic.omics.report.mapper;

import java.util.List;
import java.util.Map;

import org.kobic.omics.report.dao.ReportDao;
import org.kobic.omics.report.vo.AseemblyFilePathVo;
import org.kobic.omics.report.vo.AssemblyReportVo;
import org.springframework.stereotype.Repository;

@Repository(value = "reportMapper")
public interface ReportMapper extends ReportDao{
	
	public List<AseemblyFilePathVo> getAssemblyReport(String newTaxId);
	public List<AseemblyFilePathVo> getAssemblyReportExist(String newTaxId);
	public AssemblyReportVo getChangeAssemblyReport(Map<String, String> map);
	public AseemblyFilePathVo getRefseqAssemblyReport(String newTaxId);
	public AseemblyFilePathVo getEnsemblAssemblyReport(String newTaxId);
	public AseemblyFilePathVo getRawdataAssemblyReport(String newTaxId);
}
