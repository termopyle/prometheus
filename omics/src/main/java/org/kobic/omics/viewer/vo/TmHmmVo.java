package org.kobic.omics.viewer.vo;

import java.util.LinkedList;
import java.util.List;

public class TmHmmVo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8010247629291685362L;

	private String tax_id;
	private String refseq_assembly_id;
	private String protein_accession_id;
	private int length;
	private int no_predicted_tmhs;
	private double exp_no_aas_in_tmhs;
	private double exp_no_first_60_aas;
	private double total_prob_n_in;
	private String location;
	private List<TmhElement> elementList;

	public String getTax_id() {
		return tax_id;
	}
	public void setTax_id(String tax_id) {
		this.tax_id = tax_id;
	}
	public String getRefseq_assembly_id() {
		return refseq_assembly_id;
	}
	public void setRefseq_assembly_id(String refseq_assembly_id) {
		this.refseq_assembly_id = refseq_assembly_id;
	}
	public String getProtein_accession_id() {
		return protein_accession_id;
	}
	public void setProtein_accession_id(String protein_accession_id) {
		this.protein_accession_id = protein_accession_id;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getNo_predicted_tmhs() {
		return no_predicted_tmhs;
	}
	public void setNo_predicted_tmhs(int no_predicted_tmhs) {
		this.no_predicted_tmhs = no_predicted_tmhs;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public double getExp_no_aas_in_tmhs() {
		return exp_no_aas_in_tmhs;
	}
	public void setExp_no_aas_in_tmhs(double exp_no_aas_in_tmhs) {
		this.exp_no_aas_in_tmhs = exp_no_aas_in_tmhs;
	}
	public double getExp_no_first_60_aas() {
		return exp_no_first_60_aas;
	}
	public void setExp_no_first_60_aas(double exp_no_first_60_aas) {
		this.exp_no_first_60_aas = exp_no_first_60_aas;
	}
	public double getTotal_prob_n_in() {
		return total_prob_n_in;
	}
	public void setTotal_prob_n_in(double total_prob_n_in) {
		this.total_prob_n_in = total_prob_n_in;
	}

	public List<TmhElement> getElementList() {
		return elementList;
	}
	public void setElementList(List<TmhElement> elementList) {
		this.elementList = elementList;
	}
	public String getLocation() {
		return location;
	}
	public void setElementList(String location) {
		String[] parts = location.split("&");
		
		for(int i=0; i<parts.length; i++) {
			String[] divs = parts[i].split("-");

			if( divs != null ) {
				if( divs.length == 3 ){ 
					if( this.elementList == null ) {
						this.elementList = new LinkedList<TmhElement>();
					}
					
					TmhElement element = new TmhElement( divs[0], divs[1], divs[2] );
					this.elementList.add(element);
				}
			}
		}
	}
}


//종자 균주 표본