package org.kobic.omics.archive.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.kobic.omics.archive.service.TaxonomyService;
import org.kobic.omics.archive.vo.TaxonomyVo;
import org.kobic.omics.common.OmicsConstants;
import org.kobic.omics.common.vo.EasyUiJsonTreeVO;
import org.kobic.omics.common.vo.EasyUiRootJsonTreeVO;
import org.kobic.omics.common.vo.EasyUiStaticJsonTreeVO;
import org.kobic.omics.report.service.ReportService;
import org.kobic.omics.report.vo.AseemblyFilePathVo;
import org.kobic.omics.report.vo.AssemblyReportVo;
import org.kobic.omics.archive.vo.LineageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;

@Controller
public class ArchiveController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveController.class);
	
	@Resource(name = "taxonomyService")
	private TaxonomyService taxonomyService;
	
	@Resource(name = "reportService")
	private ReportService reportService;
	
	@RequestMapping(value = "/taxonomy", method = RequestMethod.GET)
	public ModelAndView viewer(ModelMap model, 
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="taxon_id", required=false) String taxId){
		
		ModelAndView result = new ModelAndView();

		result.setViewName("archive/genome_archive");
		result.addObject("taxon_id",taxId);
		
		return result;
	}
	
	@RequestMapping(value = "/getTaxnomyData.do", method = RequestMethod.POST)
	public void getTaxnomyData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="taxon_id", required=false) String taxId,
			@RequestParam(value="species_name", required=false) String speciesName) {
		
		LOGGER.info("getTaxonomyData.do");

		if(taxId == null || taxId == ""){
			LOGGER.info("검색에의한 데이터 호출");
			taxId = taxonomyService.getAssemblyId(speciesName).getTax_id();
		}

		List<AseemblyFilePathVo> assemblyResult = reportService.getAssemblyResult(taxId);
		List<AseemblyFilePathVo> existDB = reportService.getAssemblyReportExist(taxId);
		
        JSONObject jso=new JSONObject();
      
		jso.put("data", assemblyResult);
		jso.put("existDB", existDB);
		
		response.setContentType("application/json");
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(jso.toString());
		out.print(jso.toString());
		out.flush();
		out.close();
		
	}
	
	@RequestMapping(value = "/initData.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public void initData(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "keyword", required = false) String keyword ) {
		
		LOGGER.info("init : keyword : " + keyword);
		
		String type = "1";
		String getData = null;

		if (keyword.length() == 0) {

			List<TaxonomyVo> voList = null;

			List<EasyUiJsonTreeVO> collection = new ArrayList<EasyUiJsonTreeVO>();

			try {
				voList = this.taxonomyService.getTaxonNodes(type);

				for (TaxonomyVo vo : voList) {

					EasyUiJsonTreeVO jsonVo = new EasyUiJsonTreeVO();
					jsonVo.setId(String.valueOf(vo.getTax_id()));
					jsonVo.setText(vo.getName_txt());
					jsonVo.setEnd_yn(vo.getEnd_yn());
					jsonVo.setLeaf_node(vo.getLeaf_node());
					collection.add(jsonVo);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			EasyUiRootJsonTreeVO root = new EasyUiRootJsonTreeVO();
			root.setId(String.valueOf(OmicsConstants.ROOT_ID));
			root.setText("Classification System");
			root.setChildren(collection);
	
			Gson gson = new Gson();

			getData = gson.toJson(root);

		} else {
			
			Multimap<String, String> kingdomMap = ArrayListMultimap.create();
			Multimap<String, String> phylumMap = ArrayListMultimap.create();
			Multimap<String, String> classMap = ArrayListMultimap.create();
			Multimap<String, String> orderMap = ArrayListMultimap.create();
			Multimap<String, String> familyMap = ArrayListMultimap.create();
			Multimap<String, String> genusMap = ArrayListMultimap.create();
			Multimap<String, String> speciesMap = ArrayListMultimap.create();
			
			Map<String, String> kingdomIdMap = new HashMap<String, String>();
			Map<String, String> phylumIdMap = new HashMap<String, String>();
			Map<String, String> classIdMap = new HashMap<String, String>();
			Map<String, String> orderIdMap = new HashMap<String, String>();
			Map<String, String> familyIdMap = new HashMap<String, String>();
			Map<String, String> genusIdMap = new HashMap<String, String>();
			Map<String, String> speciesIdMap = new HashMap<String, String>();

			LOGGER.info("Taxon Tree 데이터 전달 키워드: " + keyword);

			String[] temp1 = null;
			String[] temp2 = null;
			
			List<LineageVo> voKobisfList;
			
			List<String> taxonText = new ArrayList<String>();
		
				voKobisfList = null;

				try {
					voKobisfList = this.taxonomyService.getQueryResultAsTaxonNode(keyword);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (LineageVo vo : voKobisfList) {
					taxonText.add(vo.getLineage());
					System.out.println(vo.getLineage());
				}

			String kingdomTemp = "";
			String kingdomTempId= "";
			
			String phylumTemp = "";
			String phylumTempId = "";
			
			String classTemp = "";
			String classTempId = "";
			
			String orderTemp = "";
			String orderTempId = "";
			
			String familyTemp = "";
			String familyTempId = "";
			
			String genusTemp = "";
			String genusTempId = "";
			
			String speciesTemp = "";
			String speciesTempId = "";

			for(String text : taxonText){
				
				temp1 = null;
				temp2 = null;
				
				
				temp1 = text.split("&");

				if(temp1.length < 7){
					continue;
				}else{
					
					temp2 = temp1[6].split("[|]");
					kingdomTemp = temp2[temp2.length - 1];
					kingdomTempId = temp2[0];
							
					temp2 = temp1[5].split("[|]");
					phylumTemp = temp2[temp2.length - 1];
					phylumTempId = temp2[0];
					
					temp2 = temp1[4].split("[|]");
					classTemp = temp2[temp2.length - 1];
					classTempId = temp2[0];
					
					temp2 = temp1[3].split("[|]");
					orderTemp = temp2[temp2.length - 1];
					orderTempId = temp2[0];
					
					temp2 = temp1[2].split("[|]");
					familyTemp = temp2[temp2.length - 1];
					familyTempId = temp2[0];
					
					temp2 = temp1[1].split("[|]");
					genusTemp = temp2[temp2.length - 1];
					genusTempId = temp2[0];
					
					temp2 = temp1[0].split("[|]");
					speciesTemp = temp2[temp2.length - 1];
					speciesTempId = temp2[0];
					
					if(!kingdomMap.get(String.valueOf(OmicsConstants.ROOT_ID)).contains(kingdomTemp)){
						kingdomMap.put(String.valueOf(OmicsConstants.ROOT_ID), kingdomTemp);
						kingdomIdMap.put(kingdomTemp, kingdomTempId);
					};
					
					if(!phylumMap.get(kingdomTemp).contains(phylumTemp)){
						phylumMap.put(kingdomTemp, phylumTemp);
						phylumIdMap.put(phylumTemp, phylumTempId);
					};
					
					if(!classMap.get(phylumTemp).contains(classTemp)){
						classMap.put(phylumTemp, classTemp);
						classIdMap.put(classTemp, classTempId);
					};
					
					if(!orderMap.get(classTemp).contains(orderTemp)){
						orderMap.put(classTemp, orderTemp);
						orderIdMap.put(orderTemp, orderTempId);
					};
					
					if(!familyMap.get(orderTemp).contains(familyTemp)){
						familyMap.put(orderTemp, familyTemp);
						familyIdMap.put(familyTemp, familyTempId);
					};
					
					if(!genusMap.get(familyTemp).contains(genusTemp)){
						genusMap.put(familyTemp, genusTemp);
						genusIdMap.put(genusTemp, genusTempId);
					};
					
					if(!speciesMap.get(genusTemp).contains(speciesTemp)){
						speciesMap.put(genusTemp, speciesTemp);
						speciesIdMap.put(speciesTemp, speciesTempId);
					};
				}
			}
			
			List<EasyUiStaticJsonTreeVO> rootChild = new ArrayList<EasyUiStaticJsonTreeVO>();
			
			if(kingdomMap.get(String.valueOf(OmicsConstants.ROOT_ID)).size() != 0){
				
				for(String kingdom : kingdomMap.get(String.valueOf(OmicsConstants.ROOT_ID))){
					
					EasyUiStaticJsonTreeVO vo1 = new EasyUiStaticJsonTreeVO();
					vo1.setId(kingdomIdMap.get(kingdom));
					vo1.setState(OmicsConstants.DEFAULT_STATUS);
					vo1.setText(kingdom);
					
					List<EasyUiStaticJsonTreeVO> kingdomChild = new ArrayList<EasyUiStaticJsonTreeVO>();
					
					if(phylumMap.get(kingdom).size() != 0){
						
						for(String phylum : phylumMap.get(kingdom)){
							
							EasyUiStaticJsonTreeVO vo2 = new EasyUiStaticJsonTreeVO();
							vo2.setId(phylumIdMap.get(phylum));
							vo2.setState(OmicsConstants.DEFAULT_STATUS);
							vo2.setText(phylum);
							
							List<EasyUiStaticJsonTreeVO> phylumChild = new ArrayList<EasyUiStaticJsonTreeVO>();
							
							if(classMap.get(phylum).size() != 0){
								
								for(String classs : classMap.get(phylum)){
									
									EasyUiStaticJsonTreeVO vo3 = new EasyUiStaticJsonTreeVO();
									vo3.setId(classIdMap.get(classs));
									vo3.setState(OmicsConstants.DEFAULT_STATUS);
									vo3.setText(classs);
									
									List<EasyUiStaticJsonTreeVO> classChild = new ArrayList<EasyUiStaticJsonTreeVO>();
									
									if(orderMap.get(classs).size() != 0){
										
										for(String order : orderMap.get(classs)){
											
											EasyUiStaticJsonTreeVO vo4 = new EasyUiStaticJsonTreeVO();
											vo4.setId(orderIdMap.get(order));
											vo4.setState(OmicsConstants.DEFAULT_STATUS);
											vo4.setText(order);
											
											List<EasyUiStaticJsonTreeVO> orderChild = new ArrayList<EasyUiStaticJsonTreeVO>();
											
											if(familyMap.get(order).size() != 0){
												
												for(String family : familyMap.get(order)){
													
													EasyUiStaticJsonTreeVO vo5 = new EasyUiStaticJsonTreeVO();
													vo5.setId(familyIdMap.get(family));
													vo5.setState(OmicsConstants.DEFAULT_STATUS);
													vo5.setText(family);
													
													List<EasyUiStaticJsonTreeVO> familyChild = new ArrayList<EasyUiStaticJsonTreeVO>();
													
													if(genusMap.get(family).size() != 0){
														
														for(String genus : genusMap.get(family)){
															
															EasyUiStaticJsonTreeVO vo6 = new EasyUiStaticJsonTreeVO();
															vo6.setId(genusIdMap.get(genus));
															vo6.setState(OmicsConstants.DEFAULT_STATUS);
															vo6.setText(genus);
															
															List<EasyUiStaticJsonTreeVO> genusChild = new ArrayList<EasyUiStaticJsonTreeVO>();
															
															if(speciesMap.get(genus).size() != 0){
																
																for(String species : speciesMap.get(genus)){
																	
																	List<EasyUiStaticJsonTreeVO> speciesChild = new ArrayList<EasyUiStaticJsonTreeVO>();
																	
																	EasyUiStaticJsonTreeVO vo7 = new EasyUiStaticJsonTreeVO();
																	vo7.setId(speciesIdMap.get(species));
																	vo7.setState(OmicsConstants.DEFAULT_STATUS);
																	vo7.setText(species);
																	vo7.setChildren(speciesChild);
																	
																	genusChild.add(vo7);
																}
															}
															vo6.setChildren(genusChild);
															
															familyChild.add(vo6);
														}
													}
													vo5.setChildren(familyChild);
													
													orderChild.add(vo5);
												}
											}
											vo4.setChildren(orderChild);
											
											classChild.add(vo4);
										}
									}
									vo3.setChildren(classChild);
									
									phylumChild.add(vo3);
								}
							}
							vo2.setChildren(phylumChild);
							
							kingdomChild.add(vo2);
						}
					}
					vo1.setChildren(kingdomChild);
					
					rootChild.add(vo1);
				}
			}
			
			EasyUiStaticJsonTreeVO root = new EasyUiStaticJsonTreeVO();
			root.setId(String.valueOf(OmicsConstants.ROOT_ID));
			root.setText("Classification System");
			root.setState(OmicsConstants.DEFAULT_STATUS);
			root.setChildren(rootChild);
			
			Gson gson = new Gson();
			
			getData = gson.toJson(root);
		}
		
		LOGGER.info(type + " tree getData: "+getData);

		PrintWriter pw = null;
		response.setContentType("application/json");
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		try {
			pw = new PrintWriter(response.getWriter());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pw.println(String.format(OmicsConstants.JSON_FORMAT, getData));
		pw.flush();
		pw.close();
	}
	
	@RequestMapping(value = "/taxonomy_update.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public void taxonomy_update(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "rank") String rank,
			@RequestParam(value = "keyword") String keyword) {

		int type = 1;

		LOGGER.info("분류체계 트리 클릭 이벤트 발생 시 get parameger values: id: [" + id +"], type: [" + type + "]");
		
		List<TaxonomyVo> voList = null;
		List<EasyUiJsonTreeVO> collection = new ArrayList<EasyUiJsonTreeVO>();
		
		try {

			voList = this.taxonomyService.getTaxonNodes(id);
			
			
			for (TaxonomyVo vo : voList) {

				EasyUiJsonTreeVO jsonVo = new EasyUiJsonTreeVO();
				jsonVo.setId(String.valueOf(vo.getTax_id()));
				jsonVo.setText(vo.getName_txt().replace(" ", "_"));
				jsonVo.setRank(vo.getRank());
				jsonVo.setEnd_yn(vo.getEnd_yn());
				jsonVo.setLeaf_node(vo.getLeaf_node());
				collection.add(jsonVo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Gson gson = new Gson();

		if (Integer.parseInt(id) == OmicsConstants.ROOT_ID) {
			collection.clear();
		}

		String json = gson.toJson(collection);

		List<AseemblyFilePathVo> assemblyResult = reportService.getAssemblyReportExist(id);
        JSONObject jso=new JSONObject();

		PrintWriter pw = null;
		
		response.setContentType("application/json");
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		try {
			pw = new PrintWriter(response.getWriter());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//키워드로 검색했을 경우와 Archive로 접근했을 때 필요한 데이터를 구분
		if(keyword==null || keyword == ""){
			LOGGER.info("json(collection) : " + json);
			pw.println(json);
		}else{
			jso.put("data", assemblyResult);
			pw.print(jso.toString());
		}

		pw.flush();
		pw.close();
		
	}
	
}
