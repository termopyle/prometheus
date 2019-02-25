package org.kobic.omics.circos.service;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.kobic.omics.circos.mapper.CircosMapper;
import org.kobic.omics.circos.vo.ChrVo;
import org.kobic.omics.circos.vo.CircosVo;
import org.kobic.omics.circos.vo.DrawVo;
import org.kobic.omics.circos.vo.EntrezVo;
import org.kobic.omics.circos.vo.GFFVo;
import org.kobic.omics.circos.vo.GeneVo;
import org.kobic.omics.circos.vo.SGVVo;
import org.kobic.omics.common.OmicsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ca.ualberta.stothard.cgview.Cgview;
import ca.ualberta.stothard.cgview.CgviewConstants;
import ca.ualberta.stothard.cgview.CgviewIO;
import ca.ualberta.stothard.cgview.Feature;
import ca.ualberta.stothard.cgview.FeatureRange;
import ca.ualberta.stothard.cgview.FeatureSlot;
import ca.ualberta.stothard.cgview.Legend;
import ca.ualberta.stothard.cgview.LegendItem;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Repository
public class CircosService{

	private static final Logger logger = LoggerFactory.getLogger(CircosService.class);
	
	@Resource(name = "circosMapper")
	private CircosMapper circosMapper;
	
	private final String plus = "+";
	private final String minus = "-";
	
	public String taxonomy;
	
	public static final String ncbi_url = "https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id=%s&lvl=3&lin=f&keep=1&srchmode=1&unlock";

	private Color[] colors = new Color[] { new Color(56, 165, 82), new Color(136, 131, 195), new Color(255, 209, 42),
			new Color(79, 134, 247), new Color(255, 211, 248), new Color(201, 90, 73), new Color(255, 255, 49),
			new Color(166, 231, 255), new Color(218, 38, 71), new Color(189, 130, 96), new Color(68, 215, 168),
			new Color(111, 45, 168), new Color(218, 97, 78), new Color(37, 53, 41), new Color(219, 145, 239),
			new Color(178, 243, 2), new Color(255, 228, 205), new Color(3, 89, 50), new Color(255, 136, 102),
			new Color(33, 79, 198), new Color(155, 118, 83), new Color(255, 208, 185), new Color(255, 80, 80),
			new Color(255, 207, 241), new Color(115, 130, 118), new Color(206, 200, 239), new Color(252, 90, 141),
			new Color(69, 162, 125), new Color(250, 91, 61), new Color(93, 173, 236), new Color(255, 135, 141),
			new Color(89, 70, 178), new Color(168, 55, 49), new Color(255, 170, 29), new Color(41, 150, 23),
			new Color(34, 22, 111), new Color(255, 84, 112), new Color(156, 81, 182), new Color(175, 110, 77),
			new Color(157, 218, 192), new Color(232, 142, 90), new Color(132, 222, 2), new Color(197, 49, 81),
			new Color(255, 223, 70), new Color(255, 68, 102), new Color(130, 142, 132), new Color(253, 82, 64),
			new Color(57, 18, 133), new Color(255, 133, 207), new Color(75, 199, 207), new Color(255, 70, 129),
			new Color(255, 109, 58), new Color(160, 230, 255), new Color(255, 64, 75), new Color(100, 96, 154),
			new Color(20, 169, 137), new Color(147, 55, 9), new Color(67, 108, 185), new Color(208, 83, 64),
			new Color(53, 56, 57), new Color(171, 173, 72), new Color(189, 85, 156), new Color(45, 93, 161),
			new Color(131, 42, 13), new Color(181, 105, 23), new Color(233, 116, 81), new Color(201, 160, 220),
			new Color(198, 45, 66), new Color(252, 128, 165), new Color(118, 215, 234), new Color(255, 136, 51),
			new Color(41, 171, 135), new Color(255, 203, 164), new Color(252, 214, 103), new Color(237, 10, 63),
			new Color(196, 98, 16), new Color(46, 88, 148), new Color(143, 212, 0), new Color(191, 79, 81),
			new Color(141, 78, 133), new Color(49, 145, 119), new Color(217, 134, 149), new Color(117, 117, 117),
			new Color(0, 129, 171), new Color(165, 56, 139), new Color(190, 195, 131), new Color(42, 88, 255),
			new Color(247, 192, 79), new Color(211, 255, 218), new Color(73, 183, 201), new Color(49, 49, 249),
			new Color(255, 190, 166), new Color(38, 218, 184), new Color(96, 155, 189), new Color(215, 68, 115),
			new Color(102, 168, 45), new Color(78, 198, 218), new Color(53, 37, 49), new Color(165, 239, 145),
			new Color(67, 2, 243) };
	
