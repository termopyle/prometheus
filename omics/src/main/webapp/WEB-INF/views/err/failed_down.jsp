<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false" %>
	
<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>

<!-- contents S-->
<div id="contents" class="subcon conwrap">
	<h2 class="hidden">ERROR</h2>
	<!-- contents 정렬 S-->
		<div class="error">
			<p class="errorfile">
				<strong><span class="green">파일</span>을 <span class="green">다운로드</span>할 수 없습니다.</strong>서비스 이용에 불편을 드려 죄송합니다.
			</p>
			<p class="box1">요청한 파일을 사용할 수 없거나 찾을 수 없습니다..</p>
			<a href="<c:url value='./'/>" class="btngreen">메인으로로 가기</a>
		</div>
	<!-- contents 정렬 E-->
</div>
<!-- contents E --> 

<jsp:include page="../common/footer.jsp" flush="false"/>