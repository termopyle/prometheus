package org.kobic.omics.circos.dao;

import java.io.File;

import org.kobic.omics.circos.vo.CircosVo;
import org.kobic.omics.circos.vo.SGVVo;

public interface CircosDao {
	
	File getData();
	CircosVo getCircos();
	String circosFull();
	SGVVo circosZoom(String position);

}
