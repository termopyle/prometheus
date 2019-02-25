var key_point = "";	//검색창에 입력한 단어와 매칭되는 부분
var key_left = "";	//매칭되지않는 나머지 부분의 단어
var li_add = "";	//매칭되는 단어들의 배열, 이 배열의 내용들이 검색창밑에 출력 된다.
var li_id = "";
var i = 0;	
var j = 0;

var flag = 0;
var key = "";
var count = -1;
var flagR = 0;
var temp = false;
var keyCode;
var max_count = 10;
var taxon_id = "";
var hidden_taxon_id = document.createElement("input");
hidden_taxon_id.type ="hidden";
hidden_taxon_id.id="taxon_id";
hidden_taxon_id.name="taxon_id";


$(document).keydown(function(event) {
	 keyCode = event.keyCode;
});

$(function() {
	
    $("input:text").keydown(function(evt) {	// 엔터로 검색
        if (evt.keyCode == 13){
        
        	key = document.getElementById("keyword").value;      
        	$( "#search_form" ).submit();       	    
        	writeGeneName(key);
        	outFocus();
            return false;
        }
    });
    
    $( "#searchbtn" ).click(function() {	//검색 버튼 클릭 시
    	key = document.getElementById("keyword").value;  
    	$( "#search_form" ).submit();   
    	writeGeneName(key);
    	outFocus();
    	
    });
});

function writeGeneName(text){
	
	if(text.length > 0 ){
		
		if(keyCode == '8'){
			flag = 0;
			flagR = 0;
		}
	
		//위,아래 방향키를 제외한 이벤트일때만 key 값 변경
		if(keyCode != 40 && keyCode != 38){
			key = document.getElementById("keyword").value;
		}	
		$.ajax({
			url : 'keyRecommend.do',
			type : 'POST',
			dataType : 'text',
			data : "key=" + key,
			success : function(s) {
				
				key_point = "";
				key_left = "";
				li_add = "";
				count = 0;
				
				var data = s.split(',');				      
				data.sort();
	
				document.getElementById("searchFrame").innerHTML = "<ul id='search_ul'>" +
						"</ul><div class=\"searchBoxClose\" " +
						"id=\"searchClose\" onclick=\"outFocus()\">CLOSE</div>";	
				if(key.length > 0 ){
					
					$("#searchFrame").css("display","block");
					
					for(i = 0; i < data.length; i++){

						var data_0 = data[i].split("\t")[0];	//keyword
						//var data_1 = data[i].split("\t")[1].replace( /(\s*)/g, "");	//taxon_id
						var data_1 = data[i].split("\t")[1];
						
						$("#search_form").append(hidden_taxon_id);
						key_point = data_0.substring(0, key.length);	// key_point = data 문자열중, key.length 만큼의 문자열
						key_left  = data_0.substring(key.length, data_0.length);	//key_left = data 문자열중, key를 제외한 나머지부분
									
						
						/*$("#search_ul").append("<li onclick='get_search_text(this.id)'>"+data_0+"</li>");
						count ++;
						*/
						//대소문자 구별없이 비교해서 key 와 key_point 가 맞으면 count 증가, li 추가
						if(key.toLowerCase() == key_point.toLowerCase()){
							
							li_add[i] = data_0;
							li_id[i] = data_1;

							$("#search_ul").append("<li onclick='get_search_text(this.id, "+data_1+")' id='"+data_0+"' name='"+data_1+"'>"+"<span>"+key_point+"</span>"+ key_left+"</li>");
							
							if(count == 0){	//연관검색어리스트에 최 상위 id 값을 taxon_id로 넘긴다
								hidden_taxon_id.value = data_1;													
							}
							
							count++;					
						}
						if(count > max_count){
							break;
						}
					}

				}

				if(count >= 1){
					$("#searchClose").css("display","block");
				}else{
					$("#searchClose").css("display","none");
				}
				if(flag > 0 ){
					flagR = flag - 1;
				}

				if(keyCode == '38'){ //위
	
					if(flag <= 0){
						flag = count+1;
					}
					flag --;
					
					if(flag > 0){	
						flagR = flag - 1;
					}
					temp = true;
	
					$("#search_ul > li:eq("+flagR+")").css("background-color","#eee");
					document.getElementById("keyword").value= $("#search_ul > li:eq("+flagR+")").text();	
					$("#taxon_id").val($("#search_ul > li:eq("+flag+")").attr('name'));
				}	
				if(keyCode == '40'){//아래
	
					if(flag >= count){
						flag = 0;
					}
					$("#search_ul > li:eq("+flag+")").css("background-color","#eee");
					document.getElementById("keyword").value= $("#search_ul > li:eq("+flag+")").text();
					$("#taxon_id").val($("#search_ul > li:eq("+flag+")").attr('name'));
					flag ++;
					temp = true;
				}
			}
		});
		
	}
	
}

function get_search_text(search_text, search_taxId){
	$("#taxon_id").val(search_taxId);
	
	document.getElementById("keyword").value= search_text.replace(/___/gi," ");
	document.getElementById("searchFrame").className ="searchFrame_off";
	$("#searchFrame").css("display","none");
}

function outFocus(){

	flag = 0;
	flagR = 0;
	
	document.getElementById("searchFrame").className ="searchFrame_off";
	$("#search_ul").empty();
	$("#searchFrame").css("display","none");
	$(".searchBoxClose").css("display","none");
}

