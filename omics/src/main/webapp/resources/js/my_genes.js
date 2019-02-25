function analysis_form_check() {

	var checked_count = $("input[name='path[]']:checkbox:checked").length;
	var checked_file_type = "";	//folder 인지 file 인지의 type
	var checked_file_unit = "";	//파일 크기의 단위
	var checked_file_size = "";	//파일 크기
	var flag = false;	//여러 파일에 대한 조건이 맞는지 체크

	if(checked_count != 1){
		alert("Please select only one file of 500KB or less.");
		return false;
	}else{
		
		$("input[name='path[]']:checkbox:checked").each(function(i) {
			
				checked_file_type = this.id.split(" ")[0];
				checked_file_unit = this.id.split(" ")[2];
				checked_file_size = this.id.split(" ")[1];
				
				if((checked_file_type == "file") && (checked_file_unit == "B" || (checked_file_unit == "kB" && checked_file_size < 500))){
					$( "#analysis_form" ).attr("action","do_mygenes")
					$( "#analysis_form" ).submit();
				}else{
					alert("Please select only one file of 500KB or less.");
					return false;
				}
		});
	}
}

$(function() {
	$("td").filter(":contains('시작')").remove();
	$("td").filter(":contains('종료')").remove();
	$("td").filter(":contains('Start')").remove();
	$("td").filter(":contains('End')").remove();
});

// Mypage List Detail View
$(function() {
	$('div.subconW table th').find('a').bind('click', function()
			{
				$(this.hash).toggle();
				return false;
			});
});
