var email = "";
var pipeline_id ="" ;
var title ="";

//Analysis form 값 출력하기 및 체크
function analysis_form_check(form_name){
	var result = "";
	var formName = document.getElementById(form_name);
	
	 for (var i=0; i<formName.elements.length; i++) {
		 if(formName.elements[i].type == "radio" && formName.elements[i].checked){
			 	//라디오버튼인 경우, checked 된 것만 result 에 추가 및 form 데이터 전송
				 result += formName.elements[i].name + "$" + formName.elements[i].value + "#";
		 }else if(formName.elements[i].type != "button" &&
				 formName.elements[i].type != "submit" &&
				 formName.elements[i].type != null && 
				 formName.elements[i].value != "undefined" &&
				 formName.elements[i].type != "radio" &&
				 formName.elements[i].type != "hidden"){
			 
			 	//그 외의 라디오버튼울 제외한 데이터를  result 에 추가 및 form 데이터 전송
			 	result += formName.elements[i].name + "$" + formName.elements[i].value + "#";
		 }
	}

	//유효성 체크
	if(form_name == "interpro_form"){
		if (document.interpro_form.input_interpro.value == "" || document.interpro_form.input_interpro.value == "FASTA") {
			$("[id=texts]").text("FASTA");  	
			$('#setup').show();				
			return;
		} else if(document.interpro_form.input_interpro.value.match(/>/g).length > 1) {
			$("[id=texts]").text("Please enter only one sample."); 
			$('#setup').show();				
			return;
		} else if($("#"+form_name + " input:checkbox[name=notification_interpro]").is(":checked")){
			if (document.interpro_form.email_address.value == "") {
				$("[id=texts]").text("Enter the project email.");  	
				$('#setup').show();	
				return;
			}else if (!emailCheck(document.interpro_form.email_address.value)) {
				$("[id=texts]").text("Please check the project email.");  	
				$('#setup').show();	
				return;
			}else if (document.interpro_form.project_title.value == "") {
				$("[id=texts]").text("Enter the project title.");  	
				$('#setup').show();	
				return;
			}
		}
	}
	else if(form_name == "last_form"){

		if (document.last_form.input1.value == "" || document.last_form.input1.value == "FASTA") {
			$("[id=texts]").text("Enter the reference sequence.");  	
			$('#setup').show();	
			return;
		} else if (document.last_form.input3.value == "" || document.last_form.input3.value == "FASTA") {
			$("[id=texts]").text("Enter the query sequence.");  	
			$('#setup').show();	
			return;
		} else if($("#"+form_name + " input:checkbox[name=notification_last]").is(":checked")){
			
			if (document.last_form.email_address.value == "") {
				$("[id=texts]").text("Enter the project email.");  	
				$('#setup').show();	
				return;
			}else if (!emailCheck(document.last_form.email_address.value)) {
				$("[id=texts]").text("Please check the project email.");  	
				$('#setup').show();	
				return;
			}else if (document.last_form.project_title.value == "") {
				$("[id=texts]").text("Enter the project title.");  	
				$('#setup').show();	
				return;
			}
		}
	}else if(form_name == "clustalomega_form"){
		if (document.clustalomega_form.input_clustalomega.value == "" || document.clustalomega_form.input_clustalomega.value == "FASTA") {
			$("[id=texts]").text("FASTA");  	
			$('#setup').show();				
			return;
		}else if($("#"+form_name + " input:checkbox[name=notification_clustalomega]").is(":checked")){
			if (document.clustalomega_form.email_address.value == "") {
				$("[id=texts]").text("Enter the project email.");  	
				$('#setup').show();	
				return;
			}else if (!emailCheck(document.clustalomega_form.email_address.value)) {
				$("[id=texts]").text("Please check the project email.");  	
				$('#setup').show();	
				return;
			}else if (document.clustalomega_form.project_title.value == "") {
				$("[id=texts]").text("Enter the project title.");  	
				$('#setup').show();	
				return;
			}
		}
	}
	
	else if(form_name == "blast_form"){
		if (document.blast_form.input_blast.value == "" || document.blast_form.input_blast.value == "FASTA") {
			$("[id=texts]").text("FASTA");  	
			$('#setup').show();	
			return;
		} else if($("#"+form_name + " input:checkbox[name=notification_blast]").is(":checked")){
			
			if (document.blast_form.email_address.value == "") {
				$("[id=texts]").text("Enter the project email.");  	
				$('#setup').show();	
				return;
			}else if (!emailCheck(document.blast_form.email_address.value)) {
				$("[id=texts]").text("Please check the project email.");  	
				$('#setup').show();	
				return;
			}else if (document.blast_form.project_title.value == "") {
				$("[id=texts]").text("Enter the project title.");  	
				$('#setup').show();	
				return;
			}				
		}
	}
	
	else if(form_name == "muscle_form"){
		if (document.muscle_form.input_muscle.value == "" || document.muscle_form.input_muscle.value == "FASTA") {
			$("[id=texts]").text("FASTA");  	
			$('#setup').show();	
			return;
		} else if($("#"+form_name + " input:checkbox[name=notification_muscle]").is(":checked")){
			
			if (document.muscle_form.email_address.value == "") {
				$("[id=texts]").text("Enter the project email.");  	
				$('#setup').show();	
				return;
			}else if (!emailCheck(document.muscle_form.email_address.value)) {
				$("[id=texts]").text("Please check the project email.");  	
				$('#setup').show();	
				return;
			}else if (document.muscle_form.project_title.value == "") {
				$("[id=texts]").text("Enter the project title.");  	
				$('#setup').show();	
				return;
			}				
		}
	}

	pipeline_id = $("#"+form_name+" input[name=pipeline_id]").val();
	email =  $("#"+form_name+" input[name=email_address]").val();
	title =  $("#"+form_name+" input[name=project_title]").val();

	if(form_name == "interpro_form"){
		result += "interproscan|FILE|output$/#";
	}else if(form_name == "last_form"){
		result += "last|FILE|output$/#";
	}else if(form_name == "blast_form"){
		result += "hadoop_blast|FILE|output$/#";
	}else if(form_name=="clustalomega_form"){
		result += "clustalomega|FILE|output$/#";
	}else if(form_name=="muscle_form"){
		result += "muscle|FILE|output$/#";
	}

	$('<input>', {
	    type: 'hidden',
	    id: 'result',
	    name: 'result',
	    value: result
	}).appendTo('#'+form_name);
	
	$('#subconW').empty();
	$('#subconW').html("<div class=\"foundbox\"><div class=\"load\"><strong>This is being processed..</strong>Please wate.</div></div>");

	$.ajax({
		
		url : "doAnalysis",
		type : "POST",
		data : { 
			"result": result, 
			"email":email, 
			"pipeline_id" : pipeline_id, 
			"title":title
			},	
			
		success : function() {
			location.href="complete";
		},
		
		error : function(e){
			$("[id=texts]").text("Failed load data.");  	
			//$('#setup').show();	

		}
	});
		
	//$('#'+form_name).attr('action','doProceeding').submit();
}




//Form 초기화
function form_reset(form_name){
	var formName = document.getElementById(form_name);
	formName.reset();
	document.last_form.reset();
	$("#"+form_name+" textarea").val("");
	$("#"+form_name+" input[type=checkbox]").attr('checked',false);
}

//BLAST task change
function taskChange(){
	var select_box = document.getElementById("task");
    var selected = select_box.options[select_box.selectedIndex].value;
    var blastp_only = document.getElementById("blastp_tr");
    if(selected === 'blastp'){
    	blastp_only.style.display = "table-row";
    }
    else{
    	blastp_only.style.display = "none";
    }
}

//email 유효성 검사
function emailCheck(strValue){
	var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
	if(strValue.lenght == 0) {return false;}
	if (!strValue.match(regExp)){return false;}
	return true;
} 

//text area clear
function text_area_clear(form){
	$("#"+form.id).find("textarea").text("");
}