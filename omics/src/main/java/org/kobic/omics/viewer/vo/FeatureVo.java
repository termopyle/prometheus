package org.kobic.omics.viewer.vo;

import java.util.HashMap;
import java.util.Map;

public class FeatureVo {
	private String TaxId;
	private String organism;
	private String RefSeqAssemblyID;
	private String AssemblyName;
	private String LocusVersion;
	private String Source;
	private String Feature;
	private long Start;
	private long End;
	private String Score;
	private String Strand;
	private String Frame;
	private String Attributes;
	private String ProteinID;
	private String GeneName;
	
	private int layerNo;

	private Map<String, String> AttributesMap;
	
	public FeatureVo() {}
	
	public FeatureVo( String TaxId, String organism, String RefSeqAssemblyID, String AssemblyName, String LocusVersion
			, String Source, String Feature, long Start, long End,  String Score, String Strand, String Frame, String Attributes, String ProteinID, String GeneName ) {
		this.TaxId = TaxId;
		this.organism = organism;
		this.RefSeqAssemblyID = RefSeqAssemblyID;
		this.AssemblyName = AssemblyName;
		this.LocusVersion = LocusVersion;
		this.Score = Score;
		this.Source = Source;
		this.Feature = Feature;
		this.Start = Start;
		this.End = End;
		this.Strand = Strand;
		this.Frame = Frame;
		this.Attributes = Attributes;
		this.ProteinID = ProteinID;
		this.GeneName = GeneName;

		this.initAttributes( Attributes );
	}
	
	public FeatureVo( FeatureVo vo ) {
		this.TaxId = vo.getTaxId();
		this.organism = vo.getOrganism();
		this.RefSeqAssemblyID = vo.getRefSeqAssemblyID();
		this.AssemblyName = vo.getAssemblyName();
		this.LocusVersion = vo.getLocusVersion();
		this.Score = vo.getScore();
		this.Source = vo.getSource();
		this.Feature = vo.getFeature();
		this.Start = vo.getStart();
		this.End = vo.getEnd();
		this.Strand = vo.getStrand();
		this.Frame = vo.getFrame();
		this.Attributes = vo.getAttributes();
		this.ProteinID = vo.getProteinID();
		this.GeneName = vo.getGeneName();
		
		this.initAttributes( vo.getAttributes() );
	}
	
	private void initAttributes( String attribute ) {
		this.AttributesMap = new HashMap<String, String>();
		String[] attributes = attribute.split(";");
		for(int i=0; i<attributes.length; i++) {
			String[] divs = attributes[i].split("=");
			if( divs.length == 2 ) {
				this.AttributesMap.put( divs[0], divs[1] );
			}
		}
	}

	public int getLayerNo() {
		return layerNo;
	}

	public void setLayerNo(int layerNo) {
		this.layerNo = layerNo;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}

	public String getTaxId() {
		return TaxId;
	}

	public void setTaxId(String taxId) {
		TaxId = taxId;
	}

	public String getLocusVersion() {
		return LocusVersion;
	}

	public void setLocusVersion(String locusVersion) {
		LocusVersion = locusVersion;
	}

	public String getScore() {
		return Score;
	}

	public void setScore(String score) {
		Score = score;
	}

	public String getFeature() {
		return Feature;
	}

	public void setFeature(String feature) {
		Feature = feature;
	}

	public long getStart() {
		return Start;
	}

	public void setStart(long start) {
		Start = start;
	}

	public long getEnd() {
		return End;
	}

	public void setEnd(long end) {
		End = end;
	}

	public String getStrand() {
		return Strand;
	}

	public void setStrand(String strand) {
		Strand = strand;
	}

	public String getFrame() {
		return Frame;
	}

	public void setFrame(String frame) {
		Frame = frame;
	}

	public String getAttributes() {
		return Attributes;
	}

	public void setAttributes(String attributes) {
		this.Attributes = attributes;
	}

	public Map<String, String> getAttributesMap() {
		return this.AttributesMap;
	}

	public void setAttributesMap(Map<String, String> attributeMap) {
		this.AttributesMap = attributeMap;
	}

	public String getOrganism() {
		return organism;
	}

	public void setOrganism(String organism) {
		this.organism = organism;
	}

	public String getRefSeqAssemblyID() {
		return RefSeqAssemblyID;
	}

	public void setRefSeqAssemblyID(String refSeqAssemblyID) {
		RefSeqAssemblyID = refSeqAssemblyID;
	}

	public String getAssemblyName() {
		return AssemblyName;
	}

	public void setAssemblyName(String assemblyName) {
		AssemblyName = assemblyName;
	}

	public String getProteinID() {
		return ProteinID;
	}

	public void setProteinID(String proteinID) {
		ProteinID = proteinID;
	}

	public String getGeneName() {
		return GeneName;
	}

	public void setGeneName(String geneName) {
		GeneName = geneName;
	}
	
	public boolean isOverlapped( FeatureVo vo ) {
		long min = Math.min(this.Start, vo.getStart());
		long max = Math.max(this.End, vo.getEnd());
		long range = max - min;
		
		long diff = (this.End - this.Start) + (vo.getEnd() - vo.getStart());

		if( diff > range ) return true;

		return false;	
	}
}
