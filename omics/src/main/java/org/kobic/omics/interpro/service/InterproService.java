package org.kobic.omics.interpro.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.kobic.omics.interpro.com.Utility;
import org.kobic.omics.interpro.mapper.InterproMapper;
import org.kobic.omics.interpro.vo.GeneSearchVo;
import org.kobic.omics.interpro.vo.InterproVo;
import org.kobic.omics.interpro.vo.IprDomainTypeVo;
import org.kobic.omics.interpro.vo.RepresentativeVo;
import org.kobic.omics.interpro.vo.SequenceDownloadVo;
import org.kobic.omics.interpro.vo.TaxIdWithDbVo;
import org.kobic.omics.viewer.vo.DomainVo;
import org.kobic.omics.viewer.vo.ProteinWithDomainVo;
import org.kobic.omics.viewer.vo.TaxIdVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import turbo.cache.lite.thrift.service.client.CacheLiteClient;

@Service(value = "interproService")
public class InterproService {
	
//	private static List<String> ACCEPTED_DOMAIN_TYPES = new ArrayList<String>( Arrays.asList( new String[]{"Domain", "Repeat"} ) );
	private static List<String> ACCEPTED_DOMAIN_TYPES = new ArrayList<String>( 
			Arrays.asList( new String[]{"Domain", "Family", "Repeat", "Conserved_site", "Binding_site", "Active_site", "PTM"} ) );
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(InterproService.class);

