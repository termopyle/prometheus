package org.kobic.closha.dao;

import java.util.HashMap;
import java.util.List;

import org.kobic.closha.vo.InstancePipelineVo;

public interface CloshaDao {
	
	List<InstancePipelineVo> getUserInstancePipeline();
	int getUserInstancePipelineCount(String userId, String interproId, String blastId, String lastId, String clustaloId, String uscleId);
}
