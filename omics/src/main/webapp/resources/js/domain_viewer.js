
var dialog = '';

var viewerStructure = {
	DB_NMS : ["PRINTS", "Pfam", "Gene3D", "TIGRFAM", "ProSiteProfiles", "ProSitePatterns", "Hamap", "PANTHER", "SUPERFAMILY", "SMART", "ProDom", "Colis", "PIRSF"],
//	ipr_color_type:["#0066b3", "#f598aa", "#70bf54", "#fec34e", "#ef403d", "#6850a1", "#934d2a", "#31bcad", "#21abcd", "#ffea82", "#ab3e97", "#eed903", "#71ceea", "#00854a", "#c02555", "#fab49b", "#a23e97", "#2d5980", "#f58220", "#b3beb1", "#008e83", "#f15b4e", "#ffd400", "#c48a10", "#5c6654", "#5c6654", "#ee3e75", "#00aebd", "#f99d1c", "#a6ce42", "#16568b"],
	ipr_color_type:["#00ff00", "#ffff00", "#ffa500", "#ccffff", "#ff0000", "#ee82ee", "#ccff00", "#ffffcc", "#00ffff", "#a52a2a", "#ffccff", "#ff6347", "#90ee90", "#ffe5b4", "#ff00ff", "#000080", "#db7093", "#87ceeb", "#b5651d", "#3eb489", "#da3287", "#9999ff", "#660000", "#d1e231", "#0000ff", "#ffdb58", "#987654", "#00cc00", "#cc33ff", "#ccffcc"],
	protein_name:'',
	item_bar_height:0,
	gap:0,
	width:0,
	height:0,
	margin:0,
	multiplier:0,
	protein_length:0,
	unitLength:0,
	ipr_color:[],
	ipr_terms:[],
	db_tracks:[],
	VERTICAL_EXTEND_SIZE:0,
	getIprColor:function(ipr_id) {
		var code = this.ipr_color.indexOf(ipr_id);
		if( code >= 0 )	return this.ipr_color_type[code];
		return "#FFBDFE";
	},
	getColor:function(db) {
		if( db=="PRINTS") 					return "#FFFF9C";
		else if( db == "Pfam" )				return "#EDFF88";
		else if( db == "Gene3D" ) 			return "#E9C4FF";
		else if( db == "TIGRFAM" ) 			return "purple";
		else if( db == "ProSiteProfiles" )	return "lightgray";
		else if( db == "ProSitePatterns" )	return "cyan";
		else if( db == "Hamap")				return "pink";
		else if( db == "PANTHER" )			return "#FFC1A3";
		else if( db == "SUPERFAMILY" )		return "#8AB0FF";
		else if( db == "SMART" )			return "#9DFF00";
		else if( db == "ProDom" )			return "cyan";
		else if( db == "Colis" )			return "pink";
		else								return "black";
	},
	init:function( obj ) {
		this.margin					= 8;
		this.item_bar_height		= 5;
		this.gap					= 5;
	 	this.protein_name			= obj.nm + "(" + obj.protein_id + ")";	 	
		this.VERTICAL_EXTEND_SIZE	= 10;
		this.protein_length			= obj.length;
		this.multiplier				= this.getRange( this.protein_length );
		
	 	this.resize( $("#gene-structure").width() );

	 	var jsonQuery = JSON.stringify( obj );

 	 	this.initIpr( jQuery.parseJSON(jsonQuery) );
  	 	this.initDbTracks( jQuery.parseJSON(jsonQuery) );
	},
	initDbTracks:function(obj) {
		for(var i=0; i<this.DB_NMS.length; i++) {
			var arr = [];
			for(var j=0; j<obj.dbs.length; j++) {
				if( this.DB_NMS[i] == obj.dbs[j].db ) {
					obj.dbs[j].layer_no = -1;
 					arr.push( JSON.stringify(obj.dbs[j]) );
				}
			}

			if( arr.length > 0 ) {
				var db_str = '{"db":"' + this.DB_NMS[i] + '", "domains":[';

				for(var j=0; j<arr.length; j++) {
					db_str += arr[j];
					if( j != arr.length-1 )	db_str += ',';
				}

				db_str += ']}';

				var jsonObj = jQuery.parseJSON( db_str );

				jsonObj.domains = this.getComputeDepthOfLayer( jsonObj.domains );

  				this.db_tracks.push( jsonObj );
			}
		}
	},
	getDegreeOfTerminalDepth:function( array ) {
		// 현재 배열에 들어있는 데이터 레이어의 깊이를 계산해서 반환 
		var max = -1;
		for(var i=0; i<array.length; i++) {
			if( array[i].layer_no > max )	max = array[i].layer_no;
		}
		return max;
	},
	getComputeDepthOfLayer:function( array ) {
		// 만약 겹치는 영역이 있으면 다음 레이어로 그려주는 작업을 위하여 layer_no라는 프로퍼티에 레이어 순서를 매긴다
		var index = 0;
		for(var i=0; i<array.length; i++) {
			if( array[i].layer_no == -1 )	{
				array[i].layer_no = index++;

				var tmpArr = [];
				for(var j=0; j<array.length; j++) {
					if( array[j].layer_no == -1 && this.isOverlap( array[i], array[j] ) == false )	{
						var flag = true;
						for(var k=0; k<tmpArr.length; k++) {
							if( this.isOverlap(tmpArr[k], array[j]) )	{
								flag = false;
								break;
							}
						}
						if( flag ) {
							array[j].layer_no = array[i].layer_no;
							tmpArr.push( array[j] );
						}
					}
				}
			}
		}
		return array;
	},
	initIpr:function( obj ) {
		var tmpArr = [];
		for(var i=0; i<obj.dbs.length; i++) {
			var item = obj.dbs[i];
			if( isEmpty(item.ipr_id) )	continue;

			item.layer_no = -1;
			tmpArr.push( item );

			if( this.ipr_color.indexOf( item.ipr_id ) == -1 ) this.ipr_color.push( item.ipr_id );
		}

		this.ipr_terms = this.getComputeDepthOfLayer( tmpArr );
		
		delete tmpArr;
	},
	resize:function(w) {
		this.width = w;
		this.unitLength				= this.width / this.protein_length;
	},
	getRange:function( protein_length ) {
		 var multiplier = 50;

	 	if( protein_length > 5000 )									multiplier = 2000;
		else if( protein_length <= 5000 && protein_length > 3000 )	multiplier = 1000;
		else if( protein_length <= 3000 && protein_length > 1500 )	multiplier = 500;
		else if( protein_length <= 1500 && protein_length > 1000 )	multiplier = 200;
		else if( protein_length <= 1000 && protein_length > 600)	multiplier = 150;
		else if( protein_length <= 600 && protein_length > 300)		multiplier = 100;
		else if( protein_length <= 300 && protein_length > 150)		multiplier = 50;
		else if( protein_length <= 150 && protein_length > 50)		multiplier = 20;
		else if( protein_length <= 50 && protein_length > 20)		multiplier = 10;
		else if( protein_length <= 20 && protein_length > 10)		multiplier = 5;
		else if( protein_length <= 10 && protein_length > 5)		multiplier = 2;
		else														multiplier = 1;
		
		return multiplier;
	},
	drawProteinWithIprTerm:function( base ) {
		var proteinTrack = $("<div id='protein-layer' class='track'></div>");
		var proteinTitle = $("<div class='track-name'><span class='track-name-label-header'></span><span class='protein_title' style='font-size:18px;'></span></div>");
		var proteinPanel = $("<div id='proteinPanel'></div>");
		var proteinStructure = $("<div id='protein_structure' class='clickable rounded-corners'></div>)");

		proteinPanel.append( proteinStructure );

		proteinTrack.append( proteinTitle );
		proteinTrack.append( proteinPanel );

		base.append( proteinTrack );

//		console.log( this.ipr_terms );
		var depth = 1 + this.getDegreeOfTerminalDepth( this.ipr_terms );
		var trackHeight = this.margin + this.item_bar_height + this.margin + ((depth * this.gap) + (depth * this.item_bar_height));

		proteinTitle.find("span.protein_title").text( this.protein_name );
		proteinPanel.css("height", trackHeight);

		var rangeDashedBarHeight = ( proteinPanel.height() + viewerStructure.VERTICAL_EXTEND_SIZE );
 		var rangeDashedBarWidth = ( viewerStructure.multiplier * viewerStructure.unitLength );
		
		var idx = 0;
		for(var i=0; i<=proteinPanel.width(); i+=rangeDashedBarWidth) {
			var label = idx==0?1:(idx) * viewerStructure.multiplier;

			// 범위의 좌측보더를 그려주는 레이어
			proteinPanel.append("<div class='range_border-protein range_border' style='left:"+i+"px;'></div>");
			// 각각의 범위를 표시해 주는 레이블
			proteinPanel.append("<span id='rl_"+idx+"' class='range_label'>"+label+"</span>");
			
			// 레이블은 보더의 가운데 위치해야 하므로 중앙 위치로 계산하여 그려줌
			var nP = i - ($("#rl_" + idx).width()/2);
			$("#rl_"+idx).css("left", nP);
			
			// 만약 격자를 그릴때 오른쪽이 실제 그려지는 캔버스를 벗어나는 경우 
			// 가장 오른쪽 캔버스에 마지막 단백질 길이를 적어준다
			if( (i + rangeDashedBarWidth) > proteinPanel.width() ) {
				var leftOfLastBorder = (proteinPanel.position().left + proteinPanel.width());
				
				var topOfLastBorder = (proteinPanel.position().top + proteinPanel.height());
				var heightOfLastBorder = (proteinPanel.position().top + rangeDashedBarHeight) - topOfLastBorder;
				proteinPanel.append("<div class='range_border range_border' style='left:"+(leftOfLastBorder+1)+"px;top:"+topOfLastBorder+"px;height:"+heightOfLastBorder+"px'></div>");
				proteinPanel.append("<span id='rl_last' class='range_label'>"+this.protein_length+"</span>");

				var nP2 = leftOfLastBorder - ($("#rl_last").width()/2);

				var previous = $("#rl_" + (idx));

				if( previous.position().left + previous.width() > nP2 ) {
					var pre_left = previous.css("left").replace("px", "");
					previous.css("left", pre_left - (previous.width()/2) );
				}

				$("#rl_last").css("left", nP2);
				
				delete leftOfLastBorder, topOfLastBorder, heightOfLastBorder, nP2;
			}

			idx++;
		}

		$(".range_border-protein").css("height", rangeDashedBarHeight);
		$(".range_border-protein").css("width", rangeDashedBarWidth);
		$(".range_label").css("top", proteinPanel.position().top + rangeDashedBarHeight );
		
		proteinStructure.css("top", proteinPanel.position().top + this.margin );
		proteinStructure.css("height", this.item_bar_height);

		var index = 0;
		var baseTop = (proteinStructure.position().top + this.item_bar_height) + this.gap;
		for(var i=0; i<this.ipr_terms.length; i++) {
			var item = this.ipr_terms[i];
			var top = (item.layer_no*(this.item_bar_height+this.gap)) + baseTop;
			var left = item.start * this.unitLength;
			var width = (item.end-item.start+1) * this.unitLength;
			
			var nIprTerm = $("<div class='domain str ipr_term clickable rounded-corners alpah_60' style='top:"+top+"px;left:"+left+"px;width:"+width+"px;height:"+this.item_bar_height+"px;background-color:"+this.getIprColor(item.ipr_id)+";'></div>");
			nIprTerm.addClass( item.ipr_id );
			nIprTerm.attr("ipr_id", item.ipr_id );
			nIprTerm.attr("start", item.start);
			nIprTerm.attr("end", item.end);
			nIprTerm.attr("ipr_desc", item.ipr_desc );
			
			// add tooltip
			this.generateToolTip( nIprTerm, item );

			proteinPanel.append( nIprTerm );
			index++;
		}

		proteinTrack.css("height", rangeDashedBarHeight + $(".range_label").height() +25 );
		
		delete proteinTrack, proteinTitle, proteinPanel, proteinStructure, depth, trackHeight, rangeDashedBarHeight, rangeDashedBarWidth, idx, index, baseTop;
		
		return proteinTrack.height();
	},
	paint:function( obj ) {
		var base = $("#viewerPanel").css("height", 100).css("width", this.width);
		
		var totalHeight = this.drawProteinWithIprTerm( base );

		for(var i=0; i<this.DB_NMS.length; i++) {
			var db_item = [];
			for(var j=0; j<this.db_tracks.length; j++) {
				if( this.DB_NMS[i] == this.db_tracks[j].db && this.db_tracks[j].domains.length > 0 ) {
					var domainTrackHeight = this.addDomainTracks( base, this.DB_NMS[i], this.db_tracks[j].domains );

					totalHeight += parseInt(domainTrackHeight);
				}
			}
		}
		$(".ipr_term").hover(function(){
			$(this).addClass("ipr_term_hilight");
		}, function(){
			$(this).removeClass("ipr_term_hilight");
		});
		
		$(".domain_str").hover(function(){
			$(this).addClass("domain_str_hilight");
		}, function(){
			$(this).removeClass("domain_str_hilight");				
		})

		// 각각의 DB별로 margin-top을 10 주었기 때문에 높이에 이를 반영한다
		var cntDrawingDb = $("#viewerPanel").children().length;

		totalHeight += ( cntDrawingDb * parseInt($(".track").css("margin-top")) );
		totalHeight += $("#viewerPanel > .domain-title").height();
		
		base.css("height", totalHeight);
		
/*
		$(".ipr_term").hover(function(){
			var iprTerm = this.className.split(/\s/).filter(function( cn ) {
		        return cn.indexOf('IPR') === 0;
		    });
		    
		    $("."+iprTerm).addClass("ipr_term_hilight");
		}, function(){
			var iprTerm = this.className.split(/\s/).filter(function( cn ) {
		        return cn.indexOf('IPR') === 0;
		    });
		    
		    $("."+iprTerm).removeClass("ipr_term_hilight");
		});
*/
		
		delete base;
	},
	addDomainTracks:function( base, DB_NM, db_item ) {
		var domainTrack = $("<div id='"+DB_NM+"-layer' class='track'></div>");
		var domainTitle = $("<div class='track-name'><span class='track-name-label-header'></span><span class='domain-track-name-lable'>"+DB_NM+"</span></div>");
		var domainPanel = $("<div class='viewerFrame'></div>");
		domainTrack.append( domainTitle );
		domainTrack.append( domainPanel );

		base.append( domainTrack );

 		var depth = 1 + this.getDegreeOfTerminalDepth( db_item );
 		
		var trackHeight = ( 2 * this.margin) + ( depth * this.item_bar_height ) + ( (depth -1) * this.gap );
		
		domainPanel.css("height", trackHeight);

 		var rangeDashedBarWidth = ( viewerStructure.multiplier * viewerStructure.unitLength );

		for(var i=0; i<=domainPanel.width(); i+=rangeDashedBarWidth) {
			// 범위의 좌측보더를 그려주는 레이어
			domainPanel.append("<div class='range_border-"+DB_NM+"' style='left:"+i+"px;'></div>");
		}
		$(".range_border-"+DB_NM).css("top", domainPanel.position().top);
		$(".range_border-"+DB_NM).css("height", domainPanel.height());
		$(".range_border-"+DB_NM).css("width", rangeDashedBarWidth);
		$(".range_border-"+DB_NM).addClass("range_border");

		$(".range_border-"+DB_NM).css("color", "red");

		var index = 0;
		var baseTop = domainPanel.position().top + this.margin;
		for(var i=0; i<db_item.length; i++) {
			var item = db_item[i];
			var top = (item.layer_no*(this.item_bar_height+this.gap)) + baseTop;
			var left = item.start * this.unitLength;
			var width = (item.end-item.start+1) * this.unitLength;
			
			var color=this.getColor(DB_NM);
			var round_corner = "";
			if(isEmpty( item.ipr_id) )	{
				color=this.getColor(DB_NM);
			}else{
				color=this.getIprColor( item.ipr_id );
				round_corner = "rounded-corners";
			}

			var nDomain = $("<div class='domain_str "+round_corner+" clickable alpah_60' style='top:"+top+"px;left:"+left+"px;width:"+width+"px;height:"+this.item_bar_height+"px;background-color:"+color+";'></div>");
			if(!isEmpty( item.ipr_id ) )	nDomain.addClass( item.ipr_cd );
			
			this.generateToolTip( nDomain, item );

			domainPanel.append( nDomain );
			index++;
			
			
		}
		delete depth, trackHeight, rangeDashedBarWidth, index, baseTop;
		
		domainTrack.css("height", domainPanel.height() + domainTitle.height() );
		
		return domainTrack.height();
	},
	generateToolTip:function(obj, item){
		var db_id_with_link = "";
		var ipr_with_link = "";
		var go_with_link = "";

		//  각각의 데이터베이스별로 링크를 걸기 위해 처리하는 부분
		if( isEmpty(item.ipr_id) )		ipr_with_link = '&nbsp';
		else							ipr_with_link = "<a href='http://www.ebi.ac.uk/interpro/entry/"+item.ipr_id+"'>" + item.ipr_id + "</a>";

		if( isEmpty(item.ipr_desc) )	item.ipr_desc = '&nbsp';


		if( isEmpty(item.go) )			go_with_link = '&nbsp';
		else {
			var goArray = item.go.split("|");

			for(var i=0; i<goArray.length; i++) {
				go_with_link += "<a href='http://amigo.geneontology.org/amigo/search/bioentity?q=" + goArray[i] + "'>" + goArray[i] + "</a>|";
			}
		}


		
		if( isEmpty(item.link_url) )	db_id_with_link = item.db_id;
		else							db_id_with_link = "<a href='"+item.link_url+"'>" + item.db_id + "</a>";	

		var contentText = $("<div id='qtip-domain' style='width:100%'></div>");
		var dbNmRecord = $("<div class='record'><div class='tooltip-header'>Database</div><div class='tooltip-content'>" + item.db + "</div></div>");
		var dbIdRecord = $("<div class='record'><div class='tooltip-header'>DB ID</div><div class='tooltip-content'>" + db_id_with_link + "</div></div>");
		var locusRecord = $("<div class='record'><div class='tooltip-header'>Locus</div><div class='tooltip-content'>" + item.start + " --- " + item.end + "</div></div>");
		var iprRecord = $("<div class='record'><div class='tooltip-header'>IPR ID</div><div class='tooltip-content'>" + ipr_with_link + "</div></div>");
		var iprDescRecord = $("<div class='record'><div class='tooltip-header'>IPR Desc.</div><div class='tooltip-content'>" + item.ipr_desc + "</div></div>");
		var goRecord = $("<div class='record'><div class='tooltip-header'>GO</div><div class='tooltip-content'>" + go_with_link + "</div></div>");
		
		contentText.append( dbNmRecord );
		contentText.append( dbIdRecord );
		contentText.append( locusRecord );
		contentText.append( iprRecord );
		contentText.append( iprDescRecord );
		contentText.append( goRecord );

		$(obj).qtip({
			content: {
				text: contentText,
				title: 'Tooltip'
			},
			position: {
/* 				target: 'mouse', */
				corner: {
					target: 'topRight',
					tooltip: 'bottomLeft'
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
				height: 120,
				padding: 5,
				background: '#FBFBFB',
				color: '#727272',
				textAlign: 'left',
				border: {
					width: 0,
					radius: 2,
					color: '#008570'
				},
				tip: 'bottomLeft',
				name: 'dark', // Inherit the rest of the attributes from the preset dark style
				title:{
					background:"#008570",
//					color:"gray"
				}
			}
		});
	},
	isOverlap:function(obj1, obj2) {
		var v1Max = Math.max(obj1.start, obj1.end);
	 	var v1Min = Math.min(obj1.start, obj1.end);
	 	var v2Max = Math.max(obj2.start, obj2.end);
	 	var v2Min = Math.min(obj2.start, obj2.end);
 		
 		if( v1Max >= v2Min && v1Min <= v2Min )			return true;
 		else if( v2Max >= v1Min && v2Min <= v1Min )		return true;
 		
 		delete v1Max, v1Min, v2Max, v2Min;
 		
 		return false;
	}
};

function isEmpty(val) {
	if(val == null || val == undefined || val == 'null' || val == 'undefined' || val == '' )	return true;
	
	return false;
}

function getMessageFromServer(p_tax_id, p_refseq_assembly_id, p_protein_accession_id) {
	var url2 = $(window.parent.location).attr('href');
	url2 += "/../" + "get_domains";

	var ret = $.ajax({
	 	type: "POST",
	 	dataType: "json",
	 	url: url2, //Relative or absolute path to response.php file
	 	data: {tax_id:p_tax_id, protein_accession_id:p_protein_accession_id, refseq_assembly_id:p_refseq_assembly_id},
	 	async: true,
		error:function(request, status, error){
			request.status = 500;
			alert("domain_viewer\n" + "code:"+request.status+"\n"+"message:"+request.responseText.substr(0, 10)+"...\n"+"error:"+error);
			hiding("#protein_domains_loading");
			load();
		},
		success:function(json){
			if( json != null ) {
				viewerStructure.init( json );
			  	viewerStructure.paint( json );
			  	hiding("#protein_domains_loading");
			  	load();
			}
		}
	});

 	return ret;
}

function getDomainPanel() {
	var ajaxRetObj = getMessageFromServer();

  	if( ajaxRetObj.status != 500 ) {
  		var json = jQuery.parseJSON(ajaxRetObj.responseText);

	  	viewerStructure.init( json );
	  	viewerStructure.paint( json );
  	}
  	
  	return $("#viewerPanel").position();
}

//$(document).ready(function(){
//  	var ajaxRetObj = getMessageFromServer();
//
//  	if( ajaxRetObj.status != 500 ) {
//  		var json = jQuery.parseJSON(ajaxRetObj.responseText);
//
//	  	viewerStructure.init( json );
//	  	viewerStructure.paint( json );
//  	}
//});


/*
  	$(window).resize(function(){
  		var width = $(this).width() - ( 2 * viewerStructure.margin );
  		
  		viewerStructure.resize( width );
  		viewerStructure.paint();
  	});
*/