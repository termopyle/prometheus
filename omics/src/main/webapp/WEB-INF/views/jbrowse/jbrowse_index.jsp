<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
	
<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>

<!-- viewer -->
<link rel="stylesheet" type="text/css" href="./resources/css/jquery.qtip.css"/>
<link rel="stylesheet" type="text/css" href="./resources/css/viewer.css" />

<script type="text/javascript" src="./resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="./resources/js/jquery.qtip-1.0.0-rc3.min.js"></script>

<!-- subV S -->
<div class="subV"><img src="./resources/images/sub/sub1_top.png" alt="" /></div>
<!-- subV E --> 

<!-- contents S-->
<div id="contents" class="subcon conwrap" style="min-height: 600px;">
	<h2 class="hidden">메인컨텐츠</h2>
	<h3>Genome Browser.V.0.1</h3>

	<iframe id="JBrowse" name="JBrowse" width=100% height=100% style="min-height: 600px;" 
		src="./JBrowse-1.12.0/index.html?data=data/${genomicfnaLocation}/${JBrowse}/${refSeqAssemblyID}" frameborder="0">
	</iframe>
</div>
<!-- contents E -->
	
<jsp:include page="../common/footer.jsp" flush="false"/>