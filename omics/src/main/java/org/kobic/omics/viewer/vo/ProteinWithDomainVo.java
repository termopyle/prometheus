package org.kobic.omics.viewer.vo;

import java.util.ArrayList;
import java.util.List;

import org.kobic.omics.interpro.vo.GeneSearchVo;
import org.kobic.omics.viewer.com.Utilities;

public class ProteinWithDomainVo implements java.io.Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5984417604493832302L;

	private String nid;
	private String nm;
	private String ncbi_id;
	private int length;
	private String protein_id;
	
	private String subtype;
	
	
	private String kingdom;
	
	private List<DomainVo> dbs;
	
	public ProteinWithDomainVo(String nid, String nm, String ncbi_id, int length) {
		this.nid = nid;
		this.nm = nm;
		this.ncbi_id = ncbi_id;
		this.length = length;
		
		this.dbs = new ArrayList<DomainVo>();
	}
	
	public ProteinWithDomainVo(String nid, String nm, String ncbi_id, int length, String protein_id) {
		this.nid = nid;
		this.nm = nm;
		this.ncbi_id = ncbi_id;
		this.length = length;
		this.protein_id = protein_id;
		
		this.dbs = new ArrayList<DomainVo>();
	}
	
	public ProteinWithDomainVo(String nid, String nm, String ncbi_id, int length, String protein_id, String kingdom) {
		this.nid = nid;
		this.nm = nm;
		this.ncbi_id = ncbi_id;
		this.length = length;
		this.protein_id = protein_id;
		this.kingdom = kingdom;
		
		this.dbs = new ArrayList<DomainVo>();
	}
	
	public ProteinWithDomainVo(GeneSearchVo vo) {
		this( vo.getTax_id(), vo.getSpecies(), vo.getAssembly(), Integer.valueOf( vo.getProtein_length() ), vo.getProtein_accession_version(), vo.getKingdom() );
	}
	
	public String getProtein_id() {
		return protein_id;
	}

	public void setProtein_id(String protein_id) {
		this.protein_id = protein_id;
	}

//	public ProteinWithDomainVo( List<OmicsModel> list ) {
//		this.dbs = new ArrayList<DomainVo>();
//		
//		int index = 0;
//		for (OmicsModel t : list) {
//			this.dbs.add( new DomainVo( t, index++ ) );
//		}
//
//		int length = 0;
//		if( list.size() > 0 ) {
//			String seqLen = list.get(0).getSeqLength();
//			length = Utilities.isNumeric(seqLen)==true?Integer.parseInt(seqLen):0;
//
//			this.nid = list.get(0).getTaxonomyID();
//			this.nm = list.get(0).getOrgName();
//			this.protein_id = list.get(0).getProteinID();
//			this.ncbi_id = list.get(0).getAssemblyID();
//		}
//		this.length = length;
//		
//	}
	
	public String getKingdom() {
		return kingdom;
	}

	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}

	public ProteinWithDomainVo( List<DomainVo> list ) {
		this.dbs = list;

		int length = 0;
		if( list.size() > 0 ) {
			String seqLen = list.get(0).getProtein_length();
			length = Utilities.isNumeric(seqLen)==true?Integer.parseInt(seqLen):0;

			this.nid = list.get(0).getTax_id();
			this.nm = list.get(0).getSpecies();
			this.protein_id = list.get(0).getProtein_accession_version();
			this.ncbi_id = list.get(0).getAssembly();
		}
		this.length = length;
		
	}
	
	public ProteinWithDomainVo( List<GeneSearchVo> list, boolean isGeneGroupSearch ) {
		List<DomainVo> domainVoList = new ArrayList<DomainVo>();
		for(GeneSearchVo domain : list) {
			domainVoList.add( new DomainVo( domain ) );
		}

		this.dbs = domainVoList;

		int length = 0;
		if( list.size() > 0 ) {
			String seqLen = list.get(0).getProtein_length();
			length = Utilities.isNumeric(seqLen)==true?Integer.parseInt(seqLen):0;

			this.nid = list.get(0).getTax_id();
			this.nm = list.get(0).getSpecies();
			this.protein_id = list.get(0).getProtein_accession_version();
			this.ncbi_id = list.get(0).getAssembly();
		}
		this.length = length;
	}
	
	public void addDomain(DomainVo vo) {
		if( this.dbs != null )	this.dbs.add( vo );
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getNcbi_id() {
		return ncbi_id;
	}

	public void setNcbi_id(String ncbi_id) {
		this.ncbi_id = ncbi_id;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public List<DomainVo> getDbs() {
		return dbs;
	}

	public void setDbs(List<DomainVo> dbs) {
		this.dbs = dbs;
	}
	
	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
}
