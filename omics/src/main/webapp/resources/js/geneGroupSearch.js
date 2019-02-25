// Global variables
var btn = null;
var inputboxValues = [];		// ipr_ids
var inputboxFamilies = [];		// with IPR
var inputboxWithoutIPR = [];	// without IPR
var inputboxKeys = [];			// subtypes
var radioData = null;
var perfectMatch = null;
var pagingSize = 5;

var iprDomaintype = null;
var pagingClick = 0;
var viewerPanelOnCount = 0;



//jquery post방식을 사용하여 url로이동, form생성 후 form submit하는 방법
function post_to_url(path, params, method) {
	
	method = method || "post"; // Set method to post by default, if not specified.
	
	// The rest of this code assumes you are not using a library.
	// It can be made less wordy if you use one.
	var form = document.createElement("form");
	form.setAttribute("method", method);
	form.setAttribute("action", path);
	
	for (var key in params) {
	    var hiddenField = document.createElement("input");
	    hiddenField.setAttribute("type", "hidden");
	    hiddenField.setAttribute("name", key);
	    hiddenField.setAttribute("value", params[key]);
	    form.appendChild(hiddenField);
	}
	document.body.appendChild(form);
	form.submit();

	return false;
}

jQuery.allDownloadByIprId = function getAllSequenceDownloadMessageFromServer(ipr_ids, radioData, type, subTypeIPR, kingdom, chkPerfectMatch, inputboxFamilies, inputboxWithoutIPR, downloadRadio) {
	var url2 = $(window.parent.location).attr('href');
	url2 += "/../" + "getAllSequencesDownloadByIprTerms";
	
	var ret = $.ajax({
	 	type: "POST",
	 	dataType: "json",
	 	data: {ipr_ids: ipr_ids, radio: radioData, type: type, subType: subTypeIPR, kingdom: kingdom, chkPerfectMatch: chkPerfectMatch
	 		, inputboxFamilies: inputboxFamilies, inputboxWithoutIPR: inputboxWithoutIPR, downloadRadio: downloadRadio},
	 	url: url2, //Relative or absolute path to response.php file
	 	async: true,
	 	beforeSend: function() {
	 		//통신을 시작할때 처리되는 함수 
	 		$('html').css("cursor","wait");// 현재 html 문서위에 있는 마우스 커서를 로딩 중 커서로 변경
	 	},
	 	complete: function() {
	 		//통신이 완료된 후 처리되는 함수
	 		$('html').css("cursor","auto"); // 통신이 완료 된 후 다시 일반적인 커서 모양으로 변경
	 	},
	 	success: function(data) {
			var url = "downloads?name="+data;
			$(location).attr('href', url);
	 	},
		error: function(request, status, error){
			request.status = 500;
			alert("code:"+request.status+"\n"+"message:"+request.responseText.substr(0, 10)+"...\n"+"error:"+error);
		}
	});
	return ret;
};

//jQuery.allDownload = function getAllPeptideDownloadMessageFromServer(json, data, type) {
//	var url2 = $(window.parent.location).attr('href');
//	url2 += "/../" + "getAllPeptideDownload";
//	
//	var protein_data_str = JSON.stringify( json );
//	
//	var ret = $.ajax({
//	 	type: "POST",
//	 	dataType: "json",
//	 	data: {protein_info: protein_data_str, input: data, type: type},
//	 	url: url2, //Relative or absolute path to response.php file
//	 	async: false,
//		error: function(request, status, error){
//			request.status = 500;
//			alert("code:"+request.status+"\n"+"message:"+request.responseText.substr(0, 10)+"...\n"+"error:"+error);
//		}
//	});
//	return ret;
//};

jQuery.download = function getPeptideDownloadMessageFromServer(data, type) {
	var url2 = $(window.parent.location).attr('href');
	url2 += "/../" + "getSequenceDownload";

	var ret = $.ajax({
	 	type: "POST",
	 	dataType: "json",
	 	data: { tax_id: data.nid, assembly_accession: data.ncbi_id, protein_accession_version: data.protein_id, species: data.nm, type: type, kingdom: data.kingdom, subtype: data.subtype },
	 	url: url2, //Relative or absolute path to response.php file
	 	async: false,
		error: function(request, status, error){
			request.status = 500;
			alert("code:"+request.status+"\n"+"message:"+request.responseText.substr(0, 10)+"...\n"+"error:"+error);
		}
	});

	return ret;
};


var geneGroupSearch = function( obj, order, subOrder, ipr_terms, mainFrameName) {
	this.DB_NMS = [];
	this.ipr_color_type = [];
	this.protein_name = '';
	this.item_bar_height=0;
	this.gap=0;
	this.width=0;
	this.height=0;
	this.margin=0;
	this.multiplier=0;
	this.protein_length=0;
	this.unitLength=0;
	this.ipr_color=[];
	this.ipr_terms=[];
	this.VERTICAL_EXTEND_SIZE=0;
	this.obj = obj;
	this.order = order;
	this.subOrder = subOrder;
	this.Peptide = '';
	this.protein_id = '';
	this.ipr_terms_array = ipr_terms;
	this.mainFrameName = mainFrameName;
};

