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

function open_dialog(input_id){
	
	select_input_id = input_id;
	$('#dialog').dialog('open');
	
	dialog_path_request(input_id);
}

function open_output_dialog(file_path, size){

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
				
				if(data.res.length < 1 ){
					$('#dialog_output_contents').empty();
					$('#dialog_output_contents').text("No Data");
				}else{
					$('#dialog_output_contents').empty();
					$('#dialog_output_contents').text(data.res);
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

function dialog_open_output_dialog(file_path, size){

	var file_size = size.split(" ")[0];
	var file_unit = size.split(" ")[1];
	
	if(file_unit == "kB" || file_unit == "B" || (file_unit == "MB" && file_size < 3)){
		
		var output_path = $("#dialog_select_data").text()+"/"+file_path;
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
				
				if(data.res.length < 1 ){
					$('#dialog_output_contents').empty();
					$('#dialog_output_contents').text("No Data");
				}else{
					$('#dialog_output_contents').empty();
					$('#dialog_output_contents').text(data.res);
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

function dialog_parent_path_request(){

	var path = $("input[name=dialog_p_path]:hidden").val();
	
	$.ajax({
		
		url : "get_file_list",
		dataType: "json",
		type : "get",
		data : {"path": path},
		
		success : function(data) {
			
			if(data.list_data.length == 0){
				alert('Data could not be retrieved. This is root path.');
			}else{
				
				$("#dialog_select_data").text(data.cPath);			
				$("#dialog_explorer_tbody").empty();
				$('input[name="dialog_p_path"]').val(data.pPath);
				
				$(data.list_data).each(function(index, item) {		 				
		 			
					var img;
		 			var tmp = item.name.split('^');
		 			var name = tmp[0];
		 			var size = tmp[1];
		 			
		 			if(item.isFile == true){
		 				img = '<img src=\"resources/images/sub/file.png\" alt=\"file\"/>';
		 				$('#dialog_explorer_tbody').append(
	 	 					'<tr>' + 
	 	 						'<td><input type="checkbox" id = "file '+size+'" name="dialog_path[]" value=' + "'" + item.HPath + "'" +'/></td>'+
	 	 						'<td class=\"txtL\"><a onclick=\"dialog_open_output_dialog(\''+name+'\',\''+size+'\')\">' + img + name + '</a></td>' + 
	 	 						'<td>' + size + '</td>' + 
	 	 						'<td>' + item.createDate + '</td>' +
	 						'</tr>'
	 		 			);
		 			}else{
		 				img = '<img src=\"resources/images/sub/folder.png\" alt=\"folder\"/>';
		 				$('#dialog_explorer_tbody').append(
	 	 					'<tr>' + 
	 	 						'<td><input type="checkbox" id = "folder '+size+'" name="dialog_path[]" value=' + "'" + item.HPath + "'" +'/></td>'+
	 	 						'<td class=\"txtL\">' + img + '<a style=\"cursor: pointer;\" onclick=\"dialog_path_request(' + "'" + item.HPath + "'" +');\">' + name + '</a></td>' + 
	 	 						'<td>' + size + '</td>' + 
	 	 						'<td>' + item.createDate + '</td>' +
	 						'</tr>'
	 		 			);
		 			}
				}); 
				
				$("#dialog_select_data").val(data.list);
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

function path_request(path){

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
		 	 						'<td class=\"txtL\"><a onclick=\"open_output_dialog(\''+name+'\',\''+size+'\')\">' + img + name + '</a></td>' + 
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
	 	 						'<td class=\"txtL\">' + img + '<a style=\"cursor: pointer;\" onclick=\"path_request(' + "'" + item.HPath + "'" +');\">' + name + '</a></td>' + 
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

function dialog_path_request(path){

	$.ajax({

		url : "get_file_list",
		dataType: "json",
		type : "get",
		data : {"path": path},
		
		success : function(data) {

			if(data.list_data.length == 0){
			
				if(data.cPath != 'null'){
					$("#dialog_select_data").text(data.cPath);
					$("#dialog_explorer_tbody").empty();
					$("#dialog_explorer_tbody").append(
	 	 					'<tr>' +
	 	 						'<td colspan=\"4\" class=\"txtC\">Data could not be retrieved.</td>' +
	 						'</tr>'
	 		 			);
					$('input[name="dialog_p_path"]').val(data.p_path);
				}else{
					alert('Data could not be retrieved. This is root path.');
				}
				
			}else{
				
				$("#dialog_select_data").text(data.cPath);
				$("#dialog_explorer_tbody").empty();
				$('input[name="dialog_p_path"]').val(data.pPath);
				
				$(data.list_data).each(function(index, item) {		 				
		 			
		 			var img;
		 			var tmp = item.name.split('^');
		 			var name = tmp[0];
		 			var size = tmp[1];
		 			
		 			if(item.isFile == true){
		 				img = '<img src=\"resources/images/sub/file.png\" alt=\"file\"/>';
		 				
		 				if(name.endsWith(".tsv")){
		 					$('#dialog_explorer_tbody').append(
			 	 					'<tr>' + 
			 	 						'<td><input type="checkbox" id = "file '+size+'" name="dialog_path[]" value=' + "'" + item.HPath + "'" +'/></td>'+
			 	 						'<td class=\"txtL\"><a href=\"interproAnalysis?path='+item.HPath+'\">' + img + name + '</a></td>' + 
			 	 						'<td>' + size + '</td>' + 
			 	 						'<td>' + item.createDate + '</td>' +
			 						'</tr>'
			 		 			);
		 				}else{
			 				$('#dialog_explorer_tbody').append(
		 	 					'<tr>' + 
		 	 						'<td><input type="checkbox" id = "file '+size+'" name="dialog_path[]" value=' + "'" + item.HPath + "'" +'/></td>'+
		 	 						'<td class=\"txtL\"><a onclick=\"dialog_open_output_dialog(\''+name+'\',\''+size+'\')\">' + img + name + '</a></td>' + 
		 	 						'<td>' + size + '</td>' + 
		 	 						'<td>' + item.createDate + '</td>' +
		 						'</tr>'
		 		 			);
		 				}
		 			}else{
		 				img = '<img src=\"resources/images/sub/folder.png\" alt=\"folder\"/>';
		 				$('#dialog_explorer_tbody').append(
	 	 					'<tr>' + 
	 	 						'<td><input type="checkbox" id = "folder '+size+'" name="dialog_path[]" value=' + "'" + item.HPath + "'" +'/></td>'+
	 	 						'<td class=\"txtL\">' + img + '<a style=\"cursor: pointer;\" onclick=\"dialog_path_request(' + "'" + item.HPath + "'" +');\">' + name + '</a></td>' + 
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
		
		arr.push($(this).val().replace("'","") + "^" + $(this).attr('id').replace("file", ""));
    });
	
	if(arr.length == 0){
		alert("Choose the file to download.")
	}else{
		if(arr.length == 1){
			
			if((is_small_file_check(arr[0].split("^")[1])) && (arr[0].split("^")[0].indexOf("png") == -1)){	
				location.href="do_smallSizeFile_download?path=" + arr[0].split("^")[0];  
			}else{

				$.ajax({
					url : "do_file_transfer",
					dataType: "json",
					type : "get",
					data : {"path": arr[0].split("^")[0]},
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
			}
		}else{
			alert("Choose Only one file")
		}
	}
}

function dialog_file_transfer(){
	var arr = new Array();
	
	$('[name^=dialog_path[]]:checked').each(function () {
		
		arr.push($(this).val().replace("'","") + "^" + $(this).attr('id').replace("file", ""));
    });
	
	if(arr.length == 0){
		alert("Choose the file to download.")
	}else{
		if(arr.length == 1){
			
			if(is_small_file_check(arr[0].split("^")[1])&& (arr[0].split("^")[0].indexOf("png") == -1)){	
				location.href="do_smallSizeFile_download?path=" + arr[0].split("^")[0];  
			}else{

				$.ajax({
					url : "do_file_transfer",
					dataType: "json",
					type : "get",
					data : {"path": arr[0].split("^")[0]},
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
			}
		}else{
			alert("Choose Only one file")
		}
	}
}

function is_small_file_check(file_size_value){
	
	file_size_value = file_size_value.trim();
	
	var file_size = file_size_value.split(" ")[0];
	var file_unit = file_size_value.split(" ")[1];
	
	if(file_unit == "kB" || file_unit == "B" || (file_unit == "MB" && file_size < 1)){

		return true
	}else{
		
		return false;
	}
}

function checkbox_reset(){
	$('input:checkbox').removeAttr('checked');
}