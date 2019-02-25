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
	<h3>My Page</h3>

		<div class="foundbox mt30">
			<p class="passwordok">User information is updated successfully.<br />Please, login with changed password</p>
		</div>
		<div class="txtC mt20">
			<a href="<c:url value='./'/>" class="btngreen">Home</a> <a href="<c:url value='go_login'/>" class="btnsky">Login</a>
		</div>

</div>

<!-- contents E -->

<jsp:include page="../common/footer.jsp" flush="false"/>