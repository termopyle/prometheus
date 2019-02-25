package org.kobic.omics.lucene.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kobic.omics.lucene.utils.LuceneUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LuceneController {

	@Resource(name = "luceneController")
	private LuceneController luceneController;

	private static final Logger LOGGER = LoggerFactory.getLogger(LuceneController.class);

	int pagingStart = 1;
	int pagingSize = 5;

	@RequestMapping(value = "keyRecommend.do", method = RequestMethod.POST)
	public void keyRecommend(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "key") String key) {

		LuceneUtils utils = new LuceneUtils();

		List<String> list = utils.searching(key);
		
		StringBuffer str = new StringBuffer();

		int i = 0;

		for (String s : list) {
			// s = s.split("\t")[0];
			str.append(s);
			if (i != list.size()) {
				str.append(",");
			}
			i++;
		}
		
		LOGGER.info("search count: [" + i + "]");

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

		LOGGER.info(str.toString());

		pw.println(str.toString());
		pw.flush();
		pw.close();

	}

}