	@Resource(name = "interproMapper")
	private InterproMapper interproMapper;
	
	
	public static void sendMail(String userId, String url, String email) {
		MultiPartEmail multiPartEmail = new MultiPartEmail();
		multiPartEmail.setHostName("webmail.kobic.kr");//SMTP서버 설정

		try {
			String title = "[KOrean BioInformation Center] %s님이 검색하신 IPR에 대한 결과 발송 이메일 입니다.";
			String body = "국가생명연구자원정보센터에서 자동 생성된 url : %s\n" + "이 메일은 국가생명연구자원정보센터에서 자동 발송 됩니다.";

			multiPartEmail.setSubject(String.format(title, userId));
			multiPartEmail.setMsg(String.format(body, url));
			multiPartEmail.setFrom("biodata@kobic.kr", "System Delvelopment Team");//보내는 사람의 메일 주소
			multiPartEmail.addTo(email);
			multiPartEmail.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public static String createJSPFile(HttpServletRequest request, String line, String ipr_ids, String uid) throws Exception{
		FileReader fr = null;
		FileWriter fw = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try{			
			ServletContext servletContext = request.getSession().getServletContext();
			
			String relativeWebPath = "/WEB-INF/views/viewer/background_output";
			String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);

			File generatedHtml = new File(absoluteDiskPath, "geneSearch_form.jsp");
			
			fr = new FileReader(generatedHtml);
			br = new BufferedReader(fr);

			String generatedFileName = uid + "_" + System.currentTimeMillis() + ".jsp";

			ipr_ids = ipr_ids.replace(" ", "");
			ipr_ids = ipr_ids.replace(",", "-");
			
			File newFile = new File( absoluteDiskPath, generatedFileName );
			
			fw = new FileWriter(newFile, false);
			bw = new BufferedWriter(fw);

			String filecontent = null;
			while((filecontent=br.readLine())!=null){
				if( filecontent.contains("{data}") )		filecontent = "\t\tvar data =\"" + line.replace("\"", "\\\"") + "\";";
				else if(filecontent.contains("{ipr_ids}"))	filecontent = "\t\tvar ipr_ids =\"" + ipr_ids + "\";";
				bw.write(filecontent);
				bw.newLine();
			}
		} catch(Exception e) {
			throw e;
		} finally{
			// BufferedReader FileReader를 닫아준다.
			if(br != null) try{br.close();}catch(IOException e){}
			if(fr != null) try{fr.close();}catch(IOException e){}
			
			// BufferedWriter FileWriter를 닫아준다.
			if(bw != null) try{bw.close();}catch(IOException e){}
			if(fw != null) try{fw.close();}catch(IOException e){}
			
		}
		
		return ipr_ids;
	}
	
	public static String getFileName (Map<String, String> map, String type ) {
		String filename = map.get("tax_id") + "_" + map.get("assembly_accession") + "_" + map.get("protein_accession_version");

		String filePath = filename + "_" + type + ".fa";
		
		return filePath;
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
				
				String protein_nm = "tax_id:" + map.get("tax_id") + "; assembly_id:" + map.get("assembly_accession") + "; protein_id:" + map.get("protein_accession_version")
						+ "; organism:" + map.get("species") + "; kingdom:" + map.get("kingdom") + "; subtype:" + map.get("subtype");
				String sequenceAndCDS = this.getSequenceAndCDS(map.get("tax_id"), map.get("assembly_accession"), map.get("protein_accession_version"), type);
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
	
	public static String getFileNameFromIprTerms( String uid, String kingdom, String type, String subType) {
		String filePath;
		
		if(kingdom.equals("ALL"))
			filePath = uid + "_" + subType + "_" + type + "_" + System.currentTimeMillis() + ".fa";
		else
			filePath = uid + "_" + kingdom + "_" + subType + "_" + type + "_" + System.currentTimeMillis() + ".fa";
		
		return filePath;
	}
	
	public String getAllSequenceDownloadByIprTerms( ArrayList<Map<String, String>> list, String type, String filePath, String downloadRadio ) throws Exception {
		String protein_nm = "";
		String sequenceAndCDS = "";
		
        // 파일 객체 생성
		File file = new File(filePath);
		FileWriter fw = null;
		BufferedWriter bw = null;

		// true 지정시 파일의 기존 내용에 이어서 작성
    	fw = new FileWriter(file, true);
    	bw = new BufferedWriter(fw);
    	
    	try {
	    	String protein = "";
	    	logger.info( "this.getSequenceAndCDS start!!!!!" );
	    	long start1 = System.currentTimeMillis();
			
	    	for( Map<String, String> map : list ){
	    		protein_nm = "tax_id:" + map.get("tax_id") + "; assembly_id:" + map.get("assembly_accession") + "; protein_id:" + map.get("protein_accession_version") + "; organism:" + map.get("species");
    			
	    		sequenceAndCDS = this.getSequenceAndCDS( map.get("tax_id"), map.get("assembly_accession"), map.get("protein_accession_version"), type );
	    		
				protein += ">" + protein_nm + "\n" + sequenceAndCDS + "\n";
			}
	    	
			bw.write(protein);

			long end1 = System.currentTimeMillis();
			logger.info( "this.getSequenceAndCDS END~~~ : " + (end1-start1)/1000 + " sec" );
			
		} finally{
			// BufferedWriter FileWriter를 닫아준다.
			if(bw != null) try{bw.close();}catch(IOException e){}
			if(fw != null) try{fw.close();}catch(IOException e){}
		}

		return filePath;
	}
	
	public String getAllSequenceDownloadByIprTerms3(String user_id, List<Map<String, String>> list, String type, String filePath, String downloadRadio, Map<String, String> subtypeMap ) throws Exception {
		String protein_nm = "";
		List<SequenceDownloadVo> voList = new ArrayList<SequenceDownloadVo>();
		
        // 파일 객체 생성
		File file = new File(filePath);
		FileWriter fw = null;
		BufferedWriter bw = null;

		// true 지정시 파일의 기존 내용에 이어서 작성
    	fw = new FileWriter(file, true);
    	bw = new BufferedWriter(fw);
		
    	try {
	    	String protein = "";
	    	logger.info( "this.getSequenceAndCDS start!!!!!" );
	    	long start1 = System.currentTimeMillis();
			
	    	Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
	    	map.put("proteins", list);
	    	
	    	if(type.equals("Peptide"))
	    		voList = this.interproMapper.getSequence3(map);
	    	else
	    		//원래 CDS 다운로드를 하기 위해서는 mRNAID 또는 transcriptID가 필요하지만, 편의상 ProteinID로 통일하였다.
	    		voList = this.interproMapper.getCDS3(map);
    		
    		for(SequenceDownloadVo vo : voList) {
    			String key = vo.getTaxID() + "@" + vo.getRefSeqAssemblyID() + "@" + vo.getProteinID();
    			
    			String subtype = subtypeMap.get(key);
    			protein_nm = "tax_id:" + vo.getTaxID() + "; assembly_id:" + vo.getRefSeqAssemblyID()
    					+ "; protein_id:" + vo.getProteinID() + "; organism:" + vo.getOrganism()
    					+ "; kingdom:" + vo.getKingdom() + "; subtype:" + subtype;
    			
//    			FileUtils.write(new File(filePath), ">" + protein_nm + "\n" + vo.getSequence() + "\n", "UTF-8", true);
    			
    			protein += ">" + protein_nm + "\n" + vo.getSequence() + "\n";
    		}
	    	
			bw.write(protein);
    		
    		
   			String fileName = file.getName();
			int Idx = fileName.lastIndexOf(".");
			String _fileName = fileName.substring(0, Idx);

    		if(user_id != null){
    			CacheLiteClient client = new CacheLiteClient();
    			client.write(protein, _fileName, user_id);
    		}

			long end1 = System.currentTimeMillis();
			logger.info( "this.getSequenceAndCDS END~~~ : " + (end1-start1)/1000 + " sec" );
			
		} finally{
			// BufferedWriter FileWriter를 닫아준다.
			if(bw != null) try{bw.close();}catch(IOException e){}
			if(fw != null) try{fw.close();}catch(IOException e){}
		}

		return filePath;
	}
	
	public String getSequenceAndCDS(String tax_id, String assembly_accession, String protein_accession_version, String type) {
		Map<String, String> kingdomMap = new HashMap<String, String>();
		kingdomMap.put("tax_id", tax_id);
		if( type.contains("Peptide") )	kingdomMap.put("type", "proteinfaa");
		else							kingdomMap.put("type", "rnafna");
		
		String result = null;
		List<TaxIdWithDbVo> kingdomTables = this.interproMapper.getKingdomTablesByTaxId( kingdomMap );

		for(TaxIdWithDbVo vo : kingdomTables) {
			
			Map<String, String> map = new HashMap<String, String>();
			
			map.put( "TaxId", tax_id );
			map.put( "RefSeqAssemblyID", assembly_accession );
			map.put( "ProteinID", protein_accession_version );
			map.put( "kingdom_table_query", vo.getResult() );
			map.put( "type", vo.getKingdom().toUpperCase() );
			map.put( "db", vo.getDb() );
			
			if(type.equals("Peptide"))	result = this.interproMapper.getSequence(map);
			else if(type.equals("CDS"))	result = this.interproMapper.getCDS(map);
			
			if( result != null && result.trim().length() > 0 )	break;
		}
		return result;
	}
	
	public String getSequenceAndCDS2(String tax_id, String assembly_accession, String protein_accession_version, String type) {
//		Map<String, String> kingdomMap = new HashMap<String, String>();
//		kingdomMap.put("tax_id", tax_id);
//		if( type.contains("Peptide") )	kingdomMap.put("type", "proteinfaa");
//		else							kingdomMap.put("type", "rnafna");
		
		String result = null;
//		List<TaxIdWithDbVo> kingdomTables = this.interproMapper.getKingdomTablesByTaxId( kingdomMap );

//		for(TaxIdWithDbVo vo : kingdomTables) {
			
			Map<String, String> map = new HashMap<String, String>();
			
			map.put( "TaxId", tax_id );
			map.put( "RefSeqAssemblyID", assembly_accession );
			map.put( "ProteinID", protein_accession_version );
//			map.put( "kingdom_table_query", vo.getResult() );
//			map.put( "type", vo.getKingdom().toUpperCase() );
//			map.put( "db", vo.getDb() );
			
			if(type.equals("Peptide"))	result = this.interproMapper.getSequence(map);
			else if(type.equals("CDS"))	result = this.interproMapper.getCDS(map);
			
//			if( result != null && result.trim().length() > 0 )	break;
//		}
		return result;
	}
	
	public Map<String, Map<String, String>> getGeneIDFromRepAll(String tax_id, String assembly_accession, String protein_accession_version) {
		List<TaxIdWithDbVo> kingdomTables = this.interproMapper.getKingdomAndDB(tax_id);
		
		List<String> geneIdList = new ArrayList<String>();
		String repTable = "";
		Map<String, String> map = new HashMap<String, String>();
		for(TaxIdWithDbVo vo : kingdomTables) {
			String[] repTableSplit = vo.getResult().split("@");
			
			String repAllTable = repTableSplit[0];
			repTable = repTableSplit[1];
			
			map.put( "TaxId", tax_id );
			map.put( "RefSeqAssemblyID", assembly_accession );
			map.put( "ProteinID", protein_accession_version );
			map.put( "repAllTable", repAllTable );
			
			geneIdList = this.interproMapper.getGeneIDFromRepAll(map);
			
			if( geneIdList != null && geneIdList.size() > 0 )	break;
		}
		
		Map<String, String> geneIDMap = new HashMap<String, String>();
		Map<String, Map<String, String>> totalGeneIDMap = new HashMap<String, Map<String, String>>();
		
		for(int i=0; i<geneIdList.size(); i++) {
//		for(Iterator<String> iter = geneID.iterator(); iter.hasNext();) {
//			String singleGeneID = iter.next();
			
//			geneIDMap.put("geneID", singleGeneID);
			geneIDMap.put("geneID", geneIdList.get(i));
			geneIDMap.put( "TaxId", tax_id );
			geneIDMap.put( "RefSeqAssemblyID", assembly_accession );
			geneIDMap.put("repTable", repTable);
			
			String key = tax_id + "@" + assembly_accession + "@" + geneIdList.get(i);
			
			totalGeneIDMap.put(key, geneIDMap);
		}
		
		return totalGeneIDMap;
	}
	
	public List<RepresentativeVo> getProteinFromRep(List<Map<String, String>> keyList) {
		
		RepresentativeVo protein = new RepresentativeVo();
		List<RepresentativeVo> proteinList = new ArrayList<RepresentativeVo>();
		
		long start1 = System.currentTimeMillis();
		logger.info( "------------------------this.interproMapper.getProteinFromRep(map) start!!!!!");
		
		for(int i=0; i<keyList.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			
			map.put( "GeneID", keyList.get(i).get("geneID") );
			map.put( "TaxID", keyList.get(i).get("TaxId") );
			map.put( "RefSeqAssemblyID", keyList.get(i).get("RefSeqAssemblyID") );
			map.put( "repTable", keyList.get(i).get("repTable") );
			
			protein = this.interproMapper.getProteinFromRep(map);
			
			proteinList.add(protein);
		}
		
		long end1 = System.currentTimeMillis();
		logger.info( "------------------------this.interproMapper.getProteinFromRep(map) END~~~~ : " + (end1-start1)/1000 + " sec" );
		
		// GeneID로만 검색하면 중복이 있을 수 있다.
		// 때문에 key = TaxID, RefSeqAssemblyID, GeneID 로 정의하여 검색한다.
		// 예) GeneID는 다르지만 protein은 같은 경우가 있음.
		// GeneID : 'Mtcs1' -> TaxID='936046' and RefSeqAssemblyID='GCF_000300575.1' and ProteinID='XP_006453861.1'
		// GeneID : 'Arfgap2' -> TaxID='936046' and RefSeqAssemblyID='GCF_000300575.1' and ProteinID='XP_006453861.1'
		
		// 중복이 있는 proteinList를 중복제거한 List
		List<RepresentativeVo> uniqueProteinList = new ArrayList<RepresentativeVo>(new HashSet<RepresentativeVo>(proteinList));
		
		return uniqueProteinList;
//		return proteinList;
	}
	
	public static void download(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int BUFFER_SIZE = 4096;
		
		String filePath = request.getParameter("name");
		System.out.println("filePath = " + filePath);

		ServletContext context = request.getSession().getServletContext();
		File downloadFile = new File(filePath);
		FileInputStream inputStream = new FileInputStream(downloadFile);
		
		String mimeType = context.getMimeType(filePath);
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		System.out.println("MIME type: " + mimeType);

		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		String headerKey = "Content-Disposition";
		//String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		
		response.setHeader(headerKey, "attachment; filename=" + URLEncoder.encode(downloadFile.getName(), "utf-8") + ";");
		//response.setHeader(headerKey, headerValue);

		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;
		
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outStream.close();
	}
	
	public static Map<String, String> getCompositeOrderQuery( String[] split_ipr, String radio ) {
		Map<String, String> map = new HashMap<String, String>();
		
//		String split_ipr[] = ipr_ids.split(",");
		String inputIPR = "";
		String subIPR = "";
		
		for(int i=0; i<split_ipr.length; i++) {
			inputIPR = inputIPR + " +" + split_ipr[i];
			subIPR = subIPR + "%" + split_ipr[i];
		}
		subIPR = subIPR + "%";
		
		String subQuery = "";
		if(radio.contains("ord")) {
			subQuery = "HAVING ipr_terms LIKE '" +subIPR + "'";
		}
		
		map.put("ipr_ids", inputIPR);
		map.put("subQuery", subQuery);
		
		return map;
	}

	private List<ProteinWithDomainVo> getCandidateProteinMapBySubtypesBrandNew( String subType, String perfectMatch, String[] iprTerms, String[] iprFamiliesTerms
			, String[] withoutIPRTerms, String radio ){
		
		Map<String, String> subTypeMap = new LinkedHashMap<String, String>();
		List<ProteinWithDomainVo> proteinList = null;
		long start1 = System.currentTimeMillis();
		try{
			List<GeneSearchVo> iprContainedProteinList = this.interproMapper.getUniqueProtein( InterproService.getCompositeOrderQuery(iprTerms, radio) );
//			List<GeneSearchVo> iprContainedProteinList = null;
			long end1 = System.currentTimeMillis();
//			logger.info( "this.interproMapper.getUniqueProtein : " + (end1-start1)/1000 + " sec" );
			
			proteinList = new ArrayList<ProteinWithDomainVo>();
			
			long domainTotalTime = 0;
			
			long start2 = System.currentTimeMillis();
			int unit = 100000;
			if( iprContainedProteinList != null && iprContainedProteinList.size() > 0 ) {
				Map<String, List<GeneSearchVo>> paramMap = new LinkedHashMap<String, List<GeneSearchVo>>();
				for(int i=0; i<=iprContainedProteinList.size()/unit; i++) {
					int num=0;
					
					List<GeneSearchVo> unitIprContainedProteinList = null;
					
					if(i==iprContainedProteinList.size()/unit)
						unitIprContainedProteinList = iprContainedProteinList.subList(i * unit, iprContainedProteinList.size());
					else
						unitIprContainedProteinList = iprContainedProteinList.subList(i * unit, (i + 1) * unit);
					
//					System.out.println("unitIprContainedProteinList : " + unitIprContainedProteinList.size());
					
//					if(num==0)
//						logger.info("iprContainedProteinList : " + iprContainedProteinList.size());
					
					int count=0;
					int count_isPerfected=0;
					int unitSecond = 100000;
	//				100k(나누지 않았을 경우) 154초
	//				1k 92초
	//				0.5k 94초
					
					for( int k=0; k<=unitIprContainedProteinList.size()/unitSecond; k++) {
						List<GeneSearchVo> unitSecondIprContainedProteinList = null;
						
//						System.out.println("unitIprContainedProteinList.size()/unitSecond : " + unitIprContainedProteinList.size()/unitSecond);
						
						if(k==unitIprContainedProteinList.size()/unitSecond && unitIprContainedProteinList.size() != unit)
							unitSecondIprContainedProteinList = unitIprContainedProteinList.subList(k * unitSecond, unitIprContainedProteinList.size());
						else {
							if(k==unitIprContainedProteinList.size()/unitSecond)
								break;
							else
								unitSecondIprContainedProteinList = unitIprContainedProteinList.subList(k * unitSecond, (k + 1) * unitSecond);
						}
//						System.out.println("1 : " );
	//				paramMap.put("proteins", unitIprContainedProteinList);
						paramMap.put("proteins", unitSecondIprContainedProteinList);
						
						long start333 = System.currentTimeMillis();
						Map<String, List<GeneSearchVo>> eachProteinDomainMap = InterproService.getDomainMap4EachProtein( this.interproMapper.getDomainByProteinWithList( paramMap ) );
//						Map<String, List<GeneSearchVo>> eachProteinDomainMap = null;
						long end333 = System.currentTimeMillis();
						
						domainTotalTime = domainTotalTime + (end333-start333)/1000;
						
//						if(num==0)
//							logger.info("Error Checking....getDomainMap4EachProtein pass!!!");
						
//						System.out.println("2 : " + eachProteinDomainMap.size() );
						
						for( GeneSearchVo vo : unitSecondIprContainedProteinList ) {
	//					for( GeneSearchVo vo : unitIprContainedProteinList ) {
							String protein = vo.getUniqueProteinId();
							
							List<GeneSearchVo> domainList = eachProteinDomainMap.get(protein);
							
							if( iprFamiliesTerms != null && !InterproService.isContainedFamilies(domainList, iprFamiliesTerms) )
								continue;
							if( withoutIPRTerms != null && InterproService.isContainedFamilies( domainList, withoutIPRTerms ) )
								continue;
							
//							if(num==0)
//								logger.info("Error Checking....InterproService.isContainedFamilies pass!!!");
							
							ProteinWithDomainVo proteinVo = new ProteinWithDomainVo( domainList.get(0) );
							List<DomainVo> domainVoList = new ArrayList<DomainVo>();
							for(GeneSearchVo domain : domainList)	domainVoList.add( new DomainVo( domain ) );
							proteinVo.setDbs( domainVoList );
			
							if( perfectMatch.equals("true") ) {
								count++;
								
								Object[] res = this.isMatchedDomainPerfectOrder( protein, domainList, new ArrayList<String>( Arrays.asList(iprTerms) ), subTypeMap);
								boolean isPerfectMatched = (boolean)res[0];
								String subtype = res[1].toString();
								proteinVo.setSubtype( subtype );
								
//								if(num==0)
//									logger.info("Error Checking....isPerfectMatched pass!!!");
								
								if( isPerfectMatched ) {
									proteinList.add( proteinVo );
									count_isPerfected++;
								}
							}else {
								count++;
								proteinList.add( proteinVo );
							}
							num=1;
						}
//						if(num==0)
//						logger.info( "이게 몇번찍힐까1" );
					}
	//				logger.info( "count : " + count );
	//				logger.info( "count_isPerfected : " + count_isPerfected );
//					logger.info( "이게 몇번찍힐까2" );
				}
//				logger.info( "이게 몇번찍힐까3" );
			}
			System.out.println(subTypeMap.size());
			long end2 = System.currentTimeMillis();
//			logger.info( "this.interproMapper.getDomainByProteinWithList : " + (end2-start2)/1000 + " sec" );
//			logger.info( "this.interproMapper.getDomainByProteinWithList : " + domainTotalTime + " sec" );
		}catch(Exception e){
			logger.error(e.getLocalizedMessage());
		}

		return proteinList;

	}
	
//	private static boolean isContainedFamilies( List<GeneSearchVo> domainList, String[] families ) {
//		int count = 0;
//		for(int i=0; i<families.length; i++) {
//			for(GeneSearchVo vo : domainList) {
//				if( vo.getIpr_id().equals( families[i] ) )	{
//					count++;
//					break;
//				}
//			}
//		}
//		boolean val = count==families.length?true:false;
//		
//		return val;
//	}
	
	private static boolean isContainedFamilies( List<GeneSearchVo> domainList, String[] families ) throws Exception {
		boolean val = false;
		
		Loop1 : for(int i=0; i<families.length; i++) {
			for(GeneSearchVo vo : domainList) {
				if( vo.getIpr_id().equals( families[i] ) )	{
					val = true;
					break Loop1;
				}
			}
		}
		return val;
	}
	
//	private static boolean isWithoutIPR( List<GeneSearchVo> domainList, String[] families ) {
//		int count = 0;
//		for(int i=0; i<families.length; i++) {
//			for(GeneSearchVo vo : domainList) {
//				if( vo.getIpr_id().equals( families[i] ) )	{
//					count++;
//					break;
//				}
//			}
//		}
//		boolean val = count==families.length?true:false;
//		
//		return val;
//	}

	private static Map<String, List<GeneSearchVo>> getDomainMap4EachProtein(List<GeneSearchVo> list) throws Exception {
//		System.out.println("getDomainMap4EachProtein 내부 : " + list.size());
		Map<String, List<GeneSearchVo>> map = new LinkedHashMap<String, List<GeneSearchVo>>();
		for(GeneSearchVo vo : list) {
			String protein = vo.getUniqueProteinId();
			if( !map.containsKey( protein) ) {
				List<GeneSearchVo> lst = new ArrayList<GeneSearchVo>();
				lst.add( vo );
				map.put( protein, lst );
			}else {
				map.get(protein).add( vo );
			}
		}
		return map;
	}

	private static List<GeneSearchVo> groupingSameAndOverlappedIprDomain( List<GeneSearchVo> domainList ) throws Exception {
		List<GeneSearchVo> list = new ArrayList<GeneSearchVo>();

		for(int i=0; i<domainList.size(); i++) {
			GeneSearchVo originDomainVo = domainList.get(i);

			if( InterproService.ACCEPTED_DOMAIN_TYPES.contains( originDomainVo.getType() ) ) {
				GeneSearchVo first = GeneSearchVo.clone( originDomainVo.getStart(), originDomainVo.getEnd(), originDomainVo.getIpr_id(), originDomainVo.getType() );

				boolean isNotFounded = true;
				for(GeneSearchVo previousDomainVo : list ) {
					if( previousDomainVo.getIpr_id().equals( first.getIpr_id() ) && previousDomainVo.isOverlapped( first ) ) {
						previousDomainVo.expandRange( first );
						isNotFounded = false;
						break;
					}
				}
				if( isNotFounded ) list.add( first );
			}
		}
		return list;
	}
	
	private static List<GeneSearchVo> groupingOtherOverlappedIprDomain( List<GeneSearchVo> domainList, List<String> iprTerms ) throws Exception{
		List<Integer> removingIndexes = new ArrayList<Integer>();
		
		List<Integer> queryDomainRangeIndex = new ArrayList<Integer>(); 
		for(int i=0; i<domainList.size(); i++) 	{
			if( iprTerms.contains( domainList.get(i).getIpr_id() ) && queryDomainRangeIndex.indexOf(i) == -1 )	queryDomainRangeIndex.add( i );
		}

		List<GeneSearchVo> newDomainList = new ArrayList<GeneSearchVo>();
		for(int i=0; i<domainList.size(); i++) 	{
			if( !queryDomainRangeIndex.contains(i) ) {
				if( InterproService.isDeadDomain( domainList.get(i), domainList, queryDomainRangeIndex ) ) removingIndexes.add(i);
			}
			
			if( !removingIndexes.contains(i) )	newDomainList.add( domainList.get(i) );
		}

		return newDomainList;
	}
	
	private static boolean isDeadDomain( GeneSearchVo target, List<GeneSearchVo> domainList, List<Integer> queryDomainRangeIndex ) {
		int overlapCount = 0;
		int ONLY_ONE = 1;

		for( int idx:queryDomainRangeIndex ) {
			GeneSearchVo base = domainList.get( idx );

			if( base.isOverlapped( target ) ) {
				overlapCount++;

				if( overlapCount > ONLY_ONE )	break;
			}
		}
		if( overlapCount >= ONLY_ONE ) return true;
		return false;
	}

	public Object[] isMatchedDomainPerfectOrder( String protein, List<GeneSearchVo> domainList, List<String> iprTerms, Map<String, String> subTypeMap) throws Exception {
		List<GeneSearchVo> groupedDomainList = InterproService.groupingSameAndOverlappedIprDomain(domainList);
		
		List<GeneSearchVo> newGroupedDomainList = InterproService.groupingOtherOverlappedIprDomain( groupedDomainList, iprTerms);
		
		String result = InterproService.getName( newGroupedDomainList.iterator() );
		String query = StringUtils.join( iprTerms.toArray() );
		
//		System.out.println( "result= "+ result );
//		System.out.println( "query=" + query );

		boolean res = result.startsWith(query);

		String subtype = "subtype" + (subTypeMap.size() + 1);
		if( res ) {
			if( !subTypeMap.containsKey( result ) ) {
				subTypeMap.put(result, subtype);
			}else {
				subtype = subTypeMap.get(result);
			}
		}

		return new Object[]{res, subtype};
	}
	
	private static String getName(Iterator<GeneSearchVo> iter) throws Exception {
		String result = "";
		for(Iterator<GeneSearchVo> iterator = iter; iterator.hasNext();) {
			GeneSearchVo vo = iterator.next();
			result += vo.getIpr_id();
		}
		return result;
	}
	
	private static void putIfAbsent( Map<String, String> wholeIprTerms, String key, String value ) {
		if( !wholeIprTerms.containsKey(key) ) wholeIprTerms.put(key, value);
	}
	
	public List<IprDomainTypeVo> getInterProDomainInfo(Map<String, String> inputMap, Map<String, String> iprFamiliesMap, Map<String, String> withoutIPRMap) {
		Map<String, String> wholeIprTerms = new LinkedHashMap<String, String>();
		for(Iterator<String> iter = inputMap.keySet().iterator(); iter.hasNext();) {
			String subType = iter.next();
			String[] iprTerms = inputMap.get( subType ).replace(" ", "").split(",");
			String[] iprFamiliesTerms = iprFamiliesMap.get( subType )==null?null:iprFamiliesMap.get( subType ).replace(" ", "").split(",");
			for(int i=0; i<iprTerms.length; i++) 									InterproService.putIfAbsent(wholeIprTerms, iprTerms[i], "input");
			for(int i=0; iprFamiliesTerms!=null&&i<iprFamiliesTerms.length; i++)	InterproService.putIfAbsent(wholeIprTerms, iprFamiliesTerms[i], "input");
		}
		
		String[] iprArray = new String[ wholeIprTerms.keySet().size() ];
		wholeIprTerms.keySet().toArray( iprArray );
		
		
		Map<String, List<String>> paramMap = new LinkedHashMap<String, List<String>>();
		paramMap.put("domainList", Arrays.asList(iprArray) );
		List<IprDomainTypeVo> iprDomainList = this.interproMapper.getIprDomainInfo(paramMap);

//		Map<String, IprDomainTypeVo> map = new LinkedHashMap<String, IprDomainTypeVo>();
//		for(IprDomainTypeVo vo:iprDomainList)	map.put( vo.getId(), vo.getType() );
		
		return iprDomainList;
	}
	
	public List<ProteinWithDomainVo> allDownload(String subType, String perfectMatch, String[] iprTerms, String[] iprFamiliesTerms, String[] withoutIPRTerms
			, String radio, String selectKingdom) throws Exception{
		List<ProteinWithDomainVo> proteinList = this.getCandidateProteinMapBySubtypesBrandNew( subType, perfectMatch, iprTerms, iprFamiliesTerms, withoutIPRTerms, radio );
		
		Map<String, Integer> taxIdMap = new LinkedHashMap<String, Integer>();
		for(int i=0; i<proteinList.size(); i++) {
			if( !taxIdMap.containsKey( proteinList.get(i).getNid() ) )	taxIdMap.put( proteinList.get(i).getNid(), 1 );
			else														taxIdMap.put( proteinList.get(i).getNid(), taxIdMap.get(proteinList.get(i).getNid() ) + 1 );
		}
		
		String[] arr = new String[ taxIdMap.keySet().size()];
		taxIdMap.keySet().toArray(arr);

		Map<String, List<String>> paramMap = new LinkedHashMap<String, List<String>>();
		paramMap.put( "tax_id", taxIdMap.size() > 0?Arrays.asList( arr ):null );
		Map<String, String> taxIdAndKingdomMap = InterproService.getTaxIdAndKingdomMap( this.interproMapper.getKingdomInfoByTaxId( paramMap ) );
		
		for(int i=0; i<proteinList.size(); i++) {
			String kingdom = taxIdAndKingdomMap.get( proteinList.get(i).getNid() );
			proteinList.get(i).setKingdom(kingdom);
		}
		
		if( Utility.emptyToNull( selectKingdom ) != null && !selectKingdom.equals("ALL") )	proteinList = InterproService.filteredProteinsByKingdom(proteinList, selectKingdom);
		
		return proteinList;
	}

	public Map<String, Object> compareGroupGeneSearch( Map<String, String> inputMap, Map<String, String> iprFamiliesMap, Map<String, String> withoutIPRMap
			, String radio, String perfectMatch, String strPagingSize ) throws Exception {
//		Map<String, Map<String, Object>> groupProteinMap = new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Object> groupProteinMap = new LinkedHashMap<String, Object>();

		for(Iterator<String> iter = inputMap.keySet().iterator(); iter.hasNext();) {
			String subType = iter.next();

			String[] iprTerms = inputMap.get( subType ).replace(" ", "").split(",");
			String[] iprFamiliesTerms = iprFamiliesMap.get( subType )==null?null:iprFamiliesMap.get( subType ).replace(" ", "").split(",");
			String[] withoutIPRTerms = withoutIPRMap.get( subType )==null?null:withoutIPRMap.get( subType ).replace(" ", "").split(",");
			
			List<ProteinWithDomainVo> proteinList = this.getCandidateProteinMapBySubtypesBrandNew( subType, perfectMatch, iprTerms, iprFamiliesTerms, withoutIPRTerms, radio );
//			System.out.println("getCandidateProteinMapBySubtypesBrandNew pass!!");
			List<ProteinWithDomainVo> newProteinList = new ArrayList<ProteinWithDomainVo>(); 
			for(int i=0; i<proteinList.size() && i < Integer.parseInt( strPagingSize ); i++ ) {
				newProteinList.add( proteinList.get(i) );
			}

			int diff = (int)Math.ceil( (double)proteinList.size() / Integer.parseInt( strPagingSize ));
			
			Map<String, Object> totalMap = new HashMap<String, Object>();

			totalMap.put("proteinList", newProteinList);
			totalMap.put("pagingSize", strPagingSize);
			totalMap.put("firstPagingIndex", 1);
			totalMap.put("lastPagingIndex", diff);
			totalMap.put("currentPagingIndex", 1);
			totalMap.put("iprDomain", inputMap.get(subType));
			totalMap.put("totalNoOfRecords", proteinList.size());
			
			totalMap.put("stat", this.statistics(proteinList ) ); 
			
			totalMap.put("perfectMatch", perfectMatch ); 
			totalMap.put("inputboxFamilies", iprFamiliesTerms ); 

			groupProteinMap.put( subType, totalMap );
		}
		
		return groupProteinMap;
	}
	
	private Map<String, Object> statistics( List<ProteinWithDomainVo> proteinList ) {
		Map<String, Object> statMap = new LinkedHashMap<String, Object>();

		int minDomains = Integer.MAX_VALUE;
		int maxDomains = Integer.MIN_VALUE;
		int minProteinLength = Integer.MAX_VALUE;
		int maxProteinLength = Integer.MIN_VALUE;

		Map<String, Integer> taxIdMap = new LinkedHashMap<String, Integer>();
		List<Integer> lengthList = new ArrayList<Integer>();
		for(int i=0; i<proteinList.size(); i++) {
			if( !taxIdMap.containsKey( proteinList.get(i).getNid() ) )	taxIdMap.put( proteinList.get(i).getNid(), 1 );
			else														taxIdMap.put( proteinList.get(i).getNid(), taxIdMap.get(proteinList.get(i).getNid() ) + 1 );

			lengthList.add( proteinList.get(i).getLength() );
			if( maxDomains < proteinList.get(i).getDbs().size() )	maxDomains = proteinList.get(i).getDbs().size();
			if( minDomains > proteinList.get(i).getDbs().size() )	minDomains = proteinList.get(i).getDbs().size();
			if( maxProteinLength < proteinList.get(i).getLength() )	maxProteinLength = proteinList.get(i).getLength();
			if( minProteinLength > proteinList.get(i).getLength() )	minProteinLength = proteinList.get(i).getLength();
		}
		
		if(minDomains==Integer.MAX_VALUE)	minDomains = 0;
		if(maxDomains==Integer.MIN_VALUE)	maxDomains = 0;
		if(minProteinLength==Integer.MAX_VALUE)	minProteinLength = 0;
		if(maxProteinLength==Integer.MIN_VALUE)	maxProteinLength = 0;
		
		String[] arr = new String[ taxIdMap.keySet().size()];
		taxIdMap.keySet().toArray(arr);

		Map<String, List<String>> paramMap = new LinkedHashMap<String, List<String>>();
		paramMap.put( "tax_id", taxIdMap.size() > 0?Arrays.asList( arr ):null );
		Map<String, String> taxIdAndKingdomMap = InterproService.getTaxIdAndKingdomMap( this.interproMapper.getKingdomInfoByTaxId( paramMap ) );
				
		for(int i=0; i<proteinList.size(); i++) {
			String kingdom = taxIdAndKingdomMap.get( proteinList.get(i).getNid() );
			proteinList.get(i).setKingdom(kingdom);
		}
	
		Map<String, Integer> kingdomMap = new LinkedHashMap<String, Integer>();
		
		kingdomMap.put("ALL", proteinList.size());
		
		for(Iterator<String> iter=taxIdMap.keySet().iterator(); iter.hasNext();) {
			String tax_id = iter.next();
			String kingdom = taxIdAndKingdomMap.get( tax_id );

			int countByTaxId = taxIdMap.get( tax_id );
			if( kingdomMap.containsKey(kingdom) ) 	kingdomMap.put( kingdom, kingdomMap.get(kingdom) + countByTaxId );
			else									kingdomMap.put( kingdom,  countByTaxId );

		}
		
		List<TaxIdVo> taxonomy_kingdom = this.interproMapper.getKingdomInfoByTaxIdCount(paramMap);
		Map<String, Integer> taxKingdomMap = new LinkedHashMap<String, Integer>();
		for(TaxIdVo vo : taxonomy_kingdom){
			taxKingdomMap.put(vo.getKingdom(), vo.getCnt());
		}
		taxKingdomMap.put("ALL", taxIdMap.keySet().size());
		
		List<TaxIdVo> taxonomy_total_cnt = this.interproMapper.getKingdomByTaxIdTotalCount();
		Map<String, Integer> taxKingdomTotalMap = new LinkedHashMap<String, Integer>();
		for(TaxIdVo vo : taxonomy_total_cnt){
			taxKingdomTotalMap.put(vo.getKingdom(), vo.getCnt());
		}
		
		List<String> subtypeList = new ArrayList<String>();
		int subtypeCnt = 0;
		for(int i=0; i<proteinList.size(); i++) {
			if(!subtypeList.contains(proteinList.get(i).getSubtype())) {
				subtypeList.add(proteinList.get(i).getSubtype());
				subtypeCnt++;
			}
		}
		// statMap

		statMap.put( "protein_count", Integer.toString( proteinList.size() ) );
		statMap.put( "taxonomy_count", Integer.toString( taxIdMap.keySet().size() ) );
		statMap.put( "tax_id_count", taxIdMap );
		statMap.put( "kingdom_count", kingdomMap );
		statMap.put( "max_domains", maxDomains);
		statMap.put( "min_domains", minDomains);
		statMap.put( "max_protein_length", maxProteinLength);
		statMap.put( "min_protein_length", minProteinLength);
		statMap.put( "tax_with_kingdom_map", taxIdAndKingdomMap);
		statMap.put( "tax_kingdom_count" , taxKingdomMap);
		statMap.put( "tax_kingdom_total_count", taxKingdomTotalMap);
		statMap.put( "domain_subtypes", subtypeCnt);
		

		return statMap;
	}
	
	private static Map<String, String> getTaxIdAndKingdomMap(List<TaxIdVo> kingdomList  ) {
		Map<String, String> kingdomMap = new LinkedHashMap<String, String>();
		for(TaxIdVo vo : kingdomList) {
			kingdomMap.put( vo.getTax_id(), vo.getKingdom() );
		}
		return kingdomMap;
	}
	
	private static List<ProteinWithDomainVo> filteredProteinsByKingdom( List<ProteinWithDomainVo> proteinList, String kingdom ) {
		List<ProteinWithDomainVo> filteredProteinListByKingdom = new ArrayList<ProteinWithDomainVo>();
		
		for(ProteinWithDomainVo vo:proteinList) {
			if( vo.getKingdom().equals( kingdom ) )	filteredProteinListByKingdom.add( vo );
		}
		return filteredProteinListByKingdom;
	}
	
	public Map<String, Map<String, Object>> compareGroupGeneSearch( Map<String, String> inputMap, Map<String, String> iprFamiliesMap, Map<String, String> withoutIPRMap
			, String radio, String perfectMatch, String strPagingSize, String strPagingIndex, String baseSubType, String kingdom ) throws Exception{
		int pagingSize = Integer.parseInt(strPagingSize);
		int pagingIndex = Integer.parseInt( strPagingIndex );
		
		int start = pagingSize * (pagingIndex - 1);
		
		String[] iprTerms = inputMap.get( baseSubType ).replace(" ", "").split(",");
		String[] iprFamiliesTerms = iprFamiliesMap.get( baseSubType )==null?null:iprFamiliesMap.get( baseSubType ).replace(" ", "").split(",");
		String[] withoutIPRTerms = withoutIPRMap.get( baseSubType )==null?null:withoutIPRMap.get( baseSubType ).replace(" ", "").split(",");

		List<ProteinWithDomainVo> proteinList = this.getCandidateProteinMapBySubtypesBrandNew( baseSubType, perfectMatch, iprTerms, iprFamiliesTerms, withoutIPRTerms, radio );
//		System.out.println("getCandidateProteinMapBySubtypesBrandNew pass!!");
		Map<String, Object> statMap = this.statistics(proteinList);

		if( Utility.emptyToNull( kingdom ) != null && !kingdom.equals("ALL") )	proteinList = InterproService.filteredProteinsByKingdom(proteinList, kingdom);
		
		int cnt = 0;
		List<ProteinWithDomainVo> newProteinList = new ArrayList<ProteinWithDomainVo>(); 
		for(int i=start; i<proteinList.size() && cnt < Integer.parseInt( strPagingSize ); i++ ) {
			newProteinList.add( proteinList.get(i) );
			cnt++;
		}

		int diff = (int)Math.ceil( (double)proteinList.size() / Integer.parseInt( strPagingSize ));
		
		Map<String, Object> totalMap = new HashMap<String, Object>();

		totalMap.put("proteinList", newProteinList);
		totalMap.put("pagingSize", strPagingSize);
		totalMap.put("firstPagingIndex", 1);
		totalMap.put("lastPagingIndex", diff);
		totalMap.put("currentPagingIndex", strPagingIndex);
		totalMap.put("iprDomain", inputMap.get(baseSubType));
		totalMap.put("totalNoOfRecords", proteinList.size());
		totalMap.put("stat", statMap ); 
		totalMap.put("kingdom", kingdom);

		Map<String, Map<String, Object>> groupProteinMap = new LinkedHashMap<String, Map<String, Object>>();
		groupProteinMap.put( baseSubType, totalMap );

		return groupProteinMap;
	}
	
	public List<InterproVo> getInterpro( String ipr_ids, String sub_query, int ipr_ids_counts ) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("ipr_ids", ipr_ids);
		map.put("sub_query", sub_query);
		map.put("ipr_ids_counts", Integer.toString(ipr_ids_counts) );
		
		List<InterproVo> interpro = this.interproMapper.getInterpro( map );
		
		return interpro;
	}
	
	//사용자가 입력한 ipr_ids를 이용하여 sub_query와 ipr_ids_counts를 알아낸다.
	//sub_query : 사용자가 입력한 순서대로 ipr_ids가 나열되게끔 정렬하는 쿼리(order by)
	//ipr_ids_counts : 사용자가 입력한 ipr_ids 개수
	public List<InterproVo> getInterpro( String ipr_ids ) {
		if( ipr_ids.contains(",") ) {
			String ipr_terms = "";
			String[] terms = ipr_ids.split(",");
			int count_for_terms = terms.length-1;
			
			String subQuery = "";
			for(int i=0; i<terms.length; i++) {
				subQuery += "SELECT " + i + " AS ord, " + terms[i] + " AS ipr_id ";
				ipr_terms += "'" + terms[i].trim() +"'";
				if( i<terms.length-1) {
					subQuery += "UNION ALL ";
					ipr_terms += ", ";
				}
			}

			return this.getInterpro(ipr_terms, subQuery, count_for_terms);
		} else
			return this.getInterpro( ipr_ids, "SELECT 0 AS ord, " + ipr_ids + " AS ipr_id", 0 );
//		"", "dofiuadofuas", "#$#$#"
//		if ( ipr_ids is numerical ) IPR을 위와같은 형식이 아닌 다른 형식으로 잘못 입력하였을 경우 예외처리 해야함
	}
	
	public String getSpecies(String tax_id, String assembly_accession, String protein_accession_version) {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("tax_id", tax_id);
		map.put("assembly_accession", assembly_accession);
		map.put("protein_accession_version", protein_accession_version);
		
		return this.interproMapper.getSpecies(map);
	}
	
	public List<ProteinWithDomainVo> resultInterpro(List<InterproVo> hitProteinList) {
		
		List<ProteinWithDomainVo> proteinList = new ArrayList<ProteinWithDomainVo>();
		
		String tax_id = hitProteinList.get(0).getTax_id();
		String assembly_accession = hitProteinList.get(0).getAssembly_accession();
		String protein_accession_version = hitProteinList.get(0).getProtein_accession_version();
		
//		String sequence = this.getSequence(tax_id, assembly_accession, protein_accession_version);
		
		ProteinWithDomainVo proteinVo = new ProteinWithDomainVo( tax_id, hitProteinList.get(0).getSpecies(),
				assembly_accession, Integer.valueOf( hitProteinList.get(0).getLength() ), protein_accession_version);
		proteinVo.setDbs( this.getDomain(hitProteinList.get(0).getTax_id(), hitProteinList.get(0).getAssembly_accession(), hitProteinList.get(0).getProtein_accession_version()) );
		
		proteinList.add( proteinVo );
		
		return proteinList;
	}
	
	public List<DomainVo> getDomain( String tax_id, String assembly_accession, String protein_accession_version ) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("tax_id", tax_id);
		map.put("assembly_accession", assembly_accession);
		map.put("protein_accession_version", protein_accession_version );
		
		List<DomainVo> domain = this.interproMapper.getDomain( map );
		
		return domain;
	}
	