geneGroupSearch.prototype = {
	init2:function() {
		this.DB_NMS 				= ["PRINTS", "Pfam", "Gene3D", "TIGRFAM", "ProSiteProfiles", "ProSitePatterns", "Hamap", "PANTHER", "SUPERFAMILY", "SMART", "ProDom", "Colis", "PIRSF"];
		this.ipr_color_type			= ["#0066b3", "#f598aa", "#70bf54", "#fec34e", "#ef403d", "#6850a1", "#934d2a", "#31bcad", "#f04e46", "#ffea82", "#ab3e97", "#eed903", "#71ceea", "#00854a", "#c02555", "#fab49b", "#a23e97", "#2d5980", "#f58220", "#b3beb1", "#008e83", "#f15b4e", "#ffd400", "#c48a10", "#5c6654", "#5c6654", "#ee3e75", "#00aebd", "#f99d1c", "#a6ce42", "#16568b"],
		this.margin					= 8;
		this.item_bar_height		= 5;
		this.gap					= 5;
	},
	init:function() {
		this.init2();

	 	this.protein_name			= this.obj.nm + " (Taxonomy : "+this.obj.nid+", Ncbi Id : " + this.obj.ncbi_id + " , Protein_id : " + this.obj.protein_id + ")";
		this.VERTICAL_EXTEND_SIZE	= 10;
		this.protein_length			= this.obj.length;
		this.multiplier				= this.getRange( this.protein_length );
		this.Peptide				= this.obj.Peptide;
		this.protein_id				= this.obj.protein_accession_version;

//	 	this.resize( $("#viewerPanel").width() );
		this.resize( $("#"+this.mainFrameName).width() );
	 	
	 	var jsonQuery = JSON.stringify( this.obj );
		this.initIpr( jQuery.parseJSON(jsonQuery) );
	},
	getIprColorLegend:function() {
		$("<form>a <fieldset><legend>asd</legend></fieldset></form>");
	},
	getIprColor:function(ipr_id) {
		var code = -1;
		if( (code=this.ipr_terms_array.indexOf(ipr_id)) >= 0 ){
			return this.ipr_color_type[code];
		}
		return "#FFFFFF";
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
	//각 DB중에서 가장 높은 뎊스(가장 아래쪽에 그려질 DB의 높이 번호)를 반환
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
	drawProteinWithIprTerm:function( base, obj ) {
		var proteinTrack = $("<div id='protein-layer-"+this.order+this.subOrder+"' class='track'></div>");
		var proteinTitle = $("<div class='track-name'><span class='track-name-lable'></span><span class='track-name-button'></span></div>");
		var proteinPanel = $("<div id='proteinPanel-"+this.order+this.subOrder+"' class='interpro-protein-panel'></div>");
		var proteinStructure = $("<div id='protein_structure-"+this.order+this.subOrder+"' class='clickable rounded-corners interpro-protein-structure'></div>)");
		
		var PeptideDownloadButton = $("<button class='btnblue Sqdown' id='PeptideDownloadButton"+this.order+this.subOrder+"' >Peptide</button>");
		
		$(PeptideDownloadButton).on("click", function() {
			var type = 'Peptide';
			var createDownloadFile = jQuery.parseJSON($.download(obj, type).responseText);
			var url = "downloads?name="+createDownloadFile;
			$(location).attr('href', url);
		});
		
		var CDSDownloadButton = $("<button class='btnblue Cdsdown' id='CDSDownloadButton"+this.order+this.subOrder+"' >CDS</button>");
		
		$(CDSDownloadButton).on("click", function() {
			var type = 'CDS';
			var createDownloadFile = jQuery.parseJSON($.download(obj, type).responseText);
			var url = "downloads?name="+createDownloadFile;
			$(location).attr('href', url);
		});
		
		var viewerSwitchingButton = $("<button id='viewerSwitchingButton"+this.order+this.subOrder+"' class='btnpurple Gviewer' >Gene Viewer</button>");
		
		$(viewerSwitchingButton).on("click", function() {
			var viewer = "/viewer";
			var tax_id = obj.nid;
			var refseq_assembly_id = obj.ncbi_id;
			var protein_accession_id = obj.protein_id;

			var pathName = $(location).attr('pathname');//omics/viewer
			var string = pathName.split('/');
			
			var url = '/' + string[1];
			
			//post방식으로 값을 넘기는 방법
//			post_to_url(url+viewer, {"tax_id": tax_id, "refseq_assembly_id": refseq_assembly_id, "protein_accession_id": protein_accession_id});
			
			//get방식으로 값을 넘기는 방법
			var url2 = "getViewerFromInterpro?tax_id="+tax_id+"&refseq_assembly_id="+refseq_assembly_id+"&protein_accession_id="+protein_accession_id;
//			$(location).attr('href', url2);
			window.open(url2);
		});

		proteinPanel.append( proteinStructure );

		proteinTrack.append( proteinTitle );
		
		proteinTrack.append( proteinPanel );

		base.append( proteinTrack );
		
		var proteinTitle_tooltip = $("<div id='proteinName' title='"+this.protein_name+"'>"+this.protein_name+"</div><p class='tooltip'></p>");
		
		proteinTitle.find("span.track-name-lable").append(proteinTitle_tooltip);
		proteinTitle.find("span.track-name-button").append( PeptideDownloadButton ).append( CDSDownloadButton ).append( viewerSwitchingButton );
		
		fn_tooltip('track-name','proteinName', true, 'mousemove');
		
		var depth = 1 + this.getDegreeOfTerminalDepth( this.ipr_terms );
		var trackHeight = this.margin + this.item_bar_height + this.margin + ((depth * this.gap) + (depth * this.item_bar_height));
		
		proteinPanel.css("height", trackHeight);

		var rangeDashedBarHeight = ( proteinPanel.height() + this.VERTICAL_EXTEND_SIZE );
 		var rangeDashedBarWidth = ( this.multiplier * this.unitLength );
		
		var idx = 0;
		
		for(var i=0; i<=proteinPanel.width(); i+=rangeDashedBarWidth) {
			
			//그림 아래 눈금(size)을 표시해주는 부분
			var label = idx==0?1:(idx) * this.multiplier;

			// 범위의 좌측보더를 그려주는 레이어
			proteinPanel.append("<div class='range_border-protein range_border' style='left:"+i+"px;'></div>");
			// 각각의 범위를 표시해 주는 레이블
			proteinPanel.append("<span id='rl_"+idx+"-"+this.order+this.subOrder+"' class='range_label range_label-"+this.order+this.subOrder+"'>"+label+"</span>");
			
			// 레이블은 보더의 가운데 위치해야 하므로 중앙 위치로 계산하여 그려줌
			var nP = i - ($("#rl_" + idx+"-"+this.order+this.subOrder).width()/2);
			$("#rl_"+idx+"-"+this.order+this.subOrder).css("left", nP);
			
			// 만약 격자를 그릴때 오른쪽이 실제 그려지는 캔버스를 벗어나는 경우 
			// 가장 오른쪽 캔버스에 마지막 단백질 길이를 적어준다
			if( (i + rangeDashedBarWidth) > proteinPanel.width() ) {
				var leftOfLastBorder = (proteinPanel.position().left + proteinPanel.width());

				proteinPanel.append("<div class='range_border-protein range_border' style='left:"+(leftOfLastBorder+1)+"px;'></div>");
				proteinPanel.append("<span id='rl_last-"+this.order+this.subOrder+"' class='range_label range_label-"+this.order+this.subOrder+"'>"+this.protein_length+"</span>");

				var nP2 = leftOfLastBorder - ($("#rl_last-"+this.order+this.subOrder).width()/2);

				var previous = $("#rl_" + (idx) + "-" + this.order+this.subOrder);

				if( previous.position().left + previous.width() > nP2 ) {
					var pre_left = previous.css("left").replace("px", "");
					previous.css("left", pre_left - (previous.width()/2) );
				}

				$("#rl_last-"+this.order+this.subOrder).css("left", nP2);
				
				delete leftOfLastBorder, nP2;
			}

			idx++;
		}

		$(".range_border-protein").css("height", rangeDashedBarHeight);
		$(".range_border-protein").css("width", rangeDashedBarWidth);
		$(".range_label-"+this.order+this.subOrder).css("top", proteinPanel.position().top + rangeDashedBarHeight );
//		$(".range_label").css("top", proteinPanel.position().top + rangeDashedBarHeight );
		
		proteinStructure.css("top", proteinPanel.position().top + this.margin );
		proteinStructure.css("height", this.item_bar_height);

		var index = 0;
		var baseTop = (proteinStructure.position().top + this.item_bar_height) + this.gap;
		for(var i=0; i<this.ipr_terms.length; i++) {
			var item = this.ipr_terms[i];
			var top = (item.layer_no*(this.item_bar_height+this.gap)) + baseTop;
			var left = item.start * this.unitLength;
			var width = (item.end-item.start+1) * this.unitLength;
			
			var nIprTerm = $("<div class='ipr_term clickable rounded-corners alpah_60' style='top:"+top+"px;left:"+left+"px;width:"+width+"px;height:"+this.item_bar_height+"px;background-color:"+this.getIprColor(item.ipr_id)+";'></div>");
			nIprTerm.addClass( item.ipr_id );
			nIprTerm.attr("ipr_id", item.ipr_id );
			nIprTerm.attr("start", item.start);
			nIprTerm.attr("end", item.end);
			nIprTerm.attr("ipr_desc", item.ipr_desc );

			proteinPanel.append( nIprTerm );
			index++;
		}

		proteinTrack.css("height", rangeDashedBarHeight + $(".range_label").height() + 25 );
		
		delete proteinTrack, proteinTitle, proteinPanel, proteinStructure, depth, trackHeight, rangeDashedBarHeight, rangeDashedBarWidth, idx, index, baseTop;
		
		var margin_top = 15;
		return proteinTrack.height() + margin_top;
	},
	paint:function() {
		this.getIprColorLegend();
		
//		var	base = $("#viewerPanel").css("height", 100).css("width", this.width);
		var base = $("#"+this.mainFrameName).css("height", 100).css("width", this.width);
		
		var totalHeight = this.drawProteinWithIprTerm( base, this.obj );
		
		$(".ipr_term").hover(function(){
			$(this).addClass("ipr_term_hilight");
		}, function(){
			$(this).removeClass("ipr_term_hilight");
		});
		
		$(".domain_str").hover(function(){
			$(this).addClass("domain_str_hilight");
		}, function(){
			$(this).removeClass("domain_str_hilight");				
		});

 		//delete : 개체에서 속성을 삭제하거나 배열에서 요소를 제거합니다.
		delete base;

		return totalHeight;
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

var click_kingdom="ALL";

var subType = function( order, subTypeIPR, radio, mainPanelName, currentPagingIndex, firstPagingIndex, lastPagingIndex, pagingSize, kingdom, perfectMatch, inputboxFamilies, inputboxWithoutIPR ) {
	this.order = order;
	this.subTypeIPR = subTypeIPR;
	this.radio = radio;
	this.kingdom = kingdom;
	this.perfectMatch = perfectMatch;
	this.inputboxFamilies = inputboxFamilies;
	this.inputboxWithoutIPR = inputboxWithoutIPR;
	
	this.mainPanelName = mainPanelName;
	
	// paging variables
	this.currentPagingIndex = currentPagingIndex;
	this.firstPagingIndex =  firstPagingIndex;
	this.lastPagingIndex = lastPagingIndex;
	this.pagingSize = pagingSize;
	this.previousPage = -1;
	this.nextPage = -1;
	this.displayNo = 10;
	this.pagingRangeStartIndexes = [];
};

subType.prototype = {
	button:function(order, subTypeIPR, ipr_term, radio, kingdom, perfectMatch, inputboxFamilies, inputboxWithoutIPR) {
		var allPeptideDownloadButton = $("#allPeptideDownloadButton-"+subTypeIPR);
		var allCDSDownloadButton = $("#allCDSDownloadButton-"+subTypeIPR);
		
		$(allPeptideDownloadButton).on("click", function() {
			var type = "Peptide";
			var downloadRadio = $('input:radio[name="chk_download-'+subTypeIPR+'"]:checked').val();
			
			var radioData = $('input:radio[name="chk_info"]:checked').val();
			
			var createDownloadFile = jQuery.parseJSON($.allDownloadByIprId(ipr_term, radio, type, subTypeIPR, kingdom, perfectMatch, inputboxFamilies, inputboxWithoutIPR, downloadRadio).responseText);
			
//			var url = "downloads?name="+createDownloadFile;
//			$(location).attr('href', url);
		});
		
		$(allCDSDownloadButton).on("click", function() {
			var type = "CDS";
			var downloadRadio = $('input:radio[name="chk_download-'+subTypeIPR+'"]:checked').val();
			
			var createDownloadFile = jQuery.parseJSON($.allDownloadByIprId(ipr_term, radio, type, subTypeIPR, kingdom, perfectMatch, inputboxFamilies, inputboxWithoutIPR, downloadRadio).responseText);
			
//			var url = "downloads?name="+createDownloadFile;
//			$(location).attr('href', url);
		});
	},
	proteinCount:function(order, count, stat, subtype) {
		var searchResultInfo=$("<div id='searchResultInfo"+order+"' style='background:#e5e5e5;margin-bottom:10px;padding:10px;'></div>");
		$("#"+this.mainPanelName).append(searchResultInfo);
		$("#searchResultInfo"+order).empty();
		var searchCount = $("<div sytle='float:left;' id='hit_result"+order+"'><span class='hit'>Hit : "+count+" proteins</span></div>");
//		$("#searchResultInfo"+order).append("<span id='hit_result"+order+"'> Hit : "+count+" proteins</span>");
		$("#searchResultInfo"+order).append(searchCount);
		
		var menu = "<div id='reportSlide-"+subtype+"' class='collapse expaned' style='float:right; color:#fff; line-height:25px;'><span id='reportButton1-"+subtype+"' class='reportbutton' style='background-color:#4f8bc9'>off</span><span id='reportButton2-"+subtype+"' class='reportbutton'>report</span></div>";
		$("#" + "hit_result"+order).append( $(menu) );
		
		$("#reportSlide-"+subtype).on("click", function(){
			if( !$(this).hasClass('expaned') ) {
				$(this).addClass("expaned");
				$("#" + order+"-stat-frame").show();
				$("#reportButton2-"+subtype).text("report");
				$("#reportButton1-"+subtype).text("off");
				$("#reportButton1-"+subtype).css("background-color","#4f8bc9");
				$("#reportButton2-"+subtype).css("background-color","#555555");
			}else {
				$(this).removeClass("expaned");
				$("#" + order+"-stat-frame").hide();
				$("#reportButton2-"+subtype).text("on");
				$("#reportButton1-"+subtype).text("report");
				$("#reportButton1-"+subtype).css("background-color","#555555");
				$("#reportButton2-"+subtype).css("background-color","#4f8bc9");
			}
		});
		
		var statPage = $("<div id='"+order+"-stat-frame'></div>");
		statPage.append("<div class='stat-title'>Statistics report</div>");
		
		var table = "<div class='stat-content'>";
		table += "<table class='all-stat-table'>";
		table += "<tr><th>No. of proteins</th><td>"+stat.protein_count+"</td><th>No. of taxonomies</th><td>"+stat.taxonomy_count+"</td></tr>";
		table += "<tr><th>Max no. of domains</th><td>"+stat.max_domains+"</td><th>Min no. of domains</th><td>"+stat.min_domains+"</td></tr>";
		table += "<tr><th>Max length of proteins</th><td>"+stat.max_protein_length+"</td><th>Min length of proteins</th><td>"+stat.min_protein_length+"</td></tr>";
		table += "<tr><th>No. of proteins distribution</th>";
		table += "<td colspan=3 style='background-color:#fff'>";
		table += "<table class='kingdom-stat-table'>";

		var thisObject = this;
		
		table += "<tr class='kingdom-column'><td style='text-align:center;'>Kingdom</td><td style='text-align:center;'>Hit</td></tr>";
		
		$.each(stat.kingdom_count, function(key, data){
			table += "<tr class='reportkingdom'>";
			if(key == click_kingdom){
				table += "<th id='"+subtype+"-"+key+"-btn1' class='reportkingdom' style='background-color:#e0f2fc; font-weight: bold'>"+key+"</th>";
				table += "<td id='"+subtype+"-"+key+"-btn2' style='background-color : #e0f2fc; font-weight: bold'>" + data + "</td>";
			}
			else{
				table += "<th id='"+subtype+"-"+key+"-btn1' class='reportkingdom'>"+key+"</th>";
				table += "<td id='"+subtype+"-"+key+"-btn2'>" + data + "</td>";
			}
			table += "</tr>";
			
			$("#" + subtype+"-"+key+"-btn1").die("click").live("click", function(){
				$("#"+subtype+"-"+click_kingdom+"-btn1").css({"background-color":"#f5f5f5","font-weight":"lighter"});
				$("#"+subtype+"-"+click_kingdom+"-btn2").css({"background-color":"#ffffff","font-weight":"lighter"});
//				console.log("key " + key);
				thisObject.kingdom = key;
				researchByPaging( thisObject, 1 );
//				console.log(thisObject);
				click_kingdom = key;
				pagingClick = 0;
				$("#"+subtype+"-"+key+"-btn1").css({"background-color":"#e0f2fc","font-weight":"bold"});
				$("#"+subtype+"-"+key+"-btn2").css({"background-color":"#e0f2fc","font-weight":"bold"});
			});
		});

		table += "</table>";
		table += "</td>";
		table += "</tr>";
		
		table += "<tr><th>No. of species distribution</th>";
		table += "<td colspan=3>";
		table += "<table class='kingdom-stat-table'>";
		
		table += "<tr class='kingdom-column'><td style='text-align:center;'>Kingdom</td><td style='text-align:center;'>Hit</td><td style='text-align:center;'>No. of species</td><td style='text-align:center;'>Ratio</td></tr>";

		$.each(stat.tax_kingdom_count, function(key, data){
			table += "<tr>";
			table += "<th>"+key+"</th>";
			table += "<td>" + data + "</td>";			
			table += "<td>"+ stat.tax_kingdom_total_count[key] +"</td>";
			table += "<td>"+ (data/stat.tax_kingdom_total_count[key]*100).toFixed(1) +" %</td>"; 
			table += "</tr>";
		});

		table += "</table>";
		table += "</td>";
		table += "</tr>";
		
		table += "<tr>";
		table += "<th>No. of domain subtypes</th>"
		table += "<td colspan=3>"+stat.domain_subtypes+"</td>";
		table += "</tr>";
		
		
		table += "</table>";
		table += "</div>";
		
		statPage.append( table );
		
		/* HighCharts Graph start */
		
		var graph = $("<div id='graph-frame-"+subtype+"' class='graph-frame'></div>");
		
		$(function () {
		    // Create the chart
		    $(graph).highcharts({
		        chart: {
		            type: 'column'
		        }, title: {
		            text: 'Kingdom distribution',
		            style :{
		            	color: '#333',
		            	fontWeight: 'bold',
		            	fontSize: '15px'
		            }
		        }, xAxis: {
		            type: 'category'
		        }, yAxis: {
		            title: {
		                text: 'Percent (%)'
		            },
		            max: 100,
		            tickInterval: 10
		        }, legend: {
		            enabled: false
		        }, credits: {
		            enabled: false
		        }, plotOptions: {
		            series: {
		                borderWidth: 0,
		                dataLabels: {
		                    enabled: true,
		                    format: '{point.y:.1f}%'
		                }
		            }
		        }, tooltip: {
		            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
		            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.1f}%</b> of total<br/>'
		        }, series: [{
		            name: 'Kingdom',
		            colorByPoint: true,
		            data: [{
		                name: 'Archaea',
		                y: stat.tax_kingdom_count.Archaea/stat.tax_kingdom_total_count.Archaea*100,
		            }, {
		                name: 'Bacteria',
		                y: stat.tax_kingdom_count.Bacteria/stat.tax_kingdom_total_count.Bacteria*100,
		            }, {
		                name: 'Fungi',
		                y: stat.tax_kingdom_count.Fungi/stat.tax_kingdom_total_count.Fungi*100,
		            }, {
		                name: 'Invertebrate',
		                y: stat.tax_kingdom_count.Invertebrate/stat.tax_kingdom_total_count.Invertebrate*100,
		            }, {
		                name: 'Plant',
		                y: stat.tax_kingdom_count.Plant/stat.tax_kingdom_total_count.Plant*100,
		            }, {
		                name: 'Protozoa',
		                y: stat.tax_kingdom_count.Protozoa/stat.tax_kingdom_total_count.Protozoa*100,
		            }, {
		                name: 'Vertebrate_mammalian',
		                y: stat.tax_kingdom_count.Vertebrate_mammalian/stat.tax_kingdom_total_count.Vertebrate_mammalian*100,
		            }, {
		                name: 'Vertebrate_other',
		                y: stat.tax_kingdom_count.Vertebrate_other/stat.tax_kingdom_total_count.Vertebrate_other*100,
//		            }, {
//		                name: 'ALL',
//		                y: stat.taxonomy_count/stat.tax_kingdom_total_count.ALL*100,
		            }]
		        }],
		    });
		});
		statPage.append(graph);
		/* HighCharts Graph end */

		$("#searchResultInfo"+order).append( statPage );

		var search_result_info_margin_bottom = 10;
		
		var height = $("#searchResultInfo"+order).height() + search_result_info_margin_bottom;
		
//		$("#reportSlide-"+subtype).trigger('click');

		return height;
	},

	init : function(currentPagingIndex, firstPagingIndex, lastPagingIndex, pagingSize) {
		this.currentPagingIndex = currentPagingIndex;
		this.firstPagingIndex = firstPagingIndex;
		this.lastPagingIndex = lastPagingIndex==0?1:lastPagingIndex;
		this.pagingSize = pagingSize;
		
		this.calculatePagingRangeStartIndexes();
	},
	init : function() {
		this.calculatePagingRangeStartIndexes();
	},
	paging : function( index ) {
		this.currentPagingIndex = index;

		var newPagingStartIndex = this.findStartIndexByStartIndex();

		this.drawingPagingPanel( this.subTypeIPR, newPagingStartIndex );
	},
	setPagingSize : function(size) {
		this.pagingSize = size;

		this.calculatePagingRangeStartIndexes();
	},
	findStartIndexByStartIndex : function() {
		var newStartIndex = 1;

		for(var i=0; i<this.pagingRangeStartIndexes.length - 1; i++) {
			if( this.pagingRangeStartIndexes[i] <= this.currentPagingIndex && this.currentPagingIndex < this.pagingRangeStartIndexes[i+1] ) {
				newStartIndex = this.pagingRangeStartIndexes[i];

				if( i-1 >= 0 )	this.previousPage = this.pagingRangeStartIndexes[i-1];
				else			this.previousPage = this.pagingRangeStartIndexes[i];

				if( i+1 < this.pagingRangeStartIndexes.length - 1 )	{
					this.nextPage = this.pagingRangeStartIndexes[i+1];
					if( this.nextPage > this.lastPagingIndex )		this.nextPage = this.lastPagingIndex;
				}else												this.nextPage = this.lastPagingIndex;

				break;
			}	
		}

		return newStartIndex;
	},
	drawingPagingPanel : function( subtype, newPagingStartIndex ) {
		// 페이징 시스템을 그려주는 함수
		var pagingDiv = $("#" + subtype + "-paging");
		pagingDiv.empty();
		
		if( this.lastPagingIndex >= 1 ) {
			var thisObject = this;

			// go to first index
			pagingDiv.append( $("<span id='"+subtype+"-page_first' class='viewerPanelPaging'>&lt;&lt;</span>") );
			$("#"+subtype+"-page_first").on("click", function(){
				researchByPaging( thisObject, thisObject.firstPagingIndex );
				pagingClick = 1;
			});

			if( newPagingStartIndex > this.firstPagingIndex ) {
				pagingDiv.append( $("<span id='"+subtype+"-page_prev' class='viewerPanelPaging')>&lt;</span>") );
				$("#"+subtype+"-page_prev").on("click", function(){
					researchByPaging( thisObject, thisObject.previousPage );
					pagingClick = 1;
				});
			}

			for(var i=newPagingStartIndex; i<=newPagingStartIndex+this.displayNo-1&i<=this.lastPagingIndex; i++) {
				var pbtn = $("<span id='"+subtype+"-page_" + i + "' class='viewerPanelPaging'>" + i + "</span>");
				pagingDiv.append( pbtn );
				$("#"+subtype+"-page_" + thisObject.currentPagingIndex).css("background", "#e0f2fc");
				pbtn.on("click", function(){
					researchByPaging( thisObject, $(this).text() );
					pagingClick = 1;
				});
			}
			
			if( newPagingStartIndex + this.displayNo - 1 < this.nextPage ) {
				pagingDiv.append( $("<span id='"+subtype+"-page_next' class='viewerPanelPaging'>&gt;</span>") );
				
				$("#"+subtype+"-page_next").on("click", function(){
					researchByPaging( thisObject, thisObject.nextPage );
					pagingClick = 1;
				});
			}

			// go to last index
			pagingDiv.append( $("<span id='"+subtype+"-page_last' class='viewerPanelPaging'>&gt;&gt;</span>") );
			$("#"+subtype+"-page_last").on("click", function(){
				researchByPaging( thisObject, thisObject.lastPagingIndex );
				pagingClick = 1;
			});
		}
	},
	calculatePagingRangeStartIndexes : function() {
		// 페이징 한 다음 페이징 단위를 담고 있는 배열을 만듬
		// 예) 10개씩 보여주도록 지정되어 있다면
		// 가져온 단백질 갯수를 10으로 나누고 나누어진 각 index를 배열에 담음
		this.pagingRangeStartIndexes = [];
		var index = this.firstPagingIndex;
		for(index=this.firstPagingIndex; index<=this.lastPagingIndex; index+= this.displayNo)
			this.pagingRangeStartIndexes.push( index );

		if( this.pagingRangeStartIndexes.indexOf(index) >= 0 )	index += this.displayNo;

		this.pagingRangeStartIndexes.push( index );
	},
	clearInit : function() {
		var pagingDiv = $("#" + this.subTypeIPR + "-frame");
		pagingDiv.empty();
	}
};

function fn_tooltip(containerID, linkID, titleVar, event) {
	var $container = $('.'+containerID);
	var $link = $container.find('#'+linkID).find('>a');
	var $tooltip = $container.find('.tooltip');
	
	var title = titleVar;
	var eventType = event;
	var text = '';
	
	if(title) {
		text = $link.attr('title');
	}
	
	$link.bind ({
		'mouseenter' : function(event) {
			showTooltip(event);
		}, 'mousemove' : function(event) {
			showTooltip(event);
		}, 'mouseleave' : function(event) {
			hideTooltip();
		}
	});
	
	if(eventType=='mouseover') {
		$link.unbind('mousemove');
	}
	
	function showTooltip(event) {
		if(eventType=='mouseover' || eventType=='mousemove') {
			var x = event.pageX;
			var y = event.pageY;
			
			var offset = $container.offset();
			var offsetX = x - offset.left;
			var offsetY = y - offset.top;
		}
		
		if(title) {
			$link.attr('title','');
			$tooltip.html(text);
		}
		
		if(eventType=='mouseover'||eventType=='mousemove'){
			var width = $tooltip.outerWidth();
			var height = $tooltip.outerHeight();
		}
		
		$tooltip.show();
		
		if(eventType=='mouseover'||eventType=='mousemove') {
			$tooltip.css({'left' : offsetX, 'top' : offsetY-height-10});
		}
	}
	
	function hideTooltip() {
		$tooltip.hide();
		
		if(title) {
			$tooltip.html('');
			$(this).attr('tittle', text);
		}
	}
}

function researchByPaging( obj, index ) {
	obj.paging( index );

	getPagingProteinDomainsAjax(inputboxValues, inputboxFamilies, inputboxWithoutIPR, inputboxKeys, radioData, perfectMatch, obj.subTypeIPR, obj.currentPagingIndex, obj.pagingSize, obj.order, obj);
}

function getPagingProteinDomainsAjax(inputboxValues, inputboxFamilies, inputboxWithoutIPR, inputboxKeys, radioData, perfectMatch, subType, pagingIndex, pagingSize, order, subtypeObject) {
	var url2 = $(window.parent.location).attr('href');
	url2 += "/../" + "getPagedGeneGroupSearchDesyncResult";

	jQuery.ajaxSettings.traditional = true;//ajax를 배열로 보내기 위한 세팅
	
	var ret = $.ajax({
	 	type: "POST",
	 	dataType: "json",
	 	data: {ipr_ids: inputboxValues, ipr_families: inputboxFamilies, ipr_without: inputboxWithoutIPR, ipr_subtypes: inputboxKeys, radio: radioData, chkPerfectMatch: perfectMatch, pagingSize: pagingSize, pagingIndex: pagingIndex, baseSubType: subType, kingdom: subtypeObject.kingdom},
	 	url: url2, //Relative or absolute path to response.php file
	 	async: true,
		success: function(data) {
			subtypeObject.clearInit();

			var subtype = subType;
			var ipr_term = data[subtype].iprDomain;
			
			var iprArray = getIprArray(inputboxValues);
			var familiesArray = getIprArray(inputboxFamilies);
			
			iprArray = iprArray.concat( familiesArray );
			
			var track = drawingSubtypePanel(data, subtype, ipr_term, iprArray, order, subtypeObject.kingdom, perfectMatch, inputboxFamilies[order], inputboxWithoutIPR[order], inputboxKeys.length);
			
			if($(window).scrollTop() > $('#viewerPanel').height())
			window.scrollTo(0,0);
		},
		error: function(request, status, error){
			request.status = 500;
			console.log("code:"+request.status+"\n"+"message:"+request.responseText.substr(0, 10)+"...\n"+"error:"+error);
		}
//		},
//		timeout: 6000000
	});
}


function isEmpty(val) {
	if(val == null || val == undefined || val == 'null' || val == 'undefined' || val == '' )	return true;
	
	return false;
}

function checkSpecialCharacter(val) {
	var flag = false;
	
	if((/[`~!@#$%^&*|\\\'\";:\/?]/gi).test(val) || (/[\s]/g).test(val)) {
		alert("특수문자 또는 공백은 사용할 수 없습니다.\n\n다시 입력해주세요.");
		flag = true;
	}
	
	return flag;
}

function checkDomain(val) {
	
	var flag = false;
	
	flag = checkSpecialCharacter(val);
	
//	var chk_eng = val.search(/IPR/ig);
//	var chk_num = val.search(/[0-9]/ig);
//	
//	if(chk_eng < 0 || chk_num < 0) {
//		alert("아래와같이 입력해주세요.\n\n예제) IPR123,IPR456");
//		return true;
//	}
	
	if(flag == false) {
		var word = val.split(',');
		
		for(var i=0; i<word.length; i++) {
			if(word[i].indexOf("IPR") == -1 || word[i].search(/[0-9]/ig) < 0) { // "IPR" 문자열이 포함되지 않을 경우
				alert("잘못 입력하셨습니다.\n\n" + "입력 Domain : " + val + "\n\n아래와같이 입력해주세요.\n\n예제) IPR123,IPR456");
				flag =  true;
				return flag;
				break;
			}
		}
	}
	
	return flag;
}


function getIprArray(inputboxValues) {
	var iprArray = new Array();
	for(var k=0; k<inputboxValues.length; k++) {
		iprTerms = inputboxValues[k].split(",");
		for(var j=0; j<iprTerms.length; j++) {
			if( iprArray.indexOf( iprTerms[j].trim() ) < 0 )	iprArray.push( iprTerms[j].trim() );
		}
	}
	return iprArray;
}

function drawingSubtypePanel(data, subtype, ipr_term, iprArray, order, kingdom, perfectMatch, inputboxFamilies, inputboxWithoutIPR, subGroupCount) {
	
	var totalHeight = 0;

	var menu = "";
	menu += "<div class='entry noselect' id='entry_"+subtype+"'>";
	menu += "<p>"+subtype+" : </p>";
	menu += "<div class='entrysub' id='subTypeIPR-"+subtype+"'>"+ipr_term+"</div>";
//	menu += "<div id='collapse-"+subtype+"' class='collapse expaned'>-</div>";
	menu += "<div class='btnall' id='btnall-"+subtype+"'></div>";
	menu += "</div>";

	$("#" + subtype + "-frame").append( $(menu) );
	
	var entry_margin_bottom = 10;
	totalHeight += $('#entry_'+subtype).height() + entry_margin_bottom;
	
//	$("#"+this.mainPanelName).append("<div id='collapse-"+subtype+"' class='collapse expaned'>ViewerPanel on</div>");

	var mainFrameName = subtype + "-frame";
	var currentPagingIndex = data[subtype].currentPagingIndex;
	var firstPagingIndex = data[subtype].firstPagingIndex;
	var lastPagingIndex = data[subtype].lastPagingIndex;
	var pagingSize = data[subtype].pagingSize;
	
	btn = new subType( order, subtype, radioData, mainFrameName, currentPagingIndex, firstPagingIndex, lastPagingIndex, pagingSize, kingdom, perfectMatch, inputboxFamilies[order], inputboxWithoutIPR[order] );
	var proteinCountHeight = btn.proteinCount( order, data[subtype].totalNoOfRecords, data[subtype].stat, subtype );
	
	$("#" + subtype + "-frame").append("<div id='collapse-"+subtype+"' class='collapse expaned shadowbutton' style='margin-bottom: 20px;'>ViewerPanel off ▲</div>");
	
	$("#collapse-"+subtype).on("click", function(){
		if( $(this).hasClass('expaned') ) {
			$(this).removeClass("expaned");
			$("#" + subtype + "-frame > .track").hide();
			$("#" + subtype + "-frame > .pagingall").hide();
			$("#" + subtype + "-frame > #noResult").hide();
			$(this).text("ViewerPanel on ▼");
			pagingClick=0;
			viewerPanelOnCount += 1;
		}else {
			$(this).addClass("expaned");
			$("#" + subtype + "-frame > .track").show();
			$("#" + subtype + "-frame > .pagingall").show();
			$("#" + subtype + "-frame > #noResult").show();
			$(this).text("ViewerPanel off ▲");
			pagingClick=1;
			viewerPanelOnCount -= 1;
		}
		
		if(viewerPanelOnCount > subGroupCount)	viewerPanelOnCount=subGroupCount;
		
//		console.log("viewerPanelOnCount : " + viewerPanelOnCount);
//		console.log("subGroupCount : " + subGroupCount);
		
		if(viewerPanelOnCount == subGroupCount)	$("#banner2").hide();
		else									$("#banner2").show();
	});

	
	totalHeight += proteinCountHeight;
	
	var proteinList = data[subtype].proteinList;

	if( proteinList.length == 0 ) {
		$("#" + subtype + "-frame").append("<div id='noResult'> 검색결과가 없습니다. </div>");
	} else {
		$("#btnall-"+subtype).append("<input name='chk_download-"+subtype+"' id='downloadRadioALL-"+subtype+"' type='radio' checked='checked' value='ALL' style='margin-left: 5px;'/>ALL");
		$("#btnall-"+subtype).append("<input name='chk_download-"+subtype+"' id='downloadRadioRepresentative-"+subtype+"' type='radio' value='Representative' style='margin-left: 5px;'/>Representative");
		
		$("#btnall-"+subtype).append("<button id='allPeptideDownloadButton-"+subtype+"'>Peptide Download</button>");
		$("#btnall-"+subtype).append("<button id='allCDSDownloadButton-"+subtype+"'>CDS Download</button>");
		
		var track = '';

		for(var i=0; i< proteinList.length; i++){
	  		track = new geneGroupSearch( proteinList[i], order, i, iprArray, mainFrameName );
		  	track.init();
		  	
		  	var protein_height = track.paint();

		  	totalHeight += protein_height;
		}
	}
	
//	btn.button( order, subtype, ipr_term, radioData, kingdom);
	btn.button( order, subtype, ipr_term, radioData, kingdom, perfectMatch, inputboxFamilies, inputboxWithoutIPR);
	btn.init();

	// Paging System 추가
	var paging = $("<div id='"+subtype+"-paging' class='pagingall'  >Test</div>");
	
	$("#" + subtype + "-frame").append( paging );
	
	var paging_margin_top = 10;
	totalHeight += $('#'+subtype+"-paging").height() + paging_margin_top;
//	totalHeight += $('#'+subtype+"-paging").height() + paging.height() + paging_margin_top;
	
//	$("#" + subtype + "-frame").css("height", totalHeight);
	$("#" + subtype + "-frame").css("height", "100%");
	
	btn.paging( currentPagingIndex );
	
	if(pagingClick == 0)	$("#collapse-"+subtype).trigger('click');
	
	return track;
}

function getInterproDomainInfoFromServer( inputboxValues, inputboxFamilies, inputboxWithoutIPR, inputboxKeys ) {
	var url2 = $(window.parent.location).attr('href');
	url2 += "/../" + "getInterproDomainInfo";

	jQuery.ajaxSettings.traditional = true;//ajax를 배열로 보내기 위한 세팅
	
	var ret = $.ajax({
	 	type: "POST",
	 	dataType: "json",
	 	data: {ipr_ids: inputboxValues, ipr_families: inputboxFamilies, ipr_without: inputboxWithoutIPR, ipr_subtypes: inputboxKeys, radio: radioData, pagingSize: pagingSize, chkPerfectMatch: perfectMatch},
	 	url: url2, //Relative or absolute path to response.php file
	 	async: true,
	 	beforeSend: function() {
	 		//통신을 시작할때 처리되는 함수 
	 		$('html').css("cursor","wait");// 현재 html 문서위에 있는 마우스 커서를 로딩 중 커서로 변경
	 	},
	 	complete: function() {
	 		//통신이 완료된 후 처리되는 함수
	 		$('html').css("cursor","auto"); // 통신이 완료 된 후 다시 일반적인 커서 모양으로 변경
	 	},
	 	success: function(data) {
	 		iprDomaintype = data;
	 		
	 		var iprArray = getIprArray(inputboxValues);
			var familiesArray = getIprArray(inputboxFamilies);
			
			iprArray = iprArray.concat( familiesArray );
	 		
			$("#banner2").append("<div class='Legend'>Legend</div>");

			var trackAA = new geneGroupSearch( null, null, null, iprArray, null );
			trackAA.init2();

			for(var i=0; i<iprArray.length; i++){
				var type = jQuery.map(data, function(obj) {
				    if(obj.id === iprArray[i])
				         return obj.type; // or return obj.name, whatever.
				});
				if(!isEmpty(iprArray[i])) {
					$("#banner2").append("<div id='iprColor"+i+"' style='width:15px; height:5px; margin:7px 0 0 15px;" +
							"text-align:center;float:left;  border-radius: 5px; border:1px solid #d3d3d3; background:"+trackAA.getIprColor( iprArray[i] )+"'>" +
									"<p class='circle'>"+type.toString().substring(0, 1)+"</p></div>");
					$("#banner2").append("<div id='iprName"+i+"' style=''>"+iprArray[i]+"</div>");
				}
			}
			
			$("#banner2").hide();
//			$("#banner2").show();
			
			$(window).scroll(function(){
				
//				var legend = $("#banner");
//				var screen_height = $(this).height();
//
//				var img_y = screen_height/2 - legend.height()/2 - $("#mainControlTable").height() - $(".subV").height() - $("#header").height();
				
				if($(this).scrollTop()+$('#banner').height() < $('#viewerPanel').height())
					$('#banner').animate({top:$(this).scrollTop() +"px"}, {queue: false, duration: 450});
				
				
				if($(this).scrollTop()+$('#banner').height() < 0)
					$('#banner').animate({top:"0px"}, {queue: false, duration: 450});
				
//				console.log($('#asd').height());
//				if($('#asd').height() == 88+38*0)		$('#banner').animate({top:$(this).scrollTop() +"px"}, {queue: false, duration: 350});
//				else if($('#asd').height() == 88+38*1)	$('#banner').animate({top:$(this).scrollTop()-38*1 +"px"}, {queue: false, duration: 350});
//				else if($('#asd').height() == 88+38*2)	$('#banner').animate({top:$(this).scrollTop()-38*2 +"px"}, {queue: false, duration: 350});
//				else if($('#asd').height() == 88+38*3)	$('#banner').animate({top:$(this).scrollTop()-38*3 +"px"}, {queue: false, duration: 350});
//				else if($('#asd').height() == 88+38*4)	$('#banner').animate({top:$(this).scrollTop()-38*4 +"px"}, {queue: false, duration: 350});
//				else if($('#asd').height() == 88+38*5)	$('#banner').animate({top:$(this).scrollTop()-38*5 +"px"}, {queue: false, duration: 350});
//				else if($('#asd').height() == 88+38*6)	$('#banner').animate({top:$(this).scrollTop()-38*6 +"px"}, {queue: false, duration: 350});
//				else if($('#asd').height() == 88+38*7)	$('#banner').animate({top:$(this).scrollTop()-38*7 +"px"}, {queue: false, duration: 350});
//				else if($('#asd').height() == 88+38*8)	$('#banner').animate({top:$(this).scrollTop()-38*8 +"px"}, {queue: false, duration: 350});
//				else if($('#asd').height() == 88+38*9)	$('#banner').animate({top:$(this).scrollTop()-38*9 +"px"}, {queue: false, duration: 350});
				
				
				
				
				
//				console.log($(this).scrollTop());
//				console.log("$(this).scrollTop()-38*a : " + ($(this).scrollTop()-38*1));
			
/*				if($(this).scrollTop() + img_y < 0)
					$('#banner').animate({top:0 +"px"}, {queue: false, duration: 350});
				else if($(this).scrollTop() < $("#viewerPanel").height() + $('#mainControlTable').height())
					$('#banner').animate({top:$(this).scrollTop()+img_y-($('#banner').height()/2) +"px"}, {queue: false, duration: 350});*/
					
			});
	 	},
		error: function(request, status, error){
			request.status = 500;
			console.log("code:"+request.status+"\n"+"message:"+request.responseText.substr(0, 10)+"...\n"+"error:"+error);
		}
	});
	return ret;
}

function getGeneGroupSearchDesyncResultmessageFromServer(inputboxValues, inputboxFamilies, inputboxWithoutIPR, inputboxKeys, radioData, pefectMatch, pagingSize) {
	var url2 = $(window.parent.location).attr('href');
	url2 += "/../" + "getGeneGroupSearchDesyncResult";

	jQuery.ajaxSettings.traditional = true;//ajax를 배열로 보내기 위한 세팅
	
	var ret = $.ajax({
	 	type: "POST",
//	 	timeout : 600000,
	 	dataType: "json",
	 	data: {ipr_ids: inputboxValues, ipr_families: inputboxFamilies, ipr_without: inputboxWithoutIPR, ipr_subtypes: inputboxKeys, radio: radioData, pagingSize: pagingSize, chkPerfectMatch: perfectMatch},
	 	url: url2, //Relative or absolute path to response.php file
	 	async: true,
	 	beforeSend: function() {
	 		//통신을 시작할때 처리되는 함수 
	 		$('html').css("cursor","wait");// 현재 html 문서위에 있는 마우스 커서를 로딩 중 커서로 변경
	 	},
	 	complete: function() {
	 		//통신이 완료된 후 처리되는 함수
	 		$('html').css("cursor","auto"); // 통신이 완료 된 후 다시 일반적인 커서 모양으로 변경
	 	},
	 	success: function(data) {
			var subGroupCount = Object.keys(data).length;

			var iprArray = getIprArray(inputboxValues);
			var familiesArray = getIprArray(inputboxFamilies);
//			var withoutIPRArray = getIprArray(inputboxWithoutIPR);
			
			iprArray = iprArray.concat( familiesArray );
			
			var aliveTrack = null;
			for(var k=0; k<subGroupCount; k++) {
				var subtype = Object.keys(data)[k];
				var ipr_term = data[subtype].iprDomain;

				var track = drawingSubtypePanel(data, subtype, ipr_term, iprArray, k, "ALL", perfectMatch, inputboxFamilies[k], inputboxWithoutIPR[k], subGroupCount);
				if( track != null )	aliveTrack = track;
			}

//			$("#banner2").append("<div class='Legend'>Legend</div>");
//
//			var trackAA = new geneGroupSearch( null, null, null, iprArray, null );
//			trackAA.init2();
//			
//			for(var i=0; i<iprArray.length; i++){
////				if(iprArray[i].startsWith("IPR") == false ) {//문자열에 IPR이 포함되지 않는 경우
////					iprArray[i] = "IPR" + iprArray[i];
////				}				
//				
//				$("#banner2").append("<div id='iprColor"+i+"' style='width:50px; height:5px; margin:8px 0 0 10px;" +
//						"text-align:center;float:left;border:1px solid #d3d3d3;background:"+trackAA.getIprColor( iprArray[i] )+"'></div>");
//				$("#banner2").append("<div id='iprName"+i+"' style=''>"+iprArray[i]+"</div>");
//			}
//
//			$("#banner").show();
//
//			$(window).scroll(function(){
//				$('#banner').animate({top:$(window).scrollTop()+"px"}, {queue: false, duration: 350});
//			});
		},
		error: function(request, status, error){
//			request.status = 500;
			alert("code:"+request.status+"\n"+"message:"+request.responseText.substr(0, 10)+"...\n"+"error:"+error);
			console.log("code:"+request.status+"\n"+"message:"+request.responseText.substr(0, 10)+"...\n"+"error:"+error);
		}
	});
	return ret;
}


function relocation_loading_img() {
	var loading_img = $("#Loading_Image");
	var screen_width = $(this).width();
	var screen_height = $(this).height();
	
	var img_x = screen_width/2 - loading_img.width()/2;
	var img_y = screen_height/2 - loading_img.height()/2;
	
	loading_img.css("top", $(this).scrollTop() + img_y);
	loading_img.css("left", img_x);
}

//다시 조회를 위한 화면 초기화 함수
function clearInit() {
	$("#viewerPanel").empty();
	$("#banner2").empty();
	
	$("#allSequenceDownloadButton").detach();
	$("#allCDSDownloadButton").detach();
	// .detach() : 지정한 요소 포함 하위 요소까지 제거
	// .empty() : 지정한 요소의 하위 요소만 제거
	// .remove() : 지정한 요소를 포함한 하위 요소 모두 제거 & 요소와 관련된 이벤트와 데이터 모두 제거
	
	
	// subtypes & ipr_ids 배열을 초기화함
	inputboxValues = [];
	inputboxKeys = [];
	inputboxFamilies = [];
	inputboxWithoutIPR = [];
	
	pagingClick = 0;
	click_kingdom = "ALL";
	viewerPanelOnCount = 0;
}

function addInput(inputNo){
    var strInput = "";

    removeSubtypesControl();

//    var samples = new Array("IPR002944,IPR000175", "IPR012284,IPR012473");	// 삭제 대상
//    var samples = new Array("IPR013783, IPR015129, IPR013098", "IPR002944,IPR000175");
//    var samples = new Array("IPR013783, IPR015129, IPR013098", " IPR012675, IPR002888, IPR016166, IPR005107");
//    var samples = new Array("IPR016208,IPR012675,IPR006058,IPR002888,IPR016166");
    
//    var samples = new Array("IPR012675,IPR002888,IPR016166,IPR005107", "IPR012675,IPR002888,IPR016166");
//    var samples_family = new Array("IPR012175", "IPR012675");
    
//    var samplesList = new Array("IPR012675,IPR002888,IPR016166,IPR005107"
//    		, "IPR016166"
//    		, "IPR003495"
//    		, "IPR001207"
//    		, "IPR003591,IPR001611,IPR000719"
//    		, "IPR002143,IPR016094"
//    		, "IPR001487,IPR018359"
//    		, "IPR012347,IPR002177"
//    		, "IPR018289"
//    		, "IPR004162"
//    		, "IPR007527,IPR006564"
//    		, "IPR013323"
//    		, "IPR008974,IPR002083"
//    		, "IPR012227"
//    		, "IPR009060,IPR015940,IPR029071"
//    		, "IPR009060,IPR029071"
//    		, "IPR000477,IPR012337"
//    		, "IPR001878,IPR012337"
//    		, "IPR026854"
//    		, "IPR026854,IPR009543"
//    		, "IPR016024,IPR007587"
//    		, "IPR025322"
//    		, "IPR016491,IPR000038,IPR027417"
//    		, "IPR027267,IPR000198,IPR008936"
//    		, "IPR027417,IPR014001,IPR001650,IPR011545"
//    		);
//    var samples = new Array(samplesList[random()]);
//    var samples_family = new Array("");;
//    var samples_without = new Array("");;
    var samples = new Array("IPR012675,IPR002888,IPR016166,IPR005107", "IPR012675,IPR002888,IPR016166,IPR005107");
    var samples_family = new Array("IPR012175,IPR006058", "IPR005107");
    var samples_without = new Array("IPR014307");
    
//    var samples = new Array("IPR010264,IPR001313,IPR011989,IPR016024");
//    var samples_family = new Array("");
//    var samples_without = new Array("");
    
//    var samples = new Array("IPR027434,IPR001982");
//    var samples_family = new Array("IPR023616");
//    var samples_without = new Array("IPR000883");
    
//    IPR005559, IPR014756, IPR002909, IPR013783, IPR020683, IPR008540, IPR012677, IPR000504
//    var samples = new Array("IPR012284,IPR012473");
    
//    var samples = new Array("IPR001313,IPR011989,IPR016024", "IPR012675,IPR002888,IPR016166,IPR005107");
//    var samples_family = new Array("IPR001313", "IPR005107");
//    var samples_without = new Array("IPR012940, IPR012959", "");

    for(var i=0; i< inputNo; i++) {
    	if(samples[i]==null)			samples[i]='';
    	if(samples_family[i]==null)		samples_family[i]='';
    	if(samples_without[i]==null)	samples_without[i]='';
    	
    	var idx = i + 1;

    	strInput += "<div style='margin:10px 0;padding-left:10px;height:28px;line-height:28px;color:#555;'>";
    	strInput += "<p style='float:left; width:20px; text-align:right;'>" + idx + "</p>";
    	strInput += "<input class='shadowtextbox' type=text size='8' id='inputSubType"+idx+"' name='inputSubType"+idx+"' style='margin-left:10px;float:left;' OnKeyDown='onSearchKeyDown();' value='SubType"+idx+"'>";
    	strInput += "<p style='float:left;margin-left:10px;'>Domains</p><input class='shadowtextbox' type=text size='25' name='inputIPR"+idx+"' id='inputIPR"+idx+"' style='margin-left:10px;float:left;' OnKeyDown='onSearchKeyDown();' value='"+samples[i]+"'/>";
    	strInput += "<p id='include-chk-"+idx+"' style='float:left;margin-left:10px;'>with</p><input type=checkbox name='chkInOrOut"+idx+"' id='chkInOrOut" + idx + "' style='margin-left:10px;float:left;margin-top:3px;' />";
    	strInput += "<p style='float:left;margin-left:10px;'></p><input OnKeyDown='onSearchKeyDown();' class='shadowtextbox' type=text size='10' name='inputFamilies"+idx+"' id='inputIPR-Family"+idx+"' style='margin-left:10px;float:left;' value='"+samples_family[i]+"'/>";
    	strInput += "<p id='without-chk-"+idx+"' style='float:left;margin-left:10px;'>without</p><input type=checkbox name='withoutChkInOrOut"+idx+"' id='withoutChkInOrOut" + idx + "' style='margin-left:10px;float:left;margin-top:3px;' checked/>";//checked
    	strInput += "<p style='float:left;margin-left:10px;'></p><input OnKeyDown='onSearchKeyDown();' class='shadowtextbox' type=text size='10' name='withoutIPR"+idx+"' id='withoutIPR"+idx+"' style='margin-left:10px;float:left;' value='"+samples_without[i]+"'/>";
    	strInput += "</div>";
    }
    
    $("#mainControlTable tr:last").after(
    	"<tr id='asd'>" +
    		"<td id='iprTermsController' style='background:#e5f3f0;'>" +
//    			"<td style='color:#555; vertical-align:bottom; background:#e5f3f0; padding-bottom:10px; line-height:30px;'>" +
//    				"<input type='radio' id='searchRadio1' name='chk_info' value='ord'  checked='checked'/>in order " +
//    				"<input type='radio' id='searchRadio2' name='chk_info' value='anyOrd' />in any order " +
//    				"<input type='checkbox' id='chkPerfectMatch' name='chkPerfectMatch' checked='checked' style='margin:0 5px 0 10px;'/>Perfect match " +
//    			"</td>" +
//    			"<td style='vertical-align:bottom; background:#e5f3f0; padding-bottom:10px;'>" +
//    				"<button id='searchButton'>Search</button>" +
//    			"</td>" +
    		"</td>" +
    	"</tr>"
    );
    
    $("#iprTermsController").append( strInput );
    
    $("#iprTermsController").append( 
		"<div style='color:#555; vertical-align:bottom; background:#e5f3f0; padding-bottom:10px; padding-left:10px; line-height:30px;'>" +
			"<input type='radio' id='searchRadio1' name='chk_info' value='ord'  checked='checked'/>in order " +
			"<input type='radio' id='searchRadio2' name='chk_info' value='anyOrd' />in any order " +
			"<input type='checkbox' id='chkPerfectMatch' name='chkPerfectMatch' checked='checked' style='margin:0 5px 0 10px;'/>Perfect match " +
			"<button id='searchButton'>Search</button>" +
		"</div>"
    );
    
	$("input:checkbox[name*='chkInOrOut']").change(function(){
		if($(this).is(":checked") == false)
			$("input[id='inputIPR-Family"+$(this).attr("id").replace(/[^0-9]/g,"")+"']").prop("disabled", true);
        else
        	$("input[id='inputIPR-Family"+$(this).attr("id").replace(/[^0-9]/g,"")+"']").prop("disabled", false);
    });
	
	$("input:checkbox[name*='withoutChkInOrOut']").change(function(){
	    if($(this).is(":checked") == false)
			$("input[id='withoutIPR"+$(this).attr("id").replace(/[^0-9]/g,"")+"']").prop("disabled", true);
	    else
	    	$("input[id='withoutIPR"+$(this).attr("id").replace(/[^0-9]/g,"")+"']").prop("disabled", false);
	});
    
	$("#searchButton").on("click", function() {
		
		var inputIPRCheck = '';
		var inputIPRCheckScore = 0;
		var errorIPR=1;
		
		var inputSubTypeCheck = '';
		var inputSubTypeCheckScore = 0;
		var errorSubType=1;
		
//		if(isEmpty($("#inputIPR"+inputNo).val()) ){
//			alert("Please enter the IPR");
//			$("#inputIPR"+inputNo).focus();
//		} else {
			for(errorIPR=1; errorIPR<= inputNo; errorIPR++) {
				if(isEmpty($("#inputSubType"+errorIPR).val())){
					alert("Please enter the SubType");
//					$("#inputSubType"+inputNo).focus();
					inputSubTypeCheck = true;
					break;
				} else {
				
					inputSubTypeCheck = checkSpecialCharacter($("#inputSubType"+errorIPR).val());
					if(inputSubTypeCheck == true)	inputSubTypeCheckScore += 1;
					if(inputSubTypeCheckScore > 0) {
						inputSubTypeCheck = true;
						break;
					}
				}
				 
				if(isEmpty($("#inputIPR"+errorIPR).val())){
					alert("Please enter the IPR");
//					$("#inputIPR"+inputNo).focus();
					inputIPRCheck = true;
					break;
				} else {
				
					inputIPRCheck = checkDomain($("#inputIPR"+errorIPR).val());
					if(inputIPRCheck == true)	inputIPRCheckScore += 1;
					if(inputIPRCheckScore > 0) {
						inputIPRCheck = true;
						break;
					}
				}
			}
			
			if(inputIPRCheck){
				$("#inputIPR"+errorIPR).val("");
				$("#inputIPR"+errorIPR).focus();
			} else if(inputSubTypeCheck){
				$("#inputSubType"+errorIPR).val("");
				$("#inputSubType"+errorIPR).focus();
			} else {
				clearInit();
		
				$("input[name*='inputIPR']:text").each(function(i) {
					inputboxValues.push( $(this).val() );
				});
				
				$("input[name*='inputFamilies']:text").each(function(i) {
					var idx = i+1;
					if($("input:checkbox[name='chkInOrOut"+idx+"']").is(":checked") == true)
						inputboxFamilies.push( $(this).val() );
					else
						inputboxFamilies.push("");
				});
				
				$("input[name*='withoutIPR']:text").each(function(i) {
					var idx = i+1;
					if($("input:checkbox[name='withoutChkInOrOut"+idx+"']").is(":checked") == true)
						inputboxWithoutIPR.push( $(this).val() );
					else
						inputboxWithoutIPR.push("");
				});
		
				$("input[name*='inputSubType']:text").each(function(i) {
					var inputSubType = $(this).val();
					
//					if(inputSubType.indexOf(' ') != -1) {
//						inputSubType = inputSubType.replace(/ /g,'');
//					}
//					
//					if(inputSubType.indexOf("'") != -1) {
//						inputSubType = inputSubType.replace(/'/g,'');
//					}
					
					inputboxKeys.push( inputSubType );
				});
		
				perfectMatch = $('input:checkbox[id="chkPerfectMatch"]').is(":checked");
				radioData = $('input:radio[name="chk_info"]:checked').val();
				
				pagingSize = $("#selectPagingSize").val();
		
				$("#viewerPanel").append( "<div id='"+inputboxKeys[0]+"-frame' style='position:relative;margin-top:15px;display:block; height:100% !important;;'></div>" );
				for(var i=1; i<inputboxKeys.length; i++) {
					$("#viewerPanel").append( "<div id='"+inputboxKeys[i]+"-frame' style='position:relative;margin-top:15px;display:block; height:100% !important;;'></div>" );
				}
				var ajaxRetObj = getGeneGroupSearchDesyncResultmessageFromServer( inputboxValues, inputboxFamilies, inputboxWithoutIPR, inputboxKeys, radioData, perfectMatch, pagingSize );
				
				getInterproDomainInfoFromServer( inputboxValues, inputboxFamilies, inputboxWithoutIPR, inputboxKeys );
			}
//		}
	});
}

function removeSubtypesControl() {
    if( $('#mainControlTable tr').length > 1 )	$("#mainControlTable tr:last").remove();	
}

function onKeyDown(){
	if(event.keyCode == 13)
	$("#noOfSubtypesBtn").click();
}
function onSearchKeyDown(){
	if(event.keyCode == 13)
	$("#searchButton").click();
}

//all version
$(document).ready(function(){
	
	relocation_loading_img();
	
	$("#Loading_Image").hide();
	$("#searchButton").hide();
	$("#searchResultInfo").hide();
	
	$("#Loading_Image").ajaxStart(function(){
		$(window).scroll(function(){
			relocation_loading_img();
		});
		$("#modal_content").modal(); 
		$(this).show();
	}).ajaxStop(function(){
		$.modal.close();
		$(this).fadeOut(100);
//		window.setTimeout("pageReload()", 8000);//1분뒤 새로고침, 1000이 1초
	});
	
	$("input:radio[name='chk_info']").change(function(){   
		if( this.value=='anyOrd' )	{
			$("#chkPerfectMatch").prop("checked", false);
			$("#chkPerfectMatch").prop("disabled", true);
		}else						{
			$("#chkPerfectMatch").prop("checked", true);
			$("#chkPerfectMatch").prop("disabled", false);
		}
	});
	
	$("#noOfSubtypesBtn").on("click", function() {
		var noOfSubTypes = $("#searchCount").val();
		addInput( noOfSubTypes );
		
	    for(var i=0; i < noOfSubTypes; i++) {
	    	var idx = i + 1;
	    	
	    	if($("input:checkbox[id='chkInOrOut" + idx + "']").is(":checked") == false)
	        	$("input[id='inputIPR-Family" + idx + "']").prop("disabled", true);
	        else
	        	$("input[id='inputIPR-Family" + idx + "']").prop("disabled", false);
	    	
	        if($("input:checkbox[id='withoutChkInOrOut" + idx + "']").is(":checked") == false)
	        	$("input[id='withoutIPR" + idx + "']").prop("disabled", true);
	        else
	        	$("input[id='withoutIPR" + idx + "']").prop("disabled", false);
	    }
		
		if( noOfSubTypes > 0 && noOfSubTypes <= 10 )
			$("#searchButton").show();
		else {
			removeSubtypesControl();

			alert("You have to input value betwen 1 and 10");
			$('#searchCount').val("");
			$('#searchCount').focus();
		}
	});
	
	// Test //////////////////////////////////////
	$("#noOfSubtypesBtn").trigger("click");
//	$("#searchButton").trigger("click");
	// Test //////////////////////////////////////
});

//function pageReload() {
//	window.location.reload();
//}
//
//function random() {
//	var result = Math.floor(Math.random() * 25);
//	return result;
//}
