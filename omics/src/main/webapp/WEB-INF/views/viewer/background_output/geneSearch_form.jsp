<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
	
<jsp:include page="../../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>

<!-- viewer -->
<link rel="stylesheet" type="text/css" href="./resources/css/jquery.qtip.css"/>
<link rel="stylesheet" type="text/css" href="./resources/css/viewer.css" />

<script type="text/javascript" src="./resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="./resources/js/jquery.qtip-1.0.0-rc3.min.js"></script>

<script type="text/javascript" src="./resources/js/kobic_util.js"></script>

<script type="text/javascript" src="./resources/js/geneSearch.js"></script>

<script type="text/javascript">

	$(document).ready(function(){
		
		var data = "{data}";
		
		var ipr_ids = "{ipr_ids}";
		
		var originalIpr_ids = ipr_ids.replace(/[-]/gi, ",");
		
		json = jQuery.parseJSON(data);
		
		var track = '';
		
		for(var i=0; i<json.length; i++) {
			track = new geneSearch( jQuery.parseJSON(JSON.stringify( json[i] )), i, originalIpr_ids);
		 	track.init();
		 	track.paint();
		}
		
		var allSequenceDownloadButton = $("<button id='allSequenceDownloadButton"+this.order+"' " + 
		"class='btnall'>All Sequence Download</button>");

		$(".entry").append(allSequenceDownloadButton);
	
		$(allSequenceDownloadButton).on("click", function() {
			
			var createDownloadFile = jQuery.parseJSON($.allDownload(json, originalIpr_ids).responseText);
			
			var url = "downloads?name="+createDownloadFile;
			$(location).attr('href', url);
		});
		
		var dataStrArray = originalIpr_ids.split(',');
		
		for(var i in dataStrArray );{
			$("#banner2").append("<div class='Legend'>Legend</div>");	
		}
		
		for(var i=0; i<dataStrArray.length; i++){
			
			if(dataStrArray[i].indexOf("IPR") == -1) {//문자열에 IPR이 포함되지 않는 경우
				dataStrArray[i] = "IPR" + dataStrArray[i];
			}
			
			$("#banner2").append("<div id='iprColor"+i+"' style='width:50px; height:5px; margin:8px 0 0 10px;" +
					"text-align:center;float:left;border:1px solid #d3d3d3;background:"+track.ipr_color_type[i]+"'></div>");
			$("#banner2").append("<div id='iprName"+i+"' style=''>"+dataStrArray[i]+"</div>");
		}
		
		$("#banner").show();
		
		$(window).scroll(function(){
			$('#banner').animate({top:$(window).scrollTop()+"px"}, {queue: false, duration: 350});
			
		});
		
//		var data = '[{"nid":"9913","nm":"Bos taurus","ncbi_id":"GCF_000003205.5","length":871,"sequence":"MGLCGPWLHTSVLWAAVASLLLPPAMTQQLRGAGPGPSNWDDHAGASGSSEDVSGKSDHHGGHGDENRDVSTDNGHHFWGHGDHGEEDEDVSRESQGHRYQGREVGDENISDEEEYPEHAQQAHGHRCHGNEDADDSAERGDHLPSHGSQSHQDEEEEEVVSSEHHQHHIVRQAHRGHREEEDEDEEEENVSPGYGQQVHRRQDRGEQKDGNGSEEHDRGHGPSHRHGGHKDDDDGGDAVSTEHRHQVHRHRDHGEEEEEEDDSEEHHHHHSPSHRHRGHEEEDDDEDDIVSTEHRHQVHRHRNHGEEEEEDDSEEHHHHHSPSHRHRGHEEEEDDHEDDIVSTEHRHQVHRHRDHGEEEEEAVSEEHPHHHSPSYRHRGHEEDDDEDEDDVVSTEHRHQVHRHRDHGEEEEEDDSEEHHHHHSPSHRHRGHEEEDDDEDDIVSTEHRHQVHRHQDHGEEEEEDDSEEHHHHHSPSHRHQGHEEEDDEDEDDVVSTEHRHQVHRHRDHGEEEDEDDSEEHHHHHGGNKEDEDEDLSTEHWHQAPRHTHHGLGDEEEEEEKEEITVKFSHHVASPQPQGHKSAKEEDFPEDYKKAVPGHDHQGVPKEEDEDIPARLGHQAPSHRQQDHRDEGTGHRGSIKEEMSPQSPEHIGVKDRSHLRESDSEEDEEEKEEDHSSHEEANEGSEEGEDTRHGSLDDQEDEEDEEEGHGLSLSQEEEEEEKEEKRGERAKVWVPLNQDHQEEDKEEVGLEEDELPFTIIPNPLTRKEVSGGASSEEESGEDKGQQDAQEYGNYQPGSLCGYCSFCNRCTECENCHCDEENMGEHCDQCQHCQFCYLCPLVCETLCSPGSYVDYFSSSLYQALADMLETPEP","protein_id":"NP_001095783.1","dbs":[{"no":"617757","db":"PANTHER","db_id":"150542","db_desc":"Unknown","link_url":"http://www.pantherdb.org/panther/family.do?clsAccession\u003dPTHR15054:SF2","start":2,"end":871,"evalue":"0.0"},{"no":"617770","db":"ProSitePatterns","db_id":"00328","db_desc":"HCP repeats signature.","link_url":"http://prosite.expasy.org/PS00328","start":275,"end":289,"evalue":"-","ipr_id":"002134","ipr_desc":"Histidine-rich calcium-binding repeat"},{"no":"617771","db":"ProSitePatterns","db_id":"00328","db_desc":"HCP repeats signature.","link_url":"http://prosite.expasy.org/PS00328","start":427,"end":441,"evalue":"-","ipr_id":"002134","ipr_desc":"Histidine-rich calcium-binding repeat"},{"no":"617758","db":"PANTHER","db_id":"15054","db_desc":"Unknown","link_url":"http://www.pantherdb.org/panther/family.do?clsAccession\u003dPTHR15054","start":2,"end":871,"evalue":"0.0","ipr_id":"015666","ipr_desc":"Histidine-rich calcium-binding protein"},{"no":"617767","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":174,"end":187,"evalue":"0.017","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"617762","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":275,"end":289,"evalue":"0.066","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"617760","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":300,"end":314,"evalue":"0.19","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"617766","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":325,"end":339,"evalue":"0.19","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"617764","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":351,"end":365,"evalue":"0.043","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"617759","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":376,"end":390,"evalue":"0.014","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"617769","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":402,"end":416,"evalue":"0.11","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"617761","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":427,"end":441,"evalue":"0.066","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"617763","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":452,"end":466,"evalue":"0.049","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"617765","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":477,"end":491,"evalue":"0.0031","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"617768","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":503,"end":517,"evalue":"0.015","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"}]}]';
//		var data1 = '[{"nid":"9606","nm":"Homo sapiens","ncbi_id":"GCF_000001405.26","length":699,"sequence":"MGHHRPWLHASVLWAGVASLLLPPAMTQQLRGDGLGFRNRNNSTGVAGLSEEASAELRHHLHSPRDHPDENKDVSTENGHHFWSHPDREKEDEDVSKEYGHLLPGHRSQDHKVGDEGVSGEEVFAEHGGQARGHRGHGSEDTEDSAEHRHHLPSHRSHSHQDEDEDEVVSSEHHHHILRHGHRGHDGEDDEGEEEEEEEEEEEEASTEYGHQAHRHRGHGSEEDEDVSDGHHHHGPSHRHQGHEEDDDDDDDDDDDDDDDDVSIEYRHQAHRHQGHGIEEDEDVSDGHHHRDPSHRHRSHEEDDNDDDDVSTEYGHQAHRHQDHRKEEVEAVSGEHHHHVPDHRHQGHRDEEEDEDVSTERWHQGPQHVHHGLVDEEEEEEEITVQFGHYVASHQPRGHKSDEEDFQDEYKTEVPHHHHHRVPREEDEEVSAELGHQAPSHRQSHQDEETGHGQRGSIKEMSHHPPGHTVVKDRSHLRKDDSEEEKEKEEDPGSHEEDDESSEQGEKGTHHGSRDQEDEEDEEEGHGLSLNQEEEEEEDKEEEEEEEDEERREERAEVGAPLSPDHSEEEEEEEEGLEEDEPRFTIIPNPLDRREEAGGASSEEESGEDTGPQDAQEYGNYQPGSLCGYCSFCNRCTECESCHCDEENMGEHCDQCQHCQFCYLCPLVCETVCAPGSYVDYFSSSLYQALADMLETPEP","protein_id":"NP_002143.1","dbs":[{"no":"834451","db":"PANTHER","db_id":"150542","db_desc":"Unknown","link_url":"http://www.pantherdb.org/panther/family.do?clsAccession\u003dPTHR15054:SF2","start":2,"end":699,"evalue":"0.0"},{"no":"834444","db":"Coils","db_desc":"Coiled Coil","link_url":"Unlink","start":527,"end":558,"evalue":"-"},{"no":"834452","db":"PANTHER","db_id":"15054","db_desc":"Unknown","link_url":"http://www.pantherdb.org/panther/family.do?clsAccession\u003dPTHR15054","start":2,"end":699,"evalue":"0.0","ipr_id":"015666","ipr_desc":"Histidine-rich calcium-binding protein"},{"no":"834445","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":214,"end":228,"evalue":"1.8E-4","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"834446","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":238,"end":252,"evalue":"0.025","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"834449","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":271,"end":285,"evalue":"9.8E-5","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"834450","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":295,"end":309,"evalue":"0.66","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"834447","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":319,"end":328,"evalue":"0.027","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"},{"no":"834448","db":"Pfam","db_id":"10529","db_desc":"Histidine-rich Calcium-binding repeat region","link_url":"http://pfam.xfam.org/family/PF10529","start":343,"end":356,"evalue":"0.059","ipr_id":"019552","ipr_desc":"Histidine-rich calcium-binding"}]}]';
 	
//		var track = new InterProDomainSearchStructure( jQuery.parseJSON(data0)[0], 0 );
//	 	track.init();
//	 	track.paint();
	 	
//	 	var track1 = new InterProDomainSearchStructure(  jQuery.parseJSON(data1)[0], 1 );
//	 	track1.init();
//	 	track1.paint();
	 	
	});
	
</script>	

<!-- subV S -->
<div class="subV"><img src="./resources/images/sub/sub1_top.png" alt="" /></div>
<!-- subV E --> 

<!-- contents S-->
<div id="contents" class="subcon conwrap">
	<h2 class="hidden">메인컨텐츠</h2>
	<h3>Gene Search</h3>
	
	<div class="entry">
		<p>
			IPR : <input type="text" id="searchword" /> <button id="InputButton">입력</button>
		</p>
		
		<input type="radio" id="searchRadio" name="chk_info" value="ord"/>in order
		<input type="radio" id="searchRadio" name="chk_info" value="anyOrd" checked="checked"/>in any order
		
		<div class="bannerall">
			<div id="banner">
				<div id="banner2" style="position:relative;width:100%;height:10px;background:none;"></div>
			</div>
		</div>
		
	</div>

  	<div id="viewerPanel"></div>
  	
  	<!-- 로딩 이미지 -->
  	<div id="Loading_Image">
  		<img src="./resources/images/geneSearch/ajax_loader_blue_128.gif"/>
  	</div>

</div>
<!-- contents E --> 
	
<jsp:include page="../../common/footer.jsp" flush="false"/>