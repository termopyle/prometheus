<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
	
<jsp:include page="../common/header.jsp" flush="false">
	<jsp:param name="bg_value" value="sub_bg"/>
</jsp:include>

<%
	String userId = (String) request.getSession().getAttribute("uid");
	String isAdmin = (String) request.getSession().getAttribute("is_admin");
	String isLogin = (String) request.getSession().getAttribute("is_login");
%>

<c:set value="<%=isLogin %>" var="is_login"></c:set>

<!-- Script -->
<script type="text/javascript" src="./resources/js/user.js"></script>

<!-- subV S -->
<div class="subV"><img src="./resources/images/sub/sub1_top.png" alt="" /></div>
<!-- subV E --> 

<c:if test="${is_login == true }">
	<script>
		alert("It is already signed.");
		history.back();
	</script>
</c:if>

<!-- contents S-->
<div id="contents" class="subcon conwrap">
	<h2 class="hidden">가입완료</h2>
	<h3>Register Complete</h3>
	<!-- contents 정렬 S-->    
   <script>
   
   $( document ).ready(function() {
		$("[id=texts]").text("Join is complete!");  	
		$('#setup').show();
		$('a.btn-close').bind('click', function() {
			location.href="./";
			return false;
		});
	});

   </script>
    <!-- contents 정렬 E--> 

</div>
<!-- contents E --> 
	
<jsp:include page="../common/footer.jsp" flush="false"/>