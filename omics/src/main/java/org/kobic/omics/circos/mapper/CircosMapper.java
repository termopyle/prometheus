package org.kobic.omics.circos.mapper;

import java.io.File;

import org.kobic.omics.circos.vo.CircosVo;
import org.kobic.omics.circos.vo.SGVVo;
import org.springframework.stereotype.Repository;

@Repository(value = "circosMapper")
public interface CircosMapper {

	File getData();
	CircosVo getCircos();
	String circosFull();
	SGVVo circosZoom(String position);
}
