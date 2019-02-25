package org.kobic.omics.main.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
	
		
		ModelAndView result = new ModelAndView();
	
		String data = "ANONYMOUS,Calag_0001,YP_007174246.1,Calag_0002,YP_007174247.1,Calag_0003,YP_007174248.1,Calag_0004,YP_007174249.1,Calag_0005,YP_007174250.1,Calag_0006,YP_007174251.1,Calag_0007,YP_007174252.1,Calag_0008,YP_007174253.1,Calag_0009,YP_007174254.1,Calag_0010,YP_007174255.1,Calag_0011,YP_007174256.1,Calag_0012,YP_007174257.1,Calag_0013,YP_007174258.1,Calag_0014,YP_007174259.1,Calag_0015,YP_007174260.1,Calag_0016,YP_007174261.1,Calag_0017,YP_007174262.1,Calag_0018,YP_007174263.1,Calag_0019,Calag_0020,YP_007174264.1,Calag_0021,YP_007174265.1,Calag_0022,YP_007174266.1,Calag_0023,YP_007174267.1,Calag_0024";
		result.setViewName("index");
		result.addObject("search_word_array",data);
		return result;
	}
	
	@RequestMapping(value = "/go_intro")
	public String goIntro(){
		
		logger.info("Intro page");
		
		return "intro/intro";
	}
	
	@RequestMapping(value = "/go_tutorial")
	public String goTutorial(){
		
		logger.info("Tutorial page");
		
		return "tutorial/tutorial";
	}
	
	@RequestMapping(value = "/go_contact")
	public String goContact(){
		
		logger.info("Tutorial page");
		
		return "contact/contact";
	}
}


