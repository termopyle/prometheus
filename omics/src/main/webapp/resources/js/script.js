$(document).ready(function() {
	var imgover = $(".imgover").size();
	var imgoveron = $(".imgoveron").size();
	
	if(imgover != 0) {
		$(".imgover").mouseover(function() {
			var overimg = $(this).find(">img")[0];
			
			//예외처리 (이미 on 되어있는 이미지 제외)
			var Dns;
			Dns = overimg.src;
			Dns = Dns.split("_on");
			Dns = Dns[1];
			if(Dns != ".gif") {
				overimg.src = overimg.src.replace(".gif", "_on.gif");
			}
		});
		$(".imgover").mouseout(function() {
			var overimg = $(this).find(">img")[0];
			overimg.src = overimg.src.replace("_on.gif", ".gif");
		});
		$(".imgover").focus(function() {
			var overimg = $(this).find(">img")[0];
			overimg.src = overimg.src.replace(".gif", "_on.gif");
			overimg.src = overimg.src.replace("_on_on.gif", "_on.gif");
		});
		$(".imgover").blur(function() {
			var overimg = $(this).find(">img")[0];
			overimg.src = overimg.src.replace("_on.gif", ".gif");
		})
	}
	if(imgoveron != 0) {
		var overonimg = $(".imgoveron").find(">img")[0];
		overonimg.src = overonimg.src.replace(".gif", "_on.gif");
	}
	
	
	if(imgover != 0) {
		$(".imgover").mouseover(function() {
			var overimg = $(this).find(">img")[0];
			
			//예외처리 (이미 on 되어있는 이미지 제외)
			var Dns;
			Dns = overimg.src;
			Dns = Dns.split("_on");
			Dns = Dns[1];
			if(Dns != ".png") {
				overimg.src = overimg.src.replace(".png", "_on.png");
			}
		});
		$(".imgover").mouseout(function() {
			var overimg = $(this).find(">img")[0];
			overimg.src = overimg.src.replace("_on.png", ".png");
		});
		$(".imgover").focus(function() {
			var overimg = $(this).find(">img")[0];
			overimg.src = overimg.src.replace(".png", "_on.png");
			overimg.src = overimg.src.replace("_on_on.png", "_on.png");
		});
		$(".imgover").blur(function() {
			var overimg = $(this).find(">img")[0];
			overimg.src = overimg.src.replace("_on.png", ".png");
		})
	}
	if(imgoveron != 0) {
		var overonimg = $(".imgoveron").find(">img")[0];
		overonimg.src = overonimg.src.replace(".png", "_on.png");
	}
});




if (this.$ != null) {
  $(document).ready(function()
      {
      	// load cookie related JS
	  $.getScript('resources/js/jquery.cookie.js', function() {
      	//$.getScript('../../common/assets/js/lib/jquery.cookie-1.0.pack.js', function() {
			restoreEmail();
			$('#jd_toolSubmissionForm').submit(function() {
				storeEmail();
				return true;
			});
		});
		updateLayout();
      }
  );
}

function storeEmail() {
   	// Email stored in cookies
   	var email = $('#email').val();
	var date = new Date();
	date.setMonth(date.getMonth()+1);
	$.cookie('email', email, { path: '/', expires: date });
}

function restoreEmail() {
	var email = $.cookie('email');
	if (email != null) {
		$('#email').val(email);
	}
}


function updateLayout() {
	// Adding Block UI action on submit button
  	$.getScript('resources/js/jquery.blockUI.js', function() {
  	//$.getScript('../../common/assets/js/lib/jquery.blockUI.js', function() {
        $("#jd_submitButtonPanel input").click(function()
        {
            if(typeof document.getElementById('jd_toolSubmissionForm').
                    checkValidity == "function")
            {
                if(document.getElementById('jd_toolSubmissionForm').
                        checkValidity() === false)
                    return;
            }

        	setTimeout(function() {
        		$.blockUI( { message: validation, css: {width: '300px'} } );
        	}, 100);
        });
        $('#download').each(function() {
        	var download = $(this).hide();
        	$('<a href="#" style="float: right">Close</a>').appendTo(download).click(function() {$.unblockUI();});
        	$('<li><a id="dLink" href="">Download</a></li>').appendTo($('ul.ebi_sideMenu:first')).find('a').click(function() {
        		$.blockUI({ message: download, css: {width: '300px', textAlign: 'left', padding: '5px 15px'} });
        		$('.blockOverlay').attr('title','Click to unblock').click($.unblockUI);
        		return false;
        	});
        });
		$(window).unload(function() {
			$.unblockUI();
		});
	});

  	$('.collapsed').hide().after('<p style=\"float: left; width: 100%;\"><span style="width: 100%; float: left; font-style: italic; margin-bottom: 5px;">The default settings will fulfill the needs of most users and, for that reason, are not visible.</span><a class="jd_button shadow" style="float: left;">More options...</a><span style="float: left; padding: 5px 10px; font-style: italic;">(Click here, if you want to view or change the default settings.)</span></p>').next('p').find('a').click(function() { $(this).parent().hide().prev().show(); return false; });
  	if (! $('#notification_interpro').is(':checked')) {
  		$('#submission_interpfo').hide();
	}
  	$('#notification_interpro').click(function () {
  		if ($(this).is(':checked')) {
  			$('#submission_interpro').show();
  		} else {
  			$('#submission_interpro').hide();
  		} 
  	});
  	$('#notification_last').click(function () {
  		if ($(this).is(':checked')) {
  			$('#submission_last').show();
  		} else {
  			$('#submission_last').hide();
  		} 
  	});
  	$('#notification_blast').click(function () {
  		if ($(this).is(':checked')) {
  			$('#submission_blast').show();
  		} else {
  			$('#submission_blast').hide();
  		} 
  	}); 	
}

