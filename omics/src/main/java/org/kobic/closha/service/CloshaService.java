package org.kobic.closha.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.kobic.closha.mapper.CloshaMapper;
import org.kobic.closha.vo.InstancePipelineVo;
import org.springframework.stereotype.Service;

@Service(value = "closhaService")
public class CloshaService {

	@Resource(name="closhaMapper")
	private CloshaMapper closhaMapper;
	
	public List<InstancePipelineVo> getUserPipeline(String userId, int startNo, int pageSize, String interproId,
			String blastId, String lastId, String clustaloId, String muscleId){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("user_id",userId);
		map.put("start_no", startNo);
		map.put("page_size", pageSize);
		map.put("interpro_id", interproId);
		map.put("blast_id", blastId);
		map.put("last_id", lastId);
		map.put("clustalo_id", clustaloId);
		map.put("muscle_id", muscleId);		
		return this.closhaMapper.getUserInstancePipeline(map);

	}

	public Integer getUserInstancePipelineCount(String userId, String interproId,
			String blastId, String lastId, String clustaloId, String muscleId){
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("user_id",userId);
		map.put("interpro_id", interproId);
		map.put("blast_id", blastId);
		map.put("last_id", lastId);
		map.put("clustalo_id", clustaloId);
		map.put("muscle_id", muscleId);		
		
		Integer size = closhaMapper.getUserInstancePipelineCount(map);
		
		return size;
	}


}
