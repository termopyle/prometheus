package org.kobic.omics.viewer.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.kobic.omics.interpro.vo.TaxIdWithDbVo;
import org.kobic.omics.viewer.mapper.ViewerMapper;
import org.kobic.omics.viewer.vo.DomainVo;
import org.kobic.omics.viewer.vo.FeatureVo;
import org.kobic.omics.viewer.vo.GeneStructureVo;
import org.kobic.omics.viewer.vo.MultilocVo;
import org.kobic.omics.viewer.vo.OrthoMclVo;
import org.kobic.omics.viewer.vo.OrthologVO;
import org.kobic.omics.viewer.vo.ParalogVO;
import org.kobic.omics.viewer.vo.ProteinWithDomainVo;
import org.kobic.omics.viewer.vo.TargetpVo;
import org.kobic.omics.viewer.vo.TmHmmVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service(value = "viewerService")
public class ViewerService {
	
	private static final Logger logger = LoggerFactory.getLogger(ViewerService.class);
	
	@Resource(name = "viewerMapper")
	private ViewerMapper viewerMapper;

	public ProteinWithDomainVo getProteinDomains( String taxId, String refSeqId, String proteinId ) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("tax_id", taxId);
		map.put("refseq_assembly_id", refSeqId);
		map.put("protein_accession_id", proteinId);
		
		List<DomainVo> domainList = this.viewerMapper.searchInterproDomains( map );

