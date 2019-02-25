package org.kobic.omics.viewer.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kobic.omics.archive.service.TaxonomyService;
import org.kobic.omics.archive.vo.TaxonomyVo;
import org.kobic.omics.interpro.service.InterproService;
import org.kobic.omics.viewer.service.ViewerService;
import org.kobic.omics.viewer.vo.FeatureVo;
import org.kobic.omics.viewer.vo.MultilocVo;
import org.kobic.omics.viewer.vo.OrthoMclVo;
import org.kobic.omics.viewer.vo.OrthologVO;
import org.kobic.omics.viewer.vo.ParalogVO;
import org.kobic.omics.viewer.vo.TargetpVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.google.gson.Gson;

@Controller
public class ViewerController {
	private static final Logger logger = LoggerFactory.getLogger(ViewerController.class);
	
	@Resource(name = "viewerService")
	private ViewerService viewerService;
	
	@Resource(name = "taxonomyService")
	private TaxonomyService taxonomyService;

/*	@RequestMapping(value = "/viewer", method = RequestMethod.GET)
	public String viewer(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "viewer/viewer";
	}*/
	
	@RequestMapping(value = "/viewer")
	public ModelAndView viewer(HttpServletRequest request, HttpServletResponse response) {
		
//		String tax_id="500485";
//		String refseq_assembly_id = "GCF_000226395.1";
//		String protein_accession_id = "XP_002557265.1";

//		String tax_id="10029";
//		String refseq_assembly_id = "GCF_000223135.1 (species-representative latest)";
//		String protein_accession_id = "XP_007649008.1";
		
//		String tax_id="2711";
//		String refseq_assembly_id = "GCF_000317415.1";
//		String protein_accession_id = "XP_006464290.1";
				
		String tax_id				= request.getParameter("tax_id");
		String refseq_assembly_id	= request.getParameter("refseq_assembly_id");
		String protein_accession_id = request.getParameter("protein_accession_id");
		
		return this.common(tax_id, refseq_assembly_id, protein_accession_id);
	}


