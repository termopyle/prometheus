function appendnodes(node){
	
	if (node){
		var nodes = [{
			"id":11,
			"text":"Acanthocephala|구두동물문"
		},{
			"id":12,
			"text":"Echinodermata|극피동물문"
		},{
			"id":13,
			"text":"Annelida|환형동물문"
		}];

		$('#tt').tree('append', {
			parent:node.target,
			data:nodes
		});
	}
}

function onclickEvent(){
	$('#tt').tree({
		onClick: function(node){
			alert(node.text);
		}
	});
}


function replaceAll(str, searchStr, replaceStr) {

    return str.split(searchStr).join(replaceStr);
}

function update(id, type, name, rank){

	//tree update 초기화
	$("#refSeqAssemblyID").html("");
	$("#assemblyName").html("");
	$("#assemblyLevel").html("");
	$("#assemblyType").html("");
	$("#taxId").html("");
	$("#fileSize").html("");
	$("#geneBankID").html("");
	$("#releaseDate").html("");
	$("#species_name").html("");
	$("#btn_taxnomy").attr("class","btn-off");	
	$("#ref_seq").attr("class","btn-off");	
	$("#ensembl").attr("class","btn-off");	
	$("#rawdata").attr("class","btn-off");	
	
	var node = $('#' + name).tree('getSelected');
	var child_node = $('#' + name).tree('getChildren', node.target);
	
	var keyword = getHttpParam("keyword");

	if(child_node.length == 0){
		$.ajax({
			type : 'POST',
			data : "position=0",
			contentType: "application/json",
			dataType: "text",
			url : 'taxonomy_update.do?id=' + id + '&type=' + type + '&rank=' + rank + '&keyword=' + keyword,
			async : false,
			cache : false,	
			success : function(data) {
				var stringData = data.replace("{\"data\":[","");
				stringData = stringData.replace(/[\{\}\[\]\/]/gi,"");

				//text 형태로 넘어오는 데이터를 json 으로 변환 후 데이터 변경
				if(data.length > 0){
					
					stringData = "{"+stringData+"}"
					var getData = JSON.parse(stringData);
					var existData = JSON.parse(data);
					
					if(getData.db){
//					$("#refSeqAssemblyID").html(getData.refSeqAssemblyID);
//					$("#assemblyName").html(getData.assemblyName);
//					$("#assemblyLevel").html(getData.assemblyLevel);
//					$("#assemblyType").html(getData.assemblyType);
//					$("#taxId").html(getData.taxId);
//					$("#fileSize").html(getData.fileSize);
//					$("#geneBankID").html(getData.genBankAssemblyID);
//					$("#releaseDate").html(getData.releaseDate);
//					$("#hiddenData").html("<input type='hidden' id='organism' value="+getData.organism+" />");
//					$("#species_name").html(getData.organism);
//					$("#btn_taxnomy").attr("class","btn-on");

					$("#refSeqAssemblyID").html(existData.data[0].refSeqAssemblyID);
					$("#assemblyName").html(existData.data[0].assemblyName);
					$("#assemblyLevel").html(existData.data[0].assemblyLevel);
					$("#assemblyType").html(existData.data[0].assemblyType);
					$("#taxId").html(existData.data[0].taxId);
					$("#fileSize").html(existData.data[0].fileSize);
					$("#geneBankID").html(existData.data[0].genBankAssemblyID);
					$("#releaseDate").html(existData.data[0].releaseDate);
					$("#hiddenData").html("<input type='hidden' id='organism' value="+existData.data[0].organism+" />");
					$("#species_name").html(existData.data[0].organism);
					$("#btn_taxnomy").attr("class","btn-on");
					
						

						for(var i=0; i<existData.data.length; i++){
							$("#"+existData.data[i].db).attr("class","btn-on");
						}
					}

					
					
				}else{
				//넘어오는 데이터가 없을 경우, 내용 제거					
					$("#refSeqAssemblyID").html("");
					$("#assemblyName").html("");
					$("#assemblyLevel").html("");
					$("#assemblyType").html("");
					$("#taxId").html("");
					$("#fileSize").html("");
					$("#geneBankID").html("");
					$("#releaseDate").html("");
					$("#species_name").html("");
					$("#btn_taxnomy").attr("class","btn-off");	
					$("#ref_seq").attr("class","btn-off");
					$("#ensembl").attr("class","btn-off");	
					$("#rawdata").attr("class","btn-off");	
				}
				
				//검색을 통하여 keyword가 존재할 경우에만 트리 추가
				if(keyword == null || keyword == ""){

					var node = $('#' + name).tree('getSelected');
					if (node){
						//String 형태의 데이터값을 JSON Object 형태로 parse
						var nodes = JSON.parse(data);

							$('#' + name).tree('append', {
								parent:node.target,
								data:nodes
							});					
					}
				}
			},
			error : function(request, status, error) {

				if (request.status != '0') {
					alert("code : " + request.status + "\r\nmessage : "
							+ request.reponseText + "\r\nerror : " + error);
				}
			}
		});
	}	
}

//Taxonomy Tree Scroll 마우스오버/아웃 
//type 으로 받은 인자값 별로 overflow="scroll"로 스크롤 생성
function scroll_over(type){
	
	if(type=="tb"){
		document.getElementById('tb').style.overflow="auto";
	}
}

//해당 ta,tb,tc,td 영역 마우스 아웃시, overflow = "hidden"
function scroll_out(type){
	if(type=="tb"){document.getElementById('tb').style.overflow="hidden";}
}

//파라미터 값 추출
function getHttpParam(name) {
    var regexS = "[\\?&]" + name + "=([^&#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(window.location.href);
    if (results == null) {
        return "";
    } else {
        return results[1];
    }
}