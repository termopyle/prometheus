package org.kobic.omics.jbrowse.web;

import java.io.File;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.kobic.omics.jbrowse.service.JBrowseService;
import org.kobic.omics.jbrowse.vo.JBrowseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JBrowseController {
	
	private static final Logger logger = LoggerFactory.getLogger(JBrowseController.class);
	
	@Resource(name = "jbrowseService")
	private JBrowseService jbrowseService;

	@RequestMapping(value = "/jbrowse", method = RequestMethod.GET)
	public String jbrowse(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "jbrowse/jbrowse";
	}
	
	@RequestMapping(value = "/call", method = RequestMethod.GET)
	public ModelAndView callJBrowse(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		String taxId = "2162";
		
		String kingdom = "archaea";
		String version = "GCF_000302455.1_ASM30245v1";
		
		String dataPath = "data" + File.separator + kingdom + File.separator + taxId + File.separator + version;
		return new ModelAndView("redirect:JBrowse-1.12.0/index.html?data=" + dataPath);
	}

	@RequestMapping(value = "/jbrowse_result", method = RequestMethod.GET)
	public ModelAndView jbrowse_result(Locale locale, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);

		String kingdom = request.getParameter("kingdom");
		String taxId = request.getParameter("taxId");
		String version = request.getParameter("version");
		
		String dataPath = "data" + File.separator + kingdom + File.separator + taxId + File.separator + version;
		System.out.println("JBrowse-1.12.0/index.html?data=" + dataPath);
		
		return new ModelAndView("redirect:JBrowse-1.12.0/index.html?data=" + dataPath);
	}
	
	@RequestMapping(value = "/jbrowse_first", method = RequestMethod.GET)
	public String jbrowse_first(Locale locale, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);

		return "jbrowse/jbrowse_first";
	}
	
//	컨트롤러(controller)는 웹 클라이언트에서 들어온 요청을 해당 비지니스 로직을 호출하고, 수행 결과와 함께 응답을 해주는 Dispatcher 역할을 한다.
	
	@RequestMapping(value = "/jbrowse_second", method = RequestMethod.GET)//요청 URL을 의미
	public ModelAndView jbrowse_second(Locale locale, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		String taxId=request.getParameter("taxId");
		
		List<JBrowseVo> jbrowseResult = jbrowseService.getJBrowseResult(taxId);

		ModelAndView mv = new ModelAndView();//화면에 뿌려줄 객체를 생성		
		
		mv.setViewName("jbrowse/jbrowse_second");//화면에 보여줄 jsp파일
		
		if( jbrowseResult != null && jbrowseResult.size() > 0 ) {
			mv.addObject("generalInfo", jbrowseResult.get(0));
			mv.addObject("jbrowseResult", jbrowseResult);
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/jbrowse_index", method = RequestMethod.GET)
	public ModelAndView jbrowse_index(Locale locale, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		/*예제 : taxID=1204725*/
		
		String genomicfnaLocation = request.getParameter("genomicfnaLocation");
		String JBrowse = request.getParameter("JBrowse");
		String refSeqAssemblyID = request.getParameter("refSeqAssemblyID");
		
		ModelAndView mv = new ModelAndView();//화면에 뿌려줄 객체를 생성		
		
		mv.setViewName("jbrowse/jbrowse_index");//화면에 보여줄 jsp파일
		
		mv.addObject("genomicfnaLocation", genomicfnaLocation);
		mv.addObject("JBrowse", JBrowse);
		mv.addObject("refSeqAssemblyID", refSeqAssemblyID);
		
		String dataPath = "data" + File.separator + genomicfnaLocation + File.separator + JBrowse + File.separator + refSeqAssemblyID;
		System.out.println("JBrowse-1.12.0/index.html?data=" + dataPath);
		
		return mv;
	}	
}
