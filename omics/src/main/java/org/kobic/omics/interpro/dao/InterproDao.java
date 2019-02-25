package org.kobic.omics.interpro.dao;

import java.util.List;
import java.util.Map;

import org.kobic.omics.interpro.vo.InterproVo;

public interface InterproDao {
	public List<InterproVo> getInterpro( String ipr_ids, String sub_query, int count );
	public List<InterproVo> getInterpro( String ipr_ids );
}