var modalAlert = function(string) {
	$("[id=texts]").text(string);
	$('#setup').show();
}


/************* 탭 *****************/

function con_analysis_group_tab(tab_no){
	
	if(tab_no == 1){
		
		$('#group_tab2').removeClass('on');
		$('#group_tab3').removeClass('on');
		$('#group_tab4').removeClass('on');
		$('#group_tab5').removeClass('on');
		
		$('#tab_cont1').css('display', 'block');
		$('#tab_cont2').css('display', 'none');
		$('#tab_cont3').css('display', 'none');
		$('#tab_cont4').css('display', 'none');
		$('#tab_cont5').css('display', 'none');
		$('#tab_cont6').css('display', 'none');
		
		con_tab(1);
	}else if(tab_no == 2){
		
		$('#group_tab1').removeClass('on');
		$('#group_tab2').addClass('on');
		$('#group_tab3').removeClass('on');
		$('#group_tab4').removeClass('on');
		$('#group_tab5').removeClass('on');
		
		$('#tab_cont1').css('display', 'none');
		$('#tab_cont2').css('display', 'block');
		$('#tab_cont3').css('display', 'none');
		$('#tab_cont4').css('display', 'none');
		$('#tab_cont5').css('display', 'none');
		$('#tab_cont6').css('display', 'none');
		
		con_tab(2);
	}else if(tab_no == 3){
		
		$('#group_tab1').removeClass('on');
		$('#group_tab2').removeClass('on');
		$('#group_tab3').addClass('on');
		$('#group_tab4').removeClass('on');
		$('#group_tab5').removeClass('on');
		
		$('#tab_cont1').css('display', 'none');
		$('#tab_cont2').css('display', 'none');
		$('#tab_cont3').css('display', 'none');
		$('#tab_cont4').css('display', 'block');
		$('#tab_cont5').css('display', 'block');
		$('#tab_cont6').css('display', 'none');
		
		con_tab(4);
	}else if(tab_no == 4){
		
		$('#group_tab1').removeClass('on');
		$('#group_tab2').removeClass('on');
		$('#group_tab3').removeClass('on');
		$('#group_tab4').addClass('on');
		$('#group_tab5').removeClass('on');
		
		$('#tab_cont1').css('display', 'none');
		$('#tab_cont2').css('display', 'none');
		$('#tab_cont3').css('display', 'block');
		$('#tab_cont4').css('display', 'none');
		$('#tab_cont5').css('display', 'none');
		$('#tab_cont6').css('display', 'none');
		
		con_tab(3);
	}else if(tab_no == 5){
		
		$('#group_tab1').removeClass('on');
		$('#group_tab2').removeClass('on');
		$('#group_tab3').removeClass('on');
		$('#group_tab4').removeClass('on');
		$('#group_tab5').addClass('on');
		
		$('#tab_cont1').css('display', 'none');
		$('#tab_cont2').css('display', 'none');
		$('#tab_cont3').css('display', 'none');
		$('#tab_cont4').css('display', 'none');
		$('#tab_cont5').css('display', 'none');
		$('#tab_cont6').css('display', 'block');
		
		con_tab(6);
	}
}

var tab = 1;
var tab_number = 1;


