function do_pass_check(org_password){
	
	if($('#password_check').val() =="") {
		$("[id=texts]").text("Enter your password.");  	
		$('#setup').show();		
		$('#password_check').focus();
		return;
	}
	
	var value = $("#password_check").val();

	if(org_password == value){

		$('<input>', {
		    type: 'hidden',
		    id: 'new_password',
		    name: 'new_password',
		    value: $('#password1').val()
		}).appendTo('#user_infor_form');
	
		$('#user_infor_form').attr('action','do_user_info_update').submit();
		
	}else{
		$("[id=texts]").text("Password does not match.");  	
		$('#setup').show();	
		return;
	}

}

function do_user_infor_modify_check (){
	if($('#password1').val() =="" ){
		$("[id=texts]").text("Enter your password.");  	
		$('#setup').show();		
		$('#password1').focus();
		return;
	} else if($('#password2').val() == "") {
		$("[id=texts]").text("Confirm your password.");  	
		$('#setup').show();		
		$('#password1').focus();
		return;
	}else if(!do_pass_match($('#password1').val())) {
		$('#password1').val("");
		$('#password2').val("");
		$('#password1').focus();
		return;
	} else if($('#password1').val() != $('#password2').val()){
		$("[id=texts]").text("The password you have entered does not match. Please check again.");  	
		$('#setup').show();				
		$('#password2').focus();
		return;
	} else if(!do_email_check($('#emailAdress').val())){
		$('#emailAdress').val("");
		$('#emailAdress').focus();
		return;
	}
	
	$("#user_infor_table").hide();
	$("#user_infor_button").hide();
	$("#user_pass_check").show();

}

function do_pass_match(passValue){
	var regExp = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*]).*$/;
	
	if(passValue.length < 8 || passValue.length > 20){
		$("[id=texts]").text("Password must be between 8 and 20 characters in length.");  	
		$('#setup').show();	
		
		return false;
	}
	
	if(passValue.search(/\s/) != -1){
		$("[id=texts]").text("Password cannot contain whitespace characters.");  	
		$('#setup').show();
		return false;
	}
	
	if(!passValue.match(regExp)){
		$("[id=texts]").text("Password may contain English character, numbers and punctuation marks.");  	
		$('#setup').show();
		return false;
	}
	
	return true;
}

function do_email_check(strValue){

	strValue = strValue.replace(/^\s+/,"").replace(/\s+$/,"");
	var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
	
	if(strValue.length == 0) {
		$("[id=texts]").text("Enter your email address.");
		$('#setup').show();
		return false;
	}

	if (!strValue.match(regExp)){
		$("[id=texts]").text("Invalid email address.");
		$('#setup').show();
		return false;
	}
	
	$('#emailAdress').val(strValue);
	return true;
}
