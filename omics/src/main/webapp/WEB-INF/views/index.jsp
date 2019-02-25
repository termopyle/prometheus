<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
		
<jsp:include page="./common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="main_bg"/>
</jsp:include>

<%
	String userId = (String) request.getSession().getAttribute("uid");
	String isAdmin = (String) request.getSession().getAttribute("is_admin");
	String isLogin = (String) request.getSession().getAttribute("is_login");
%>

<c:set value="<%=userId %>" var="user_id"></c:set>
<c:set value="<%=isAdmin %>" var="is_admin"></c:set>
<c:set value="<%=isLogin %>" var="is_login"></c:set>

<form action="taxonomy" id="search_form">
	<!-- search S -->
	<div class="search">
		<fieldset>
			<legend>search</legend>
			<label for="query"></label>
			<input type="text" id="keyword" name="keyword" class="sch1" 
			placeholder="search" value=""
			onkeyup="writeGeneName(this.value);"
			onfocus="writeGeneName(this.value);"
			autocomplete = "off" 
			/>
			
			<div id="searchFrame" class="main_search_frame" onblur="outFocus();"							
			style="position:absolute; width:380px; border:1px solid #eee; background-color:rgba(255, 255, 255, 0.95);
			margin-left:275px; top:37px; ">						
			</div>
			
			<label for="sch2">searchbutton</label>
			<input type="submit" id="searchbtn" class="sch2" alt="search" value=""/>
		</fieldset>					
	</div>
	<!-- search E --> 
	<!-- <input type="button" name="download_btn" id="download_btn" value="Download" class="btnblue ml20" /> -->
</form>

<script type="text/javascript" src="./resources/js/search.js"></script>

<!-- mainV S -->
<div id="mainV" class="revolver container stack">
	<div class="visual">
      <ul class="slider">
		<li><span><strong>Welcome to Prometheus…</strong> The Korea Bioinformation Center (KOBIC)<br />
			curates a cloud-service based portal for Genome wide Comparative genomics analysis.<br />
			Platforms for Genome analysis (genome assembly and annotation),<br />
			Transcriptome analysis, GWAS analysis will be update.</span><img class="slide" src="./resources/images/main/main_img1.png" alt="" />
		</li>
		<li><span><strong>Welcome to Prometheus…</strong> The Korea Bioinformation Center (KOBIC)<br />
			curates a cloud-service based portal for Genome wide Comparative genomics analysis.<br />
			Platforms for Genome analysis (genome assembly and annotation),<br />
			Transcriptome analysis, GWAS analysis will be update.</span><img class="slide" src="./resources/images/main/main_img2.png" alt="" />
		</li>
		</ul>
	</div>
</div>
<!--  
<div class="controls"> 
	<a class="imgover" href="#">
		<img src="./resources/images/main/left.png" alt="" class="previous" /></a> 
	<a class="imgover" href="#">
		<img src="./resources/images/main/right.png" alt="" class="next" />
	</a> 
</div>
-->

<script type="text/javascript">
	var $revolver = $('#mainV').revolver(),
			revolver  = $revolver.data('revolver'),
			$controls = $('.controls');
			
	$controls.find('.previous, .next').click(function(e){
			e.preventDefault();
			revolver[this.className]();
	});
</script> 
<!-- mainV E -->

<!-- contents S-->
<div id="contents" class="conwrap">
	<h2 class="hidden">메인컨텐츠</h2>
	<div id="col1">
		<h3 class="hidden">바로가기</h3>
		<ul class="conlink">
			<li><a href="<c:url value="taxonomy"/>" class="thumb"><strong><img src="./resources/images/main/conlink1.png" alt="Genome Archive" /></strong><span>Genome Archive provides 17,215 genome information of 16,730 species including 435 eukaryotic genomes, 15,984 prokaryotic genomes and 311 archaea genomes. The genome information were sorted by NCBI taxonomy tree.</span></a></li>
			<li><a href="<c:url value="geneGroupSearch"/>" class="thumb"><strong><img src="./resources/images/main/conlink2.png" alt="Gene Search" /></strong><span>Gene Search provides domain architecture-based search system for genes of interests. Users can identify genes of interests using combination of their IPR terms.</span></a></li>
			<li><a href="<c:url value="analysis"/>" class="thumb"><strong><img src="./resources/images/main/conlink4.png" alt="Genome Analysis" /></strong><span>Genome Analysis provides bioinformatics tools for further comparative analysis.</span></a></li>	
			<li><a href="<c:url value="http://www.bioexpress.re.kr/bioexpress.en"/>" class="thumb" target="_blank"><strong><img src="./resources/images/main/conlink3.png" alt="Bio-express" /></strong><span>Elastic HPC-Cloud-based automation of NGS data analysis through workflow and data management platform system.</span></a></li>						
		</ul>
	</div>
	
	<script type="text/javascript">
	$(document).ready(function() {				
		$("ul.conlink li").hover(function() {					
			var thumbOver = $(this).find("img").attr("src");
			$(this).find("a.thumb").css({'background' : 'url(' + thumbOver + ') no-repeat center bottom'});
			$(this).find("strong").stop().fadeTo('normal', 0 , function() {
				$(this).hide() 
			}); 
		} , function() { 
			$(this).find("strong").stop().fadeTo('normal', 1).show();
		});			
	});			
	</script>
	<!-- col1 E -->

	<div id="col2">
		<div class="datdabases">
			<h3>All Datdabases</h3>
			<ul>
				<li class="bg01"><strong>16,295</strong>Prokaryote<span>(species)</span></li>
				<li class="bg02"><strong>435</strong>Eukaryote<span>(species)</span></li>
				<li class="bg03"><strong>45</strong>Primary DB<span>(375,188,071 records)</span></li>
				<li class="bg04"><strong>6</strong>Secondary DB<span>(787,865,532 records)</span></li>
				<li class="bg05"><strong>ver 1.0</strong>Version</li>
				<li class="bg06"><strong>17.07.13</strong>Last Update</li>
			</ul>
		</div>
	</div>
	<!-- col2 E -->

</div>
<!-- contents E -->
	
<jsp:include page="./common/footer.jsp" flush="false"/>
	