<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="../common/header.jsp">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>
<script src="resources/js/user.js"></script>

<!-- contents S -->
<div id="contents" class="subcon conwrap">

	<div class="specialcheck">
		<strong>You access has been restricted because you have made a suspicious login attempt.</strong>	
		<span>We apologize for any inconvenience you may have experienced. </span>
		<p>Your request has not been properly processed.<br />We will do our best to provide you with more accurate and convenient services. Thank you.</p>
		<div class="checkbtn">
			<a href="javascript:history.back()" class="check01">Back</a>
			<a href="<c:url value='goFindId'/>" class="check02">Find ID/Password</a>
		</div>
	</div>
			
</div>
<!-- contents E -->

<jsp:include page="../common/footer.jsp"></jsp:include>