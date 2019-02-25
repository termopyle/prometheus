/**
 * open window HDFS explorer
 */

var explorer_win;

/*function openHDFSExplorer(input_id){
	
	explorer_win = window.open('open_explorer_window?input_id=' + input_id, 'HDSF Explorer',
			'height=400,width=600, top=30, left=300, scrollbars=yes, resizable=yes');
	
	explorer_win.window.focus();
}
*/
/*function setValue(input_id, value){
	explorer_win.close();
	var find_id = "#" + input_id
	$(find_id).val(value);
}*/

/**
 * open HDFS select dialog
 */

var select_input_id;

function open_dialog(input_id, id){
	
	select_input_id = input_id;
	$('#dialog').dialog('open');
	
	path_request(input_id, id);
}

function open_output_dialog(file_path, size, id){
	console.log(id);
	var file_size = size.split(" ")[0];
	var file_unit = size.split(" ")[1];
	
	if(file_unit == "kB" || file_unit == "B" || (file_unit == "MB" && file_size < 3)){
		
		var output_path = $("#select_data").text()+"/"+file_path;
		$('#output_dialog').dialog('open');
		$('#output_dialog').dialog('option','title',file_path);
		
		$('#dialog_output_contents').empty();
		$('#dialog_output_contents').text("Loading..");

		$.ajax({
			url : "do_mypage_file_read",
			dataType: "json",
			type : "get",
			data : {"output_path": output_path},
			success : function(data) {
				if (id == 1){
					if(data.res.length < 1 ){
						$('#input1').empty();
						$('#input1').val("No Data");
					}else{				
						$('#input1').empty();
						$('#input1').val(data.res);
						$('#dialog').dialog('close');	
					}	
				}else if (id == 2){
					if(data.res.length < 1 ){
						$('#input3').empty();
						$('#input3').val("No Data");
					}else{				
						$('#input3').empty();
						$('#input3').val(data.res);
						$('#dialog').dialog('close');	
					}
				}else if (id == 3){
					if(data.res.length < 1 ){
						$('#input_blast').empty();
						$('#input_blast').val("No Data");
					}else{				
						$('#input_blast').empty();
						$('#input_blast').val(data.res);
						$('#dialog').dialog('close');	
					}
				}else if (id == 4){
					if(data.res.length < 1 ){
						$('#input_interpro').empty();
						$('#input_interpro').val("No Data");
					}else{				
						$('#input_interpro').empty();
						$('#input_interpro').val(data.res);
						$('#dialog').dialog('close');	
					}
				}else if (id == 5){
					if(data.res.length < 1 ){
						$('#input_clustalomega').empty();
						$('#input_clustalomega').val("No Data");
					}else{				
						$('#input_clustalomega').empty();
						$('#input_clustalomega').val(data.res);
						$('#dialog').dialog('close');	
					}
				}else if (id == 6){
					if(data.res.length < 1 ){
						$('#input_muscle').empty();
						$('#input_muscle').val("No Data");
					}else{				
						$('#input_muscle').empty();
						$('#input_muscle').val(data.res);
						$('#dialog').dialog('close');	
					}
				}else if (id == 8){
					if(data.res.length < 1 ){
						$('#newick').empty();
						$('#newick').val("No Data");
					}else{				
						$('#newick').empty();
						$('#newick').val(data.res);
						$('#dialog').dialog('close');	
					}
				}
				

			},
			error : function(e){
				alert("Failed data load!");
			}
		});
		
		
	}else{
		alert("You can check the file that larger than 3MB by download");
	}

	var output_path = $("#select_data").text()+"/"+file_path;	
}

$(function() {
	
	var path = $("#select_data");
	allFields = $([]).add(path);
	
	$("#dialog").dialog({
		bgiframe: true,
		autoOpen: false,
		width: 900,
		height: 700,
		modal: true,
		buttons: {
			"Close": function() {
				$(this).dialog('close');
			}
		},
		close: function() {
			allFields.val('').removeClass('ui-state-error');
		}
	});

	$("#output_dialog").dialog({
		bgiframe: true,
		autoOpen: false,
		width: 800,
		height: 500,
		modal: true,
		buttons: {
			"Close": function() {
				$(this).dialog('close');
			}
		},
		close: function() {
			allFields.val('').removeClass('ui-state-error');
		}
	});
	
});
function parent_path_request(){

	var path = $("input[name=p_path]:hidden").val();
	
	$.ajax({
		
		url : "get_file_list",
		dataType: "json",
		type : "get",
		data : {"path": path},
		
		success : function(data) {
			
			if(data.list_data.length == 0){
				alert('Data could not be retrieved. This is root path.');
			}else{
				
				$("#select_data").text(data.cPath);			
				$("#explorer_tbody").empty();
				$('input[name="p_path"]').val(data.pPath);
				
				$(data.list_data).each(function(index, item) {		 				
		 			
					var img;
		 			var tmp = item.name.split('^');
		 			var name = tmp[0];
		 			var size = tmp[1];
		 			
		 			if(item.isFile == true){
		 				img = '<img src=\"resources/images/sub/file.png\" alt=\"file\"/>';
		 				$('#explorer_tbody').append(
	 	 					'<tr>' + 
	 	 						'<td><input type="checkbox" id = "file '+size+'" name="path[]" value=' + "'" + item.HPath + "'" +'/></td>'+
	 	 						'<td class=\"txtL\"><a onclick=\"open_output_dialog(\''+name+'\',\''+size+'\')\">' + img + name + '</a></td>' + 
	 	 						'<td>' + size + '</td>' + 
	 	 						'<td>' + item.createDate + '</td>' +
	 						'</tr>'
	 		 			);
		 			}else{
		 				img = '<img src=\"resources/images/sub/folder.png\" alt=\"folder\"/>';
		 				$('#explorer_tbody').append(
	 	 					'<tr>' + 
	 	 						'<td><input type="checkbox" id = "folder '+size+'" name="path[]" value=' + "'" + item.HPath + "'" +'/></td>'+
	 	 						'<td class=\"txtL\">' + img + '<a style=\"cursor: pointer;\" onclick=\"path_request(' + "'" + item.HPath + "'" +');\">' + name + '</a></td>' + 
	 	 						'<td>' + size + '</td>' + 
	 	 						'<td>' + item.createDate + '</td>' +
	 						'</tr>'
	 		 			);
		 			}
				}); 
				
				$("#select_data").val(data.list);
				$(this).dialog('close');
			}
		},
		error : function(e){
			alert("Failed data load!");
		}
	});
}

