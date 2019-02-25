var Track = function( name, start, end, order, track_width, track_height, label_width, unitLength ) {
	this._ord = order;
	this._name = name;
	this._start = start;
	this._end = end;
	this._label_width = label_width;
	this._width = track_width;
	this._feature_area_width = parseInt(this._width) - parseInt(this._label_width);
	this._unit_length = unitLength;
	this._features = [];
	this._active = true;
	
	this._height = track_height;
};

Track.prototype = {
	getName : function() {
		return this._name;
	},
	setName : function(sName) {
		this._name = sName;
	},
	turnOn : function(){
		this._active = true;
	},
	turnOff : function() {
		this._active = false;
	},
	isActiveTrack : function() {
		return this._active;
	},
	getFeatureLayers : function () {
		
//		var S_featureLayer = Math.floor(Date.now());

		var hashmap = new Map();

		var layer1 = [];

		layer1.push( this._features[0] );
		hashmap.put(1, layer1);

		for(var i=1; i<this._features.length; i++) {	// features
			var depth = hashmap.size();
			var currentFeature = this._features[i];

			var isOverlapped = false;
			for(var j=1; j<=depth; j++) {				// layer depth
				var currentLayerElements = hashmap.get(j);

				// Check this feature range is overlapped with just one layer
				for(var k=0; k<currentLayerElements.length; k++) {
//					if( currentFeature.getStart() > currentLayerElements[k].getEnd() ) continue;
//					if( currentFeature.getEnd() < currentLayerElements[k].getStart() ) continue;

					isOverlapped = currentFeature.isOverlapped( currentLayerElements[k] );

					if( isOverlapped == true )
						break;
				}
				
				if( isOverlapped == false ) {
					var baseElement = currentLayerElements[0];
					
					currentFeature.setTrackOrder( baseElement.getTrackOrder() );
					
					if( baseElement.getStart() > baseElement.getStart() ) currentLayerElements.unshift(currentFeature);
					else currentLayerElements.push(currentFeature);
					
					break;
				}
			}

			if( isOverlapped == true ) {
				var newDepth = parseInt(depth) + 1;
				var layerNew = [];

				currentFeature.setTrackOrder( newDepth );
				
				layerNew.push( currentFeature );

				hashmap.put( newDepth, layerNew );
			}
		}
//		var E_featureLayer = Math.floor(Date.now());
		
//		console.log( "make layer : " + (E_featureLayer-S_featureLayer)/1000 + "sec");

		return hashmap;
	},
	getFeatureLayersImproved : function () {
//		var S_featureLayer = Math.floor(Date.now());

		var hashmap = new Map();

//		console.log( this._name + " " + this._features.length );
		for(var i=0; i<this._features.length; i++) {	// features
			var layer = this._features[i]._trackOrder;
			
			if( hashmap.containsKey(layer) ) {
				hashmap.get(layer).push( this._features[i] );
			}else {
				var layer1 = [];
				layer1.push( this._features[i] );
				hashmap.put( layer, layer1 );
			}
		}
//		var E_featureLayer = Math.floor(Date.now());
		
//		console.log( "make layer : " + (E_featureLayer-S_featureLayer)/1000 + "sec");

		return hashmap;
	},
	getDiv : function() {
		var featureLayers = this.getFeatureLayersImproved();

		var trackHeight = this._height * parseInt( featureLayers.size() );
		if( featureLayers.size() == 0 )	trackHeight = this._height;

		var labelAreaWith = this._label_width;
		var featureAreaWidth = parseInt( this._width ) - parseInt( this._label_width );

		var ysBorderClass = "border";

		if( this._ord == 0 )	ysBorderClass = "";

		var trackFrame = $("<div id='"+this._name+"-track' class='gene-str-track "+ysBorderClass+"' style='left:0px;width:920px;height:"+trackHeight+"px;float:left;background:#fff;'></div>");

		var lableFrame = $("<div class='label-area noselect' style='width:120px;height:"+trackHeight+"px;float:left;background:none;text-align:center;line-height:"+trackHeight+"px;cursor:default'>"+this._name+" Track</div>");
		var featureFrame = $("<div class='feature-area' style='width:800px;height:"+trackHeight+"px;float:left;background:none;position:relative;'></div>");

		
// 계영수정S		var trackFrame = $("<div id='"+this._name+"-track' class='gene-str-track "+ysBorderClass+"' style='left:0px;width:"+this._width+"px;height:"+trackHeight+"px;float:left;background:#e5eff3;'></div>");
//
//				var lableFrame = $("<div class='label-area' style='width:"+this._label_width+"px;height:"+trackHeight+"px;float:left;background:none;text-align:center;line-height:"+trackHeight+"px'>"+this._name+" Track</div>");
// 계영수정E		var featureFrame = $("<div class='feature-area' style='width:"+featureAreaWidth+"px;height:"+trackHeight+"px;float:left;background:none;position:relative;'></div>");

//		var backboneFrame = $("<div class='backbone' style='top:11px;width:"+featureAreaWidth+"px;height:3px;float:left;background:lightgray;position:relative;z-index:-1'></div>");
//
//		featureFrame.append( backboneFrame );
		
		if( !(this._name == 'Gene' || this._name == 'mRNA') && this._features.length > 0 ) {
			for(var i=0;i<featureLayers.size();i++) {
				var min = this._features[i]._start;
				var max = this._features[this._features.length-1]._end;
	
				var start = parseInt(parseInt( min ) - parseInt( this._start ) );
				var end = parseInt(parseInt(max ) - parseInt( this._start ));
				var width = parseInt((parseInt(end) - parseInt(start) + 1)) * parseFloat( this._unit_length );
				
				start = parseInt(start) * parseFloat(this._unit_length);
				end = parseInt(end) * parseFloat(this._unit_length);
	
				var newHeight = 2;
				
				var idx = i+1;
	
				var featureTop = ((parseInt(idx) - parseInt(1)) * parseInt(this._features[i]._trackHeight)) + ((this._features[i]._trackHeight-newHeight)/2);
	
				var feature_colour = getGeneFeatureColour( this._features[0]._type );
	
				var background = $("<div id='background-frame-"+this._name+"' class='feature noselect' style='top:"+featureTop+"px;left:"+start+"px;width:"+width+"px;height:"+newHeight+"px;float:left;background:"+feature_colour+";text-align:center;line-height:"+this._height+"px;z-index:1;cursor:default;'></div>");
	
				console.log( this._trackOrder);
				console.log( background );
	
				featureFrame.append( background );
			}
		}

		for(var i=0;i<this._features.length;i++) {
			var feature = this._features[i].getDiv( this._start, this._unit_length );
			featureFrame.append( feature );
		}

		trackFrame.append( lableFrame );
		trackFrame.append( featureFrame );
		
		this._height = trackHeight;

		return trackFrame;
	},
	addFeatures : function(f) {
		for(var i=0; i<f.length; i++)
			this.addFeature( f[i] );
	},
	addFeature : function(f){
		this._features.push(f);
	},
	getSize : function(){
		return this._features.length;
	},
	getHeight : function() {
		return this._height;
	}
};


