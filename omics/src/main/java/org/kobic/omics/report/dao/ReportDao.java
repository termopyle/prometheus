package org.kobic.omics.report.dao;

import java.util.List;
import java.util.Map;

import org.kobic.omics.report.vo.AseemblyFilePathVo;
import org.kobic.omics.report.vo.AssemblyReportVo;

public interface ReportDao {

	public List<AseemblyFilePathVo> getAssemblyReport(String newTaxId);
	public AssemblyReportVo getChangeAssemblyReport(Map<String, String> map);
}