		ProteinWithDomainVo proteinVo = new ProteinWithDomainVo( domainList );
		
//		SharkClient client = new SharkClient();
//
//		List<OmicsModel> list = client.getOmics( taxId, refSeqId, proteinId ).getOmicsData();
//
//		ProteinWithDomainVo proteinVo = new ProteinWithDomainVo( list );
//
//		client.close();

/***
		ProteinWithDomainVo proteinVo = new ProteinWithDomainVo("358766", "Methanoregula boonei", "YP_001404333.1", 4079);
		proteinVo.addDomain( new DomainVo("2558515","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1740,1773,4.799997768E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558516","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3202,3235,2.900000363E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558517","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",516,549,2.599999467E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558518","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3168,3201,1.400000458E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558519","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3984,4017,1.0E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558520","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",380,413,5.800002907E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558521","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1502,1535,0.001799999517,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558522","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3372,3405,2.700001836E-9,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558523","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1162,1195,3.600000387E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558524","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3338,3371,0.004500000347,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558525","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",652,685,1.900000685E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558526","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",788,821,1.400000458E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558527","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3712,3745,6.800002006E-8,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558528","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",686,719,0.001100000299,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558529","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",448,481,1.299999958E-4,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558530","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2624,2657,1.300000672E-8,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558531","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2284,2317,1.100000299E-4,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558532","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",584,617,2.19999901E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558533","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2862,2895,0.003399999724,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558534","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1876,1909,3.00000068E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558535","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1842,1875,0.002200000218,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558536","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3848,3881,9.399999748E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558537","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1468,1501,5.099998824E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558538","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2386,2419,2.19999901E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558539","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1434,1467,1.199999857E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558540","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",40,73,1.800001493E-10,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558541","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1400,1433,1.800001493E-9,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558542","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1196,1229,3.599996434E-10,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558543","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1264,1297,1.39999892E-9,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558544","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1026,1059,4.100000492E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558545","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2794,2827,3.899998618E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558546","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3780,3813,9.200004347E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558547","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",720,753,1.300000672E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558548","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",414,447,1.199999857E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558549","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2182,2215,2.100000373E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558550","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",754,787,1.300000672E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558551","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2046,2079,7.899996099E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558552","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",890,923,1.0E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558553","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2930,2963,0.02900000363,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558554","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",856,889,5.299999485E-4,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558555","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3882,3915,4.100000492E-8,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558556","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3508,3541,4.500000347E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558557","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2556,2589,5.499999426E-4,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558558","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3304,3337,1.0E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558559","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3678,3711,3.799998626E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558560","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2828,2861,5.300002395E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558561","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3066,3099,5.399999774E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558562","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",4018,4051,1.199999857E-8,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558563","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2420,2453,6.400000388E-9,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558564","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",74,107,0.01199999857,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558565","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2148,2181,0.02999999856,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558566","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2318,2351,0.001100000299,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558567","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",6,39,3.600000387E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558568","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2692,2725,2.799998894E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558569","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2590,2623,1.39999892E-9,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558570","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3440,3473,1.300000672E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558571","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2964,2997,0.04500000347,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558572","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1060,1093,7.899996099E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558573","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1706,1739,7.499996059E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558574","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1910,1943,4.899999518E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558575","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",822,855,2.399997982E-10,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558576","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1672,1705,2.000000752E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558577","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",618,651,6.599998525E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558578","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1332,1365,1.60000065E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558579","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",312,345,3.499998511E-8,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558580","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3814,3847,0.004600000443,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558581","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1536,1569,6.099999771E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558582","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",958,991,2.000000752E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558583","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1094,1127,2.200000218E-4,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558584","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3270,3303,1.499999776E-4,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558585","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1774,1807,0.1299999958,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558586","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3236,3269,8.299995812E-8,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558587","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",244,277,9.00000408E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558588","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1128,1161,0.09899999026,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558589","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3610,3643,9.899996308E-8,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558590","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2896,2929,1.199999857E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558591","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2250,2283,0.03199999869,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558592","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2454,2487,0.005000000864,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558593","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3100,3133,0.01800000011,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558594","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",210,243,0.1599999991,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558595","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",482,515,5.599999894E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558596","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3644,3677,1.699999223E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558597","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2080,2113,1.100000299E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558598","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2012,2045,5.500005465E-9,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558599","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2114,2147,2.000000752E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558600","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",108,141,6.000003616E-9,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558601","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2658,2691,1.100000299E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558602","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3474,3507,4.300001706E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558603","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3746,3779,1.100000299E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558604","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2726,2759,2.19999901E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558605","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2216,2249,5.999997029E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558606","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3406,3439,5.399999774E-8,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558607","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3576,3609,4.299999346E-4,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558608","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1570,1603,1.0E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558609","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3134,3167,4.700000687E-4,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558610","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1944,1977,3.600000387E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558611","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",176,209,1.499999776E-10,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558612","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",924,957,1.100000299E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558613","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2998,3031,0.002599999467,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558614","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1808,1841,2.100000373E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558615","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2488,2521,9.300000485E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558616","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",550,583,1.499999776E-8,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558617","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3916,3949,1.400000458E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558618","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3950,3983,2.599999467E-8,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558619","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2352,2385,5.799999723E-4,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558620","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1366,1399,1.100000299E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558621","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",278,311,1.799999517E-4,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558622","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1978,2011,0.001499999776,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558623","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",142,175,1.100000299E-8,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558624","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3032,3065,6.999999656E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558625","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1230,1263,9.599999147E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558626","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2522,2555,5.499999426E-7,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558627","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",2760,2793,2.299999357E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558628","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",3542,3575,3.00000068E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558629","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1638,1671,2.500000864E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558630","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",1298,1331,1.499999776E-6,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558631","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",346,379,0.005000000864,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558632","SMART","SM00028","Tetratricopeptide repeats","http://smart.embl.de/smart/do_annotation.pl?DOMAIN=SM00028",992,1025,1.199999857E-5,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558633","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2556,2589,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005524|GO:0016887") );
		proteinVo.addDomain( new DomainVo("2558634","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1468,1501,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558635","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",4018,4051,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558636","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2352,2385,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558637","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1366,1399,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558638","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3542,3575,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558639","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1128,1161,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558640","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3712,3745,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558641","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",482,515,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558642","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1944,1977,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558643","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2250,2283,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558644","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",210,243,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558645","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",890,923,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558646","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1060,1093,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558647","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",788,821,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558648","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",856,889,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558649","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1842,1875,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558650","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3168,3201,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558651","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2930,2963,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558652","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1672,1705,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558653","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1400,1433,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558654","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3950,3983,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558655","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3100,3133,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558656","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1502,1535,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558657","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3236,3269,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558658","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2658,2691,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558659","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3474,3507,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558660","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1298,1331,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558661","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2488,2521,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558662","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1740,1773,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558663","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2080,2113,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558664","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3610,3643,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558665","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",108,141,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558666","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2114,2147,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558667","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",278,311,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558668","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",584,617,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558669","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1706,1739,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558670","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",346,379,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558671","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2420,2453,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558672","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",652,685,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558673","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2522,2555,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558674","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1536,1569,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558675","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2726,2759,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558676","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2760,2793,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558677","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",992,1025,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558678","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",380,413,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558679","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2386,2419,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558680","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",414,447,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558681","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2692,2725,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558682","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1638,1671,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558683","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3066,3099,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558684","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3746,3779,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558685","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",244,277,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558686","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3644,3677,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558687","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2624,2657,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558688","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3882,3915,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558689","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2454,2487,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558690","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",958,991,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558691","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3338,3371,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558692","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",516,549,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558693","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",312,345,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558694","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1570,1603,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558695","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2148,2181,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558696","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3780,3813,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558697","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2046,2079,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558698","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",550,583,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558699","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",618,651,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558700","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1230,1263,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558701","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3304,3337,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558702","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3848,3881,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558703","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",176,209,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558704","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",720,753,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558705","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",822,855,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558706","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2182,2215,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558707","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2012,2045,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558708","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1910,1943,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558709","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",6,39,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005524|GO:0016887") );
		proteinVo.addDomain( new DomainVo("2558710","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1094,1127,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558711","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2284,2317,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558712","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2794,2827,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558713","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2998,3031,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558714","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1264,1297,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558715","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2896,2929,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558716","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3372,3405,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558717","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",74,107,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558718","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3814,3847,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558719","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3576,3609,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558720","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1196,1229,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558721","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",754,787,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558722","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3270,3303,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558723","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2590,2623,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558724","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3202,3235,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558725","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2828,2861,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558726","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2964,2997,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558727","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1434,1467,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558728","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3032,3065,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558729","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3678,3711,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558730","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",40,73,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558731","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1604,1637,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558732","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",142,175,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558733","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2318,2351,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558734","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",4052,4079,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558735","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1808,1841,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558736","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3134,3167,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558737","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",686,719,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558738","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3508,3541,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558739","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",448,481,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558740","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3440,3473,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558741","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3406,3439,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558742","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1978,2011,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558743","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1332,1365,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558744","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2216,2249,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558745","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",2862,2895,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558746","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1876,1909,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558747","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1162,1195,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558748","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",1026,1059,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558749","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3916,3949,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558750","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",924,957,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558751","ProSiteProfiles","PS50005","TPR repeat profile.","http://prosite.expasy.org/PS50005",3984,4017,0.0,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558752","PANTHER","PTHR23083:SF364","Unknown","http://www.pantherdb.org/panther/family.do?clsAccession=PTHR23083:SF364",10,4058,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558753","Pfam","PF00515","Tetratricopeptide repeat","http://pfam.xfam.org/family/PF00515",1436,1467,1.4E-4,"IPR001440","Tetratricopeptide TPR1","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558754","Pfam","PF00515","Tetratricopeptide repeat","http://pfam.xfam.org/family/PF00515",3678,3711,1.9E-4,"IPR001440","Tetratricopeptide TPR1","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558755","Pfam","PF00515","Tetratricopeptide repeat","http://pfam.xfam.org/family/PF00515",6,38,1.9E-4,"IPR001440","Tetratricopeptide TPR1","GO:0005524|GO:0016887") );
		proteinVo.addDomain( new DomainVo("2558756","Pfam","PF00515","Tetratricopeptide repeat","http://pfam.xfam.org/family/PF00515",1638,1668,0.021,"IPR001440","Tetratricopeptide TPR1","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558757","Gene3D","G3DSA:1.25.40.10","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.10",3820,4079,2.2E-73,"IPR011990","Tetratricopeptide-like helical domain","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558758","Gene3D","G3DSA:1.25.40.10","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.10",3443,3573,3.0E-29,"IPR011990","Tetratricopeptide-like helical domain","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558759","Gene3D","G3DSA:1.25.40.10","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.10",4,143,1.2E-38,"IPR011990","Tetratricopeptide-like helical domain","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558760","Gene3D","G3DSA:1.25.40.10","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.10",2796,3033,2.1E-54,"IPR011990","Tetratricopeptide-like helical domain","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558761","Gene3D","G3DSA:1.25.40.10","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.10",2390,2499,1.7E-27,"IPR011990","Tetratricopeptide-like helical domain","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558762","Gene3D","G3DSA:1.25.40.10","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.10",3034,3171,1.6E-27,"IPR011990","Tetratricopeptide-like helical domain","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558763","Gene3D","G3DSA:1.25.40.10","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.10",893,1131,1.5E-59,"IPR011990","Tetratricopeptide-like helical domain","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558764","Gene3D","G3DSA:1.25.40.10","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.10",3574,3819,1.8E-61,"IPR011990","Tetratricopeptide-like helical domain","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558765","Gene3D","G3DSA:1.25.40.10","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.10",1410,1618,7.5E-55,"IPR011990","Tetratricopeptide-like helical domain","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558766","Gene3D","G3DSA:1.25.40.10","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.10",666,892,2.5E-65,"IPR011990","Tetratricopeptide-like helical domain","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558767","Gene3D","G3DSA:1.25.40.10","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.10",3199,3442,2.9E-66,"IPR011990","Tetratricopeptide-like helical domain","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558768","Pfam","PF07719","Tetratricopeptide repeat","http://pfam.xfam.org/family/PF07719",244,275,1.8E-4,"IPR013105","Tetratricopeptide TPR2","") );
		proteinVo.addDomain( new DomainVo("2558769","Pfam","PF07719","Tetratricopeptide repeat","http://pfam.xfam.org/family/PF07719",586,617,0.0013,"IPR013105","Tetratricopeptide TPR2","") );
		proteinVo.addDomain( new DomainVo("2558770","Pfam","PF07719","Tetratricopeptide repeat","http://pfam.xfam.org/family/PF07719",689,719,0.039,"IPR013105","Tetratricopeptide TPR2","") );
		proteinVo.addDomain( new DomainVo("2558771","ProSiteProfiles","PS50293","TPR repeat region circular profile.","http://prosite.expasy.org/PS50293",1,4079,0.0,"IPR013026","Tetratricopeptide repeat-containing domain","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558772","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",1268,1572,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558773","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",455,749,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558774","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",3783,4079,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558775","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",3164,3472,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558776","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",199,487,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558777","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",3444,3780,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558778","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",2325,2663,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558779","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",2937,3241,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558780","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",954,1298,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558781","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",1775,2014,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558782","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",731,994,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558783","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",1954,2320,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558784","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",5,246,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558785","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",1537,1773,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558786","SUPERFAMILY","SSF48452","Unknown","http://supfam.cs.bris.ac.uk/SUPERFAMILY/cgi-bin/scop.cgi?ipid=SSF48452",2594,2963,0.0,"","","") );
		proteinVo.addDomain( new DomainVo("2558787","Gene3D","G3DSA:1.25.40.120","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.120",429,665,1.5E-65,"","","") );
		proteinVo.addDomain( new DomainVo("2558788","Gene3D","G3DSA:1.25.40.120","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.120",1146,1409,4.2E-69,"","","") );
		proteinVo.addDomain( new DomainVo("2558789","Gene3D","G3DSA:1.25.40.120","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.120",1897,2073,8.3E-37,"","","") );
		proteinVo.addDomain( new DomainVo("2558790","Gene3D","G3DSA:1.25.40.120","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.120",2074,2380,1.7E-65,"","","") );
		proteinVo.addDomain( new DomainVo("2558791","Gene3D","G3DSA:1.25.40.120","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.120",1619,1896,7.1E-64,"","","") );
		proteinVo.addDomain( new DomainVo("2558792","Gene3D","G3DSA:1.25.40.120","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.120",2500,2787,9.199999999E-71,"","","") );
		proteinVo.addDomain( new DomainVo("2558793","Gene3D","G3DSA:1.25.40.120","Unknown","http://www.cathdb.info/version/latest/superfamily/1.25.40.120",144,428,2.6E-75,"","","") );
		proteinVo.addDomain( new DomainVo("2558794","Pfam","PF13181","Tetratricopeptide repeat","http://pfam.xfam.org/family/PF13181",1265,1295,0.48,"IPR019734","Tetratricopeptide repeat","GO:0005515") );
		proteinVo.addDomain( new DomainVo("2558795","Pfam","PF13432","Tetratricopeptide repeat","http://pfam.xfam.org/family/PF13432",2941,2989,1.1E-6,"","","") );
		proteinVo.addDomain( new DomainVo("2558796","Pfam","PF13432","Tetratricopeptide repeat","http://pfam.xfam.org/family/PF13432",622,685,3.0E-9,"","","") );
		proteinVo.addDomain( new DomainVo("2558797","Pfam","PF13432","Tetratricopeptide repeat","http://pfam.xfam.org/family/PF13432",2461,2517,1.2E-6,"","","") );
		proteinVo.addDomain( new DomainVo("2558798","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",344,411,9.1E-8,"","","") );
		proteinVo.addDomain( new DomainVo("2558799","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",721,785,3.3E-9,"","","") );
		proteinVo.addDomain( new DomainVo("2558800","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",40,103,3.0E-12,"","","") );
		proteinVo.addDomain( new DomainVo("2558801","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",1140,1191,2.9E-7,"","","") );
		proteinVo.addDomain( new DomainVo("2558802","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",3847,3913,1.2E-12,"","","") );
		proteinVo.addDomain( new DomainVo("2558803","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",2316,2382,7.0E-9,"","","") );
		proteinVo.addDomain( new DomainVo("2558804","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",2590,2655,9.9E-16,"","","") );
		proteinVo.addDomain( new DomainVo("2558805","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",1671,1737,6.7E-9,"","","") );
		proteinVo.addDomain( new DomainVo("2558806","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",3166,3233,1.1E-11,"","","") );
		proteinVo.addDomain( new DomainVo("2558807","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",925,989,9.4E-8,"","","") );
		proteinVo.addDomain( new DomainVo("2558808","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",4020,4079,8.9E-10,"","","") );
		proteinVo.addDomain( new DomainVo("2558809","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",1196,1258,6.3E-12,"","","") );
		proteinVo.addDomain( new DomainVo("2558810","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",279,343,2.6E-11,"","","") );
		proteinVo.addDomain( new DomainVo("2558811","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",175,233,4.8E-11,"","","") );
		proteinVo.addDomain( new DomainVo("2558812","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",3510,3569,4.8E-11,"","","") );
		proteinVo.addDomain( new DomainVo("2558813","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",514,581,6.8E-14,"","","") );
		proteinVo.addDomain( new DomainVo("2558814","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",3611,3675,4.1E-9,"","","") );
		proteinVo.addDomain( new DomainVo("2558815","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",3948,4015,3.3E-13,"","","") );
		proteinVo.addDomain( new DomainVo("2558816","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",414,479,5.7E-10,"","","") );
		proteinVo.addDomain( new DomainVo("2558817","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",1468,1533,1.6E-8,"","","") );
		proteinVo.addDomain( new DomainVo("2558818","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",2254,2315,5.1E-9,"","","") );
		proteinVo.addDomain( new DomainVo("2558819","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",1739,1803,5.8E-8,"","","") );
		proteinVo.addDomain( new DomainVo("2558820","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",2864,2927,1.0E-9,"","","") );
		proteinVo.addDomain( new DomainVo("2558821","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",3032,3097,3.0E-12,"","","") );
		proteinVo.addDomain( new DomainVo("2558822","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",1878,1941,8.9E-10,"","","") );
		proteinVo.addDomain( new DomainVo("2558823","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",2796,2858,2.3E-10,"","","") );
		proteinVo.addDomain( new DomainVo("2558824","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",2391,2451,4.0E-13,"","","") );
		proteinVo.addDomain( new DomainVo("2558825","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",3780,3845,7.2E-12,"","","") );
		proteinVo.addDomain( new DomainVo("2558826","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",2660,2723,9.5E-9,"","","") );
		proteinVo.addDomain( new DomainVo("2558827","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",106,170,1.0E-13,"","","") );
		proteinVo.addDomain( new DomainVo("2558828","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",1808,1869,4.2E-9,"","","") );
		proteinVo.addDomain( new DomainVo("2558829","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",2010,2077,1.0E-11,"","","") );
		proteinVo.addDomain( new DomainVo("2558830","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",1065,1124,1.9E-8,"","","") );
		proteinVo.addDomain( new DomainVo("2558831","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",3375,3435,4.8E-13,"","","") );
		proteinVo.addDomain( new DomainVo("2558832","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",3440,3505,8.2E-10,"","","") );
		proteinVo.addDomain( new DomainVo("2558833","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",787,853,3.3E-13,"","","") );
		proteinVo.addDomain( new DomainVo("2558834","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",1296,1363,1.0E-9,"","","") );
		proteinVo.addDomain( new DomainVo("2558835","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",3712,3777,2.8E-11,"","","") );
		proteinVo.addDomain( new DomainVo("2558836","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",3235,3297,3.1E-10,"","","") );
		proteinVo.addDomain( new DomainVo("2558837","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",2521,2583,1.9E-9,"","","") );
		proteinVo.addDomain( new DomainVo("2558838","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",855,920,3.2E-10,"","","") );
		proteinVo.addDomain( new DomainVo("2558839","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",1536,1601,1.2E-10,"","","") );
		proteinVo.addDomain( new DomainVo("2558840","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",3102,3162,5.4E-7,"","","") );
		proteinVo.addDomain( new DomainVo("2558841","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",1944,2007,5.2E-10,"","","") );
		proteinVo.addDomain( new DomainVo("2558842","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",2728,2791,2.5E-10,"","","") );
		proteinVo.addDomain( new DomainVo("2558843","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",1366,1431,2.5E-15,"","","") );
		proteinVo.addDomain( new DomainVo("2558844","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",3304,3369,2.6E-7,"","","") );
		proteinVo.addDomain( new DomainVo("2558845","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",2079,2145,2.4E-8,"","","") );
		proteinVo.addDomain( new DomainVo("2558846","Pfam","PF13414","TPR repeat","http://pfam.xfam.org/family/PF13414",994,1056,4.0E-7,"","","") );
		proteinVo.addDomain( new DomainVo("2558847","Pfam","PF13424","Tetratricopeptide repeat","http://pfam.xfam.org/family/PF13424",2182,2247,2.3E-9,"","","") );
**/