var Feature = function( name, start, end, type, trackHeight, featureHeight ) {
	this._name = name;
	this._start = start;
	this._end = end;
	this._type = type;
	this._height = featureHeight;
	this._trackHeight = trackHeight;
	this._trackOrder = 1;
};

Feature.prototype = {
		getStart : function() {
			return this._start;
		},
		getEnd : function() {
			return this._end;
		},
		setTrackOrder : function(order) {
			this._trackOrder = order;
		},
		getTrackOrder : function() {
			return this._trackOrder;
		},
		getDiv : function( basePos, unitLength ){
			var start = parseInt(parseInt( this.getStart() ) - parseInt( basePos ) );
			var end = parseInt(parseInt( this.getEnd() ) - parseInt( basePos ));
			var width = parseInt((parseInt(end) - parseInt(start) + 1)) * parseFloat(unitLength);

			start = parseInt(start) * parseFloat(unitLength);
			end = parseInt(end) * parseFloat(unitLength);
			
			var printLabel = this._name;
			
			if( !(this._type == 'gene' || this._type == 'mRNA') )	printLabel = '';

			var barMargin = (parseInt(parseInt(this._trackHeight) - parseInt(this._height))/2);
			var featureTop = ((parseInt(this._trackOrder) - parseInt(1)) * parseInt(this._trackHeight)) + barMargin;

			var feature_colour = getGeneFeatureColour( this._type );
			
			var feature = $("<div class='feature "+ this._type.toLowerCase() + " noselect' style='top:"+featureTop+"px;left:"+start+"px;width:"+width+"px;height:"+this._height+"px;float:left;background:"+feature_colour+";text-align:center;line-height:"+this._height+"px;z-index:10;cursor:default;'>"+printLabel+"</div>");
			
			return feature;
		},
		isOverlapped : function(feature) {
			var min = Math.min(this._start, feature.getStart());
			var max = Math.max(this._end, feature.getEnd());
			var range = parseInt(max) - parseInt(min);
			
			var diff = (parseInt(this._end) - parseInt(this._start)) + (parseInt(feature.getEnd()) - parseInt(feature.getStart()));

			if( parseInt(diff) > parseInt(range) ) return true;

			return false;
		}
};

