package org.kobic.omics.circos.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.kobic.omics.circos.service.CircosService;
import org.kobic.omics.circos.vo.SGVVo;
import org.kobic.omics.common.OmicsConstants;
import org.kobic.omics.report.service.ReportService;
import org.kobic.omics.report.vo.AseemblyFilePathVo;
import org.kobic.omics.report.vo.AssemblyReportVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class CircosController {

	private static final Logger logger = LoggerFactory.getLogger(CircosController.class);
	
	@Resource(name = "reportService")
	private ReportService reportService;
	
	@Resource(name = "circosService")
	private CircosService circosService;  

	@RequestMapping(value = "/circos", method = RequestMethod.GET)
	public ModelAndView circos(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "taxon_id", required = true) String taxId,
			@RequestParam(value = "species_name", required = false) String speciesName,
			@RequestParam(value = "pep", required = false) String pep,
			@RequestParam(value = "cds", required = false) String cds,
			@RequestParam(value = "genome", required = false) String genome,
			@RequestParam(value = "db", required = true) String db) {

		logger.info("[circos] taxon_id:" + taxId + ", species_name:" + speciesName + ", db:" + db);
		
		ModelAndView mv = new ModelAndView();

		if(db == null || db == ""){
			db = "ref_seq";
		}
		
		boolean exist = isGFFExist(db, taxId);
		logger.info("gff data exist : " + exist);
		
		String db_path=getDBpath(db, taxId);
		
		List<AseemblyFilePathVo> assemblyResult = null;
		AseemblyFilePathVo result = null;
		String geneNames = null;
		
		if(db.equals("ref_seq")){
			
			result = reportService.getRefseqAssemblyReport(taxId);

			mv.addObject("db", OmicsConstants.REF_SEQ_DB);
		}else if(db.equals("ensembl")){
			
			result = reportService.getEnsemblAssemblyReport(taxId);

			mv.addObject("db", OmicsConstants.ENSEMBL_DB);
		}else{
			
			result = reportService.getRawdataAssemblyReport(taxId);

			mv.addObject("db", OmicsConstants.PLANT_DB);
		}
		
		mv.addObject("assemblyResult", result);
		mv.addObject("pep", result.getPepFile());
		mv.addObject("cds", result.getCdsFile());
		mv.addObject("genome", result.getGenomeFile());
		
		if(exist){

			assemblyResult = reportService.getAssemblyResult(taxId);
			geneNames = circosService.getGene(taxId, db);
			
			mv.addObject("gene_names", geneNames);
			mv.addObject("db_path", db_path);
		}
		
		mv.setViewName("circos/circos_viewer");
		mv.addObject("taxon_id", taxId);
		mv.addObject("species_name", speciesName.trim().replace("_", " "));
		mv.addObject("entrez", circosService.getEntrezRcord(taxId));
		
		System.out.println(geneNames);
		
		return mv;
	}
	
	@RequestMapping(value = "/circos_full", method = RequestMethod.GET)
	public void full(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="taxonomy_id", required=true) String taxonomyId,
			@RequestParam(value="db", required=true) String db){
		
		System.out.println("getCircosData 호출 taxonomyId:" + taxonomyId + ", db:" + db);
		
		ServletContext servletContext = request.getSession().getServletContext();
		
		String context_path = servletContext.getRealPath(File.separator);
		
		System.out.println("context_path"+ context_path);
		
		JSONObject jso = new JSONObject();
		
		//파일이 있는지 없는지 검사
		boolean exist = isGFFExist(db, taxonomyId);
		logger.info("gff data exist : " + exist);
		
		if(exist){
			SGVVo sgv = circosService.full(context_path, taxonomyId, db);			
			jso.put("sgv", sgv.getSgv());
	        jso.put("length", sgv.getLength());
		}else{
			jso.put("sgv","0");
		}
        
        response.setContentType("application/json");
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}	

		out.print(jso.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "/circos_zoom", method = {RequestMethod.GET, RequestMethod.POST})
	public void zoom(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value="taxonomy_id", required=true) String taxonomyId,
			@RequestParam(value="db", required=true) String db,
			@RequestParam(value="type", required=true) String type,
			@RequestParam(value="zoom", required=true) double zoom,
			@RequestParam(value="query", required=true) String query) {
		
		logger.info("[circos_zoom]");
		
		PrintWriter pw = null;
		response.setContentType("application/json");
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		
		System.out.println(taxonomyId + ":" + db + ":" + type + ":" + zoom + ":" + query);
		
		try {
			pw = new PrintWriter(response.getWriter());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ServletContext servletContext = request.getSession().getServletContext();
		String context_path = servletContext.getRealPath(File.separator) + "/";
		
		if(type.equals("position")){
			String sgv = circosService.zoom(context_path, zoom, Integer.parseInt(query), taxonomyId, db).getSgv();
			pw.println(sgv);
		}else{
			pw.println(circosService.zoom(context_path, zoom, query, taxonomyId, db).getSgv());
		}
		
		pw.flush();
		pw.close();
		
	}
	
	@RequestMapping(value = "/auto_circos_zoom", method = {RequestMethod.GET, RequestMethod.POST})
	public void auto_zoom(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value="taxonomy_id", required=true) String taxonomyId,
			@RequestParam(value="db", required=true) String db,
			@RequestParam(value="auto_position", required=false) String ap,
			@RequestParam(value="total_length", required=false) String tl,
			@RequestParam(value="strand", required=true) String strand) {
		
		logger.info("[auto_circos_zoom]");
		
		System.out.println(ap + " : " + tl);
		
		PrintWriter pw = null;
		response.setContentType("application/json");
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		
		try {
			pw = new PrintWriter(response.getWriter());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ServletContext servletContext = request.getSession().getServletContext();
		String context_path = servletContext.getRealPath(File.separator) + "/";
		
		int total_length = 0;
		int auto_position = 0;
		
		if(ap.length() == 0){
			auto_position = 0;
		}else{
			auto_position = Integer.parseInt(ap);
		}
		
		if(tl.length() == 0){
			total_length = 0;
		}else{
			total_length = Integer.parseInt(tl);
		}
		
		
		if(strand.equals("nex")){
			
			if(total_length == 0){
				auto_position = auto_position + 10000;
			}else{
				auto_position = auto_position + (int)(total_length / 100);
			}

			if(auto_position > total_length){
				auto_position = total_length;
			}
			
		}else{
			
			if(total_length == 0){
				auto_position = auto_position - 10000;
			}else{
				auto_position = auto_position - (int)(total_length / 100);
			}

			if(auto_position < 0){
				auto_position = 0;
			}
			
		}
		
		System.out.println(taxonomyId + ":" + db + ":" + auto_position);
		
		SGVVo sgv = circosService.zoom(context_path, 300.0d, auto_position, taxonomyId, db);
		
		JSONObject jso = new JSONObject();
		jso.put("sgv", sgv.getSgv());
        jso.put("auto_position", auto_position);
		jso.put("total_length", sgv.getLength());
		
		pw.println(jso.toString());
		pw.flush();
		pw.close();
		
	}
	
	@RequestMapping(value = "/getChangeAssemblyReport",  method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getChangeAssemblyReport(HttpServletResponse response, HttpServletRequest request){
		
		Gson gson = new Gson();
		
		String taxId = request.getParameter("taxon_id");
		String refSeqAssemblyID = request.getParameter("refSeqAssemblyID");
				
		String line = gson.toJson(this.reportService.getChangeAssemblyReport(taxId, refSeqAssemblyID));

		return line;
	}
	
	private boolean isGFFExist(String db, String taxonomy){
		
		String db_path = null;
		
		String prefix = "/BiO/gff_data";
		
		if(db.equals(OmicsConstants.REF_SEQ_DB)){
			db_path =  prefix + "/ref_seq/" + taxonomy + ".circus";
		}else if(db.equals(OmicsConstants.PLANT_DB)){
			db_path = prefix + "/raw_data/" + taxonomy + ".circus";
		}else if(db.equals(OmicsConstants.ENSEMBL_DB)){
			db_path = prefix + "/ensemble/" + taxonomy + ".circus";
		}

		System.out.println(db_path);
		
		File file = new File(db_path);

		if(!file.exists()){
			logger.info("gff 파일이 존재하지 않습니다.");
			return false;
		}else{
			return file.exists();
		}
	}
	
	private String getDBpath(String db, String taxonomy){
		String db_path = null;
		
		if(db.equals(OmicsConstants.REF_SEQ_DB)){
			db_path =  "gff_data/ref_seq/" + taxonomy + ".circus";
		}else if(db.equals(OmicsConstants.PLANT_DB)){
			db_path = "gff_data/raw_data/" + taxonomy + ".circus";
		}else if(db.equals(OmicsConstants.ENSEMBL_DB)){
			db_path = "gff_data/ensemble/" + taxonomy + ".circus";
		}
		System.out.println("db_path : " + db_path);
		
		return db_path;
	}
	
}