	@RequestMapping(value = "/get_domains", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getProteinDomains(HttpServletRequest request) {
		Gson gson = new Gson();

		String taxId = request.getParameter("tax_id");
		String proteinId = request.getParameter("protein_accession_id");
		String refSeqId = request.getParameter("refseq_assembly_id");
		
		String line = gson.toJson( this.viewerService.getProteinDomains( taxId, refSeqId, proteinId ) );

		logger.info( line );

		return line;
	}

	@RequestMapping(value = "/get_tmhmm", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getTMHMM(HttpServletRequest request) {
		Gson gson = new Gson();

		String tax_id = request.getParameter("tax_id");
		String protein_accession_id = request.getParameter("protein_accession_id");
		String refseq_assembly_id = request.getParameter("refseq_assembly_id");
		
		logger.info( tax_id + " " + protein_accession_id + " " + refseq_assembly_id);

		String line = gson.toJson( this.viewerService.getTMHMM( tax_id, protein_accession_id, refseq_assembly_id ) );

		logger.info( line );

		return line;
	}

	@RequestMapping(value = "/get_gene_structure", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getGeneStructure(HttpServletRequest request) {
		Gson gson = new Gson();

//		String taxId = "1257118";
//		String refSeqId = "GCF_000313135.1";
//		String proteinId = "XP_004333665.1";
		String taxId = request.getParameter("tax_id");
		String proteinId = request.getParameter("protein_accession_id");
		String refSeqId = request.getParameter("refseq_assembly_id");

		String line = gson.toJson( this.viewerService.getGeneStructure( taxId, proteinId, refSeqId ) );

		logger.info( line );

		return line;
	}
	
	@RequestMapping(value = "/getSequenceDownloadByProteinID",  method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getSequenceDownload(HttpServletResponse response, HttpServletRequest request) throws Exception{
		
		Gson gson = new Gson();
		
		String species = request.getParameter("organism");
		String taxId = request.getParameter("tax_id");
		String refSeqId = request.getParameter("refseq_assembly_id");
		String proteinId = request.getParameter("protein_accession_id");
		String type = request.getParameter("type");
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("species", species);
		map.put("tax_id", taxId);
		map.put("refseq_assembly_id", refSeqId);
		map.put("protein_accession_id", proteinId);
		
		ServletContext context = request.getSession().getServletContext();
		String appPath = context.getRealPath("");
		
		String filePath = appPath + File.separator + "Data" + File.separator + this.viewerService.getFileNameFromProteinID( map, type );
		
		//버튼 클릭한 protein의 파일 생성
		this.viewerService.getSequenceDownload(map, type, filePath);
		
		return gson.toJson( filePath );
	}
	
	// get방식과 post방식으로 모두 받을 수 있는 메소드
	@RequestMapping(value = "/getViewerFromCircos", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String getViewerFromCircos(HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		String proteinKey = request.getParameter("proteinKey");
		
		String taxId = proteinKey.split(";")[0];
		String refSeqId =  proteinKey.split(";")[1];
		String geneName = proteinKey.split(";")[2];
		
		String proteinId = this.viewerService.getProteinID(taxId, refSeqId, geneName);
		
		redirectAttributes.addFlashAttribute("tax_id", taxId);
		redirectAttributes.addFlashAttribute("protein_accession_id", proteinId);
		redirectAttributes.addFlashAttribute("refseq_assembly_id", refSeqId);

	    return "redirect:/geneviewer";
	}
	
	// get방식과 post방식으로 모두 받을 수 있는 메소드
	@RequestMapping(value = "/getViewerFromInterpro", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String getViewerFromInterpro(HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		String taxId = request.getParameter("tax_id");
		String proteinId =  request.getParameter("protein_accession_id");
		String refSeqId = request.getParameter("refseq_assembly_id");
		
		redirectAttributes.addFlashAttribute("tax_id", taxId);
		redirectAttributes.addFlashAttribute("protein_accession_id", proteinId);
		redirectAttributes.addFlashAttribute("refseq_assembly_id", refSeqId);

	    return "redirect:/geneviewer";
	}
	
	@RequestMapping(value = "/geneviewer")
	public ModelAndView geneViewer(HttpServletRequest request, HttpServletResponse response) {
		Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
		
		String tax_id				= (String) map.get("tax_id");
		String refseq_assembly_id	= (String) map.get("refseq_assembly_id");
		String protein_accession_id = (String) map.get("protein_accession_id");
		
		return this.common(tax_id, refseq_assembly_id, protein_accession_id);
	}
	
	private ModelAndView common( String tax_id, String refseq_assembly_id, String protein_accession_id) {
		
		MultilocVo multiloc_result = viewerService.getMultilocResult( refseq_assembly_id, protein_accession_id, tax_id );
		TargetpVo targetp_result = viewerService.getTargetpResult(tax_id, refseq_assembly_id, protein_accession_id);
		
//		// To find orthologs
//		List<OrthologVO> orthologs = this.viewerService.findOrtholog(tax_id, protein_accession_id);
//		
//		// To find paralogs
//		List<ParalogVO> paralogs = this.viewerService.findParalog(tax_id, protein_accession_id);
//
//		// To find genes of ortholog
//		Map<String, List<FeatureVo>> orthologMap = new HashMap<String, List<FeatureVo>>();
//		
//		// To find genes of paralog
//		Map<String, List<FeatureVo>> paralogMap = new HashMap<String, List<FeatureVo>>();
//		for( OrthologVO vo : orthologs ) {
//			List<FeatureVo> ortholosGenes = this.viewerService.findGeneFromProtein( vo.getOrtholog_tax_id(), vo.getOrtholog_id() );
//			orthologMap.put(vo.getOrtholog_id(), ortholosGenes);
//		}
//		
//		for(ParalogVO vo : paralogs) {
//			List<FeatureVo> paralogGenes = this.viewerService.findGeneFromProtein( vo.getParalog_tax_id(), vo.getParalog_id() );
//			paralogMap.put(vo.getParalog_id(), paralogGenes);
//		}
		
		List<OrthoMclVo> orthoMclResult = this.viewerService.getOrthoMCLResults(tax_id, refseq_assembly_id, protein_accession_id);
		
		ModelAndView mv = new ModelAndView();
		
		List<TaxonomyVo> taxonomyVoList = this.taxonomyService.getFullLineagePathFrom( tax_id );
		
		mv.setViewName("viewer/viewer");
		mv.addObject("multiloc_result", multiloc_result);
		mv.addObject("targetp_result", targetp_result);
		mv.addObject("tax_info", taxonomyVoList);
		mv.addObject("tax_id", tax_id);
		mv.addObject("refseq_assembly_id", refseq_assembly_id);
		mv.addObject("protein_accession_id", protein_accession_id);
		mv.addObject("ortho_mcl", orthoMclResult);
//		mv.addObject("orthologs", orthologMap);
//		mv.addObject("paralogs", paralogMap);
		
		return mv;
	}
}