var GeneStructure = {
	gap:0,
	width:0,
	height:0,
	margin:0,
	length:0,
	start:0,
	original_end:-1,
	original_start:-1,
	end:0,
	gene_start:0,
	gene_end:0,
	gene_length:0,
	item_bar_height:0,
	track_height:0,
	gene_name:null,
	unitLength:0,
	label_width:180,
	json:null,
	init:function( obj ) {
		this.json					= obj;
		this.margin					= 30;
		this.item_bar_height		= 13;
		this.track_height			= 30;
		this.gap					= 5;
		this.start					= obj.leftBoundary;
		this.end					= obj.rightBoundary;
//	 	this.gene_name				= obj.LocusVersion;
		this.length					= (this.end - this.start+1);
		
		this.gene_start				= obj.Start;
		this.gene_end				= obj.End;
		this.gene_length			= (this.gene_end - this.gene_start+1);

		this.width 					= $("#gene-structure").width();
		this.unitLength				= (this.width - this.label_width) / this.length;

//		this.gene_name = obj.Attributes.split("Name=")[1].split(";")[0];
		this.gene_name = obj.GeneName;

		this.addButtonEvent();
	},getColor:function(feature) {
		if( feature == "region" )				return "#EDFF88";
		else if( feature == "gene" ) 			return "#009782";
		else if( feature == "mRNA" ) 			return "#025faf";
		else if( feature == "exon" )			return "#003869";
		else if( feature == "CDS" )				return "#7c5200";
		else if( feature == "ncRNA")			return "ffa800";
		else if( feature == "repeat_region" )	return "#433f79";
		else if( feature == "tRNA" )			return "#5aa0e5";
		else if( feature == "rRNA" )			return "#9fc80d";
		else									return "black";
	}, zoomin:function(){
		var window_size = parseInt( this.length * 0.05 );

		this.setOriginalLocus();

		if( this.end <= this.start )	this.end = this.start + 100;
		else							this.end -= window_size;

		this.length					= (parseInt(this.end) - parseInt(this.start)+1);
		this.unitLength				= (this.width - (2*this.margin)) / this.length;

		$("#gene-structure > .gene-structure-view").html('');

		this.paint();
	}, zoomout:function(){
		var window_size = parseInt(this.length*0.05);
		
		this.setOriginalLocus();

		if( this.end >= this.original_end )	this.end = this.original_end;
		else								this.end += window_size;

		this.length					= (this.end - this.start+1);
		this.unitLength				= (this.width - (2*this.margin)) / this.length;
		
		$("#gene-structure > .gene-structure-view").html('');

		this.paint();
	}, pan_left:function(){
		var window_size = parseInt(this.length * 0.01);
		this.setOriginalLocus();

		this.start -= window_size;
		this.end -= window_size;
		
		if( this.start < 1 ){
			this.start = 1;
			this.end = this.length + 1;
		}
		
		this.paint();
	}, pan_right:function(){
		var window_size = parseInt(this.length * 0.01);

		this.setOriginalLocus();

		this.start += window_size;
		this.end += window_size;

		if( this.end > this.original_end ) {
			this.end = this.original_end;
			this.start = this.orignal_end - window_size + 1;
		}
		
		this.paint();
	}, paint:function() {
		var str = numberWithCommas(this.start) + "-" + numberWithCommas(this.end);
		$(".locus").val(  str );

		this.paintFeatures();
	}, optimizeCanvasBox:function( tracksHeight ) {
		var title = $("#gene-structure > #title-frame > .gene-structure-title");

		title.text("Gene Structure");

		var geneStrView = $(".gene-structure-view");

		var titlebar_height = $("#gene-structure > #title-frame > .gene-structure-title").height();
		var controlbar_height = $("#gene-structure > #title-frame > .gene-structure-controller").height();

		$("#gene-structure").css("height", parseInt(titlebar_height) + parseInt(tracksHeight) + parseInt(controlbar_height) + 3 );
		$("#gene-structure").css("background", "none" );
		geneStrView.css( "height", tracksHeight );

	}, paintFeatures:function() {
		var geneStrView = $(".gene-structure-view");
		
		var segments = ["mRNA", "exon", "CDS", "ncRNA", "repeat_region", "tRNA", "rRNA"];
		
		// Processing for gene area
		var geneTrack = new Track( "Gene", this.start, this.end, 0, this.width, this.track_height, this.label_width, this.unitLength );
		geneTrack.addFeature( new Feature(this.gene_name, this.gene_start, this.gene_end, "gene", this.track_height, this.item_bar_height ) );

		var geneTrackObj = geneTrack.getDiv ();
		geneStrView.append( geneTrackObj );
		
		var tracksHeight = parseInt( geneTrack.getHeight() ) + parseInt(geneTrackObj.css("border-top") );
		
		// add tracks
		for(var i=0; i<segments.length; i++) {
			var nTrack = new Track( segments[i], this.start, this.end, i, this.width, this.track_height, this.label_width, this.unitLength );

			var parameters = getFeatures( this.json.Features, segments[i], this.track_height, this.item_bar_height );

			nTrack.addFeatures( parameters );

			if( nTrack.isActiveTrack() == true ) {

				// It's too slow location, Have to speed up drawing
				var trackObj = nTrack.getDiv ();

				geneStrView.append( trackObj );
				
				tracksHeight = parseInt( tracksHeight ) + parseInt( nTrack.getHeight() ) + parseInt(trackObj.css("border-top"));
			}
		}

		this.optimizeCanvasBox( tracksHeight );
	},
	generateToolTip:function(obj, item){
		var contentText = $("<div id='qtip-gene-str' style='width:100%'></div>");
		var featureRecord = $("<div class='record'><div class='tooltip-header'>Feature</div><div class='tooltip-content'>" + item.Feature + "</div></div>");
		var locusRecord = $("<div class='record'><div class='tooltip-header'>Locus</div><div class='tooltip-content'>" + item.Start + "-" + item.End + "</div></div>");
		var locusVerRecord = $("<div class='record'><div class='tooltip-header'>LocusVersion</div><div class='tooltip-content'>" + item.LocusVersion + "</div></div>");
		var strandRecord = $("<div class='record'><div class='tooltip-header'>Strand</div><div class='tooltip-content'>" + item.Strnad + "</div></div>");
		
		contentText.append( featureRecord );
		contentText.append( locusVerRecord );
		contentText.append( locusRecord );
		contentText.append( strandRecord );

		$(obj).qtip({
			content: {
				text: contentText,
				title: 'Tooltip'
			},
			position: {
				corner: {
					target: 'bottomLeft',
					tooltip: 'topLeft'
				}
			},show: {
                when: { target: false, event: 'mouseover' },//click target: 'img'false
                delay: 300,
                solo: true,
                ready: false
            }, hide: {
                fixed: true
                , delay: 500
            }, style: {
				width: 300,
				height: 80,
				padding: 5,
				background: '#FFE99F',
				color: '#727272',
				textAlign: 'left',
				border: {
					width: 0,
					radius: 2,
					color: '#FF94B2'
				},
				tip: 'bottomLeft',
				name: 'dark', // Inherit the rest of the attributes from the preset dark style
				title:{
					background:"#B9F582",
					color:"gray"
				}
			}
		});
	}, addButtonEvent:function() {
		$(".zoom-in").click(function(){
			GeneStructure.zoomin();
		});
		
		$(".zoom-out").click(function(){
			GeneStructure.zoomout();
		});
		
		$(".left").click(function(){
			GeneStructure.pan_left();
		});
		
		$(".right").click(function(){
			GeneStructure.pan_right();
		});
	}, setOriginalLocus:function() {
		if( this.original_end == -1 )	this.original_end = this.end;
		if( this.original_start == -1 ) this.original_start = this.start;
	}
};