	//동기화버전
	public List<InterproVo> compareInterproSingle(List<InterproVo> interpro){
		//30개를 List<InterproVo>로 갖고온다.
		Map<String, List<InterproVo>> protein_map = new HashMap<String, List<InterproVo>>();

		//vo.getTax_id() + "-" + vo.getAssembly_accession() + "-" + vo.getProtein_accession_version()를 key로 하여 같은 key들 3개씩 묶어준다.
		for(InterproVo vo : interpro) {
			vo.externalDoSplitPosition();
			
			String key = vo.getTax_id() + "-" + vo.getAssembly_accession() + "-" + vo.getProtein_accession_version();
			
			if( protein_map.containsKey(key) ) {
				List<InterproVo> list = protein_map.get(key);
				list.add( vo );
			}else {
				List<InterproVo> subList = new ArrayList<InterproVo>();
				subList.add( vo );
				protein_map.put(key, subList);
			}
		}
		
		List<InterproVo> hitProteinList = new ArrayList<InterproVo>();
		//ipr_term이 순서대로 있으면 count++해준다. count가 count == lst.size() - 1이면 hit
		List<ProteinWithDomainVo> proteinList = new ArrayList<ProteinWithDomainVo>();
		for(Iterator<String> iter = protein_map.keySet().iterator(); iter.hasNext();) {
			String key = iter.next();
			
			List<InterproVo> lst = protein_map.get(key);

			int count = 0;
			for(int i=0; i<lst.size()-1; i++) {
				InterproVo baseObj = lst.get(i);
				InterproVo compareObj = lst.get(i+1);
					
				if( baseObj.compareWith( compareObj ) ) {
					count++;
				}
			}
			
			if( count == lst.size() - 1 && proteinList.size() < 3) {//&& proteinList.size() < 3
				hitProteinList.add(lst.get(0));
//				System.out.println( key + " : hit!!" );
//				ProteinWithDomainVo proteinVo = new ProteinWithDomainVo( lst.get(0).getTax_id(), lst.get(0).getSpecies(), lst.get(0).getProtein_accession_version(), Integer.valueOf( lst.get(0).getLength() ) );
//				proteinVo.setDbs( this.getDomain(lst.get(0).getTax_id(), lst.get(0).getAssembly_accession(), lst.get(0).getProtein_accession_version()) );
//				proteinList.add(proteinVo );
			}
		}
		//10개
		return hitProteinList;
	}
	
	
	//비동기화버전(서버단에서 모든 처리가 완료된 후에 클라이언트단으로 넘어가 그림을 그린다)
	//mysql full data version
	public static List<ProteinWithDomainVo> getProteinWithDomain(List<GeneSearchVo> geneSearch){
		//30개를 List<InterproVo>로 갖고온다.
		Map<String, List<GeneSearchVo>> uniqueProteinMap = new HashMap<String, List<GeneSearchVo>>();
		
		//vo.getTax_id() + "-" + vo.getAssembly_accession() + "-" + vo.getProtein_accession_version()를 key로 하여 같은 key들끼리 묶어준다.
		for(GeneSearchVo vo : geneSearch) {
			String key = vo.getTax_id() + "-" + vo.getAssembly_accession() + "-" + vo.getProtein_accession_version();
			
			if( uniqueProteinMap.containsKey(key) ) {
				List<GeneSearchVo> list = uniqueProteinMap.get(key);
				list.add( vo );
			}else {
				List<GeneSearchVo> subList = new ArrayList<GeneSearchVo>();
				subList.add( vo );
				uniqueProteinMap.put(key, subList);
			}
		}
		
		List<ProteinWithDomainVo> proteinList = new ArrayList<ProteinWithDomainVo>();
		for(Iterator<String> iter = uniqueProteinMap.keySet().iterator(); iter.hasNext();) {
			String key = iter.next();
			
			List<GeneSearchVo> lst = uniqueProteinMap.get(key);
			
			ProteinWithDomainVo proteinVo = new ProteinWithDomainVo( lst.get(0).getTax_id(), lst.get(0).getSpecies(),
					lst.get(0).getAssembly_accession(), Integer.valueOf( lst.get(0).getProtein_length() ), lst.get(0).getProtein_accession_version() );
			
			List<DomainVo> domainVoList = new ArrayList<DomainVo>();
			for(GeneSearchVo vo : lst) {
				domainVoList.add(new DomainVo(vo.getIdx(), vo.getDb(), vo.getDb_id(), vo.getDb_desc(), vo.getLink_url(), vo.getStart(),
						vo.getEnd(), vo.getEvalue(), vo.getIpr_id(), vo.getIpr_desc(), vo.getGo_term()));
			}
			
			proteinVo.setDbs( domainVoList );
			proteinList.add( proteinVo );
		}
		return proteinList;
	}
	
