package org.kobic.omics.common;

public interface OmicsConstants {

	public static final String DEFAULT_TEMP_NAMES = "KOBIS_";
	
	public static final String NULL_ID = "NULL";
	
	public static final Integer ROOT_ID = -1;
	
	public static final String ROOT_NAME = "Classification System";
	
	public static final String JSON_FORMAT = "[%s]";
	
	public static final String DEFAULT_STATUS = "open";
	
	public static final int NCBI_TAXON = 0x001;
	public static final int ITIS_TAXON = 0x002;
	public static final int GBIF_TAXON = 0x003;
	public static final int KOBIS_TAXON = 0x004;
	
	public static final String REF_SEQ_DB = "ref_seq";
	public static final String ENSEMBL_DB = "ensembl";
	public static final String PLANT_DB = "plant";
	
	public static final String ID_MATCH = "^[a-zA-Z]{1}[a-zA-Z0-9_]{3,11}$";
	public static final String PASSWD_MATCH = "([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])|([a-zA-Z0-9])";
	
	public static final String[] FILE_ARROW_EXTETION = new String[] {"zip", "png", "pdf", "doc", "hwp", "txt", "xlsx", "gz", "x-gzip", "x-apple-diskimage","dmg"};
}
