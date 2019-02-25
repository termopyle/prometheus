package org.kobic.closha.mapper;

import java.util.HashMap;
import java.util.List;

import org.kobic.closha.dao.CloshaDao;
import org.kobic.closha.vo.InstancePipelineVo;
import org.springframework.stereotype.Repository;

@Repository(value = "closhaMapper")
public interface CloshaMapper extends CloshaDao{
	
	List<InstancePipelineVo> getUserInstancePipeline(HashMap<String, Object> map);

	Integer getUserInstancePipelineCount(HashMap<String, Object> map);

}
