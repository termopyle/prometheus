
function mover(t){
	$('#'+t).css({"opacity":"1","transition":"opacity 0.3s"});
	$('#th_'+t).css("background-color","#dddddd");
	$('#td_'+t).css("background-color","#f5f5f5");
}

function mout(t,o){
	$('#'+t).css("opacity",o);
	$('#th_'+t).css("background-color","#f5f5f5");
	$('#td_'+t).css("background-color","#fff");
}