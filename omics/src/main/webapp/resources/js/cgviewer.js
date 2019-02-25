var preview_html = $("#preview").html();
var flag_success = false;
var count = 0;

$(document).ready(function() {
	var db = $("#db").val();	
	circos_view_call(db);
	
});

function circos_view_call(db){
	flag_success = false;
	var taxonomy_id = $("#taxon_id").val();
	
	$("#preview").empty();
	$("#preview").html(preview_html);
	progress_circos_data();
	
//	change_tab(tab_number);

	$.ajax({
		
		url : "circos_full",
		dataType: "json",
		type : "get",
		data : {"db":db, "taxonomy_id" : taxonomy_id},
		success : function(getData) {
			flag_success = true;
			count = 0;
			
			//circos데이터가 있는 경우와 없는 경우
			if(getData.sgv == 0){
				no_circos_data();			
			}else{			
				search_btn_able();
				
				setTimeout(function() {	
					var path =  getData.sgv;
					$("#sgv_value").empty();
					$("#sgv_value").html("<img src=\""+path+"\" />");
//					$("#sgv_value_"+tab_number).html(path);
					$("#preview").empty();
					$("#preview").text(getData.length + '/' + getData.length + ' satisfy filter(s)');
				}, 1000);
			}

		},
		error : function(e){
			flag_success = true;
			count = 0;
			no_circos_data();
			$("[id=texts]").text("Failed to data load.");  	
			$('#setup').show();	
		}
	});
}

function check_position(tab_number){
	
	var num_regx=/^[0-9]*$/;
	var selected_option = $('#search_type').val();
	if(selected_option == 'position'){
		if ($('#query').val() == "") {
			alert("Please, enter position");
			$('#query').focus();
			return false;
		}
		if(!numCheck($('#query').val())){
			alert("Position must be only numbers.");
			$('#query').val('');
			$('#query').focus();
			return false;
		}	
	}
	progress_circos_data();

	setTimeout(function() {	
		zoom(tab_number);
	}, 1000);	

}

function numCheck(strValue){

	var regExp = /^[0-9]*$/;
	if(strValue.lenght == 0) {return false;}
	if (!strValue.match(regExp)){return false;}
	return true;
} 

function zoom(tab_number){
	
	$.ajax({
		type : 'GET',
		data : {"zoom":$("#zoom_in").val(), "type":$("#search_type").val(), "query":$("#query").val(),"db":$("#db").val(), "taxonomy_id":$("#taxon_id").val()},
		contentType: "application/json",
		dataType: "text",
		url : 'circos_zoom',
		async : false,
		cache : false,	
		success : function(data) {
			
			$("#sgv_value").html(data);	
			$("#preview").text($("#query").val() + '/' + data.length + ' satisfy filter(s)');
		},
		error : function(request, status, error) {

			if (request.status != '0') {
				alert("code : " + request.status + "\r\nmessage : "
						+ request.reponseText + "\r\nerror : " + error);
			}
		}
	});
}

function auto_zoom(strand, tab_number){
	
	$.ajax({
		url : "auto_circos_zoom",
		dataType: "json",
		type : "get", 
		data : {"strand": strand, "auto_position":$("#auto_position").val(),
			"total_length":$("#total_length").val(),
			"db":$("#db").val(),
			"taxonomy_id":$("#taxon_id").val()
		},
		success : function(data) {	
			$("#sgv_value_"+tab_number).html(data.sgv);
			$("#auto_position").val(data.auto_position);
			$("#total_length").val(data.total_length);
			$("#preview").text(data.auto_position + '/' + data.total_length + ' satisfy filter(s)');
		},
		error : function(request, status, error) {
			if (request.status != '0') {
				alert("code : " + request.status + "\r\nmessage : "
						+ request.reponseText + "\r\nerror : " + error);
			}
		}
	});
}

function rangeEvt(){
	 $("#range").change( function(e){
	        $("#valBox").html($(this).val());
	 });
}

function progress (type, tab_number){
	
	progress_circos_data();

	if(type=="pre" || type == "nex"){
		setTimeout(function() {	
			auto_zoom(type, tab_number);
		}, 1000);
	}else{
		setTimeout(function() {	
			genome_viewer_refresh();
		}, 1000);	
	}
}

function changeAssemblyResult(taxon_id, refSeqAssemblyID){
	$.ajax({
		url : "getChangeAssemblyReport",
		type : "POST",
		async : true,
		dataType : "json",
		data :{taxon_id : taxon_id, refSeqAssemblyID : refSeqAssemblyID},
		success : function(data){	
			$('#assembly_genomeSize').html(data.fileSize);
			$('#assembly_status').html(data.assemblyLevel);
			$('#assembly_scaffoldNumber').html(data.taxId);
			$('#assembly_releaseDate').html(data.depositDate);
			$('#annotation_latestVersion').html(data.refSeqAssemblyID);
			$('#annotation_geneNumber').html(data.assemblyName);
			$('#annotation_releaseDate').html(data.depositDate);
		}
	});
}

function no_circos_data(){
	$("#sgv_value").html("<br/><br/><br/><strong>There is No data.</strong>");
	$("#preview").text("No data");
	$("#searchbtn").attr("disabled","true");
	$("#prev_btn").attr("disabled","true");
	$("#refresh_btn").attr("disabled","true");
	$("#next_btn").attr("disabled","true");

}

function progress_circos_data(){
	if(flag_success==false){
		$("#preview").empty();
		$("#preview").html(preview_html);
		$("#sgv_value").html("<br/><br/><div class=\"load2\"></div>" +
				"<br/><strong>This is being processed.." + count*10 + " %</strong>" +
				"<br/>Please wait...");
	
		if(count <= 10){
			setTimeout(function(){progress_circos_data();}, 20000);
			count++;
		}else{
			flag_success = true;
			count = 0;
			no_circos_data();
			$("[id=texts]").text("Failed to data load.");  	
			$('#setup').show();
		}
	}
}

function search_btn_able(){
	$("#searchbtn").removeAttr("disabled");
	$("#prev_btn").removeAttr("disabled");
	$("#refresh_btn").removeAttr("disabled");
	$("#next_btn").removeAttr("disabled");
}

function change_tab(tab) {
	if (tab == 1) {		
		$("#div_tab_1").css("display", "block");
		$("#div_tab_2").css("display", "none");
		$("#div_tab_3").css("display", "none");
		document.getElementById("tab_cont1").className = "on";
		document.getElementById("tab_cont2").className = "off";
		document.getElementById("tab_cont3").className = "off";
	}
	if (tab == 2) {	
		$("#div_tab_1").css("display", "none");
		$("#div_tab_2").css("display", "block");
		$("#div_tab_3").css("display", "none");
		document.getElementById("tab_cont1").className = "off";
		document.getElementById("tab_cont2").className = "on";
		document.getElementById("tab_cont3").className = "off";
	}
	if (tab == 3) {
		$("#div_tab_1").css("display", "none");
		$("#div_tab_2").css("display", "none");
		$("#div_tab_3").css("display", "block");
		document.getElementById("tab_cont1").className = "off";
		document.getElementById("tab_cont2").className = "off";
		document.getElementById("tab_cont3").className = "on";
	}	
}