<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
	
<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>

<%-- <jsp:include page="security.jsp" flush="false"/> --%>

<!-- viewer -->
<link rel="stylesheet" type="text/css" href="./resources/css/jquery.qtip.css"/>
<link rel="stylesheet" type="text/css" href="./resources/css/viewer.css" />

<script type="text/javascript" src="./resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="./resources/js/jquery.qtip-1.0.0-rc3.min.js"></script>
<script type="text/javascript" src="./resources/js/jquery.simplemodal-1.4.4.js"></script>

<script type="text/javascript" src="./resources/js/kobic_util.js"></script>


<script type="text/javascript" src="./resources/js/interproAnalysis.js"></script>

<!-- subV S -->
<div class="subV"><img src="./resources/images/sub/sub1_top.png" alt="" /></div>
<!-- subV E --> 

<!-- contents S-->
<div id="contents" class="subcon conwrap">
	<h2 class="hidden">메인컨텐츠</h2>
	<h3>InterPro Analysis</h3>
	
	<div id="analysisViewerPanel">
  		<div class="domain-title anlysisTitle" style="background:#008570;">Domain Architecture</div>
	</div>
	
	<!-- <div id="analysisViewerPanelLegend" style="position:relative;background:none;width:100%;"> -->
	<div id="analysisViewerPanelLegend">
		<fieldset id="viewerPanelFieldsetLegend" style="width:130px;clear:both;display:block;border:1px solid #ddd; padding:10px; margin-top:30px;">
			<legend>Legend</legend>
		</fieldset>
	</div>
	
	<input type='hidden' id='filePath' value="${filePath}"/>


  	<!-- 로딩 이미지 -->
<!--   	<div id="Loading_Image">
  		<img src="./resources/images/geneSearch/ajax_loader_blue_128.gif"/>
  	</div> -->

</div>
<!-- contents E --> 
	
<jsp:include page="../common/footer.jsp" flush="false"/>