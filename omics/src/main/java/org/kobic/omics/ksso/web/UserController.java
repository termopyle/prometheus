package org.kobic.omics.ksso.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.json.JSONObject;
import org.kobic.closha.service.CloshaService;
import org.kobic.closha.vo.InstancePipelineVo;
import org.kobic.closha.xml.model.XmlDispatchModel;
import org.kobic.closha.xml.model.XmlParameterModel;
import org.kobic.closha.xml.utils.XMLUtils;
import org.kobic.ksso.thrift.autowise.service.UserModel;
import org.kobic.omics.common.OmicsConstants;
import org.kobic.omics.common.utils.Utils;
import org.kobic.omics.common.vo.PageVo;
import org.kobic.omics.ksso.service.UserService;
import org.kobic.omics.ksso.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import turbo.cache.lite.thrift.service.FileModel;
import turbo.cache.lite.thrift.service.client.CacheLiteClient;

@Controller
public class UserController {
private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "closhaService")
	private CloshaService closhaService;
	
	@RequestMapping(value = "/doIdCheck", method = RequestMethod.POST)
	public void doIdChcek(HttpServletRequest request, 
			HttpServletResponse response, @RequestParam(value = "user_id", required = true) String userId) throws TException {
		
		logger.info("사용자 아이디 조회");

		boolean result = userService.existUserId(userId);
		
		PrintWriter pw = null;	
		
		response.setContentType("application/text");
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
		try {
			pw = new PrintWriter(response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}

		pw.println(String.valueOf(result));
		pw.flush();
		pw.close();
	}

	@RequestMapping(value = "/goLogin", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, 
			HttpServletResponse response) {
		
		logger.info("로그인페이지");

		ModelAndView result = new ModelAndView();
		
		result.setViewName("user/login");
		return result;
	}
	
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public ModelAndView doLogin(HttpServletRequest request, 
			HttpServletResponse response, @ModelAttribute("user_vo") UserVo userVo) throws TException {

		logger.info("사용자 로그인 진행");

		
		ModelAndView result = new ModelAndView();
		
		Pattern idPattern = Pattern.compile(OmicsConstants.ID_MATCH);
		Matcher idMatcher = idPattern.matcher(userVo.getUserId());
		
		Pattern passwdPattern = Pattern.compile(OmicsConstants.PASSWD_MATCH);
		Matcher passwdMatcher = passwdPattern.matcher(userVo.getPassword());
		
		if (!idMatcher.find()) {
			
			result.setViewName("user/special_check");
			
			return result;
		}else if(!passwdMatcher.find()){
			
			result.setViewName("user/special_check");
			
			return result;
		}
		
		boolean login = userService.login(userVo.getUserId(), userVo.getPassword());

		logger.info("로그인 결과: " + login);
		
		if (login) {
			request.getSession().setAttribute("uid", login ? userVo.getUserId() : null);
			request.getSession().setAttribute("is_admin", String.valueOf(userService.isAdmin(userVo.getUserId())));
			result.setViewName("redirect:/");
		} else{
			result.setViewName("user/not_found_userinfor");
		}
		
		request.getSession().setAttribute("is_login", String.valueOf(login));
		
		return result;
	}	
	
	@RequestMapping(value = "/doLogout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,
			HttpServletResponse response) throws TException {
		
		logger.info("logout");

		String uid = (String) request.getSession().getAttribute("uid");
		request.getSession().removeAttribute("uid");
		request.getSession().removeAttribute("is_admin");
		request.getSession().removeAttribute("is_login");

		userService.logout(uid);

		return "redirect:/";
	}
	
	@RequestMapping(value = "/goJoin", method = RequestMethod.GET)
	public ModelAndView join(HttpServletRequest request, 
			HttpServletResponse response) {
		
		logger.info("회원가입페이지");
		
		ModelAndView result = new ModelAndView();
		
		result.setViewName("user/join");
		return result;
	}
	
	@RequestMapping(value = "/doJoin", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ModelAndView doJoin(HttpServletRequest request, 
			HttpServletResponse response, @ModelAttribute("user_vo") UserVo userVo) throws TException {

		logger.info("사용자 회원가입 진행");
				
		ModelAndView result = new ModelAndView();

		String userId = userService.kssoUserRegister(userVo);
		
		if(userId == null){
			
			result.setViewName("user/special_chech");
			
			return result;
		}
		
		result.addObject("user_id", userId);
		
		result.setViewName("user/join_complete");
		return result;
	}	
	
	@RequestMapping(value = "/doFindUserInfo", method = RequestMethod.POST)
	public ModelAndView findUserInfo(HttpServletRequest request, 
			HttpServletResponse response, @RequestParam(value = "find_type", required = true) String findType,
			@ModelAttribute("user_vo") UserVo userVo) throws TException {
		
		ModelAndView result = new ModelAndView();
		
		response.setContentType("text/html;charset=UTF-8");
		
		if (findType.equals("find_user_id")) {
			
			logger.info(findType + " : " + userVo.getUserName() + " : " + userVo.getEmailAdress());

			UserModel user = userService.findUserInfo(userVo.getUserName(), userVo.getEmailAdress());
			
			if (user != null) {
			
				userVo.setUserId(user.getUser_id());
				result.setViewName("user/find_id_2");
			} else {
				
				result.setViewName("user/not_found_userinfor");	
			}
			
		} else {

			if (userService.userCheckWithMail(userVo.getUserId(), userVo.getEmailAdress())) {

				String tempPassword = userService.getTempPasswd(userVo.getUserId(), userVo.getEmailAdress());
				
				Utils utils = new Utils();
				utils.sendMail(userVo.getUserId(), tempPassword, userVo.getEmailAdress());
				
				result.addObject("user_email", userVo.getEmailAdress());
				result.setViewName("user/find_pw_2");
			}else {

				result.setViewName("user/not_found_userinfor");
			}			
		}
	
		return result;
	}
	
	@RequestMapping(value ="/doUserInfoUpdate", method = RequestMethod.POST)
	public ModelAndView userUpdate(HttpServletRequest request, 
			HttpServletResponse response) throws TException{
		
		ModelAndView result = new ModelAndView();
		
		String uid = (String) request.getSession().getAttribute("uid");
		String userId = request.getParameter("user_id");
		String userEmail = request.getParameter("user_email");
		String password = request.getParameter("newPassword");
		
		int update = userService.updatePassword(userId, userEmail, password);
		
		if(update == 1){
			request.getSession().removeAttribute("uid");
			request.getSession().removeAttribute("is_admin");
			
			userService.logout(uid);
			
			result.setViewName("kobis/login/login");
		}else{
			result.setViewName("error/error");
		}
		
		return result;
	}
		
	@RequestMapping(value = "/goAgreement", method = RequestMethod.GET)
	public ModelAndView agreement(HttpServletRequest request, 
			HttpServletResponse response) {

		logger.info("약관동의");
		
		ModelAndView result = new ModelAndView();

		result.setViewName("user/agreement");
		
		return result;
	}
	
	@RequestMapping(value = "/goFindId", method = RequestMethod.GET)
	public ModelAndView goFindId(HttpServletRequest request, 
			HttpServletResponse response) {

		logger.info("ID 찾기");
		
		ModelAndView result = new ModelAndView();

		result.setViewName("user/find_id_1");
		
		return result;
	}

	@RequestMapping(value = "/goFindPw", method = RequestMethod.GET)
	public ModelAndView goFindPw(HttpServletRequest request, 
			HttpServletResponse response) {
		
		logger.info("패스워드를 찾으러 왔습니다.");
		
		ModelAndView result = new ModelAndView();

		result.setViewName("user/find_pw_1");
		
		return result;
	}
	
	@RequestMapping(value = "/mypage", method = RequestMethod.GET)
	public ModelAndView mypage(HttpServletRequest request, 
			HttpServletResponse response) throws TException {
		
		logger.info("마이페이지");
		
		ModelAndView result = new ModelAndView();

		String uid = (String) request.getSession().getAttribute("uid");
		String isLogin = (String) request.getSession().getAttribute("is_login");
		
		UserModel userModel = userService.getUserModel(uid);
		
		if(userModel == null) {
			
			result.setViewName("user/special_check");
			
			return result;
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

		result.addObject("user_model", userModel);
		
		result.setViewName("mypage/mypage_list");
		
		return result;
	}	
	
	@RequestMapping(value ="/do_user_info_update", method = RequestMethod.POST)
	public ModelAndView doUserInfoUpdate(HttpServletRequest request, 
			HttpServletResponse response) throws TException{

		logger.info("my info update");
		
		ModelAndView mv = new ModelAndView();
		
		String uid = (String) request.getSession().getAttribute("uid");
		String userId = request.getParameter("user_id");
		String userEmail = request.getParameter("user_email");
		String password = request.getParameter("new_password");

		int update = userService.updateUserInfo(userId, userEmail, password);

		if(update == 1){
			request.getSession().removeAttribute("uid");
			request.getSession().removeAttribute("is_admin");
			request.getSession().removeAttribute("is_login");
			userService.logout(uid);
			mv.setViewName("mypage/user_info_modify_complete");
		}else{
			mv.setViewName("error/error");
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/do_mypage_file_read", method = RequestMethod.GET)
	public void myPageFileRead(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="output_path", required=true) String outputPath ) {
		
		System.out.println("outputPath:::"+outputPath);
		CacheLiteClient client = new CacheLiteClient();
		List<String> list = client.read(outputPath);
		
		String res = StringUtils.join(list, "\n");
		
		System.out.println(res);
		
		JSONObject jso = new JSONObject();        
        jso.put("res", res);
        
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
	
/*	@RequestMapping(value = "/open_explorer_window", method = RequestMethod.GET)
	public ModelAndView openExplorer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="input_id", required=true) String inputID ) {

		ModelAndView result = new ModelAndView();
		result.setViewName("pipeline/explorer");
		result.addObject("input_id", inputID);
		return result;
	}*/
	
	@RequestMapping(value = "/get_file_list", method = RequestMethod.GET)
	public void getFileList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="path", required=true) String cPath ) {
		
		logger.info("currunt path: " + cPath);
		
		cPath = cPath.replace("\\", "/");
		
		Properties props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("/omics/props/globals.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String rPath = props.getProperty("Globals.hdsf.root.path");
		String pPath = new File(cPath).getParent();
		
		List<FileModel> file_list = new ArrayList<FileModel>();
	
		if(cPath.equals(rPath)){
			file_list = null;
		}else{
			CacheLiteClient client = new CacheLiteClient();
			file_list = client.getFiles(cPath);
			
			for(FileModel f : file_list){
				f.setCreateDate(Utils.millisToDate(Long.parseLong(f.getCreateDate())));
				f.setName(f.getName() + "^" + Utils.humanReadableByteCount(f.getSize(), true));
			}
		}
		
		JSONObject jso = new JSONObject();        
        jso.put("list_data", file_list);
        jso.put("cPath", cPath);
        jso.put("pPath", pPath);
        jso.put("rPath", rPath);
        
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
}
