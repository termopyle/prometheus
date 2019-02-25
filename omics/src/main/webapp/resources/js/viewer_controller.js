function loading(parent, loading) {
	var parentDiv = $(parent);
	var a = parentDiv.width();
	var b = parentDiv.offset().left;
	var c = parentDiv.offset().top;
	
	var loadingImgDiv = $(loading);
	loadingImgDiv.css("top", c);
	
	loadingImgDiv.css("z-index", 99999);
	
	var x = $("#gene-structure").width()/2 - (loadingImgDiv.width()/2);
	loadingImgDiv.css("left", b + x);
}

function load() {
	loading(".gene-structure-view", "#gene_structure_loading");
	loading(".domain-title", "#protein_domains_loading");
	loading("#canvas", "#tmhmm_loading");
}

function hiding(loading) {
	$(loading).hide();
}


function showing(loading) {
	$(loading).hide();
}

//function hide(loading) {
//	hiding("#gene_structure_loading");
//	hiding("#protein_domains_loading");
//	hiding("#tmhmm_loading");
//}

function show() {
	$("#gene_structure_loading").show();
	$("#protein_domains_loading").show();
	$("#tmhmm_loading").show();
}



$(document).ready(function(){
	load();
	show();
	
//	data: {tax_id:'210', protein_accession_id:'WP_026938321.1', refseq_assembly_id:'GCF_000688915.1'},
	var tax_id = $("#tax_id").val();
	var refseq_assembly_id = $("#refseq_assembly_id").val();
	var protein_accession_id = $("#protein_accession_id").val();
	
	try {
		// To get gene structure information from server
		var geneStructureAjaxRetObj = getGeneStructureMessageFromServer( tax_id, refseq_assembly_id, protein_accession_id );
		
		if( geneStructureAjaxRetObj.status != 500 ) {
			var json = jQuery.parseJSON(geneStructureAjaxRetObj.responseText);
//				
//	  		GeneStructure.init( json );
//	  		GeneStructure.paint();
		}
	}catch(err) {
		
	}

	try {
		// To get InterPro domains from server
		var domainInfoAjaxRetObj = getMessageFromServer( tax_id, refseq_assembly_id, protein_accession_id );
		if( domainInfoAjaxRetObj.status != 500 ) {
			var json = jQuery.parseJSON(domainInfoAjaxRetObj.responseText);

//		  	viewerStructure.init( json );
//		  	viewerStructure.paint( json );
		}
	}catch(err) {
		
	}

	try {
		
		// To get TmHmm information from server
		var tmmInfoAjaxRetObj = getTMHMMMessageFromServer( tax_id, refseq_assembly_id, protein_accession_id );
	
		if( tmmInfoAjaxRetObj.status != 500 ) {
			var json = jQuery.parseJSON( tmmInfoAjaxRetObj.responseText );
					
//			transMembraneObj.init();
//		 	transMembraneObj.paint( eval(json) );
		}
	}catch(err) {
		
	}
});