package org.kobic.omics.mygene.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kobic.closha.service.CloshaService;
import org.kobic.closha.vo.InstancePipelineVo;
import org.kobic.closha.xml.model.XmlDispatchModel;
import org.kobic.closha.xml.model.XmlParameterModel;
import org.kobic.closha.xml.utils.XMLUtils;
import org.kobic.omics.common.utils.Utils;
import org.kobic.omics.common.vo.PageVo;
import org.kobic.omics.interpro.service.InterproService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import turbo.cache.lite.thrift.service.FileModel;
import turbo.cache.lite.thrift.service.client.CacheLiteClient;

@Controller
public class MygeneController {

	private static final Logger logger = LoggerFactory.getLogger(InterproService.class);

	@Resource(name = "closhaService")
	private CloshaService closhaService;
	
	@RequestMapping(value = "/my_genes", method = RequestMethod.GET)
	public ModelAndView myGenes(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "page_num", required = false) String pageNum,
			@RequestParam(value = "type", required = false) String type) {
	
		logger.debug("[My Gene]");
		
		ModelAndView mv = new ModelAndView();
		
		String isLogin = (String) request.getSession().getAttribute("is_login");
		String userId = (String) request.getSession().getAttribute("uid");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out;
		
		if(isLogin != "true"){
			try {
				out = response.getWriter();
				out.println("<script>alert('The service requires a login.'); "
							+ "location.href='goLogin'</script>");
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Properties props = new Properties();
		
		try {
		
			props.load(getClass().getResourceAsStream("/omics/props/globals.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (pageNum == null || pageNum == "") {
			pageNum = "1";
		}
		//Mypage에서 last, blast, interpro 를 가르키는 변수 type
		if(type == null || type == ""){
			type = "all";
		}
		
		int instancePipelineCount = 0;
		
		String interproId = "";
		String blastId = "";
		String lastId = "";
		String clustaloId = "";
		String muscleId = "";
		
		if (type.equals("last")){
			lastId = props.getProperty("Globals.analysis.id.last");
			mv.addObject("type", "last");
		}else if (type.equals("blast")){
			blastId = props.getProperty("Globals.analysis.id.blast");
			mv.addObject("type", "blast");
		}else if (type.equals("interpro")){
			interproId = props.getProperty("Globals.analysis.id.interpro");
			mv.addObject("type", "interpro");
		}else if (type.equals("muscle")){
			muscleId = props.getProperty("Globals.analysis.id.muscle");
			mv.addObject("type", "muscle");
		}else if (type.equals("clustalo")){
			clustaloId = props.getProperty("Globals.analysis.id.clustalomega");
			mv.addObject("type", "clustalo");
		}else{
			interproId = props.getProperty("Globals.analysis.id.interpro");
			blastId = props.getProperty("Globals.analysis.id.blast");
			lastId = props.getProperty("Globals.analysis.id.last");
			clustaloId = props.getProperty("Globals.analysis.id.clustalomega");
			muscleId = props.getProperty("Globals.analysis.id.muscle");
			mv.addObject("type", "all");
		}
		
		instancePipelineCount = closhaService.getUserInstancePipelineCount(userId, interproId, blastId, lastId, clustaloId, muscleId);
		
        String cPath = props.getProperty("Globals.hdsf.root.path") + "/" + userId;
        String rPath = props.getProperty("Globals.hdsf.root.path");
        String pPath = rPath;
       
		List<FileModel> file_list = new ArrayList<FileModel>();
		
		CacheLiteClient client = new CacheLiteClient();
		file_list = client.getFiles(cPath);
		
		for(FileModel f : file_list){
			f.setCreateDate(Utils.millisToDate(Long.parseLong(f.getCreateDate())));
			f.setName(f.getName() + "^" + Utils.humanReadableByteCount(f.getSize(), true));
		}
	
		PageVo pageInfo = new PageVo();
		pageInfo.setPageNo(Integer.parseInt(pageNum));
		pageInfo.setPageSize(5);
		pageInfo.setTotalCount(instancePipelineCount);
		
		List<InstancePipelineVo> instancePipeline = closhaService.getUserPipeline(userId, pageInfo.getStartRow(),
				pageInfo.getPageSize(), interproId,blastId,lastId,clustaloId,muscleId);
		
		Map<String, XmlDispatchModel> map = new HashMap<String, XmlDispatchModel>();
		Map<String, String> pathMap = new HashMap<String, String>();
		
		for(InstancePipelineVo vo : instancePipeline){
			map.put(vo.getInstanceID(), XMLUtils.parserXML(vo.getInstanceXML().trim()));
		}
		
		//output folder path
		for( Map.Entry<String, XmlDispatchModel> elem : map.entrySet() ){
			for(XmlParameterModel m : elem.getValue().getParametersBeanList()){
				
				System.out.println(m.getName() + " : " + m.getParameterType() + " : " + m.getType());
				
				if(m.getName().equals("output_folder") && m.getType().equals("FOLDER")){
					pathMap.put(elem.getKey(), m.getSetupValue());
				}
			}			
		}
		
		mv.addObject("page_info", pageInfo);
		mv.addObject("page_num",pageNum);
		mv.addObject("instance_pipline", instancePipeline);	
		mv.addObject("xml_dispatch_map", map);
		mv.addObject("path_map", pathMap);
		
		mv.addObject("file_list", file_list);
		mv.addObject("c_path", cPath);
		mv.addObject("p_path", pPath);
		mv.addObject("r_path", rPath);
		mv.addObject("user_id", userId);

		mv.setViewName("mygenes/my_genes");

		return mv;
	}
	
	@RequestMapping(value = "/do_mygenes", method = RequestMethod.GET)
	public ModelAndView doMygenes(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="path[]", required=true) String path,
			@RequestParam(value="analysis_program_name", required=true) String analysisProgramName) {
		
		logger.debug("[do My Gene]");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:analysis?path="+path+"&analysis_program_name="+analysisProgramName);
		return mv;
	}
}
