package org.kobic.omics.viewer.vo;

import java.util.ArrayList;
import java.util.List;

public class GeneStructureVo extends FeatureVo{
	private long leftBoundary;
	private long rightBoundary;

	private List<FeatureVo> Features;
	
	public GeneStructureVo() {
		this.Features = new ArrayList<FeatureVo>();
	}

	public GeneStructureVo(String TaxId, String organism, String RefSeqAssemblyID, String AssemblyName, String LocusVersion
			, String Source, String Feature, long Start, long End,  String Score, String Strand, String Frame, String Attribute, String ProteinID, String GeneName) {
		super(TaxId, organism, RefSeqAssemblyID, AssemblyName, Source, LocusVersion, Feature, Start, End, Score, Strand, Frame, Attribute, ProteinID, GeneName);
		
		this.leftBoundary = Start;
		this.rightBoundary = End;
		
		this.Features = new ArrayList<FeatureVo>();
	}
	
	public GeneStructureVo( List<FeatureVo> featureList ) {
		super( GeneStructureVo.getFindRegionFeature( featureList ) );
		
		this.Features = new ArrayList<FeatureVo>();
		for(FeatureVo feature : featureList ) {
			this.Features.add( feature );
		}
	}

	public List<FeatureVo> getFeatures() {
		return Features;
	}

	public void setFeatures(List<FeatureVo> features) {
		Features = features;
		this.reCalibarateBoundary();
	}
	
	public void addFeature( FeatureVo feature ) {
		this.Features.add( feature );
	}
	
	private static FeatureVo getFindRegionFeature( List<FeatureVo> list ) {
		for(FeatureVo feature : list) {
			if( feature.getFeature().equals("region") )	{
				FeatureVo tmp = feature;
				list.remove( feature );

				return tmp;
			}
		}
		return null;
	}

	public long getLeftBoundary() {
		return leftBoundary;
	}

	public void setLeftBoundary(long leftBoundary) {
		this.leftBoundary = leftBoundary;
	}

	public long getRightBoundary() {
		return rightBoundary;
	}

	public void setRightBoundary(long rightBoundary) {
		this.rightBoundary = rightBoundary;
	}
	
	
	public void reCalibarateBoundary() {
		long min =  Long.MAX_VALUE;
		long max = Long.MIN_VALUE;
		for(FeatureVo feature : this.Features) {
			if( feature.getStart() < min ) min = feature.getStart();
			if( feature.getEnd() > max ) max = feature.getEnd();
		}
		this.leftBoundary = min;
		this.rightBoundary = max;
	}
}
