var loginCheck = function(){
	if ($('#userId').val() == "") {
		$("[id=texts]").text("Enter user ID");  	
		$('#setup').show();
		$('#userId').blur();
		return;
	} else if ($('#password').val() == "") {
		$("[id=texts]").text("Enter password");  	
		$('#setup').show();
		$('#password').blur();
		return;
	}
	
	$('#login_check_form').attr('action','doLogin').submit();
}

var joinCheck = function(){

	if ($('#userId').val() == "") {
		$("[id=texts]").text("Enter user id.");  	
		$('#setup').show();
		$('#userId').focus();
		return;
	} else if ($('#userId').val() != $('#duplicate').val()) {
		$("[id=texts]").text("ID duplicate check.");
		$('#setup').show();
		$('#userId').focus();
		return;
	} else if ($('#password').val() == "") {
		$("[id=texts]").text("Enter password.");  	
		$('#setup').show();
		$('#password').focus();
		return;
	} else if ($('#password2').val() == "") {
		$("[id=texts]").text("Check your password.");  	
		$('#setup').show();
		$('#password2').focus();
		return;
	} else if(!passMatch($('#password').val())) {
		$('#password').val("");
		$('#password2').val("");
		$('#password').focus();
		return;
	} else if($('#password').val() != $('#password2').val()){
		$("[id=texts]").text("Password doesn't match.");  	
		$('#setup').show();				
		$('#password2').focus();
		return;
	} else if (!nameCheck($('#userName').val())) {
		$('#userName').focus();
		return;
	} else if($('#identity_number').val() != "" && $('#identity_number').val().length != 8) {
		$("[id=texts]").text("Scientific Technology Registration Number must be 8 characters in length.");  	
		$('#setup').show();
		$('#identity_number').val("")
		return;
	}else if ($('#organization').val().replace(/^\s+/,"").replace(/\s+$/,"") == "") {
		$("[id=texts]").text("Enter your organization..");  	
		$('#setup').show();
		$('#organization').val("");
		$('#organization').focus();
		return;
	} else if(!emailCheck($('#emailAdress').val())){
		$('#emailAdress').focus();
		return;
	} else if ($('#tel').val() == "") {
		alert("Enter your phone number");
		$('#tel').focus();
		return;
	}
	
	$('#join_check_form').attr('action','doJoin').submit();

}

function emailCheck(strValue){

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

function nameCheck(name){
	
	var regExp = /([^가-힣\x20a-zA-Z])/i;
	
	name = name.replace(/^\s+/,"").replace(/\s+$/,"");
	
	if(name.length < 1){
		$("[id=texts]").text("Enter your name.");
		$('#setup').show();
		$('#userName').val("");
		return false;
	}
	
	if(name.match(regExp)){
		$("[id=texts]").text("Korean alphabet or English.");
		$('#setup').show();
		$('#userName').val("");
		return false;
	}
	
	return true;
}

function passMatch(passValue){
	var regExp = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*]).*$/;
	
	if(passValue.length < 8 || passValue.length > 20){
		$("[id=texts]").text("8~20 digits.");
		$('#setup').show();
		return false;
	}
	
	if(passValue.search(/\s/) != -1){
		$("[id=texts]").text("can not contain spaces.");
		$('#setup').show();
		return false;
	}
	
	if(!passValue.match(regExp)){
		$("[id=texts]").text("Please combination.");
		$('#setup').show();
		return false;
	}
	
	return true;
}

function idMatch(idValue){
	var regExp = "^[a-zA-Z][a-zA-Z0-9]{3,11}$";

	if(!idValue.match(regExp)){
		
		return false;
	}
	
	return true;
}

function onlyNum() {

	var code = (event.which) ? event.which : event.keyCode;

	if((code >= 48 && code <= 57) || (code >= 96 && code <= 105) || code == 110 
			|| code == 190 || code == 8 || code == 9 || code == 13 || code == 46){
		
		return;
	}else{
		
		return false;
	}
}

function removeChar(event) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 )
	return;
	else
	event.target.value = event.target.value.replace(/[^0-9]/g, "");
}

function checkId(){
	
	var user_id = $('#userId').val();
	
	if (user_id != null && !idMatch(user_id)) {
		$("[id=texts]").text("Invalid format.");  	
		$('#setup').show();
		$('#userId').val("");
		$('#userId').focus();
		return;
	}
	$.ajax({
		type : 'POST',
		data : "user_id=" + user_id,
		dataType : 'json',
		url : 'doIdCheck',
		success : function(data) {

			if (data == 1) {
    			$("[id=texts]").text("This user ID can be registry");  	
    			$('#setup').show();
    			$("[id=duplicate]").val(user_id);
    	 		
			} else {
				$("[id=userId]").val("");
    			$("[id=texts]").text("User ID is duplicated");  
    			$('#setup').show();
			}
		},
		error : function(request, status, error) {
			if (request.status != '0') {
				
				$("[id=texts]").text("code : " + request.status + "\r\nmessage : "
						+ request.reponseText + "\r\nerror : " + error);
				$('#setup').show();	
				//alert("code : " + request.status + "\r\nmessage : " +
				//request.reponseText + "\r\nerror : " + error);
			}
		}
	});		
}

var findCheck_by_id = function(){

	if ($('#userName').val() == "") {
		$("[id=texts]").text("Enter user name");  	
		$('#setup').show();	
		return;
	} else if ($('#emailAdress').val() == "") {
		$("[id=texts]").text("Enter email address");  	
		$('#setup').show();	
		return;
	} else if(!emailCheck($('#emailAdress').val())){
		$("[id=texts]").text("This email address format is incorrect");  	
		$('#setup').show();			 				
		return;
	}
	
	$('#find_check_form_id').attr('action','doFindUserInfo').submit();
}


var findCheck_by_pass = function(){

	if ($('#userId').val() == "") {
		$("[id=texts]").text("Enter user ID");  	
		$('#setup').show();					
		return;
	} else if ($('#emailAdress').val() == "") {
		$("[id=texts]").text("Enter email address");  	
		$('#setup').show();	
		return;
	} else if(!emailCheck($('#emailAdress').val())){
		$("[id=texts]").text("This email address format is incorrect");  	
		$('#setup').show();	
		return;
	}
	
	$('#find_check_form_pass').attr('action','doFindUserInfo').submit();
}

var agreement = function() {
	
	if ( !$("input:checkbox[id='register_agree']").is(":checked") ) {
		$("[id=texts]").text("Please agree to the all terms.");  	
		$('#setup').show();				
		$('#register_agree').blur();	
		return;
	} else if (!$("input:checkbox[id='member_agree']").is(":checked") ) {
		$("[id=texts]").text("Please agree to the all terms.");  	
		$('#setup').show();				
		$('#member_agree').blur();	
		return;
	}
	
	location.href="goJoin";
};
