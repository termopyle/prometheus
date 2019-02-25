<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
	
<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>

<jsp:include page="security.jsp" flush="false"/>

<!-- viewer -->
<link rel="stylesheet" type="text/css" href="./resources/css/jquery.qtip.css"/>
<link rel="stylesheet" type="text/css" href="./resources/css/viewer.css" />

<script type="text/javascript" src="./resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="./resources/js/jquery.qtip-1.0.0-rc3.min.js"></script>

<script type="text/javascript" src="./resources/js/kobic_util.js"></script>

<script type="text/javascript" src="./resources/js/geneSearch.js"></script>



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
		
		<div class="btnall"></div>
	</div>

	<div id="searchResultInfo" style="width:100%;height:25px;background:lightgray;margin-bottom:10px;"></div>

  	<div id="viewerPanel"></div>
  	
  	
  	<div id="pagingSystem">
  		<div id="pagingPanel" class="pagingController" style="width:80%;"></div>
  		<div id="pagingSizePanel" class="pagingController" style="width:20%;">
  			<span class="pagingControllerText">Paging</span>
  			<select id="selectPagingSize">
			  <option value="5">5</option>
			  <option value="10">10</option>
			  <option value="15">15</option>
			  <option value="30">30</option>
			  <option value="50">50</option>
			  <option value="100">100</option>
			</select>
  		</div>
  	</div>
  	
  	<!-- 로딩 이미지 -->
  	<div id="Loading_Image">
  		<img src="./resources/images/geneSearch/ajax_loader_blue_128.gif"/>
  	</div>

</div>
<!-- contents E --> 
	
<jsp:include page="../common/footer.jsp" flush="false"/>