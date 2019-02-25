package org.kobic.omics.interpro.vo;

import java.util.ArrayList;
import java.util.List;

public class InterproVo {
	private String idx;
	private String tax_id;
	private String assembly_accession;
	private String protein_accession_version;
	private String ipr_id;
	private String ord;
	private String length;
	private String species;
	private String ipr_terms;
	private String sequence;
	
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	List<RangeObj> list;
	
	public InterproVo(){
		
	}
	
	public InterproVo(String tax_id, String assembly_accession, String protein_accession_version) {
		this.tax_id = tax_id;
		this.assembly_accession = assembly_accession;
		this.protein_accession_version = protein_accession_version;
	}

	public InterproVo(String tax_id, String assembly_accession, String protein_accession_version,
			String ipr_id, String ord, String ipr_terms) {
		this.tax_id = tax_id;
		this.assembly_accession = assembly_accession;
		this.protein_accession_version = protein_accession_version;
		this.ipr_id = ipr_id;
		this.ord = ord;
		this.ipr_terms = ipr_terms;
		
		this.doSplitPosition( this.ipr_terms );
	}
	
	public void externalDoSplitPosition() {
		this.doSplitPosition( this.ipr_terms );
	}
	
	private void doSplitPosition(String line) {
		this.list = new ArrayList<RangeObj>();

		if( this.ipr_terms != null && this.ipr_terms.length() > 0 ) {
			
			String[] divs = line.split(",");
			for(int i = 0; i<divs.length; i++) {
				String[] pos = divs[i].split("-");
				if( pos.length != 2 ) {
					System.out.println( "line : " + line );
					System.out.println( "splited : " + divs[i]);
				}
				
				if( this.list != null ) {
					this.list.add( new RangeObj(i, pos[0], pos[1]) );
				}
			}
		}
	}
	
	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getTax_id() {
		return tax_id;
	}

	public void setTax_id(String tax_id) {
		this.tax_id = tax_id;
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

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}

	public String getIpr_terms() {
		return ipr_terms;
	}

	public void setIpr_terms(String ipr_terms) {
		this.ipr_terms = ipr_terms;
	}

	public List<RangeObj> getList() {
		return list;
	}

	public void setList(List<RangeObj> list) {
		this.list = list;
	}

	public boolean compareWith( InterproVo vo ) {
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
}
