package org.kobic.closha.pipeline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import turbo.cache.lite.thrift.service.client.CacheLiteClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:config/spring/context/context-datasource.xml", 
        "classpath:config/spring/context/context-mybatis.xml" })
public class CloshaTest {
	
	@Test
	public void read(){
		
		String path = "";
		
		CacheLiteClient client = new CacheLiteClient();
		List<String> list = client.read(path);
		
		System.out.println(list);
	}
	
	public void pipeline(){
		
		String pipeline_id = "68FF82E7-4FE1-4D95-9AAE-60D9578146C9";
		String project_name = "27";
		String user_id = "kogun82";
		String email = "kogun82@naver.com";
		Map<String, String> params = new HashMap<String, String>();
		
		CacheLiteClient client = new CacheLiteClient();
		client.new_instance_pipeline(pipeline_id, project_name, user_id, email, params);
		
	}
}