		logger.debug("get protein domains");

		// TODO Auto-generated method stub
		return proteinVo;
	}
	
	private Map<String, List<FeatureVo>> seperatedByFeature( List<FeatureVo> featureList ) {
		Map<String, List<FeatureVo>> map = new HashMap<String, List<FeatureVo>>();
		for(int i=0; i<featureList.size(); i++) {
			FeatureVo feature = featureList.get(i);
			
			if( map.containsKey( feature.getFeature() ) ) {
				map.get(feature.getFeature()).add( feature );
			}else {
				List<FeatureVo> list = new ArrayList<FeatureVo>();
				list.add( feature );
				map.put( feature.getFeature(), list );
			}
		}
		return map;
	}
	
	private void differentiateLayer( List<FeatureVo> list ) {
		Map<String, List<FeatureVo>> hashMap = new HashMap<String, List<FeatureVo>>();

		List<FeatureVo> lst = new ArrayList<FeatureVo>();
		
		FeatureVo firstFeature = list.get(0);
		firstFeature.setLayerNo(1);
		lst.add( firstFeature );
		hashMap.put("1", lst );

		for( int i=1; i<list.size(); i++ ) {
			FeatureVo currentFeature = list.get(i);

			int depth = hashMap.keySet().size();
			boolean isOverlapped = false;
			
			for(int j=1; j<=depth; j++) {
				List<FeatureVo> currentLayerElements = hashMap.get(Integer.toString(j));
				
				for(int k=0; k<currentLayerElements.size(); k++) {
					isOverlapped = currentFeature.isOverlapped( currentLayerElements.get(k) );
					
					if( isOverlapped == true )
						break;
				}

				if( isOverlapped == false ) {
					FeatureVo baseElement = currentLayerElements.get(0);
				
					currentFeature.setLayerNo( baseElement.getLayerNo() );
				
					if( baseElement.getStart() > baseElement.getStart() ) currentLayerElements.add(0, currentFeature );
					else currentLayerElements.add(currentFeature);

					break;
				}
			}
			
			if( isOverlapped == true ) {
				int newDepth = depth + 1;
				List<FeatureVo> layerNew = new ArrayList<FeatureVo>();

				currentFeature.setLayerNo( newDepth );
				
				layerNew.add( currentFeature );

				hashMap.put( Integer.toString( newDepth ), layerNew );
			}
		}
	}
	
	private void makeLayerNo( List<FeatureVo> featureList ) {
		Map<String, List<FeatureVo>> map = this.seperatedByFeature(featureList);

		for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
			String featureType = iter.next();

			this.differentiateLayer( map.get(featureType) );
		}
	}

	public GeneStructureVo getGeneStructure( String taxId, String proteinId, String refSeqId ) {
		// TODO Auto-generated method stub
		
//		String tableName = this.viewerMapper.getKingdomByTaxId( taxId );
		List<TaxIdWithDbVo> kingdomTables = this.getKingdomTablesMap(taxId, refSeqId, proteinId, "genomicgff");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put( "tax_id", taxId );
		paramMap.put( "keyword", proteinId );
		paramMap.put( "refseq_assembly_id", refSeqId);
		
		GeneStructureVo geneStr = null;
		List<FeatureVo> featureList = null;
		for(TaxIdWithDbVo vo : kingdomTables) {
			paramMap.put( "tableName", vo.getResult() );
			paramMap.put( "db", vo.getDb() );
			
			if(vo.getDb().equals("RefSeq"))	{
				geneStr = (GeneStructureVo) this.viewerMapper.getGeneInfoByRefSeq( paramMap );
				featureList = this.viewerMapper.getGeneStructureByRefSeq( paramMap );
			} else {
				geneStr = (GeneStructureVo) this.viewerMapper.getGeneInfo( paramMap );
				featureList = this.viewerMapper.getGeneStructure( paramMap );
			}
			
			this.makeLayerNo( featureList );

			if( geneStr != null ) {
				geneStr.setFeatures( featureList );
				break;
			}
		}
		logger.debug("get gene structure");
		
		return geneStr;
	}

	public TmHmmVo getTMHMM( String tax_id, String protein_accession_id, String refseq_assembly_id ) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tax_id", tax_id);
		map.put("protein_accession_id", protein_accession_id);
		map.put("refseq_assembly_id", refseq_assembly_id);

		TmHmmVo thm = this.viewerMapper.getTMHMM( map );
		
		if(thm != null)	thm.setElementList( thm.getLocation() );
		
		return thm;
	}
	
	public MultilocVo getMultilocResult(String refseq_assembly_id, String protein_accession_id, String tax_id){
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("refseq_assembly_id", refseq_assembly_id);
		map.put("protein_accession_id", protein_accession_id);
		map.put("tax_id", tax_id);
		
		return viewerMapper.getMultiloc(map);
	}
	
	public TargetpVo getTargetpResult(String tax_id, String refseq_assembly_id, String protein_accession_id){
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("tax_id", tax_id);
		map.put("refseq_assembly_id", refseq_assembly_id);
		map.put("protein_accession_id", protein_accession_id);
		
		return viewerMapper.getTargetp(map);
	}

	public List<FeatureVo> findGeneFromProtein( String tax_id, String protein_id ) {
//		String tableName = this.viewerMapper.getKingdomByTaxId(tax_id);
//		if( tableName != null ) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("tax_id", tax_id);
			map.put("protein_id", protein_id);
//			map.put("tableName", tableName );
			
			return viewerMapper.findGeneFromProtein( map );
//		}
//		return null;
	}
	
	public List<OrthologVO> findOrtholog( String tax_id, String protein_id ) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tax_id", tax_id);
		map.put("protein_id", protein_id);
		
		return viewerMapper.findOrtholog(map);
	}
	
	public List<ParalogVO> findParalog( String tax_id, String protein_id ) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tax_id", tax_id);
		map.put("protein_id", protein_id);
		
		return viewerMapper.findParalog(map);
	}
	
	public static String getFileNameFromProteinID ( Map<String, String> map, String type) {
		String protein_nm = map.get("tax_id") + "_" + map.get("refseq_assembly_id") + "_" + map.get("protein_accession_id");
		
		String filePath = protein_nm + "_" + type + ".fa";
		
		return filePath;
	}
	
	public List<TaxIdWithDbVo> getKingdomTablesMap(String tax_id, String assembly_accession
			, String protein_accession_version, String type) {
		Map<String, String> kingdomMap = new HashMap<String, String>();
		
		kingdomMap.put("tax_id", tax_id);
		if( type.contains("Peptide") ) {
			kingdomMap.put("type", "proteinfaa");
			kingdomMap.put("modified", "");
		} else if ( type.contains("CDS") ) {
			kingdomMap.put("type", "rnafna");
			kingdomMap.put("modified", "");
		} else {
			kingdomMap.put("type", "genomicgff");
			kingdomMap.put("modified", "_modified");
		}
		
		List<TaxIdWithDbVo> kingdomTables = this.viewerMapper.getKingdomTablesByTaxId( kingdomMap );
		
		return kingdomTables;
	}
	
	public String getSequenceAndCDS(String tax_id, String assembly_accession, String protein_accession_version, String type) {
		String result = null;
		
		List<TaxIdWithDbVo> kingdomTables = this.getKingdomTablesMap(tax_id, assembly_accession, protein_accession_version, type);

		for(TaxIdWithDbVo vo : kingdomTables) {
//			if(type.equals("CDS") && vo.getDb().equals("RefSeq") && vo.getKingdom().equals("Bacteria")) {
//				result = "RefSeq_Bacteria";
//				break;
//			}

			Map<String, String> map = new HashMap<String, String>();
			
			map.put( "TaxId", tax_id );
			map.put( "RefSeqAssemblyID", assembly_accession );
			map.put( "ProteinID", protein_accession_version );
			map.put( "kingdom_table_query", vo.getResult() );
			map.put( "type", vo.getKingdom().toUpperCase() );
			map.put( "db", vo.getDb() );
			
			if(type.equals("Peptide"))	result = this.viewerMapper.getSequence(map);
			else if(type.equals("CDS"))	result = this.viewerMapper.getCDS(map);
			
			if( result != null && result.trim().length() > 0 )	break;
		}
		
		return result;
	}
	
	public String getSequenceDownload(Map<String, String> map, String type, String filePath) throws Exception {
	    // 파일 객체 생성
		File file = new File(filePath);
		FileWriter fw = null;
		BufferedWriter bw = null;
	
		try {
	//			if(file.isFile())
	//				return filePath;
	//			else {
				fw = new FileWriter(file, false);
				bw = new BufferedWriter(fw);
				
				String protein_nm = "tax_id:" + map.get("tax_id") + "; assembly_id:" + map.get("refseq_assembly_id") + "; protein_id:" + map.get("protein_accession_id") + "; organism:" + map.get("species");
				String sequenceAndCDS = this.getSequenceAndCDS(map.get("tax_id"), map.get("refseq_assembly_id"), map.get("protein_accession_id"), type);
				String data = ">" + protein_nm + "\n" + sequenceAndCDS + "\n";
				bw.write(data);
	//			}
		} finally{
			// BufferedWriter FileWriter를 닫아준다.
			if(bw != null) try{bw.close();}catch(IOException e){}
			if(fw != null) try{fw.close();}catch(IOException e){}
		}
	
		return filePath;
	}
	
	public String getProteinID( String tax_id, String assembly_accession, String geneName) {
		
		List<TaxIdWithDbVo> kingdomTable = this.viewerMapper.getKingdomAndDB(tax_id);
		
		String table = null;
		String ProteinID = null;
		for(TaxIdWithDbVo vo : kingdomTable) {
			table = vo.getResult();
			
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("tax_id", tax_id);
			map.put("refseq_assembly_id", assembly_accession);
			map.put("geneName", geneName);
			map.put("kingdom_table_query", table);
			
			ProteinID = this.viewerMapper.getProteinID(map);
			
			if( ProteinID != null && ProteinID.trim().length() > 0 )	break;
		}
		
		return ProteinID;
	}

	public List<OrthoMclVo> getOrthoMCLResults( String tax_id, String refseq_assembly_id, String protein_accession_id ) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tax_id", tax_id);
		map.put("refseq_assembly_id", refseq_assembly_id);
		map.put("protein_accession_id", protein_accession_id);
		
		return viewerMapper.getOrthoMCLResults(map);
	}
}