String.prototype.endsWith = function(str){
	
	if(this.length < str.length){
		
		return false;
	}
	
	return this.lastIndexOf(str) + str.length == this.length;
}

function path_request(path, id){
	$.ajax({

		url : "get_file_list",
		dataType: "json",
		type : "get",
		data : {"path": path},
		
		success : function(data) {

			if(data.list_data.length == 0){
			
				if(data.cPath != 'null'){
					$("#select_data").text(data.cPath);
					$("#explorer_tbody").empty();
					$("#explorer_tbody").append(
	 	 					'<tr>' +
	 	 						'<td colspan=\"4\" class=\"txtC\">Data could not be retrieved.</td>' +
	 						'</tr>'
	 		 			);
					$('input[name="p_path"]').val(data.p_path);
				}else{
					alert('Data could not be retrieved. This is root path.');
				}
				
			}else{
				
				$("#select_data").text(data.cPath);
				$("#explorer_tbody").empty();
				$('input[name="p_path"]').val(data.pPath);
				
				$(data.list_data).each(function(index, item) {		 				
		 			
		 			var img;
		 			var tmp = item.name.split('^');
		 			var name = tmp[0];
		 			var size = tmp[1];
		 			
		 			if(item.isFile == true){
		 				img = '<img src=\"resources/images/sub/file.png\" alt=\"file\"/>';
		 				
		 				if(name.endsWith(".tsv")){
		 					$('#explorer_tbody').append(
			 	 					'<tr>' + 
			 	 						'<td><input type="checkbox" id = "file '+size+'" name="path[]" value=' + "'" + item.HPath + "'" +'/></td>'+
			 	 						'<td class=\"txtL\"><a href=\"interproAnalysis?path='+item.HPath+'\">' + img + name + '</a></td>' + 
			 	 						'<td>' + size + '</td>' + 
			 	 						'<td>' + item.createDate + '</td>' +
			 						'</tr>'
			 		 			);
		 				}else{
			 				$('#explorer_tbody').append(
		 	 					'<tr>' + 
		 	 						'<td><input type="checkbox" id = "file '+size+'" name="path[]" value=' + "'" + item.HPath + "'" +'/></td>'+
		 	 						'<td class=\"txtL\"><a onclick=\"open_output_dialog(\''+name+'\',\''+size+'\','+id+')\">' + img + name + '</a></td>' + 
		 	 						'<td>' + size + '</td>' + 
		 	 						'<td>' + item.createDate + '</td>' +
		 						'</tr>'
		 		 			);
		 				}
		 			}else{
		 				img = '<img src=\"resources/images/sub/folder.png\" alt=\"folder\"/>';
		 				$('#explorer_tbody').append(
	 	 					'<tr>' + 
	 	 						'<td><input type="checkbox" id = "folder '+size+'" name="path[]" value=' + "'" + item.HPath + "'" +'/></td>'+
	 	 						'<td class=\"txtL\">' + img + '<a style=\"cursor: pointer;\" onclick=\"path_request(' + "'" + item.HPath + "','" + id + "'" +');\">' + name + '</a></td>' + 
	 	 						'<td>' + size + '</td>' + 
	 	 						'<td>' + item.createDate + '</td>' +
	 						'</tr>'
	 		 			);
		 			}
				}); 

				$(this).dialog('close');
			}			
		},
		error : function(e){
			alert("Failed data load!");
		}
	});
}

function file_transfer(){
	
	var arr = new Array();
	
	$('[name^=path[]]:checked').each(function () {
		arr.push($(this).val().replace("'",""));
    });
	
	if(arr.length == 0){
		alert("Choose the file to download.")
	}else{
		if(arr.length == 1){
						
			$.ajax({
				url : "do_file_transfer",
				dataType: "json",
				type : "get",
				data : {"path": arr[0]},
				success : function(data) {
					if(data.res == "true"){
						alert(data.res_path + " you can download file using KoDS software.");
						checkbox_reset();
					}
				},
				error : function(e){
					alert("Data could not be loaded.");
				}
			});
			
		}else{
			alert("Choose Only one file")
		}
	}
}

$(document).ready(function(){
	var inputTypeNum = document.getElementById("input_type_id").value;
	var inputSubNum = document.getElementById("input_sub_type_id").value;
	con_analysis_group_tab(inputTypeNum);
	con_tab(inputSubNum)
});