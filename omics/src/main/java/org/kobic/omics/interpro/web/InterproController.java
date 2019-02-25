package org.kobic.omics.interpro.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kobic.omics.interpro.com.Utility;
import org.kobic.omics.interpro.service.InterproService;
import org.kobic.omics.interpro.vo.RepresentativeVo;
import org.kobic.omics.viewer.vo.ProteinWithDomainVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class InterproController {
	
	private static final Logger logger = LoggerFactory.getLogger(InterproController.class);
	
	@Resource(name = "interproService")
	private InterproService interproService;
	
//	@RequestMapping(value = "/geneGroupSearch", method = RequestMethod.GET)
//	public String geneGroupSearch(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		
//		return "viewer/geneGroupSearch";
//	}
	
	@RequestMapping(value = "/geneGroupSearch", method = RequestMethod.GET)
	public String geneGroupSearch() {
		logger.info("Welcome home! The client locale is {}.");
		
		return "viewer/geneGroupSearch";
	}
	
	@RequestMapping(value = "/getGeneGroupSearchDesyncResult", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getGeneGroupSearchDesyncResult(HttpServletRequest request) {
		String result=null;
		
		long start1 = System.currentTimeMillis();
//		logger.info( "------------------------GeneGroupSearch start!!!!" );
		
		try{
			String iprIds[] = request.getParameterValues("ipr_ids");
			String iprFamilies[] = request.getParameterValues("ipr_families");
			String withoutIPR[] = request.getParameterValues("ipr_without");
			String iprSubTypes[] = request.getParameterValues("ipr_subtypes");
			String radio = request.getParameter("radio");
			String perfectMatch = request.getParameter("chkPerfectMatch");
			String pagingSize = request.getParameter("pagingSize");
			
//			result = this.getComparedGroupGeneSearch(iprIds, iprFamilies, withoutIPR, iprSubTypes, radio, perfectMatch, pagingSize, null, null, false, null);
			result = this.getGeneGroupSearchDesyncResultInternal(iprIds, iprFamilies, withoutIPR, iprSubTypes, radio, perfectMatch, pagingSize);
		} catch(Exception e){
			logger.error(e.toString());
		}
		
		long end1 = System.currentTimeMillis();
//		logger.info( "------------------------GeneGroupSearch End~~~~ : " + (end1-start1)/1000 + " sec" );
		
		return result;
	}
	
	
	public String getGeneGroupSearchDesyncResultInternal(String[] iprIds, String[] iprFamilies, String[] withoutIPR, String[] iprSubTypes, String radio, String perfectMatch, String pagingSize){
		String result=null;
		
		try{
			result = this.getComparedGroupGeneSearch(iprIds, iprFamilies, withoutIPR, iprSubTypes, radio, perfectMatch, pagingSize, null, null, false, null);
		} catch(Exception e){
			logger.error(e.toString());
		}
	
		return result;
	}
	
	@RequestMapping(value = "/getGeneGroupSearchDesyncResult2", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public void getGeneGroupSearchDesyncResult2(HttpServletRequest request) throws Exception {
		long total_time = 0;
		int cnt = 1000;
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
//		String createFilePath = "C:/Users/KOBIC/Documents/choijh/task/";
		String createFilePath = "/home/kogun82/geneSearch/";
		String createFileName = "geneSearchLog.txt";
		
		fw = new FileWriter(createFilePath + createFileName, true);
		bw = new BufferedWriter(fw);
		
		String[] asd = null;
		
		try {
			for(int i=0; i<cnt; i++) {
				
				String keyword = "";
				
				asd = this.random();
				
				for(int k=0; k<asd.length; k++) {
					if(keyword.equals(""))
						keyword = keyword + asd[k];
					else
						keyword = keyword + "," + asd[k];
				}
				
//				logger.info( "-----------" + i + " : " + keyword + " start");
				bw.write("-----------" + i + " : " + keyword + " start" + "\n");
				bw.flush();
				long start = System.currentTimeMillis();
							
				this.getGeneGroupSearchDesyncResultInternal(asd, new String[]{""}, new String[]{""}, new String[]{"SubType1"}, "ord", "true", "5");
				
				long end = System.currentTimeMillis();
				
				total_time += (end-start);
				
//				logger.info( "----------- : " + (end-start)/1000 + " sec" );
				bw.write("----------- : " + (end-start)/1000 + " sec end" + "\n");
				bw.flush();
			}
			
			long average_time = total_time / cnt;
			
			logger.info( "------------------------GeneGroupSearch End~~~~  " + "total : " + total_time/1000 + " sec" + ", average : " + average_time/1000 + " sec");
			bw.write("------------------------GeneGroupSearch End~~~~  " + "total : " + total_time/1000 + " sec" + ", average : " + average_time/1000 + " sec");
			bw.flush();
			
		} finally {
			// BufferedWriter FileWriter를 닫아준다.
			if(bw != null) try{bw.close();}catch(IOException e){ System.out.println(asd);}
			if(fw != null) try{fw.close();}catch(IOException e){ System.out.println(asd);}
		}
	}
	
	public String[] random() throws Exception {
		Random random = new Random();
		
		//예제 1000개 조합
		 String[][] samplesList = new String[][] {
			 {"IPR002172","IPR002172","IPR002172","IPR002172","IPR002172","IPR000742","IPR023415","IPR002172","IPR002172","IPR009030","IPR002172","IPR023415","IPR000742","IPR009030","IPR013032","IPR018097","IPR001881","IPR000742","IPR001881","IPR000742","IPR000152","IPR013032","IPR011042","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000742","IPR011042","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR009030","IPR000742","IPR002172","IPR002172","IPR002172","IPR002172","IPR002172","IPR023415","IPR002172","IPR002172","IPR002172","IPR002172","IPR002172","IPR023415","IPR002172","IPR002172","IPR002172","IPR002172","IPR002172","IPR023415","IPR002172","IPR002172","IPR002172","IPR002172","IPR002172","IPR002172","IPR002172","IPR023415","IPR002172","IPR002172","IPR002172","IPR002172","IPR009030","IPR023415","IPR000742","IPR000742","IPR013032","IPR011042","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR009030","IPR000742","IPR011042","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033","IPR000033"},
			 {"IPR001715","IPR001715","IPR001715","IPR001715","IPR001715","IPR001715","IPR001715","IPR001715","IPR022613","IPR001715","IPR001715","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048"},
			 {"IPR002126","IPR015919","IPR002126","IPR002126","IPR002126","IPR002126","IPR020894","IPR015919","IPR002126","IPR002126","IPR002126","IPR002126","IPR002126","IPR002126","IPR020894","IPR015919","IPR002126","IPR002126","IPR002126","IPR002126","IPR020894","IPR015919","IPR002126","IPR002126","IPR002126","IPR002126","IPR002126","IPR020894","IPR015919","IPR002126","IPR002126","IPR002126","IPR002126","IPR002126","IPR015919","IPR002126","IPR002126","IPR002126","IPR002126","IPR002126","IPR002126","IPR020894","IPR015919","IPR002126","IPR002126","IPR002126","IPR002126","IPR020894","IPR015919","IPR002126","IPR002126","IPR002126","IPR002126","IPR020894","IPR015919","IPR002126","IPR002126","IPR002126","IPR000742","IPR000742","IPR002049","IPR013032","IPR013032","IPR001881","IPR000742","IPR000742","IPR000742","IPR013032","IPR013032","IPR000742","IPR000742","IPR001881","IPR008985","IPR013320","IPR001791","IPR001791","IPR001791","IPR001881","IPR000742","IPR000742","IPR000742","IPR000152","IPR013320","IPR013032","IPR013032","IPR008985","IPR001791","IPR001791","IPR001791","IPR000742","IPR001881","IPR000742","IPR000152","IPR013032","IPR013032","IPR000742","IPR000742","IPR001881","IPR013032","IPR002049","IPR002049"},
			 {"IPR000742","IPR000742","IPR013032","IPR013032","IPR000742","IPR000742","IPR013032","IPR001881","IPR000742","IPR001881","IPR018097","IPR000742","IPR000152","IPR017878","IPR017878","IPR017878","IPR017878","IPR000742","IPR001881","IPR017878","IPR000742","IPR000152","IPR013032","IPR001881","IPR000742","IPR001881","IPR009030","IPR000742","IPR000152","IPR013032","IPR018097","IPR001881","IPR001881","IPR000742","IPR000742","IPR001881","IPR018097","IPR001881","IPR000742","IPR000152","IPR013032","IPR001881","IPR000742","IPR000742","IPR001881","IPR001881","IPR009030","IPR000742","IPR000152","IPR013032","IPR018097","IPR000742","IPR001881","IPR001881","IPR000742","IPR000152","IPR013032","IPR000742","IPR018097","IPR001881","IPR001881","IPR000742","IPR009030","IPR000152","IPR013032","IPR018097","IPR001881","IPR001881","IPR000742","IPR000742","IPR001881","IPR001881","IPR018097","IPR000742","IPR000152","IPR013032","IPR018097","IPR001881","IPR001881","IPR000742","IPR000152","IPR017878","IPR017878","IPR017878","IPR017878","IPR018097","IPR000742","IPR001881","IPR001881","IPR000742","IPR000152","IPR013032","IPR000742","IPR018097","IPR001881","IPR000742","IPR000152","IPR017878","IPR017878"},
			 {"IPR001715","IPR001715","IPR001715","IPR001715","IPR001715","IPR001715","IPR001715","IPR001715","IPR022613","IPR001715","IPR001715","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048","IPR000048","IPR000048","IPR027417","IPR000048","IPR000048","IPR000048"},
			 {"IPR003599","IPR013783","IPR007110","IPR013098","IPR013783","IPR003599","IPR003598","IPR007110","IPR013098","IPR013783","IPR003599","IPR003598","IPR013783","IPR007110","IPR013098","IPR003599","IPR003598","IPR013783","IPR013098","IPR007110","IPR003599","IPR003598","IPR007110","IPR013098","IPR013783","IPR003599","IPR003598","IPR003596","IPR007110","IPR013098","IPR003599","IPR003598","IPR013783","IPR003596","IPR007110","IPR013098","IPR003599","IPR003598","IPR013783","IPR013783","IPR007110","IPR013098","IPR003599","IPR003598","IPR013783","IPR007110","IPR013098","IPR003599","IPR003598","IPR003596","IPR007110","IPR013098","IPR013783","IPR003599","IPR003598","IPR013783","IPR007110","IPR013098","IPR003599","IPR003598","IPR013783","IPR007110","IPR013098","IPR003599","IPR003598","IPR013783","IPR013098","IPR007110","IPR003599","IPR003598","IPR013783","IPR007110","IPR013098","IPR003599","IPR003598","IPR003596","IPR007110","IPR013098","IPR013783","IPR003599","IPR003598","IPR013098","IPR007110","IPR003599","IPR003598","IPR013783","IPR003596","IPR007110","IPR013098","IPR003599","IPR003598","IPR013783","IPR007110","IPR013783","IPR013098","IPR003599","IPR003598","IPR013783","IPR013098","IPR007110"},
			 {"IPR000742","IPR000742","IPR013032","IPR000742","IPR000742","IPR013032","IPR017878","IPR017878","IPR017878","IPR017878","IPR018097","IPR001881","IPR000742","IPR001881","IPR000742","IPR000152","IPR013032","IPR017878","IPR017878","IPR017878","IPR017878","IPR000742","IPR018097","IPR009030","IPR001881","IPR000742","IPR000152","IPR013032","IPR000742","IPR018097","IPR001881","IPR001881","IPR000742","IPR000152","IPR013032","IPR018097","IPR001881","IPR001881","IPR000742","IPR018097","IPR001881","IPR001881","IPR000742","IPR009030","IPR000742","IPR001881","IPR018097","IPR001881","IPR000742","IPR000152","IPR013032","IPR018097","IPR000742","IPR001881","IPR001881","IPR000742","IPR000152","IPR013032","IPR000742","IPR018097","IPR001881","IPR001881","IPR000742","IPR009030","IPR000152","IPR013032","IPR000742","IPR001881","IPR018097","IPR001881","IPR000742","IPR000152","IPR013032","IPR000742","IPR001881","IPR001881","IPR018097","IPR000742","IPR000152","IPR013032","IPR000742","IPR018097","IPR001881","IPR001881","IPR000742","IPR000152","IPR013032","IPR018097","IPR001881","IPR000742","IPR000152","IPR017878","IPR017878","IPR017878","IPR017878","IPR018097","IPR001881","IPR000742","IPR000152","IPR018097"},
			 {"IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630","IPR010630"},
			 {"IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR013783","IPR003961","IPR003961","IPR003961","IPR013783","IPR003961","IPR003961","IPR013783","IPR003961","IPR003961","IPR003961","IPR013783","IPR003961","IPR003961","IPR013783","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR013783","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR013783","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR013783","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR013783","IPR003961","IPR013783","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR013783","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR013783","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR013783","IPR003961","IPR013783","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961","IPR013783","IPR003961","IPR003961","IPR013783","IPR003961","IPR003961","IPR003961","IPR013783","IPR003961","IPR003961","IPR003961","IPR003961","IPR003961"},
			 {"IPR000742","IPR001881","IPR000742","IPR000742","IPR000742","IPR013032","IPR013032","IPR000742","IPR000742","IPR001881","IPR013032","IPR013032","IPR013032","IPR000742","IPR018097","IPR001881","IPR000742","IPR000742","IPR000152","IPR013032","IPR013032","IPR018097","IPR000742","IPR001881","IPR000742","IPR000742","IPR000152","IPR013032","IPR000742","IPR018097","IPR001881","IPR000742","IPR000742","IPR000152","IPR013032","IPR013032","IPR018097","IPR000742","IPR001881","IPR000742","IPR000152","IPR000742","IPR000742","IPR001881","IPR000742","IPR000152","IPR013032","IPR013032","IPR000742","IPR001881","IPR000742","IPR000742","IPR013032","IPR013032","IPR000742","IPR018097","IPR001881","IPR000742","IPR000742","IPR000152","IPR000742","IPR000742","IPR008985","IPR013032","IPR013032","IPR013320","IPR001791","IPR001791","IPR001791","IPR000742","IPR001881","IPR000742","IPR000742","IPR000152","IPR008985","IPR013032","IPR013032","IPR013320","IPR001791","IPR001791","IPR001791","IPR000742","IPR001881","IPR000742","IPR000742","IPR013320","IPR013032","IPR008985","IPR001791","IPR001791","IPR001791","IPR000742","IPR000742","IPR000742","IPR001881","IPR000152","IPR013032","IPR013032","IPR001881"}
		 };

//		 String[] sampleIPR = new String[2];
		 List<String> sampleIPR = new ArrayList<String>();
		 
		 int rowIdx = (int)(Math.random() * (samplesList.length - 1));
		 int columnIdx = (int)(Math.random() * 10);
		 
		 for(int i=0; i<columnIdx; i++) {
			 String sample = samplesList[rowIdx][i];
			 sampleIPR.add( sample );
		 }
		 
		 String[] ret = new String[ sampleIPR.size() ];
		
		 sampleIPR.toArray( ret );
		
		return ret;
	}

	@RequestMapping(value = "/getPagedGeneGroupSearchDesyncResult", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getPagedGeneGroupSearchDesyncResult(HttpServletRequest request) {
		String result=null;
		
//		logger.info( "Error Checking....getPagedGeneGroupSearchDesyncResult start!!!!" );
		
		try{
			String iprIds[] = request.getParameterValues("ipr_ids");
			String iprFamilies[] = request.getParameterValues("ipr_families");
			String withoutIPR[] = request.getParameterValues("ipr_without");
			String iprSubTypes[] = request.getParameterValues("ipr_subtypes");
			String radio = request.getParameter("radio");
			String perfectMatch = request.getParameter("chkPerfectMatch");
			String pagingSize = request.getParameter("pagingSize");
			String pagingIndex = request.getParameter("pagingIndex");
			String baseSubType = request.getParameter("baseSubType");
			String kingdom = Utility.emptyToNull( request.getParameter("kingdom") );
		
			result= this.getComparedGroupGeneSearch(iprIds, iprFamilies, withoutIPR, iprSubTypes, radio, perfectMatch, pagingSize, pagingIndex, baseSubType, true, kingdom);
		} catch(Exception e){
			logger.error(e.toString());
		}
		return result;
	}
	
	@RequestMapping(value = "/getInterproDomainInfo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getInterproDomainTypeInfo(HttpServletRequest request) throws Exception {
		Gson gson = new Gson();

		String iprIds[] = request.getParameterValues("ipr_ids");
		String iprFamilies[] = request.getParameterValues("ipr_families");
		String withoutIPR[] = request.getParameterValues("ipr_without");
		String iprSubTypes[] = request.getParameterValues("ipr_subtypes");
		
		Map<String, String> ipr_idsMap = new LinkedHashMap<String, String>();
		Map<String, String> ipr_familiesMap = new LinkedHashMap<String, String>();
		Map<String, String> withoutIPRMap = new LinkedHashMap<String, String>();

		for(int i=0; i<iprSubTypes.length; i++)	ipr_idsMap.put( iprSubTypes[i], iprIds[i].trim().replace(" ", "") );
		for(int i=0; i<iprSubTypes.length; i++)	ipr_familiesMap.put( iprSubTypes[i], Utility.emptyToNull( iprFamilies[i].replace(" ", "") ) );
		for(int i=0; i<iprSubTypes.length; i++)	withoutIPRMap.put( iprSubTypes[i], Utility.emptyToNull( withoutIPR[i].replace(" ", "" ) ) );
		
		return gson.toJson( this.interproService.getInterProDomainInfo(ipr_idsMap, ipr_familiesMap, withoutIPRMap ) );
	}

	private String getComparedGroupGeneSearch(String[] iprIds, String[] iprFamilies, String[] withoutIPR, String[] iprSubTypes, String radio, String perfectMatch, String pagingSize, String pagingIndex, String baseSubtype, boolean isPaged, String kingdom) throws Exception{
		Gson gson = new Gson();

		Map<String, String> ipr_idsMap = new LinkedHashMap<String, String>();
		Map<String, String> ipr_familiesMap = new LinkedHashMap<String, String>();
		Map<String, String> withoutIPRMap = new LinkedHashMap<String, String>();

		for(int i=0; i<iprSubTypes.length; i++)	ipr_idsMap.put( iprSubTypes[i], iprIds[i].trim().replace(" ", "") );
		for(int i=0; i<iprSubTypes.length; i++)	ipr_familiesMap.put( iprSubTypes[i], Utility.emptyToNull( iprFamilies[i].replace(" ", "") ) );
		for(int i=0; i<iprSubTypes.length; i++)	withoutIPRMap.put( iprSubTypes[i], Utility.emptyToNull( withoutIPR[i].replace(" ", "" ) ) );

		String line = null;
		
		if( isPaged )	line = gson.toJson( this.interproService.compareGroupGeneSearch(ipr_idsMap, ipr_familiesMap, withoutIPRMap, radio, perfectMatch, pagingSize, pagingIndex, baseSubtype, kingdom) );
		else			line = gson.toJson( this.interproService.compareGroupGeneSearch(ipr_idsMap, ipr_familiesMap, withoutIPRMap, radio, perfectMatch, pagingSize) );
//		System.out.println("compareGroupGeneSearch pass!!");
//		logger.info( line );
		
		return line;
	}
	
	@RequestMapping(value = "/getSequenceDownload",  method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getSequenceDownload(HttpServletResponse response, HttpServletRequest request) throws Exception{
		
		Gson gson = new Gson();
		
		String species = request.getParameter("species");
		String tax_id = request.getParameter("tax_id");
		String assembly_accession = request.getParameter("assembly_accession");
		String protein_accession_version = request.getParameter("protein_accession_version");		
		String type = request.getParameter("type");
		String kingdom = request.getParameter("kingdom");
		String subtype = request.getParameter("subtype");
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("species", species);
		map.put("tax_id", tax_id);
		map.put("assembly_accession", assembly_accession);
		map.put("protein_accession_version", protein_accession_version);
		map.put("kingdom", kingdom);
		map.put("subtype", subtype);
		
		ServletContext context = request.getSession().getServletContext();
		String appPath = context.getRealPath("");
		
		String filePath = appPath + File.separator + "Data" + File.separator + InterproService.getFileName( map, type );
		
		//버튼 클릭한 protein의 파일 생성
		this.interproService.getSequenceDownload(map, type, filePath);
		
		return gson.toJson( filePath );
	}
	
	@RequestMapping(value = "/downloads", method = RequestMethod.GET)
	public void doDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InterproService.download(request, response);
	}

	@RequestMapping(value = "/getAllSequencesDownloadByIprTerms",  method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getAllSequencesDownloadByIprTerms(HttpServletResponse response, HttpServletRequest request) throws Exception{
		String uid = (String) request.getSession().getAttribute("uid");
		
		Gson gson = new Gson();
		long start0 = System.currentTimeMillis();
		logger.info( "------------------------AllSequencesDownload ["+request.getParameter("downloadRadio")+"]["+request.getParameter("type")+"] start!!!!!");
		
		String type = request.getParameter("type");// peptide 다운로드인지, CDS 다운로드인지 구별
		String subType = request.getParameter("subType");
		String ipr_ids = request.getParameter("ipr_ids");
		String inputboxFamilies = Utility.emptyToNull(request.getParameter("inputboxFamilies"));
		String inputboxWithoutIPR = Utility.emptyToNull(request.getParameter("inputboxWithoutIPR"));
		String radio = request.getParameter("radio");
		String perfectMatch = request.getParameter("chkPerfectMatch");
		String kingdom = Utility.emptyToNull( request.getParameter("kingdom") );
		String downloadRadio = request.getParameter("downloadRadio");// ALL 다운로드인지, Representative 다운로드인지 구별
		
		ServletContext context = request.getSession().getServletContext();
		String appPath = context.getRealPath("");
		
		String filePath = appPath + File.separator + "Data" + File.separator + InterproService.getFileNameFromIprTerms( uid, kingdom, type, subType );
		
		String[] iprTerms = ipr_ids.replace(" ", "").split(",");
		String[] iprFamiliesTerms = inputboxFamilies==null?null:inputboxFamilies.replace(" ", "").split(",");
		String[] withoutIPRTerms = inputboxWithoutIPR==null?null:inputboxWithoutIPR.replace(" ", "").split(",");
		
		Map<String, String> subtypeMap = new LinkedHashMap<String, String>();
		
		List<ProteinWithDomainVo> strList = interproService.allDownload( subType, perfectMatch, iprTerms, iprFamiliesTerms, withoutIPRTerms, radio, kingdom );
		
		for(ProteinWithDomainVo vo : strList) {
			String key = vo.getNid() + "@" + vo.getNcbi_id() + "@" + vo.getProtein_id();
			
			subtypeMap.put( key, vo.getSubtype() );
		}
		
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		List<Map<String, String>> keyList = new ArrayList<Map<String, String>>();
		
		if(downloadRadio.equals("Representative")) {
			for(ProteinWithDomainVo vo : strList) {
				Map<String, Map<String, String>> totalKeyMap = this.interproService.getGeneIDFromRepAll(vo.getNid(), vo.getNcbi_id(), vo.getProtein_id());
				
				for(Iterator<String> iterator = totalKeyMap.keySet().iterator(); iterator.hasNext();) {
					String key = iterator.next();
					
					if(!keyList.contains(key)) {
						Map<String, String> keyMap = totalKeyMap.get(key);
						keyList.add(keyMap);
					}
				}
			}
			
			List<RepresentativeVo> proteinList = this.interproService.getProteinFromRep(keyList);
			
			for(RepresentativeVo vo : proteinList) {
				Map<String, String> map = new HashMap<String, String>();
				
				map.put("species", vo.getOrganism());
				map.put("tax_id", vo.getTaxID());
				map.put("assembly_accession", vo.getRefSeqAssemblyID());
				map.put("protein_accession_version", vo.getProteinID());
				
				list.add(map);
			}
			
		} else {
			for(ProteinWithDomainVo vo : strList) {
				Map<String, String> map = new HashMap<String, String>();
				
				map.put("species", vo.getNm());
				map.put("tax_id", vo.getNid());
				map.put("assembly_accession", vo.getNcbi_id());
				map.put("protein_accession_version", vo.getProtein_id());
				
				list.add(map);
			}
		}
		
		String user_id = (String) request.getSession().getAttribute("uid");
		
//		long start1 = System.currentTimeMillis();
		this.interproService.getAllSequenceDownloadByIprTerms3(user_id, list, type, filePath, downloadRadio, subtypeMap);
//		long end1 = System.currentTimeMillis();
//		logger.info( "this.interproService.getAllSequenceDownloadByIprTerms : " + (end1-start1)/1000 + " sec" );
		
		long end0 = System.currentTimeMillis();
		logger.info( "------------------------AllSequencesDown END~~~~ : " + (end0-start0)/1000 + " sec" );
		
		return gson.toJson( filePath );
	}
}
