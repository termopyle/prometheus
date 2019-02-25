var key_point = "";	//검색창에 입력한 단어와 매칭되는 부분
var key_left = "";	//매칭되지않는 나머지 부분의 단어
var li_add = "";	//매칭되는 단어들의 배열, 이 배열의 내용들이 검색창밑에 출력 된다.
var i = 0;	
var j = 0;

var flag = 0;
var key = "";
var count = 0;
var flagR = 0;
var temp = false;
var keyCode;
var max_count = 10;

$(document).keydown(function(event) {
	 keyCode = event.keyCode;
});


$(function() {
    $("input:text").keydown(function(evt) {
        if (evt.keyCode == 13){
        	$( "#searchbtn" ).focus();
        	key = document.getElementById("query").value;       
        	writeGeneName(key);
        	outFocus();
            return false;
        }
    });
});

function writeGeneName(text){
	
	if(keyCode == '8'){
		flag = 0;
		flagR = 0;
	}

	//위,아래 방향키를 제외한 이벤트일때만 key 값 변경
	if(keyCode != 40 && keyCode != 38){
		key = document.getElementById("query").value;
	}

	key_point = "";
	key_left = "";
	li_add = "";
	count = 0;

	document.getElementById("searchFrame").innerHTML = "<ul id='search_ul'></ul><div class=\"searchBoxClose\" id=\"searchClose\" onclick=\"outFocus()\">Close</div>";
	
	if(key.length > 0 ){
		
		$("#searchFrame").css("display","block");
		
		for(i = 0; i < data.length; i++){
			
			key_point = data[i].substring(0, key.length);	// key_point = data 문자열중, key.length 만큼의 문자열
			key_left  = data[i].substring(key.length, data[i].length);	//key_left = data 문자열중, key를 제외한 나머지부분
						
			//대소문자 구별없이 비교해서 key 와 key_point 가 맞으면 count 증가, li 추가
			if(key.toLowerCase() == key_point.toLowerCase()){	
				li_add[i] = data[i];
				$("#search_ul").append("<li onclick='get_search_text(this.id)' id='"+key_point + key_left +"'>"+"<span>"+key_point+"</span>"+ key_left +"</li>");
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
/*	if(temp){
		$("#search_ul > li:eq("+flagR+")").css("background-color","#eee");
	}*/
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
		document.getElementById("query").value= $("#search_ul > li:eq("+flagR+")").text();	
	}	
	if(keyCode == '40'){//아래

		if(flag >= count){
			flag = 0;
		}
		$("#search_ul > li:eq("+flag+")").css("background-color","#eee");
		document.getElementById("query").value= $("#search_ul > li:eq("+flag+")").text();
		flag ++;
		temp = true;
	}
}

function get_search_text(search_text){
	document.getElementById("query").value= search_text;
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