function getFeatures(obj, type, trackHeight, itemBarHeight) {
	var featureArray = [];

	for(var i=0; i<obj.length; i++) {
		var item = obj[i];

		if( item.Feature == type ) {
			var name = getNameFromAttribures(item.Attributes);

			var f = new Feature( name, item.Start, item.End, type, trackHeight, itemBarHeight )
			f.setTrackOrder( item.layerNo );

			featureArray.push( f );
		}
	}
	
	return featureArray;
}

function getGeneFeatureColour( type ) {
	if( type == "gene" ) 				return "#009782";
	else if( type == "mRNA" ) 			return "#025faf";
	else if( type == "exon" )			return "#003869";
	else if( type == "CDS" )			return "#7c5200";
	else if( type == "ncRNA")			return "ffa800";
	else if( type == "repeat_region" )	return "#433f79";
	else if( type == "tRNA" )			return "#5aa0e5";
	else if( type == "rRNA" )			return "#9fc80d";
	else								return "black";
}

function getNameFromAttribures(attribures) {
	var name = "";
	try {
		name = attribures.split("Name=")[1].split(";")[0];	
	}catch(err) {
		name = "";
	}
	return name;
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function getGeneStructureMessageFromServer(p_tax_id, p_refseq_assembly_id, p_protein_accession_id) {
	var url2 = $(window.parent.location).attr('href');
	url2 += "/../" + "get_gene_structure";

	var ret = $.ajax({
	 	type: "POST",
	 	dataType: "json",
	 	url: url2, //Relative or absolute path to response.php file
	 	data: {tax_id:p_tax_id, protein_accession_id:p_protein_accession_id, refseq_assembly_id:p_refseq_assembly_id},
	 	async: true,
		error:function(request, status, error){
			request.status = 500;
			alert("gene-structure\n" + "code:"+request.status+"\n"+"message:"+request.responseText.substr(0, 10)+"...\n"+"error:"+error);
			hiding("#gene_structure_loading");
			load();
		},
		success:function( json ) {
			$("#locustag-value").text(json.LocusVersion);
			$("#species-value").text(json.organism);
			$("#taxonomy-value").text(json.TaxId);
			$("#annot-value").text(json.RefSeqAssemblyID);
			
			if( json != null ) {
		  		GeneStructure.init( json );
		  		GeneStructure.paint();
		  		
		  		hiding("#gene_structure_loading");
		  		load();
			}
			
			var type;
	  		
			$("#PeptideDownloadButton").on("click", function() {
				type = 'Peptide';
				var download = getSequenceAndCDSDownloadMessageFromServer(p_tax_id, p_refseq_assembly_id, p_protein_accession_id, json.organism, type).responseText;
				
				//g : 발생할 모든 pattern에 대한 전역 검색, i : 대/소문자 구별 안함
				var createDownloadFile = download.replace(/"/gi,'');
				var url = "downloads?name="+createDownloadFile;
				$(location).attr('href', url);
			});
			
			$("#CDSDownloadButton").on("click", function() {
				type = 'CDS';
				var download = getSequenceAndCDSDownloadMessageFromServer(p_tax_id, p_refseq_assembly_id, p_protein_accession_id, json.organism, type).responseText;
				
				//g : 발생할 모든 pattern에 대한 전역 검색, i : 대/소문자 구별 안함
				var createDownloadFile = download.replace(/"/gi,'');
				var url = "downloads?name="+createDownloadFile;
				$(location).attr('href', url);
			});
		}
	});

 	return ret;
}

function getSequenceAndCDSDownloadMessageFromServer(p_tax_id, p_refseq_assembly_id, p_protein_accession_id, p_organism, p_type) {
	var url2 = $(window.parent.location).attr('href');
	url2 += "/../" + "getSequenceDownloadByProteinID";
	
	var ret = $.ajax({
	 	type: "POST",
	 	dataType: "json",
	 	data: {tax_id:p_tax_id, protein_accession_id:p_protein_accession_id, refseq_assembly_id:p_refseq_assembly_id, organism:p_organism, type:p_type},
	 	url: url2, //Relative or absolute path to response.php file
	 	async: false,
		error: function(request, status, error){
			request.status = 500;
			alert("code:"+request.status+"\n"+"message:"+request.responseText.substr(0, 10)+"...\n"+"error:"+error);
		}
	});
	return ret;
};

//$(document).ready(function(){
//	var ajaxRetObj = getGeneStructureMessageFromServer();
//
//  	if( ajaxRetObj.status != 500 ) {
//  		var json = jQuery.parseJSON(ajaxRetObj.responseText);
//
//  		GeneStructure.init( json );
//  		GeneStructure.paint();
//  	}
//});