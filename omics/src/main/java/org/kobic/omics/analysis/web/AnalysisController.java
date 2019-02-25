package org.kobic.omics.analysis.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.json.JSONObject;
import org.kobic.ksso.thrift.autowise.service.UserModel;
import org.kobic.omics.common.utils.Utils;
import org.kobic.omics.interpro.service.InterproService;
import org.kobic.omics.ksso.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import turbo.cache.lite.thrift.service.client.CacheLiteClient;

@Controller
public class AnalysisController {
	
	private static final Logger logger = LoggerFactory.getLogger(InterproService.class);
	private Utils utils = new Utils();
	
	@Resource(name = "userService")
	private UserService userService;
	
	@RequestMapping(value = "/newick", method = RequestMethod.GET)
	public ModelAndView newick (HttpServletRequest request, HttpServletResponse response) {
	
		logger.debug("[newick]");

		ModelAndView result = new ModelAndView();

		result.setViewName("analysis/newick");

		return result;
	}
	
	
	@RequestMapping(value = "/analysis", method = RequestMethod.GET)
	public ModelAndView interproscan(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="path", required=false) String path,
			@RequestParam(value="analysis_program_name", required=false) String analysisProgramName) throws TException {

		ModelAndView mv = new ModelAndView();
		logger.info("[analysis] , user id : " + request.getSession().getAttribute("uid"));
		
		String inputBlast = null;
		String inputInterpro = null;
		String inputClustalomega = null;
		String inputMuscle = null;
		
		int inputTypeNum = 0;
		int inputSubNum = 0;
		
		if(path == null){

		}else{
			//MyGenes 에서 넘어온 데이터
			CacheLiteClient client = new CacheLiteClient();

			StringBuffer buffer = new StringBuffer();
			
			for(String s : client.read(path)){
				buffer.append(s + "\r\n");
			}
			
			mv.addObject("line", buffer.toString());
			mv.addObject("analysis_program_name", analysisProgramName);
			
			System.out.println("line\n"+buffer.toString());
			System.out.println("from My Genes");
			
			
			if (analysisProgramName.equals("blast")){
				inputBlast = buffer.toString();
				inputTypeNum = 2;
			} else if (analysisProgramName.equals("interpro")){
				inputInterpro = buffer.toString();
				inputTypeNum = 4;
			} else if (analysisProgramName.equals("clustalomega")){
				inputClustalomega = buffer.toString();
				inputTypeNum = 3;
			} else if (analysisProgramName.equals("muscle")){
				inputMuscle = buffer.toString();
				inputTypeNum = 3;
				inputSubNum = 5;
			}
			
		}
		
		if(inputBlast == null){
			inputBlast = "FASTA";
		} 
		if (inputInterpro == null){
			inputInterpro = "FASTA";
		} 
		if (inputClustalomega == null){
			inputClustalomega = "FASTA";
		} 
		if (inputMuscle == null){
			inputMuscle = "FASTA";
		}
		
		System.out.println("analysisProgramName : "+analysisProgramName);
		
		String isLogin = (String) request.getSession().getAttribute("is_login");
		String userid = (String) request.getSession().getAttribute("uid");
		
		UserModel userModel = userService.getUserModel(userid);
		
		if(userModel == null){
			
			mv.setViewName("redirect:/goLogin");
			
			return mv;
		}
		
		Properties props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("/omics/props/globals.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out;
		
		if(isLogin != "true"){
			try {
				out = response.getWriter();
				out.println("<script>alert('The service requires a login.'); "
							+ "location.href='./goLogin'</script>");
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		mv.addObject("interpro_id", props.getProperty("Globals.analysis.id.interpro"));
		mv.addObject("blast_id", props.getProperty("Globals.analysis.id.blast"));
		mv.addObject("last_id", props.getProperty("Globals.analysis.id.last"));
		mv.addObject("clustalomega_id", props.getProperty("Globals.analysis.id.clustalomega"));
		mv.addObject("muscle_id", props.getProperty("Globals.analysis.id.muscle"));
		
		mv.addObject("user_model", userModel);

		mv.addObject("interpro_title",utils.getDate("interproscan"));
		mv.addObject("blast_title",utils.getDate("blast"));
		mv.addObject("clustalomega_title",utils.getDate("clustalomega"));
		mv.addObject("muscle_title",utils.getDate("muscle"));
		mv.addObject("last_title",utils.getDate("last"));
		
		mv.addObject("user_id",userid );
		mv.addObject("inputBlast", inputBlast);
		mv.addObject("inputInterpro", inputInterpro);
		mv.addObject("inputClustalomega", inputClustalomega);
		mv.addObject("inputMuscle", inputMuscle);
		mv.addObject("inputTypeNum", inputTypeNum);
		mv.addObject("inputSubNum", inputSubNum);
		
		mv.setViewName("analysis/genome_analysis");
		return mv;

	}
	
	@RequestMapping(value = "/doAnalysis", method=RequestMethod.POST)
	public void doAnalysis( HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="pipeline_id", required=true) String pipeline_id,
			@RequestParam(value="result", required=true) String getData,
			@RequestParam(value="title", required=true) String project_name,
			@RequestParam(value="email", required=true) String email){

		logger.info("[doAnalysis]");

		String user_id = (String) request.getSession().getAttribute("uid");
		
		logger.info("user id: " + user_id);
		
		logger.info("title:"+project_name+", email:"+email+", pipeline_id:"+pipeline_id);
		
		CacheLiteClient client = new CacheLiteClient();

		Map<String, String> params = new HashMap<String, String>();
		
		String[] keyData = null;
		
		String[] pairs = getData.toString().split("#");
		int i = 0;
		for(String p : pairs){
			System.out.println(i++ + p);
		}

		String temp[] = null;
		String path = null;
		
		for (String pair : pairs){
		  
			keyData = pair.split("[$]");
		    
		    if(keyData[0].length() != 0){
		    	
				temp = keyData[0].split("[|]");
				
				if(temp.length > 2){
					if(temp[1].equals("FILE")){
						if(temp[2].toLowerCase().startsWith("input")){
							System.out.println(keyData[1]);
							path = client.write(keyData[1] + "\n", project_name, user_id);
							params.put(temp[0] + ":" + temp[2], path);
						}else if(temp[2].toLowerCase().startsWith("output")){ 
							//output 경로 설정
							path = "/express/" + user_id + "/closha/project/" + project_name + "/" + project_name + "_output";
							params.put(temp[0] + ":" + temp[2], path);
						}
					}else if(temp[1].equals("FOLDER")){
						path = "/express/" + user_id + "/closha/project/" + project_name + "/output";
						params.put(temp[0] + ":" + temp[2], path);
					}else{
						params.put(temp[0] + ":" + temp[2], keyData[1]);
					}
				}
			}
		}
		
		for(Map.Entry<String, String> elem : params.entrySet()){
			System.out.println(elem.getKey() + " ==> " + elem.getValue());
		}
		
		client.new_instance_pipeline(pipeline_id, project_name, user_id, email, params);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/complete", method = RequestMethod.GET)
	public ModelAndView complete (HttpServletRequest request, HttpServletResponse response) {
	
		logger.debug("[complete]");

		ModelAndView result = new ModelAndView();

		result.setViewName("analysis/complete");

		return result;
	}
	
	@RequestMapping(value = "/do_file_transfer", method = RequestMethod.GET)
	public void doFileTransfer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="path", required=true) String hPath ) {

		logger.info("currunt path: " + hPath);

		Properties props = new Properties();
		
		try {
			props.load(getClass().getResourceAsStream("/omics/props/globals.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String hdfsUrl = props.getProperty("Globals.closha.hdfs.url");
		String hdfsRoot = props.getProperty("Globals.hdsf.root.path");
		String rapidantRoot = props.getProperty("Globals.rapidant.root.path");
		
		String target = hPath;
		
		target = target.replaceAll(hdfsUrl, "");
		target = target.replaceAll(hdfsRoot, rapidantRoot);
		
		CacheLiteClient client = new CacheLiteClient();
		boolean res = client.copyingFileFromHDFSToLocal(hPath, target);
		
		JSONObject jso = new JSONObject();
        jso.put("res", String.valueOf(res));
        jso.put("res_path", hPath);
        
        PrintWriter out = null;
        response.setContentType("text/html;charset=UTF-8");

		try {
			out = new PrintWriter(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}

		out.print(jso.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "do_smallSizeFile_download", method = RequestMethod.GET)
	public String doSmallSizeFileDownload(HttpServletRequest request, @RequestParam(value="path", required = true) String hPath){
		
		logger.info("smallSize File currunt path: " + hPath);
		
		CacheLiteClient client = new CacheLiteClient();
		List<String> list = client.read(hPath);
		
		String res = StringUtils.join(list, "\n");
		
		System.out.println(res);
		
		Properties props = new Properties();
		
		try {
			props.load(getClass().getResourceAsStream("/omics/props/globals.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String uid = (String) request.getSession().getAttribute("uid");
		String downloadFilePath = props.getProperty("Globals.smallSizeFile.download.path") + "/" + uid + "_" + hPath.substring(hPath.lastIndexOf("/") + 1, hPath.length());
		
		logger.info("downloadFilePath : " + downloadFilePath);
		
		try {
		
			FileUtils.write(new File(downloadFilePath), res, "UTF-8");
		} catch (IOException e) {
			
			logger.error("File wirte error");
		}
		
		return "redirect:/do_file_down?path=" + downloadFilePath;
	}
}
