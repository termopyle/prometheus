<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String uid = (String)request.getSession().getAttribute ("uid");

/* 	if( uid == null ) {
		pageContext.forward( "../index.jsp" );
	} */
%>

<c:if test="${uid == null}">
	<script type="text/javascript">
		alert('잘못된 접근입니다. 로그인 후 이용하세요');
		location.href='./goLogin';
	</script>
</c:if>