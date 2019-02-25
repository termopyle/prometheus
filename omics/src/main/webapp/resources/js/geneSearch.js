// 사용자가 입력한 IPR terms과 순서 상관 유무에 대한 내용을 담는 전역 변수 선언
// 입력이라는 버튼을 누를 경우만 이 변수가 변경됨
var ipr_ids = "";
var radioData = "";

var pagingObject = {
	currentPagingIndex : -1,
	firstPagingIndex :  -1,
	lastPagingIndex : -1,
	pagingSize : 5,
	previousPage : -1,
	nextPage : -1,
	displayNo : 10,
	pagingRangeStartIndexes : [],

	init : function(currentPagingIndex, firstPagingIndex, lastPagingIndex, pagingSize) {
		this.currentPagingIndex = currentPagingIndex;
		this.firstPagingIndex = firstPagingIndex;
		this.lastPagingIndex = lastPagingIndex==0?1:lastPagingIndex;
		this.pagingSize = pagingSize;
		
		this.calculatePagingRangeStartIndexes();
	},
	paging : function( index ) {
		this.currentPagingIndex = index;

		var newPagingStartIndex = this.findStartIndexByStartIndex();

		this.drawingPagingPanel( newPagingStartIndex );
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
	drawingPagingPanel : function( newPagingStartIndex ) {
		// 페이징 시스템을 그려주는 함수 
		var pagingDiv = $("#pagingPanel");
		pagingDiv.empty();

		if( this.lastPagingIndex >= 1 ) {
			// go to first index
			pagingDiv.append( $("<span id='page_first' class='paging' onclick=paging('"+this.firstPagingIndex+"')>&lt;&lt;</span>") );
			
			if( newPagingStartIndex > this.firstPagingIndex )
				pagingDiv.append( $("<span id='page_prev' class='paging' onclick=paging('"+this.previousPage+"')>&lt;</span>") );
			
//			console.log( "newPagingStartIndex=" + newPagingStartIndex + "   this.nextPage=" + this.nextPage +  " this.firstPagingIndex=" + this.firstPagingIndex + "   this.currentPagingIndex=" + this.currentPagingIndex + "    this.lastPagingIndex=" + this.lastPagingIndex);

			for(var i=newPagingStartIndex; i<=newPagingStartIndex+this.displayNo-1&i<=this.lastPagingIndex; i++) {
				pagingDiv.append( $("<span id='page_" + i + "' class='paging' onclick=paging('" + i + "')>" + i + "</span>") );
			}
			
			if( newPagingStartIndex + this.displayNo - 1 < this.nextPage )
				pagingDiv.append( $("<span id='page_next' class='paging' onclick=paging('"+this.nextPage+"')>&gt;</span>") );

			// go to last index
			pagingDiv.append( $("<span id='page_last' class='paging' onclick=paging('"+this.lastPagingIndex+"')>&gt;&gt;</span>") );
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
	}
};

var geneSearch = function( obj, order, ipr_terms) {
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
	this.sequence = '';
	this.protein_id = '';
	this.ipr_terms_array = ipr_terms;
};

geneSearch.prototype = {
	init:function() {
		this.DB_NMS 				= ["PRINTS", "Pfam", "Gene3D", "TIGRFAM", "ProSiteProfiles", "ProSitePatterns", "Hamap", "PANTHER", "SUPERFAMILY", "SMART", "ProDom", "Colis", "PIRSF"];
		this.ipr_color_type			= ["#0066b3", "#f598aa", "#70bf54", "#fec34e", "#ef403d", "#6850a1", "#934d2a", "#31bcad", "#f04e46", "#ffea82", "#ab3e97", "#eed903", "#71ceea", "#00854a", "#c02555", "#fab49b", "#a23e97", "#2d5980", "#f58220", "#b3beb1", "#008e83", "#f15b4e", "#ffd400", "#c48a10", "#5c6654", "#5c6654", "#ee3e75", "#00aebd", "#f99d1c", "#a6ce42", "#16568b"],
//		this.ipr_color_type			= [""],
		this.margin					= 8;
		this.item_bar_height		= 5;
		this.gap					= 5;
	 	this.protein_name			= this.obj.nm + " ( Taxonomy : "+this.obj.nid+", Ncbi Id : " + this.obj.ncbi_id + " )";	 	
		this.VERTICAL_EXTEND_SIZE	= 10;
		this.protein_length			= this.obj.length;
		this.multiplier				= this.getRange( this.protein_length );
		this.sequence				= this.obj.sequence;
		this.protein_id				= this.obj.protein_accession_version;

	 	this.resize( $("#viewerPanel").width() );
	 	
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
		var proteinTrack = $("<div id='protein-layer-"+this.order+"' class='track'></div>");
		var proteinTitle = $("<div class='track-name'><span class='track-name-label-header'></span><span class='track-name-lable protein_title'></span></div>");
		var proteinPanel = $("<div id='proteinPanel-"+this.order+"' class='interpro-protein-panel'></div>");
		var proteinStructure = $("<div id='protein_structure-"+this.order+"' class='clickable rounded-corners interpro-protein-structure'></div>)");
		
//		var sequenceDownloadButton = $("<a href='downloads?name="+jQuery.parseJSON(getSequenceDownloadMessageFromServer(obj).responseText)+
//				"'><input type='button' id='sequenceDownloadButton"+this.order+"' value=' Sequence Download ' "+
//				"style='vertical-align:middle; float: right; margin-top:12px'/></a>");
		
		var sequenceDownloadButton = $("<button class='btnblue Sqdown' id='sequenceDownloadButton"+this.order+"' >Peptide</button>");
		
		$(sequenceDownloadButton).on("click", function() {
			var type = 'sequence';
			var createDownloadFile = jQuery.parseJSON($.download(obj, type).responseText);
			var url = "downloads?name="+createDownloadFile;
			$(location).attr('href', url);
		});
		
		var CDSDownloadButton = $("<button class='btnblue Cdsdown' id='CDSDownloadButton"+this.order+"' >CDS</button>");
		
		$(CDSDownloadButton).on("click", function() {
			var type = 'CDS';
			var createDownloadFile = jQuery.parseJSON($.download(obj, type).responseText);
			var url = "downloads?name="+createDownloadFile;
			$(location).attr('href', url);
		});
		
		var viewerSwitchingButton = $("<button id='viewerSwitchingButton"+this.order+"' class='btnpurple Gviewer' >Gene Viewer</button>");
		
		$(viewerSwitchingButton).on("click", function() {
			var viewer = "/viewer";
			var tax_id = obj.nid;
			var refseq_assembly_id = obj.ncbi_id;
			var protein_accession_id = obj.protein_id;
			
//			"http://localhost:8080/omics/viewer"
			
//			var protocol = $(location).attr('protocol');//http://
//			var host = $(location).attr('host');//localhost:8080
			var pathName = $(location).attr('pathname');//omics/viewer
			var string = pathName.split('/');
			
			var url = '/' + string[1];
			
			post_to_url(url+viewer, {"tax_id": tax_id, "refseq_assembly_id": refseq_assembly_id, "protein_accession_id": protein_accession_id});
		});

		proteinPanel.append( proteinStructure );

		proteinTrack.append( proteinTitle );
		
		proteinTrack.append( proteinPanel );

		base.append( proteinTrack );

		var depth = 1 + this.getDegreeOfTerminalDepth( this.ipr_terms );
		var trackHeight = this.margin + this.item_bar_height + this.margin + ((depth * this.gap) + (depth * this.item_bar_height));

		proteinTitle.find("span.track-name-lable").text( this.protein_name ).append( sequenceDownloadButton ).append( CDSDownloadButton ).append( viewerSwitchingButton );
		
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
			proteinPanel.append("<span id='rl_"+idx+"-"+this.order+"' class='range_label range_label-"+this.order+"' style='left:"+(i-25)+"px;'>"+label+"</span>");
			
			// 레이블은 보더의 가운데 위치해야 하므로 중앙 위치로 계산하여 그려줌
//			var nP = i - ($("#rl_" + idx+"-"+this.order).width()/2);
//			$("#rl_"+idx+"-"+this.order).css("left", nP);
			
			// 만약 격자를 그릴때 오른쪽이 실제 그려지는 캔버스를 벗어나는 경우 
			// 가장 오른쪽 캔버스에 마지막 단백질 길이를 적어준다
			if( (i + rangeDashedBarWidth) > proteinPanel.width() ) {
				var leftOfLastBorder = (proteinPanel.position().left + proteinPanel.width());

				proteinPanel.append("<div class='range_border-protein range_border' style='left:"+(leftOfLastBorder+1)+"px;'></div>");
				proteinPanel.append("<span id='rl_last-"+this.order+"' class='range_label range_label-"+this.order+"'>"+this.protein_length+"</span>");

				var nP2 = leftOfLastBorder - ($("#rl_last-"+this.order).width()/2);

				var previous = $("#rl_" + (idx) + "-" + this.order);

				if( previous.position().left + previous.width() > nP2 ) {
					var pre_left = previous.css("left").replace("px", "");
					previous.css("left", pre_left - (previous.width()/2) );
				}

				$("#rl_last-"+this.order).css("left", nP2);
				
				delete leftOfLastBorder, nP2;
			}

			idx++;
		}

		$(".range_border-protein").css("height", rangeDashedBarHeight);
		$(".range_border-protein").css("width", rangeDashedBarWidth);
		$(".range_label-"+this.order).css("top", proteinPanel.position().top + rangeDashedBarHeight );
		
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

		proteinTrack.css("height", rangeDashedBarHeight + $(".range_label").height() );
		
		delete proteinTrack, proteinTitle, proteinPanel, proteinStructure, depth, trackHeight, rangeDashedBarHeight, rangeDashedBarWidth, idx, index, baseTop;
		
//		console.log( proteinTrack );
		
		return proteinTrack.height();
	},
	paint:function() {
		this.getIprColorLegend();
		
		var	base = $("#viewerPanel").css("height", 100).css("width", this.width);
		
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
	},
	callAjax : function () {
		
	}
};

function isEmpty(val) {
	if(val == null || val == undefined || val == 'null' || val == 'undefined' || val == '' )	return true;
	
	return false;
}

function checkSpecialCharacter(val) {
	
	if((/[~!@\#$%<>^&*\()\-=+_\’]/gi).test(val)) {
		alert("특수문자는 입력하실수 없습니다.\n\n다시 입력해주세요.");
		return true;
	}
	
	var chk_eng = val.search(/IPR/ig);
	var chk_num = val.search(/[0-9]/g);
	
	if(chk_eng < 0 || chk_num < 0) {
		alert("아래와같이 입력해주세요.\n\n예제) IPR123,IPR456");
		return true;
	}
	
	return false;
}


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

jQuery.allDownloadByIprId = function getAllSequenceDownloadMessageFromServer(ipr_ids, radioData, type) {
	var url2 = $(window.parent.location).attr('href');
	url2 += "/../" + "getAllSequencesDownloadByIprTerms";
	
	var ret = $.ajax({
	 	type: "POST",
	 	dataType: "json",
	 	data: {ipr_ids: ipr_ids, radio: radioData, type: type},
	 	url: url2, //Relative or absolute path to response.php file
	 	async: false,
		error: function(request, status, error){
			request.status = 500;
			alert("code:"+request.status+"\n"+"message:"+request.responseText.substr(0, 10)+"...\n"+"error:"+error);
		}
	});
	return ret;
};

jQuery.download = function getSequenceDownloadMessageFromServer(data, radioData, type) {
	var url2 = $(window.parent.location).attr('href');
	url2 += "/../" + "getSequenceDownload";
		
	var protein_data_str = JSON.stringify( data );
	
	var ret = $.ajax({
	 	type: "POST",
	 	dataType: "json",
	 	data: {protein_info: protein_data_str, type: type},
	 	url: url2, //Relative or absolute path to response.php file
	 	async: false,
		error: function(request, status, error){
			request.status = 500;
			alert("code:"+request.status+"\n"+"message:"+request.responseText.substr(0, 10)+"...\n"+"error:"+error);
		}
	});
	return ret;
};

$(window).resize(function() {
	relocation_loading_img();
});

function relocation_loading_img() {
	var loading_img = $("#Loading_Image");
	var screen_width = $(this).width();
	var screen_height = $(this).height();

	var img_x = screen_width/2 - loading_img.width()/2;
	var img_y = screen_height/2 - loading_img.height()/2;
	
	loading_img.css("top", img_y);
	loading_img.css("left", img_x);
}

function getPagingSizeByAjax( ipr_ids, radioData, pagingSize ) {
	var url2 = $(window.parent.location).attr('href');
	url2 += "/../" + "getInterproDesyncResultWithPagingSize";

	var ret = $.ajax({
	 	type: "POST",
	 	dataType: "json",
	 	data: {ipr_ids: ipr_ids, radio: radioData},	 
	 	url: url2, //Relative or absolute path to response.php file
	 	async: true,
		success: function(data) {
			var diff = Math.ceil(data / pagingSize) - 1;
			
			$("#searchResultInfo").empty();
			$("#searchResultInfo").append("<span id='hit_result'>Hit : "+data+" proteins</span>");

//			init : function(currentPagingIndex, firstPagingIndex, lastPagingIndex, pagingSize) {
			// Paging System 초기화
			pagingObject.init( 1, 1, diff, pagingSize ); 

			// Paging system update
			paging( 1 );
		}
	});
}

function paging(index) {
	clearInit();

	pagingObject.paging( index );

	var pagingIndex = Number( Number( Number(pagingObject.currentPagingIndex) -1 ) * Number(pagingObject.pagingSize) ) + 1;
	
//	console.log( "pagingIndex=" + pagingIndex );
	
	// 현재 선택된 index 백그라운드 색깔 변경
	$(".paging").removeClass("hilight_paging");
	$(".paging").addClass("normal_paging");

	$("#page_" + pagingObject.currentPagingIndex).addClass("hilight_paging");
	
	getProteinDomainsAjax(ipr_ids, radioData, pagingIndex, pagingObject.pagingSize);
}

function getProteinDomainsAjax(ipr_ids, radioData, pagingIndex, pagingSize) {
	var url2 = $(window.parent.location).attr('href');
	url2 += "/../" + "getInterproDesyncResultWithPaging";
	
	var ret = $.ajax({
	 	type: "POST",
	 	dataType: "json",
	 	data: {ipr_ids: ipr_ids, radio: radioData, pagingIndex: pagingIndex, pagingSize: pagingSize},
	 	url: url2, //Relative or absolute path to response.php file
	 	async: true,
		success: function(data) {
//			console.log(data);
			if( data.length == 0 ) {
				$("#viewerPanel").append("검색결과가 없습니다.");
				return;
			}else {
				var track = '';

				// IPR 도메인별 색깔지정
				var iprArray = new Array();
				for(var k=0; k<ipr_ids.length; k++) {
					var iprTerms = ipr_ids.split(",");
					for(var j=0; j<iprTerms.length; j++) {
						if( iprArray.indexOf( iprTerms[j].trim() ) < 0 )	iprArray.push( iprTerms[j].trim() );
					}
				}
				
				for(var i=0; i< data.length; i++){
			  		track = new geneSearch( data[i], i, iprArray );
				  	track.init();
				  	track.paint();
				}

				$(".btnall").append("<button id='allSequenceDownloadButton'>All Peptides Download</button>");
				$(".btnall").append("<button id='allCDSDownloadButton'>All CDS Download</button>");
				
				var allSequenceDownloadButton = $("#allSequenceDownloadButton");
				var allCDSDownloadButton = $("#allCDSDownloadButton");
				
				$(allSequenceDownloadButton).on("click", function() {
					var type = "sequence";
					
//					var createDownloadFile = jQuery.parseJSON($.allDownload(data, ipr_ids, type).responseText);
					var createDownloadFile = jQuery.parseJSON($.allDownloadByIprId(ipr_ids, radioData, type).responseText);
					
					var url = "downloads?name="+createDownloadFile;
					$(location).attr('href', url);
				});
				
				$(allCDSDownloadButton).on("click", function() {
					var type = "CDS";
					
//					var createDownloadFile = jQuery.parseJSON($.allDownload(data, ipr_ids, type).responseText);
					var createDownloadFile = jQuery.parseJSON($.allDownloadByIprId(ipr_ids, radioData, type).responseText);
					
					var url = "downloads?name="+createDownloadFile;
					$(location).attr('href', url);
				});

				$("#banner2").append("<div class='Legend'>Legend</div>");
				
				for(var i=0; i<iprArray.length; i++){
//					console.log(track.ipr_color_type[i]);
//								$("#iprColor").css("background",track.ipr_color_type[i]);
					//$("셀렉터").html() : 셀렉터 태그 내에 존재하는 자식 태그를 통채로 읽어올 경우 사용하는 함수(태그를 동적으로 사용할 때 주로 사용됨)
					//$("셀렉터").text() : 셀렉터 태그 내에 존재하는 자식 태그들 중에 html태그까지 모두 문자로 인식하는 함수
					//$("셀렉터").val() : input태그에 정의된 value속성의 값을 확인하고자 할 경우 사용하는 함수
//								$("#iprName").html(dataStrArray[i]);
					
//					if(iprArray[i].StartsWith("IPR") == false ) {//문자열에 IPR이 포함되지 않는 경우
//						iprArray[i] = "IPR" + iprArray[i];
//					}
					
					$("#banner2").append("<div id='iprColor"+i+"' style='width:50px; height:5px; margin:8px 0 0 10px;" +
							"text-align:center;float:left;border:1px solid #d3d3d3;background:"+track.getIprColor( iprArray[i] )+"'></div>");
					$("#banner2").append("<div id='iprName"+i+"' style=''>"+iprArray[i]+"</div>");
				}
				
				$("#banner").show();
				
				$(window).scroll(function(){
					$('#banner').animate({top:$(window).scrollTop()+"px"}, {queue: false, duration: 350});
					
				});
			}
		},
		error: function(request, status, error){
			request.status = 500;
			console.log("code:"+request.status+"\n"+"message:"+request.responseText.substr(0, 10)+"...\n"+"error:"+error);
		}
	});
	return ret;
}

// Paging 및 단백질 조회 Ajax call 하는 함수
function getDesyncInterpromessageFromServer(ipr_ids, radioData) {
	var pagingSize = $("#selectPagingSize").val();

	// To get counts of protein and compute to make a paging system
	getPagingSizeByAjax( ipr_ids, radioData, pagingSize );

//	// To get proteins by ipr terms
//	getProteinDomainsAjax( ipr_ids, radioData, 1, pagingSize );
}


// 다시 조회를 위한 화면 초기화 함수
function clearInit() {
	$("#viewerPanel").empty();
	$("#banner2").empty();
	$("#allSequenceDownloadButton").detach();
	$("#allCDSDownloadButton").detach();
	// .detach() : 지정한 요소 포함 하위 요소까지 제거
	// .empty() : 지정한 요소의 하위 요소만 제거
	// .remove() : 지정한 요소를 포함한 하위 요소 모두 제거 & 요소와 관련된 이벤트와 데이터 모두 제거
}

//all version
$(document).ready(function(){
	
	relocation_loading_img();
	
	$("#Loading_Image").hide();
	
	$("#Loading_Image").ajaxStart(function(){
		$(this).show();
	}).ajaxStop(function(){
		$(this).fadeOut(100);
	});

	$("#InputButton").on("click", function() {
		clearInit();

//		IPR018105,IPR018103
//		018105,018103,011323,011057
//		IPR013783,IPR015129,IPR013098
		ipr_ids = $("#searchword").val();
		ipr_ids = ipr_ids.replace(/ /g, '');//모든 공백 제거
//		var data = searchwordTrim.replace(/,/g, "-");//모든 콤마(,)를 -로 변경
		
		radioData = $('input:radio[name="chk_info"]:checked').val();
		
		if(isEmpty(ipr_ids))
			alert("검색어를 입력해주세요.");
		else if(checkSpecialCharacter(ipr_ids)) {
			$('#searchword').val("");
			$('#searchword').focus();
		}else
			var ajaxRetObj = getDesyncInterpromessageFromServer( ipr_ids, radioData );
	});
});

