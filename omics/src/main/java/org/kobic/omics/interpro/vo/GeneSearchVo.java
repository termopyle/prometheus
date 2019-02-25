package org.kobic.omics.interpro.vo;

import java.util.List;

public class GeneSearchVo {
	private String idx;
	private String tax_id;
	private String species;
	private String assembly_accession;
	private String assembly;
	private String protein_accession_version;
	private String protein_length;
	private String db;
	private String db_id;
	private String db_desc;
	private String link_url;
	private long start;
	private long end;
	private String evalue;
	private String ipr_id;
	private String ipr_desc;
	private String go_term;
	private String type;
	
	private String kingdom;
	
//	List<GeneSearchVo> domains;

	public String getKingdom() {
		return kingdom;
	}

	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}

	List<RangeObj> list;

	public GeneSearchVo() {
		;
	}
	
	public GeneSearchVo(long start, long end, String ipr_id) {
		this.start = start;
		this.end = end;
		this.ipr_id = ipr_id;
	}

	public String getAssembly_accession() {
		return assembly_accession;
	}

	public void setAssembly_accession(String assembly_accession) {
		this.assembly_accession = assembly_accession;
	}

	public String getProtein_accession_version() {
		return protein_accession_version;
	}

	public void setProtein_accession_version(String protein_accession_version) {
		this.protein_accession_version = protein_accession_version;
	}

	public String getIpr_id() {
		return ipr_id;
	}

	public void setIpr_id(String ipr_id) {
		this.ipr_id = ipr_id;
	}

	public String getProtein_length() {
		return protein_length;
	}

	public void setProtein_length(String protein_length) {
		this.protein_length = protein_length;
	}
	
	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
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

	public String getIpr_desc() {
		return ipr_desc;
	}

	public void setIpr_desc(String ipr_desc) {
		this.ipr_desc = ipr_desc;
	}

	public String getGo_term() {
		return go_term;
	}

	public void setGo_term(String go_term) {
		this.go_term = go_term;
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

	public String getAssembly() {
		return assembly;
	}

	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}
	
	public List<RangeObj> getList() {
		return list;
	}
	
	

//	public List<GeneSearchVo> getDomains() {
//		return domains;
//	}
//
//	public void setDomains(List<GeneSearchVo> domains) {
//		this.domains = domains;
//	}
	
	

	public void setList(List<RangeObj> list) {
		this.list = list;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean compareWith( GeneSearchVo vo ) {
		if( this.list == null )		return false;
		if( vo.getList() == null )	return false;
		
		List<RangeObj> targetList = vo.getList();
		
		for(int i=0; i<this.list.size(); i++) {
			RangeObj _1st = this.list.get(i);
			for(int j=0; j<targetList.size(); j++) {
				RangeObj _2nd = targetList.get(j);
				if( _1st.getEnd() < _2nd.getStart() )
					return true;
			}
		}
		
		return false;
	}
	
	public boolean isOverlapped(long start, long end) {
		return start < this.start + this.end && start + end > this.start;
	}
	
	public boolean isOverlapped(GeneSearchVo vo) {
		return vo.getStart() < this.start + (this.end-this.start+1) && vo.getStart() + (vo.getEnd()-vo.getStart()+1) > this.start;
	}
	
	public void expandRange(GeneSearchVo newVo) {
		this.start = Math.min( this.start, newVo.getStart() );
		this.end = Math.max( this.end, newVo.getEnd() );
	}
	
	
	public String getUniqueProteinId() {
		return this.getTax_id() + "@" + this.getAssembly_accession() + "@" + this.getProtein_accession_version();
	}
	
	public static GeneSearchVo clone(long start, long end, String ipr_id, String type) {
		GeneSearchVo vo = new GeneSearchVo();
		vo.setStart( start );
		vo.setEnd( end );
		vo.setIpr_id( ipr_id );
		vo.setType( type );
		
		return vo;
	}
}
