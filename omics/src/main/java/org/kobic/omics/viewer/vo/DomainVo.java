package org.kobic.omics.viewer.vo;

import org.kobic.omics.interpro.vo.GeneSearchVo;
import org.kobic.omics.thrift.service.InterproScanModel;

public class DomainVo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7819498224896143463L;
	private String tax_id;
	private String species;
	private String assembly_accession;
	private String assembly;
	private String protein_accession_version;
	private String protein_length;

	
	
	private String no;
	private String db;
	private String db_id;
	private String db_desc;
	private String link_url;
	private long start;
	private long end;
	private String evalue;
	private String ipr_id;
	private String ipr_desc;
	private String go;
	
	private String type;
	private int typeWeight;


	public DomainVo() {
		
	}

	public DomainVo( InterproScanModel model, int no ) {
		this.no = Integer.toString(no);
		this.db =  model.getPfam();
		this.db_id = model.getSigAccssion();
		this.db_desc = model.getSigDesc();
		this.link_url = model.getRefLink();
		this.start = Long.parseLong( model.getDomainStart() );
		this.end = Long.parseLong( model.getDomainEnd() );
//		this.evalue = Utilities.isNumericWithExp( model.getEvalue())==true?Double.parseDouble(  model.getEvalue() ):0;
		this.evalue = model.getEvalue();
		this.ipr_id = model.getAnnotationAccession();
		this.ipr_desc = model.getAnnotationDesc();
		this.go = model.getGo();
	}

	public DomainVo( String no, String db, String db_id, String db_desc, String link_url, long start, long end, String evalue, String ipr_id, String ipr_desc, String go) {
		this.no = no;
		this.db = db;
		this.db_id = db_id;
		this.db_desc = db_desc;
		this.link_url = link_url;
		this.start = start;
		this.end = end;
		this.evalue = evalue;
		this.ipr_id = ipr_id;
		this.ipr_desc = ipr_desc;
		this.go = go;
	}
	
	public DomainVo( String no, String db, String db_id, String db_desc, String link_url, long start, long end, double evalue, String ipr_id, String ipr_desc, String go) {
		this.no = no;
		this.db = db;
		this.db_id = db_id;
		this.db_desc = db_desc;
		this.link_url = link_url;
		this.start = start;
		this.end = end;
		this.evalue = Double.toString(evalue);
		this.ipr_id = ipr_id;
		this.ipr_desc = ipr_desc;
		this.go = go;
	}
	
	public DomainVo(String db, String db_id, String db_desc, String link_url, String start, String end, String evalue, String ipr_id, String ipr_desc, String go) {
		this.db = db;
		this.db_id = db_id;
		this.db_desc = db_desc;
		this.link_url = link_url;
		this.start = Long.parseLong(start);
		this.end = Long.parseLong(end);
		this.evalue = evalue;
		this.ipr_id = ipr_id;
		this.ipr_desc = ipr_desc;
		this.go = go;
	}
	
	public DomainVo( GeneSearchVo vo ) {
		this( vo.getIdx(), vo.getDb(), vo.getDb_id(), vo.getDb_desc(), vo.getLink_url(), vo.getStart(), vo.getEnd(), vo.getEvalue(), vo.getIpr_id(), vo.getIpr_desc(), vo.getGo_term() );
	}
	

	public String getTax_id() {
		return tax_id;
	}
	public void setTax_id(String tax_id) {
		this.tax_id = tax_id;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public String getAssembly_accession() {
		return assembly_accession;
	}
	public void setAssembly_accession(String assembly_accession) {
		this.assembly_accession = assembly_accession;
	}
	public String getAssembly() {
		return assembly;
	}
	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}
	public String getProtein_accession_version() {
		return protein_accession_version;
	}
	public void setProtein_accession_version(String protein_accession_version) {
		this.protein_accession_version = protein_accession_version;
	}
	public String getProtein_length() {
		return protein_length;
	}
	public void setProtein_length(String protein_length) {
		this.protein_length = protein_length;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getDb() {
		return db;
	}
	public void setDb(String db) {
		this.db = db;
	}
	public String getDb_id() {
		return db_id;
	}
	public void setDb_id(String db_id) {
		this.db_id = db_id;
	}
	public String getDb_desc() {
		return db_desc;
	}
	public void setDb_desc(String db_desc) {
		this.db_desc = db_desc;
	}
	public String getLink_url() {
		return link_url;
	}
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public String getEvalue() {
		return evalue;
	}
	public void setEvalue(String evalue) {
		this.evalue = evalue;
	}
	public String getIpr_id() {
		return ipr_id;
	}
	public void setIpr_id(String ipr_id) {
		this.ipr_id = ipr_id;
	}
	public String getIpr_desc() {
		return ipr_desc;
	}
	public void setIpr_desc(String ipr_desc) {
		this.ipr_desc = ipr_desc;
	}
	public String getGo() {
		return go;
	}
	public void setGo(String go) {
		this.go = go;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTypeWeight() {
		return typeWeight;
	}
	public void setTypeWeight(int typeWeight) {
		this.typeWeight = typeWeight;
	}
}