function con_tab(tab) {
	if (tab == 1) {		
		$("#div_tab_1").css("display", "block");
		$("#div_tab_2").css("display", "none");
		$("#div_tab_3").css("display", "none");
		$("#div_tab_4").css("display", "none");
		$("#div_tab_5").css("display", "none");
		$("#div_tab_6").css("display", "none");
		document.getElementById("tab_cont1").className = "on";
		document.getElementById("tab_cont2").className = "off";
		document.getElementById("tab_cont3").className = "off";
		document.getElementById("tab_cont4").className = "off";
		document.getElementById("tab_cont5").className = "off";
		document.getElementById("tab_cont6").className = "off";
		tab_number = 1;
	}
	if (tab == 2) {	
		$("#div_tab_1").css("display", "none");
		$("#div_tab_2").css("display", "block");
		$("#div_tab_3").css("display", "none");
		$("#div_tab_4").css("display", "none");
		$("#div_tab_5").css("display", "none");
		$("#div_tab_6").css("display", "none");
		document.getElementById("tab_cont1").className = "off";
		document.getElementById("tab_cont2").className = "on";
		document.getElementById("tab_cont3").className = "off";
		document.getElementById("tab_cont4").className = "off";
		document.getElementById("tab_cont5").className = "off";
		document.getElementById("tab_cont6").className = "off";
		
		tab_number = 2;
	}
	if (tab == 3) {
		$("#div_tab_1").css("display", "none");
		$("#div_tab_2").css("display", "none");
		$("#div_tab_3").css("display", "block");
		$("#div_tab_4").css("display", "none");
		$("#div_tab_5").css("display", "none");
		$("#div_tab_6").css("display", "none");
		document.getElementById("tab_cont1").className = "off";
		document.getElementById("tab_cont2").className = "off";
		document.getElementById("tab_cont3").className = "on";
		document.getElementById("tab_cont4").className = "off";
		document.getElementById("tab_cont5").className = "off";
		document.getElementById("tab_cont6").className = "off";
		tab_number = 3;
	}	
	if (tab == 4) {
		$("#div_tab_1").css("display", "none");
		$("#div_tab_2").css("display", "none");
		$("#div_tab_3").css("display", "none");
		$("#div_tab_4").css("display", "block");
		$("#div_tab_5").css("display", "none");
		$("#div_tab_6").css("display", "none");
		document.getElementById("tab_cont1").className = "off";
		document.getElementById("tab_cont2").className = "off";
		document.getElementById("tab_cont3").className = "off";
		document.getElementById("tab_cont4").className = "on";
		document.getElementById("tab_cont5").className = "off";
		document.getElementById("tab_cont6").className = "off";
		tab_number = 4;
	}	
	if (tab == 5) {
		$("#div_tab_1").css("display", "none");
		$("#div_tab_2").css("display", "none");
		$("#div_tab_3").css("display", "none");
		$("#div_tab_4").css("display", "none");
		$("#div_tab_5").css("display", "block");
		$("#div_tab_6").css("display", "none");
		document.getElementById("tab_cont1").className = "off";
		document.getElementById("tab_cont2").className = "off";
		document.getElementById("tab_cont3").className = "off";
		document.getElementById("tab_cont4").className = "off";
		document.getElementById("tab_cont5").className = "on";
		document.getElementById("tab_cont6").className = "off";
		tab_number = 5;
	}	
	if (tab == 6) {
		$("#div_tab_1").css("display", "none");
		$("#div_tab_2").css("display", "none");
		$("#div_tab_3").css("display", "none");
		$("#div_tab_4").css("display", "none");
		$("#div_tab_5").css("display", "none");
		$("#div_tab_6").css("display", "block");
		document.getElementById("tab_cont1").className = "off";
		document.getElementById("tab_cont2").className = "off";
		document.getElementById("tab_cont3").className = "off";
		document.getElementById("tab_cont4").className = "off";
		document.getElementById("tab_cont5").className = "off";
		document.getElementById("tab_cont6").className = "on";
		tab_number = 6;
	}
	if (tab == 7) {
		$("#div_tab_1").css("display", "none");
		$("#div_tab_2").css("display", "none");
		$("#div_tab_3").css("display", "none");
		$("#div_tab_4").css("display", "none");
		$("#div_tab_5").css("display", "none");
		$("#div_tab_6").css("display", "none");
		document.getElementById("tab_cont1").className = "off";
		document.getElementById("tab_cont2").className = "off";
		document.getElementById("tab_cont3").className = "off";
		document.getElementById("tab_cont4").className = "off";
		document.getElementById("tab_cont5").className = "off";
		document.getElementById("tab_cont6").className = "off";
		tab_number = 7;
	}	
}


//사이트 
$(function(){
	$(".site p img").click(function(){		
		$(".site ul").slideToggle("fast");

		});
	});