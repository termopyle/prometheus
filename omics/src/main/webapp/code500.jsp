<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false" %>
	
<jsp:include page="./WEB-INF/views/common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>

<!-- contents S-->
<div id="contents" class="subcon conwrap">
	<h2 class="hidden">ERROR</h2>
	<!-- contents 정렬 S-->
		<div class="error">
			<p class="error500">
				<strong><span class="green">The service</span> is not <span class="green">smooth</span></strong> Sorry for any inconvenience caused.
			</p>
			<p class="box1">
				I experienced an issue on the requested service.<br />
				We track these errors automatically, but if the problem persists feel free to contact us. 
				In the meantime, try refreshing. Thank you.
			</p>
			<a href="<c:url value='./'/>" class="btngreen">Go to main page</a>
		</div>
	<!-- contents 정렬 E-->
</div>
<!-- contents E --> 
	
<jsp:include page="./WEB-INF/views/common/footer.jsp" flush="false"/>