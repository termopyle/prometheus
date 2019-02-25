package org.kobic.omics.interpro.analysis.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.kobic.omics.interpro.analysis.service.InterproAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import turbo.cache.lite.thrift.service.client.CacheLiteClient;

@Controller
public class InterproAnalysisController {

	private static final Logger logger = LoggerFactory.getLogger(InterproAnalysisController.class);
	
	@Resource(name = "interproAnalysisService")
	private InterproAnalysisService interproAnalysisService;
	
	@RequestMapping(value = "/interproAnalysis", method = RequestMethod.GET)
	public ModelAndView geneGroupSearch(@RequestParam(value="path", required=true) String filePath) {
		logger.info("Welcome home! The client locale is {}.");
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("viewer/interproAnalysis");
		
		mv.addObject("filePath", filePath);
		
		return mv;
	}
	
	@RequestMapping(value = "/getInterproAnalysis", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getInterproAnalysis(HttpServletRequest request) throws Exception {
		
		String filePath = request.getParameter("filePath");
		
		logger.info("filePath : " + filePath);
		
//		Aquca_023_00143.1	84c4fa4110ebf8a539ffb1f60b9edb7e	392	ProSiteProfiles	PS51061	R3H domain profile.	333	392	9.362	T	24-03-2017	IPR001374	R3H domain	GO:0003676
//		Aquca_023_00143.1	84c4fa4110ebf8a539ffb1f60b9edb7e	392	Pfam	PF05633	Protein of unknown function (DUF793)	1	385	3.1E-172	T	24-03-2017	IPR008511	Protein BYPASS-related
		
//		String taxId = request.getParameter("tax_id");
//		String proteinId = request.getParameter("protein_accession_id");
//		String refSeqId = request.getParameter("refseq_assembly_id");
		
		Gson gson = new Gson();
		String line = gson.toJson(this.interproAnalysisService.getProteinDomains(filePath));
		
		logger.info(line);

		return line;
	}
}