	public static int randomRange(int n1, int n2) {
	    return (int) (Math.random() * (n2 - n1 + 1)) + n1;
	  }
	
	public DrawVo getDrawData(String taxonomy, String db, boolean is_full) {		
		// TODO Auto-generated method stub
		
		this.taxonomy = taxonomy;
		
		logger.info("taxonomy: " + taxonomy +", db:" + db);
		
		if(db == null || db == ""){
			db = "ref_seq";
		}
		 
		String db_path = null;
		
		String prefix = "/BiO/gff_data";
		
		if(db.equals(OmicsConstants.REF_SEQ_DB)){
			db_path =  prefix + "/ref_seq/" + taxonomy + ".circus";
		}else if(db.equals(OmicsConstants.PLANT_DB)){
			db_path = prefix + "/raw_data/" + taxonomy + ".circus";
		}else if(db.equals(OmicsConstants.ENSEMBL_DB)){
			db_path = prefix + "/ensemble/" + taxonomy + ".circus";
		}
		
		logger.info("db_paht: " + db_path);
		
		File gff_file  = new File(db_path);
		
		if(!gff_file.exists()){
			return null;
		}
		
		Path path = Paths.get(gff_file.toURI());
		
		List<GFFVo> gff = new ArrayList<GFFVo>();
		Map<String, ChrVo> chromosome_map = new HashMap<String, ChrVo>();
		Map<String, Color> legend_map = new HashMap<String, Color>();
		
		String s = null;
		String chr_name = null;
		String seq_name = null;
		String temp_1[] = null;
		String temp_2[] = null;
		String temp_3[] = null;
		
		int start = 0;
		int end = 0;
		int length = 0;
		
		boolean isRange = false;
		
		try {
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			
			while ((s = reader.readLine()) != null) {
				
				temp_1 = s.split("\t");

				if(s.startsWith("#")){
					
					isRange = temp_1[0].split("[:]")[1].length() > 9 ? true : false;
					
					if(isRange){
						length = (int) (Long.parseLong(temp_1[0].split("[:]")[1]) / 1000);
					}else{
						length = Integer.parseInt(temp_1[0].split("[:]")[1]);
					}
					
					temp_2 = temp_1[1].split(",");
					
					for(String chr : temp_2){
						
						temp_3 = chr.split("[|]");
						
						chr_name = temp_3[0];
						
						if(isRange){
							start = (int) (Long.parseLong(temp_3[1].split("-")[0]) / 1000);
							end = (int) (Long.parseLong(temp_3[1].split("-")[1]) / 1000);
						}else{
							start = Integer.parseInt(temp_3[1].split("-")[0]);
							end = Integer.parseInt(temp_3[1].split("-")[1]);
						}
						
						seq_name = temp_3[2];
						
						ChrVo chrVo = new ChrVo();
						chrVo.setChr(chr_name);
						chrVo.setStart(start);
						chrVo.setEnd(end);
						chrVo.setSeqName(seq_name);
						
						chromosome_map.put(chr_name, chrVo);
						
						if (legend_map.get(chr_name) == null) {
//							legend_map.put(chr_name, colors[color_index]);
							legend_map.put(chr_name, colors[randomRange(0, colors.length - 1)]);
						}
					}
					
				}else if (temp_1.length >= 13 && !s.startsWith("#")) {
					
					GFFVo gm = new GFFVo();
					gm.setTaxonomy(temp_1[0]);
					gm.setSpecies(temp_1[1]);
					gm.setAssemblyId(temp_1[2]);
					gm.setAssemblyName(temp_1[3]);
					gm.setChr(temp_1[4]);
					gm.setDb(temp_1[5]);
					gm.setType(temp_1[6]);
					
					
					if(isRange){
						gm.setStart((int)(Long.parseLong(temp_1[7]) / 1000));
						gm.setEnd((int)(Long.parseLong(temp_1[8]) / 1000));
					}else{
						gm.setStart(Integer.parseInt(temp_1[7]));
						gm.setEnd(Integer.parseInt(temp_1[8]));
					}
					
					gm.setScore(temp_1[9]);
					gm.setStrand(temp_1[10]);
					gm.setPhase(temp_1[11]);
					gm.setGeneName(temp_1[12]);
				
					if (!gm.getType().equals("region")) {
						
						if (legend_map.get(gm.getType()) == null) {
//							legend_map.put(gm.getType(), colors[color_index]);
							legend_map.put(gm.getType(), colors[randomRange(0, colors.length - 1)]);
						}
						
						if(isRange){
							if(is_full){
								if(gm.getEnd() - gm.getStart() > 100 && gm.getEnd() != gm.getStart()){
									gff.add(gm);
								}
							}else{
								if(gm.getEnd() != gm.getStart()){
									gff.add(gm);
								}
							}
						}else{
							gff.add(gm);
						}
					}
				}
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DrawVo drawVo = new DrawVo();
		drawVo.setLength(length);
		drawVo.setGff(gff);
		drawVo.setChromosomeMap(chromosome_map);
		drawVo.setLegendMap(legend_map);
		drawVo.setRange(isRange);
		
		return drawVo;
	}
	
	public Map<String, GeneVo> getMapData(String query, String db) {		
		// TODO Auto-generated method stub
		
		DrawVo vo = getDrawData(query, db, false);
		
		Map<String, GeneVo> map = new HashMap<String, GeneVo>();
		
		for(GFFVo gff : vo.getGff()){
			
			GeneVo genePositionVo = new GeneVo();
			genePositionVo.setStart(gff.getStart());
			genePositionVo.setEnd(gff.getEnd());
			genePositionVo.setGeneName(gff.getGeneName());
			map.put(gff.getGeneName(), genePositionVo);
		}
		
		return map;
	}
	
	public String getGene(String query, String db) {		
		// TODO Auto-generated method stub
		
		DrawVo vo = getDrawData(query, db, false);
		
		List<String> gene_name = new ArrayList<String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		 
		for(GFFVo gff : vo.getGff()){
			if(map.get(gff.getGeneName()) == null){
				map.put(gff.getGeneName(), 1);
			}else{
				map.put(gff.getGeneName(), map.get(gff.getGeneName()) + 1);
			}
		}
		
		for( Map.Entry<String, Integer> elem : map.entrySet() ){
			gene_name.add(elem.getKey());
        }
		
		return StringUtils.join(gene_name, ",");
	}
	
	public CircosVo makeCirocsStructure(String query, String db, boolean is_full) {
		// TODO Auto-generated method stub

		Map<String, FeatureSlot> feature_slot_map = new HashMap<String, FeatureSlot>();

		DrawVo vo = getDrawData(query, db, is_full);

		String speciesName = vo.getGff().get(0).getSpecies();

		System.out.println("tatal length: " + vo.getLength());

		Cgview circos = new Cgview(vo.getLength());
		
		circos.setWidth(1138);
		circos.setHeight(1200);
		circos.setBackboneRadius(250.0f);
		circos.setBackboneColor(new Color(196, 229, 246));
		circos.setBackboneThickness(10);
		circos.setMoveInnerLabelsToOuter(true);
		circos.setShowWarning(false);
		circos.setDrawLegends(true);
		circos.setShowShading(true);
		circos.setShiftSmallFeatures(true);
		circos.setRulerTextPadding(6.0f);
		circos.setLabelPlacementQuality(10);
		circos.setLabelLineLength(8.0d);
		circos.setLabelLineThickness(0.5f);
		circos.setFeatureSlotSpacing(0);
		circos.setShowBorder(false);
		circos.setRulerFont(new Font("Courier New", Font.PLAIN, 13));
		circos.setLabelFont(new Font("Courier New", Font.BOLD, 15));
		circos.setTitleFont(new Font("Courier New", Font.BOLD, 17));
		
		Legend titleLegend = new Legend(circos);
		titleLegend.setPosition(CgviewConstants.LEGEND_UPPER_LEFT);
		
		LegendItem alegendItem = new LegendItem(titleLegend);
		alegendItem.setLabel(speciesName);
		alegendItem.setFont(new Font("Courier New", Font.PLAIN, 20));
		alegendItem.setTextAlignment(CgviewConstants.LEGEND_ITEM_ALIGN_CENTER);

//		TreeMap<String, ChrVo> chr = new TreeMap<String, ChrVo>(vo.getChromosomeMap());
//		Iterator<String> chr_sort = chr.keySet().iterator();
//
//		while (chr_sort.hasNext()) {
//
//			String key = chr_sort.next();
//			ChrVo value = vo.getChromosomeMap().get(key);
//
//			LegendItem legendItem = new LegendItem(titleLegend);
//			legendItem.setLabel(value.getChr() + "[" + value.getSeqName() + "]");
//			legendItem.setFont(new Font("Courier New", Font.PLAIN, 20));
//			legendItem.setTextAlignment(CgviewConstants.LEGEND_ITEM_ALIGN_LEFT);
//		}

		LegendItem legendItem = new LegendItem(titleLegend);
		legendItem.setFont(new Font("Courier New", Font.PLAIN, 20));
		legendItem.setTextAlignment(CgviewConstants.LEGEND_ITEM_ALIGN_LEFT);

		if(vo.isRange()){
			legendItem.setLabel("Total Length: " + vo.getLength() + "kbp");
		}else{
			legendItem.setLabel("Total Length: " + vo.getLength() + "bp");
		}
		
		
		Legend regionLegend = new Legend(circos);
		regionLegend.setPosition(CgviewConstants.LEGEND_UPPER_RIGHT);
		regionLegend.setBackgroundColor(new Color(255, 255, 255));
		regionLegend.setFont(new Font("Courier New", Font.PLAIN, 13));
		regionLegend.setBackgroundOpacity(1.5f);
		

		TreeMap<String, Color> tm = new TreeMap<String, Color>(vo.getLegendMap());
		Iterator<String> treeMapIter = tm.keySet().iterator();

		while (treeMapIter.hasNext()) {

			String key = treeMapIter.next();
			Color value = vo.getLegendMap().get(key);
			
			if(!key.startsWith("chr_")){
				legendItem = new LegendItem(regionLegend);
				legendItem.setDrawSwatch(1);
				legendItem.setLabel(key);
				legendItem.setSwatchColor(value);
				
				System.out.println(key);
			}
		}

		System.out.println(vo.getLegendMap().size());

		CircosVo circosVo = new CircosVo();
		circosVo.setGffVOList(vo.getGff());
		circosVo.setLegendMap(vo.getLegendMap());
		circosVo.setFeatureSlotMap(feature_slot_map);
		circosVo.setCircos(circos);
		circosVo.setLength(vo.getLength());
		circosVo.setChromosomeMap(vo.getChromosomeMap());
		
		FeatureSlot chrFeatureSlot = new FeatureSlot(circosVo.getCircos(), CgviewConstants.DIRECT_STRAND);
		chrFeatureSlot.setFeatureThickness(15); // 두께.
		chrFeatureSlot.setShowShading(true);
		chrFeatureSlot.sortFeaturesByStart();
		
		for (Map.Entry<String, ChrVo> elem : circosVo.getChromosomeMap().entrySet()) {
			Feature feature = new Feature(chrFeatureSlot, elem.getValue().getChr());
			feature.setColor(circosVo.getLegendMap().get(elem.getValue().getChr()));
			feature.setShowShading(true);
			feature.setDecoration(CgviewConstants.BASES);

			FeatureRange featureRange = new FeatureRange(feature,
					elem.getValue().getStart() == 0 ? 1 : elem.getValue().getStart(), elem.getValue().getEnd());

			featureRange.setDecoration(CgviewConstants.DIRECT_STRAND);
		}

		FeatureSlot genFeatureSlot = new FeatureSlot(circosVo.getCircos(), CgviewConstants.DIRECT_STRAND);
		genFeatureSlot.setFeatureThickness(30); // 두께.
		genFeatureSlot.setShowShading(true);

		FeatureSlot otherFeatureSlot = new FeatureSlot(circosVo.getCircos(), CgviewConstants.DIRECT_STRAND);
		otherFeatureSlot.setFeatureThickness(30); // 두께.
		otherFeatureSlot.setShowShading(true);
		
		FeatureSlot etcFeatureSlot = new FeatureSlot(circosVo.getCircos(), CgviewConstants.DIRECT_STRAND);
		etcFeatureSlot.setFeatureThickness(30);
		etcFeatureSlot.setShowShading(true);

		boolean is_gene = false;

		Map<String, String> geneNameMap = new HashMap<String, String>();
		
		for (int i = 0; i < circosVo.getGffVOList().size(); i++) {

			GFFVo gffVO = circosVo.getGffVOList().get(i);
			
			Feature feature = null;

			is_gene = false;

			if (gffVO.getType().equals("gene") || gffVO.getType().equals("rRNA")
					|| gffVO.getType().equals("tRNA") || gffVO.getType().equals("repeat_region")
					|| gffVO.getType().equals("repeat_unit")) {
				
				if(gffVO.getType().equals("gene")){
					
					if (!is_full && geneNameMap.get(gffVO.getGeneName()) == null) {
						
						geneNameMap.put(gffVO.getGeneName(), gffVO.getGeneName());
						
						feature = new Feature(genFeatureSlot, gffVO.getGeneName());
					} else {
						feature = new Feature(genFeatureSlot);
					}
					
					is_gene = true;
				}else{
					feature = new Feature(genFeatureSlot);
				}
			} 
			
			else if (gffVO.getType().equals("mRNA") || gffVO.getType().equals("exon")
					|| gffVO.getType().equals("intron") ||  gffVO.getGeneName().equals("ncRNA")) {
				feature = new Feature(otherFeatureSlot);
			} 
			
			else if (!gffVO.getType().contains("chr_") && !gffVO.getType().equals("gene")
					&& !gffVO.getType().equals("rRNA") && !gffVO.getType().equals("tRNA")
					&& !gffVO.getType().equals("repeat_region") && !gffVO.getType().equals("repeat_unit")
					&& !gffVO.getType().equals("mRNA") && !gffVO.getType().equals("exon")
					&& !gffVO.getType().equals("intron")) {
				feature = new Feature(etcFeatureSlot);
			}

			if (feature != null) {

				feature.setColor(circosVo.getLegendMap().get(gffVO.getType()));

				FeatureRange featureRange = new FeatureRange(feature, gffVO.getStart(), gffVO.getEnd());

				if (!is_full && is_gene) {
					featureRange.setMouseover("start = [" + gffVO.getStart() + "], stop = [" + gffVO.getEnd() + "]");
//					featureRange.setHyperlink("http://localhost:8080/omics/getViewerFromCircos?tax_id=" + taxonomy + 
//					featureRange.setHyperlink("http://192.168.150.58:8080/omics/getViewerFromCircos?tax_id=" + taxonomy +
//							"&refseq_assembly_id="+gffVO.getAssemblyId()+
//							"&geneName=" + gffVO.getGeneName());
					featureRange.setHyperlink("http://192.168.150.58:8080/prometheus/getViewerFromCircos?"
							+ "proteinKey="+taxonomy+";"+gffVO.getAssemblyId()+";"+gffVO.getGeneName());
//					featureRange.setHyperlink("http://localhost:8080/omics/getViewerFromCircos?"
//							+ "proteinKey="+taxonomy+";"+gffVO.getAssemblyId()+";"+gffVO.getGeneName());
				}
				
				if (is_gene) {
					featureRange.setDecoration(CgviewConstants.DECORATION_STANDARD);
				} else {
					if (gffVO.getStrand().equals(plus)) {
						featureRange.setDecoration(CgviewConstants.DECORATION_CLOCKWISE_ARROW);
					} else if (gffVO.getStrand().equals(minus)) {
						featureRange.setDecoration(CgviewConstants.DECORATION_COUNTERCLOCKWISE_ARROW);
					} else {
						featureRange.setDecoration(CgviewConstants.DECORATION_STANDARD);
					}
				}
			}
		}
		
		return circosVo;
	}

	public SGVVo full(String context_path, String query, String db) {
		// TODO Auto-generated method stub
		
		CircosVo circosVo = makeCirocsStructure(query, db, true);

		String png_path = context_path + "resources/images/circos/%s/%s_full.png";
		
		logger.info("context_path : " + context_path);
		
		File png_file = new File(String.format(png_path, query, query));
		
		if(!png_file.getParentFile().exists()){
			png_file.getParentFile().mkdirs();
		}
		
		try {
			if(!png_file.exists()){
				CgviewIO.writeToPNGFile(circosVo.getCircos(), png_file.getAbsolutePath());
			}else{
				System.out.println("1111");
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
		SGVVo vo = new SGVVo();
		vo.setSgv(String.format(png_path, query, query).replace(context_path, ""));
		vo.setLength(circosVo.getLength());
		
		return vo;
	}

	@SuppressWarnings("deprecation")
	public SGVVo zoom(String context_path, double zoom, String geneName, String query, String db) {
		// TODO Auto-generated method stub
		
		CircosVo circosVo = makeCirocsStructure(query, db, false);
		
		GeneVo geneVo = getMapData(query, db).get(geneName);
		
		SGVVo sgvVo = new SGVVo();
		
		try {
			boolean embedFonts = true;
			boolean useCompression = false;
			boolean usePreviouslyDrawnLabels = false;
			
			String path = context_path + "resources/images/circos/%s/%s.sgv";
			
			File file = new File(String.format(path, query, query));
			
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			
			circosVo.getCircos().setDesiredZoom(zoom);
			circosVo.getCircos().setDesiredZoomCenter(geneVo.getStart());
			
			CgviewIO.writeToSVGFile(circosVo.getCircos(), file.getAbsolutePath(), embedFonts, useCompression,
					usePreviouslyDrawnLabels);

			sgvVo.setSgv(FileUtils.readFileToString(file));
			sgvVo.setLength(circosVo.getLength());
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
		return sgvVo;
	}
	
	@SuppressWarnings("deprecation")
	public SGVVo zoom(String context_path, double zoom, int position, String query, String db) {
		// TODO Auto-generated method stub
		
		CircosVo circosVo = makeCirocsStructure(query, db, false);
		
		SGVVo sgvVo = new SGVVo();
		
		try {
			boolean embedFonts = true;
			boolean useCompression = false;
			boolean usePreviouslyDrawnLabels = false;
			
			String path = context_path + "resources/images/circos/%s/%s.sgv";
			
			File file = new File(String.format(path, query, query));
			
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			
			circosVo.getCircos().setDesiredZoom(zoom);
			circosVo.getCircos().setDesiredZoomCenter(position);
			
			CgviewIO.writeToSVGFile(circosVo.getCircos(), file.getAbsolutePath(), embedFonts, useCompression,
					usePreviouslyDrawnLabels);

			sgvVo.setSgv(FileUtils.readFileToString(file));
			sgvVo.setLength(circosVo.getLength());
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
		return sgvVo;
	}
	
	public List<EntrezVo> getEntrezRcord(String taxonomy_id){
		
		String url = String.format(ncbi_url, taxonomy_id);
		String hrefValue = null;
		
		List<EntrezVo> listVO = new ArrayList<>();
		
		try {

			Document doc = null;
			Connection con = Jsoup.connect(url);
			doc = con.get();

			Elements copys = doc.select("body form table td table tr td a");
			
			for (Element copy : copys) {
				
				hrefValue = copy.attr("href");

				EntrezVo entrezVO = new EntrezVo();

				if (hrefValue.contains("/nuccore/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Nucleotide");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/nucest/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Nucleotide EST");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/nucgss/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Nucleotide GSS");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/protein/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Protein");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/structure/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Structure");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/genome/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Genome");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/popset/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Popset");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/snp/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("SNP");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/cdd/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Domains");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/gds/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("GEO Datasets");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/unigene/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("UniGene");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/pmc/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Pubmed Central");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/gene/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Gene");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/homologene/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("HomoloGene");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/sra/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("SRA Experiments");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/probe/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Probe");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/assembly/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Assembly");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/bioproject/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Bio Project");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/biosample/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Bio Sample");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/biosystems/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Bio Systems");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/clone/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Clone DB");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/dbvar/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("dbVar");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/geoprofiles/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("GEO Porfiles");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/pcassay/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("PubChem BioAssay");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/proteinclusters/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Protein Clusters");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				} else if (hrefValue.contains("/taxonomy/?term=") && hrefValue.contains("noexp")) {
					entrezVO.setDatabaseNm("Taxonomy");
					entrezVO.setDatabaseLinks(hrefValue);
					entrezVO.setLinksCount(copy.text());
				}
				listVO.add(entrezVO);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listVO;
	}
}