package org.kobic.omics.lucene.listener;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.kobic.omics.lucene.utils.LuceneUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuceneIndexListener extends Thread implements
		ServletContextListener {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LuceneIndexListener.class);

	public LuceneIndexListener() throws Exception {

	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		// TODO Auto-generated method stub
		run();
	}

	@Override
	public void run() {
		
		Properties props = new Properties();
		
		try {
			props.load(getClass().getResourceAsStream("/omics/props/globals.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(props.getProperty("Globals.access.log.path"));
		
		File file = new File(props.getProperty("Globals.access.log.path"));
		
		if(!file.exists()){
			if(file.mkdirs()){
				LOGGER.info("로그를 생성할 디렉토리가 존재하지 않아 생성하였습니다.");
			}else{
				LOGGER.info("로그 디렉토리 생성 중 장애가 발생하였습니다.");
			}
		}else{
			LOGGER.info("로그 디렉토리가 존재합니다. [" + props.getProperty("Globals.access.log.path") + "]");
		}
		
		/**
		 * 
		 */
		
		System.out.println(props.getProperty("Globals.lucene.index.path") + "/species");
		
		File index_file = new File(props.getProperty("Globals.lucene.index.path")  + "/species");
		
		if(!index_file.exists()){
			if(index_file.mkdirs()){
				LOGGER.info("로그를 생성할 디렉토리가 존재하지 않아 생성하였습니다.");
			}else{
				LOGGER.info("로그 디렉토리 생성 중 장애가 발생하였습니다.");
			}
		}else{
			LOGGER.info("로그 디렉토리가 존재합니다. [" + props.getProperty("Globals.lucene.index.path") + "/species]");
		}
		
		long startTime = System.currentTimeMillis();

		LuceneUtils utils = new LuceneUtils();
		utils.indexing();

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;

		LOGGER.info("루씬을 이용한 인덱싱 파일 생성 시간: " + elapsedTime);
	}
}
