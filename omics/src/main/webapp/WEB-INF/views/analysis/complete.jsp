<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" %>
		
<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>

<!-- subV S -->
<div class="subV"><img src="./resources/images/sub/sub1_top.png" alt="" /></div>
<!-- subV E -->

<!-- contents S-->
<div id="contents" class="subcon conwrap">
	<h2 class="hidden">메인컨텐츠</h2>
	<h3>Analysis</h3>

	<div class="foundbox">
		<p class="finish ">
			Program running is complete.<br />
			<strong>Please check at my page.</strong>
		</p>
	</div>
	<div class="txtC mt20">
		<input type="button" class="btnblue" value="Home" onclick="location.href='./'"/> 
		<input type="button" class="btngreen" value="My Genes" onclick="location.href='my_genes'"/>
	</div>

</div>

<!-- contents E -->

<jsp:include page="../common/footer.jsp" flush="false"/>
	