	//mysql full data version
//	public List<GeneSearchVo> getHittedDomainByIPR(String[] ipr_ids, String radio) {
//		Map<String, String> map = InterproService.getCompositeOrderQuery(ipr_ids, radio);
//		
//		List<GeneSearchVo> geneSearch = this.interproMapper.getGeneSearch( map );
//		
//		return geneSearch;
//	}
//	
//	public List<GeneSearchVo> getHittedDomainByIPRWithPaging(String strIprIds, String radio, String strPagingIndex, String strPagingSize) {
//		String[] ipr_ids = strIprIds.replace(" ", "").split(",");
//
//		Map<String, String> map = InterproService.getCompositeOrderQuery(ipr_ids, radio);
//		
//		map.put("startIndex", strPagingIndex);
//		map.put("pagingSize", strPagingSize);
//
//		List<GeneSearchVo> geneSearch = this.interproMapper.getGeneSearchModified( map );
//
//		return geneSearch;
//	}
//	
//	public Integer getHitProteinTotalCounts(String[] ipr_ids, String radio) {
//		Map<String, String> map = InterproService.getCompositeOrderQuery(ipr_ids, radio);
//		
//		Integer size = this.interproMapper.getHitProteinTotalCounts( map );
//		
//		return size;
//	}
	
//	public String getAllSequenceDownload(ArrayList<Map<String, String>> list, String input, String type) throws Exception {
//	String filePath = InterproService.getFileNameFromIprTerms(input, type);
//	String protein_nm = "";
//	
//    // 파일 객체 생성
//	File file = new File(filePath);
//	FileWriter fw = null;
//	BufferedWriter bw = null;
//	
//	if(file.isFile()){
//		return filePath;
//	}else {
//		// true 지정시 파일의 기존 내용에 이어서 작성
//    	fw = new FileWriter(file, true);
//    	bw = new BufferedWriter(fw);
//    	 
//    	String data = "";
//    	for(Map<String, String> map : list){
//    		protein_nm = "tax_id:" + map.get("tax_id") + "; assembly_id:" + map.get("assembly_accession") + "; protein_id:" + map.get("protein_accession_version") + "; organism:" + map.get("species");
//			
//			String sequenceAndCDS = this.getSequenceAndCDS(map.get("tax_id"), map.get("assembly_accession"), map.get("protein_accession_version"), type);
//			
//			data += ">" + protein_nm + "\n" + sequenceAndCDS + "\n";
//		}
//		
//		bw.write(data);
//
//		// BufferedWriter FileWriter를 닫아준다.
//		bw.close();
//		fw.close();
//	}
//	
//	return filePath;
//}
	
}
