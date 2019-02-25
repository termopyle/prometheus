package org.kobic.omics.common.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kobic.omics.common.OmicsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommonController {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@RequestMapping(value = "/do_down", method = RequestMethod.GET)
	public void doDown(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		int BUFFER_SIZE = 4096;
		
		String getPath = new String(request.getParameter("path").getBytes("iso-8859-1"), "utf-8");
		
		String filePath = "/resources/files/" + getPath;
		
		int pos = filePath.lastIndexOf( "." );
		String ext = filePath.substring( pos + 1 );

		if(!Arrays.asList(OmicsConstants.FILE_ARROW_EXTETION).contains(ext)){
			logger.info("잘 못 요청된 파일: " + filePath);

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out;
			
			try {
				out = response.getWriter();
				out.println("<script>location.href='failed_down'</script>");
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ServletContext context = request.getSession().getServletContext();
		String appPath = context.getRealPath("/");

		logger.info("appPath = " + appPath);
		
		String fullPath = appPath + filePath;
		File downloadFile = new File(fullPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		String mimeType = context.getMimeType(fullPath);
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		
		logger.info("MIME type: " + mimeType);

		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		String headerKey = "Content-Disposition";
		response.setHeader(headerKey, "attachment; filename=" + URLEncoder.encode(downloadFile.getName(), "utf-8") + ";");

		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;
		
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outStream.close();
	}
	
	@RequestMapping(value = "failed_down")
	public String failedDown(){
		
		logger.info("다운로드 할 수 없는 파일 형식입니다.");
		
		return "err/failed_down";
	}
	
	@RequestMapping(value = "/do_file_down", method = RequestMethod.GET)
	public void doSequenceFileDown(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value = "path", required = false) String path) throws IOException {

		int BUFFER_SIZE = 4096;
		
		ServletContext context = request.getSession().getServletContext();
		
		File downloadFile = new File(path);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		String mimeType = context.getMimeType(path);
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		
		logger.info("MIME type: " + mimeType);

		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(downloadFile.getName().getBytes("euc-kr"), "8859_1") + ";");
		response.setContentLength((int) downloadFile.length());

		String headerKey = "Content-Disposition";
		
		String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		
		response.setHeader(headerKey, headerValue );

		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outStream.close();
	}
}
